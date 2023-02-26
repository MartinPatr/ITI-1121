/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * 3600;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * 3600;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
			MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 * This probability is calculated in the constructor based on the perHourArrivalRate
	 * passed to the constructor.
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second.
	 */
	private int clock;

	/**
	 * Total number of steps (simulated seconds) that the simulation should run for.
	 * This value is fixed at the start of the simulation. The simulation loop
	 * should be executed for as long as clock < steps. When clock == steps, the
	 * simulation is finished.
	 */
	private int steps;

	/**
	 * Instance of the parking lot being simulated.
	 */
	private ParkingLot lot;

	/**
	 * Queue for the cars wanting to enter the parking lot
	 */
	private Queue<Spot> incomingQueue;

	/**
	 * Queue for the cars wanting to leave the parking lot
	 */
	private Queue<Spot> outgoingQueue;

	/**
	 * @param lot                 is the parking lot to be simulated
	 * @param perHourArrivalRate  is the HOURLY rate at which cars show up in front of the lot
	 * @param steps               is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) {

		//Constructor Variables
		this.lot = lot;

		this.steps = steps;

		this.clock = 0;
				
		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, 3600);

		incomingQueue = new LinkedQueue<>();
		outgoingQueue = new LinkedQueue<>();


	}

	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable.
	 * In this method, you will implement the algorithm shown in Figure 3 of the A2 description.
	 */
	public void simulate() {
	
		//Local variables
		this.steps = SIMULATION_DURATION;

		this.clock = 0;

		Car newCar = null, frontCar= null;
		Spot newSpot = null , frontSpot = null;

		int duration;

		boolean tryingPark = false;
		
		// 0. Loop for the number of steps specified by the steps instance variable
		while (clock < steps) {
			if (RandomGenerator.eventOccurred(probabilityOfArrivalPerSec)) {
				// 1. Create a new car
				newCar = RandomGenerator.generateRandomCar();
				// 2. Create a new spot
				newSpot = new Spot(newCar, clock);
				// 3. Add the spot to the incoming queue
				incomingQueue.enqueue(newSpot);
			}
			// 4. If the incoming queue is not empty, try to park the car at the front of the queue
			if (!incomingQueue.isEmpty()) {
				// 5. If the car can be parked, remove the spot from the incoming queue and add it to the parking lot
				// The car is dequeued and is stored in a variable and no other car is dequeued until it finds  spot 
				if (tryingPark == false){
					frontSpot = incomingQueue.dequeue();
					tryingPark = true;
				}
				frontCar = frontSpot.getCar();
				if (lot.attemptParking(frontCar ,clock)) {
					System.out.println(frontCar + " ENTERED at timestep " + clock + "; occupancy is at " +
					lot.getTotalOccupancy());
					tryingPark = false;					
				}
			}
			// 6. If the parking lot is not empty, try to remove a car from the parking lot
			for (int i = 0;i < lot.getNumRows();i++) {
				for (int j = 0;j < lot.getNumSpotsPerRow();j++) {
					if (!(lot.getSpotAt(i,j) == null)) {
						// 7. Calculate the duration of the car's stay in the lot
						duration = clock - lot.getSpotAt(i,j).getTimestamp();
						// 8. Check if the car is ready to leave, if it is then remove it from the parking lot and add it to the outgoing queue
						if (RandomGenerator.eventOccurred(departurePDF.pdf(duration)) || duration >= MAX_PARKING_DURATION) {
							outgoingQueue.enqueue(lot.getSpotAt(i,j));
							System.out.println(lot.getSpotAt(i, j).getCar() + " EXITED at timestep " + clock + "; occupancy is at " + lot.getTotalOccupancy());							
							lot.remove(i,j);
						}
					}
				}
			}
			// 9. If the outgoing queue is not empty, remove the car from the outgoing queue
			if (!outgoingQueue.isEmpty()) {
				outgoingQueue.dequeue();
			}
			clock++;
		}
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the parking-lot design. Next, it simulates the parking lot
	 * for a number of steps (this number is specified by the steps parameter). At
	 * the end, the method prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();
		
		if (args.length < 2) {
			System.out.println("Usage: java Simulator <lot-design filename> <hourly rate of arrival>");
			System.out.println("Example: java Simulator parking.inf 11");
			return;
		}
		
		
		if (!args[1].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}
		
		ParkingLot lot = new ParkingLot(args[0]);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		Simulator sim = new Simulator(lot, Integer.parseInt(args[1]), SIMULATION_DURATION);

		long start, end;

		System.out.println("=== SIMULATION START ===");
		start = System.currentTimeMillis();
		sim.simulate();
		end = System.currentTimeMillis();
		System.out.println("=== SIMULATION END ===");

		System.out.println();

		System.out.println("Simulation took " + (end - start) + "ms.");

		System.out.println();

		int count = 0;;

		// The Queue interface does not provide a method to get the size of the queue.
		// We thus have to dequeue all the elements to count how many elements the queue has!
		
		while (!sim.incomingQueue.isEmpty()) {
			sim.incomingQueue.dequeue();
			count++;
		}

		System.out.println("Length of car queue at the front at the end of simulation: " + count);

	}
}
