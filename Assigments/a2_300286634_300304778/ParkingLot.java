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
	private Spot[][] occupancy;

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


	public int getNumRows() {
		return numRows;
	}

	public int getNumSpotsPerRow() {
		return numSpotsPerRow;
	}



	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c, int timestamp) {
		Spot s = new Spot(c, timestamp);
		occupancy[i][j]=s;
	}
	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Spot remove(int i, int j) {
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
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at position (i, j)
	 */
	public Spot getSpotAt(int i, int j) {
		return occupancy[i][j];
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

		if (i >= numRows || j >= numSpotsPerRow) {
			return false;
		}

		if (occupancy[i][j] != null) {
			return false;
		}

		CarType carType = c.getType();
		CarType spotType = lotDesign[i][j];

		if (carType == CarType.ELECTRIC) {
			return (spotType == CarType.ELECTRIC) || (spotType == CarType.SMALL) || (spotType == CarType.REGULAR)
					|| (spotType == CarType.LARGE);

		} else if (carType == CarType.SMALL) {
			return (spotType == CarType.SMALL) || (spotType == CarType.REGULAR) || (spotType == CarType.LARGE);

		} else if (carType == CarType.REGULAR) {
			return (spotType == CarType.REGULAR) || (spotType == CarType.LARGE);
		} else if (carType == CarType.LARGE) {
			return (spotType == CarType.LARGE);
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
	 * @return the occupancy of the parking lot (i.e., the two-dimensional array)
	 */
	public Spot getOccupancy(int i, int j) {
		return occupancy[i][j];
	}


	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		int totalOccupancy=0;
		for (int i=0;i<numRows;i++){
			for (int j =0;j<numSpotsPerRow;j++){
				if (occupancy[i][j]!=null){
					totalOccupancy++;
				}
			}
		}
		return totalOccupancy;
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
			//This is checking to make sure a line inbetween is not empty
			if (str.isEmpty()){
				numRows--;
			}
			numRows++;
			
        }
		numRows++;
		scanner.close();
	}
	/**
	 * Method that populates the two arrays lotDesign and occupancy
	 * @param strFilename is the file inserted
	 */
	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Spot[numRows][numSpotsPerRow];
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
		scanner.close();
	}

	/**
	 * Method that parks a car if it can be parked in the parking lot
	 * @param strFilename is the file inserted
	 * @param timestamp is the time the car is parked
	 */	
	public boolean attemptParking(Car c, int timestamp) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numSpotsPerRow; j++) {
				if (canParkAt(i, j, c)) {
					park(i, j, c,timestamp);
					return true;
				}
			}
		}
		return false;
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