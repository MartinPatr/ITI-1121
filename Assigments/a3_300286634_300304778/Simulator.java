/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Length of car plate numbers
	 */
	public static final int PLATE_NUM_LENGTH = 3;

	/**
	 * Number of seconds in one hour
	 */
	public static final int NUM_SECONDS_IN_1H = 3600;

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * NUM_SECONDS_IN_1H;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * NUM_SECONDS_IN_1H;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
			MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second
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

		// Exception Handling
		if (lot == null) {
            throw new IllegalArgumentException("The ParkingLot cannot be null.");
        }
        if (perHourArrivalRate <= 0) {
            throw new IllegalArgumentException("The hourly rate of car arrival must be larger than zero.");
        }
        if (steps <= 0) {
            throw new IllegalArgumentException("The total number of simulation steps must be larger than zero.");
        }


		//Constructor Variables
		this.lot = lot;

		this.steps = steps;

		this.clock = 0;
				
		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, NUM_SECONDS_IN_1H);

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
		Spot newSpot = null;

		int duration;
		// 0. Loop for the number of steps specified by the steps instance variable
		while (clock < steps) {
			// Check if a car arrives at the current timestep
			if (RandomGenerator.eventOccurred(probabilityOfArrivalPerSec)) {
				// 1. Create a new car
				newCar = new Car(RandomGenerator.generateRandomString(PLATE_NUM_LENGTH));
				// 2. Create a new spot
				newSpot = new Spot(newCar, clock);
				// 3. Add the spot to the incoming queue
				incomingQueue.enqueue(newSpot);
			}
			// 4. If the incoming queue is not empty, try to park the car at the front of the queue
			if (!incomingQueue.isEmpty()) {
				frontCar = incomingQueue.peek().getCar();
				// 5. If the car can be parked, remove the spot from the incoming queue and add it to the parking lot
				if (lot.attemptParking(frontCar ,clock)) {
					incomingQueue.dequeue();	
				}
			}
			// 6. If the parking lot is not empty, try to remove a car from the parking lot
			for (int i = 0;i < lot.getOccupancy();i++){
				if (lot.getSpotAt(i) != null){
					// 7. Calculate the duration of the car's stay in the lot
					duration = clock - lot.getSpotAt(i).getTimestamp();
					// 8. Check if the car is ready to leave, if it is then remove it from the parking lot and add it to the outgoing queue
					if (RandomGenerator.eventOccurred(departurePDF.pdf(duration)) || duration >= MAX_PARKING_DURATION) {
						outgoingQueue.enqueue(lot.getSpotAt(i));
						lot.remove(i);
						}						
					}
			// 9. If the outgoing queue is not empty, remove the car from the outgoing queue
			if (!outgoingQueue.isEmpty()) {
				outgoingQueue.dequeue();
			}
			}
			// 10. Increment the clock by one second
			clock++;
		}
	}
	public int getIncomingQueueSize() {
		return incomingQueue.size();	
	}
}
