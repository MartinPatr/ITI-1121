public class Q3_ArrayInsertionDemo{

	public static int[] insertIntoArray(int[] beforeArray, int indexToInsert, int valueToInsert){
		int n = beforeArray.length;
        int[] newArray = new int[n +1];
        int j = 0;
        for(int i = 0; i < n+1;i++){
            if (i == indexToInsert){
                newArray[i] = valueToInsert;
                j = 1;
            }
            else{
                newArray[i] = beforeArray[i-j];
            }
        }
        return newArray;

	}

	public static void main(String[] args){
		int[] anArray;
		anArray = new int[]{1,5,4,7,9,6};
        int[] arrayToPrint = insertIntoArray(anArray,3,15);
        for(int i = 0; i < arrayToPrint.length;i++){
            System.out.println(arrayToPrint[i]);
        }
	}
}
