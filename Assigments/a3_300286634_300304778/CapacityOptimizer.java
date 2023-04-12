public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
		// Setting Variables
		int n = 1;		
		long startTime;
		long endTime;

		// Keep the simulations running until we find a minumum size
		while(true){
			System.out.println("==== " + "Setting lot capacity to: " + n + "=====");
			double avgQueueLength = 0.0d;
			// Run the simulation 10 times at a certian amount of parking size
			for (int i = 0;i<NUM_RUNS;i++){
				ParkingLot lot = new ParkingLot(n);
				Simulator sim = new Simulator(lot, hourlyRate, Simulator.SIMULATION_DURATION);
				
				
				startTime = System.currentTimeMillis();
				sim.simulate();
				endTime = System.currentTimeMillis();
				

				avgQueueLength += sim.getIncomingQueueSize();
				System.out.println("Simulation run " + (i+1) + " (" + (endTime-startTime) + "ms);" + " Queue length at the end of the simulation run: " + sim.getIncomingQueueSize());
			}
			// Check if the average queue length of 10 simulation as size n is less than 5
			if ((avgQueueLength/NUM_RUNS) <= THRESHOLD){
				return n;
			}
			System.out.println("");
			n++;

		}
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}