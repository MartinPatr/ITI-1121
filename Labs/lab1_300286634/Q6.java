import java.util.Scanner;
public class Q6{
	public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        double[] grades;
		grades = new double[n];
        for (int i = 0; i < n;i++){
            double myDouble = sc.nextDouble();
            grades[i] = myDouble;
        }
        sc.close();
        System.out.println("Average: " + calculateAverage(grades));
        System.out.println("Median: " + calculateMedian(grades));
        System.out.println("Number of students failed: " + calculateNumberFailed(grades));
        System.out.println("Number of students passed: " + calculateNumberPassed(grades));
	}

	public static double calculateAverage(double[] notes){
		double average;
        average = 0;
        for(int i = 0; i < notes.length; i++){
			average += notes[i];
		}
        average = average/(notes.length);	
		return average;
	}
    
	public static double calculateMedian(double[] notes){
        double temp;
        for(int i = 0;i < notes.length;i++){
            for(int j = 0;j < notes.length;j++){
                if (notes[j] < notes[i]){
                    temp = notes[i];
                    notes[i] = notes[j];
                    notes[j] = temp;
                }
            }
        }
        double median;
        int position; position = notes.length/2;
        if (notes.length%2 == 0){
            median = notes[position];
        }
        else{
            median = (notes[position] + notes[position])/2;
        }
        return median;
	} 
    

	public static int calculateNumberFailed(double[] notes){
        int numberFailed;
        numberFailed = 0;
        for (int i = 0;i < notes.length;i++){
            if (notes[i] < 50){
                numberFailed++;
            }
        }
        return numberFailed;
    }

	public static int calculateNumberPassed(double[] notes){
        int numberPassed;
        numberPassed = 0;
        for (int i = 0;i < notes.length;i++){
            if (notes[i] >= 50){
                numberPassed++;
            }
        }
        return numberPassed;
    }
	}

