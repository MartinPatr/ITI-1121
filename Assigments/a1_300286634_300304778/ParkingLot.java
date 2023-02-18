import java.io.File;
import java.util.Scanner;

/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Car[][] occupancy;

	/**
	 * Instance variable for storing the number of cars that were parked
	 */
	private int numParked;


	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */

	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// determine numRows and numSpotsPerRow; 
		calculateLotDimensions(strFilename);

		// populate lotDesign and occupancy; 
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		occupancy[i][j]=c;
		numParked++;
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
		if (i>occupancy.length){
			return null;
		}
		if (j>occupancy[0].length){
			return null;
		}
		if (occupancy[i][j]==null){
			return null;
		}
		else{
			occupancy[i][j]=null;
			return occupancy[i][j];
		}

	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		
	if (i>=occupancy.length){
		return false;
	}
	if (j>occupancy[0].length){
		return false;
	}
		if (occupancy[i][j]!=null){
			return false;
		}
		if (c.getType()==CarType.ELECTRIC){
			if (lotDesign[i][j]==CarType.ELECTRIC||lotDesign[i][j]==CarType.SMALL||lotDesign[i][j]==CarType.REGULAR||lotDesign[i][j]==CarType.LARGE){
				return true;
			}
		}
		if (c.getType()==CarType.SMALL){
			if (lotDesign[i][j]==CarType.SMALL||lotDesign[i][j]==CarType.REGULAR||lotDesign[i][j]==CarType.LARGE){
				return true;
			}
		}
		if (c.getType()==CarType.REGULAR){
			if (lotDesign[i][j]==CarType.REGULAR||lotDesign[i][j]==CarType.LARGE){
				return true;
			}
		}
		if (c.getType()==CarType.LARGE){
			if (lotDesign[i][j]==CarType.LARGE){
				return true;
			}
		}
		return false;

	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		int total=0;
		for (int i=0;i<lotDesign.length;i++){
			for (int j =0;j<lotDesign[0].length;j++){
				if (lotDesign[i][j]==CarType.ELECTRIC){
					total=total+1;
				}
				if  (lotDesign[i][j]==CarType.LARGE){
					total=total+1;
				}
				if (lotDesign[i][j]==CarType.SMALL){
					total=total+1;
				}
				if (lotDesign[i][j]==CarType.REGULAR){
					total=total+1;
				}
			}
			
		}
		return total; 
	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		return numParked;
	}

	//Method used
	private void calculateLotDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));
		
		/*A group of code to set the number of spots per row */
		if (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] spots = line.split(",");
			numSpotsPerRow = spots.length;
		  }

		
		/*While loop finding the amount of rows */
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine().trim();
			if (str.equals(SECTIONER)){
				numRows++;
				break;
			}
			//This is checking to make sure a line inbetween is not empty
			if (str.isEmpty()){
				numRows--;
			}
			numRows++;
			
        }
		scanner.close();
	}
	/**
	 * Method that populates the two arrays lotDesign and occupancy
	 * @param strFilename is the file inserted
	 */
	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Car[numRows][numSpotsPerRow];
		//boolean variable stating if line is empty
		boolean emptyCheck = false;


		// for loop for reading and populating the lot design 
		for (int k = 0; k < numRows; k++) {
			emptyCheck = false;
			String str = scanner.nextLine().trim();
			str = str.replaceAll("\\s", "");
			if (str.isEmpty()){
				emptyCheck = true;
				k--;
			}
			if (emptyCheck == false){
				String[] tempArray = str.split(SEPARATOR);
				for (int i = 0; i < numSpotsPerRow;i++){
					lotDesign[k][i] = Util.getCarTypeByLabel(tempArray[i]);
				}
			}
		}

		//For loop for populating each block with null in occupancy
		for (int k = 0; k < numRows; k++) {
			for (int i = 0; i < numSpotsPerRow;i++){
				occupancy[k][i] = null;
			}
		}
		
		//While loop to continue going down text file until it hits SECTIONER
		while(true){
			String str = scanner.nextLine().trim();
			if (str.equals(SECTIONER)){
				break;
			}
		}
		// while loop for reading occupancy data
		while (scanner.hasNextLine()) {
			String str = scanner.nextLine().trim();
			str = str.replaceAll("\\s", "");
			if (!str.isEmpty()){
				String[] tempArray = str.split(SEPARATOR);
				
				int x = Integer.parseInt(tempArray[0]);
				int y = Integer.parseInt(tempArray[1]);

				Car temp = new Car(Util.getCarTypeByLabel(tempArray[2]),tempArray[3]);
				if (canParkAt(x, y,temp)){
					park(x, y, temp);
				}
				else{
					System.out.println("Car " + tempArray[2] + "(" + tempArray[3] +  ")" +  " cannot be parked at (" + tempArray[0] + "," + tempArray[1] + ")");
				}
				}
		}

		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 * 
	 * @return String containing the parking lot information
	 */
	public String toString() {
		// NOTE: The implementation of this method is complete. You do NOT need to
		// change it for the assignment.
		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}

		}
		return buffer.toString();
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);
		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);

		scanner.close();
	}
}