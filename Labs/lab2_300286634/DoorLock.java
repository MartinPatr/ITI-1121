public class DoorLock {
    // Constant.
    public static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    // Instance variables.
    private Combination combination;
    private boolean open;
    private boolean activated;
    private int numberOfAttempts;

    // Constructor.
    public DoorLock( Combination combination ) {
        this.combination = combination;
        open = false;
        activated = true;
        numberOfAttempts = 0;
    }

    // Access methods.
    public boolean isOpen() {
        return open;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean open( Combination combination ) {
        if (this.combination.equals(combination) == true && activated == true){
            open = true;
            numberOfAttempts = 0;
        } else {
            numberOfAttempts++;
        } if (numberOfAttempts == MAX_NUMBER_OF_ATTEMPTS){
            activated = false;
        }
        return true;
    }

    public void activate( Combination c ) {
        if (this.combination.equals(c) == true) {
            activated = true; 
        }
    }

}