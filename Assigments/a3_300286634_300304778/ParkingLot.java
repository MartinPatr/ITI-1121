public class ParkingLot {

	// IMPORTANT: You are *discouraged* from defining any new instance variables in
	// ParkingLot. You are expected to provide a list-based implementation of
	// ParkingLot. Defining new instance variables can take you away from this
	// implementation goal and thus result in the loss of marks.
	/**
	 * List for storing occupancy information in the lot
	 */
	private List<Spot> occupancy;

	/**
	 * The maximum number of cars that the lot can accommodate
	 */
	private int capacity;

	/**
	 * Constructs a parking lot with a given (maximum) capacity
	 * 
	 * @param capacity is the (maximum) capacity of the lot
	 * @throws IllegalArgumentException if capacity is non-positive
	 */
	public ParkingLot(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity must be positive");
		}

		this.capacity = capacity;
		this.occupancy = new SinglyLinkedList<Spot>();
	}

	/**
	 * Parks a car (c) in the parking lot.
	 * 
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 * @throws RuntimeException if the parking lot is full
	 */
	public void park(Car c, int timestamp) {
		if (getOccupancy() >= capacity) {
			throw new RuntimeException("The Parking lot is full");
		}
		if (c == null || timestamp < 0) {
			throw new IllegalArgumentException("Car cannot be null");
		}
		Spot newSpot = new Spot(c, timestamp);
		occupancy.add(newSpot);
	}

	/**
	 * Removes the car (spot) parked at list index i in the parking lot
	 * 
	 * @param i is the index of the car to be removed
	 * @return the car (spot) that has been removed
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public Spot remove(int i) {
		if (i < 0) {
			throw new IndexOutOfBoundsException("Index is less than 0");
		}
		if (i >= occupancy.size()) {
			throw new IndexOutOfBoundsException("Index is greater than the size of the list");
		}
		return occupancy.remove(i);
	}

	/**
	 * Attempts to park a car in the parking lot.
	 * 
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car attempts to park
	 * @return true if the car was successfully parked, false otherwise
	 */
	public boolean attemptParking(Car c, int timestamp) {
		if (getOccupancy() >= capacity) {
			return false;
		}
		park(c, timestamp);
		return true;
	}

	/**
	 * @return the capacity of the parking lot
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at a given position (i, j)
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public Spot getSpotAt(int i) {
		if (i < 0 || i >= occupancy.size()) {
			throw new IndexOutOfBoundsException("index is less than 0 or greater than the size of the list");
		}
		return occupancy.get(i);
	}

	/**
	 * @return the total number of cars parked in the lot
	 */
	public int getOccupancy() {
		return occupancy.size();
	}

	/**
	 * @return String representation of the parking lot
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Total capacity: " + this.capacity + System.lineSeparator());
		buffer.append("Total occupancy: " + this.occupancy.size() + System.lineSeparator());
		buffer.append("Cars parked in the lot: " + this.occupancy + System.lineSeparator());

		return buffer.toString();
	}
}