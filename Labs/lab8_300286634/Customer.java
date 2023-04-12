import java.util.Random;

public class Customer {
    int arrivalTime;
    int initialNumberOfItems;
    int numberOfItems;

    public Customer(int arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.initialNumberOfItems = new Random().nextInt(10) + 1;
        this.numberOfItems = this.initialNumberOfItems;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getNumberOfServedItems() {
        return initialNumberOfItems - numberOfItems;
    }

    public void serve() {
        numberOfItems--;
    }
}
