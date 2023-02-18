public class Combination {
    int first,second,third;

    public Combination( int first, int second, int third ) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    // Check if it the combination is the same
    public boolean equals( Combination other ) {
        if (this.first == other.first && this.second == other.second  && this.third == other.third){
            return true;
        }
        else{ 
            return false;
        }
    }

    // Returns a String representation of this Combination.
    public String toString() {
        String str1 = Integer.toString(first);
        String str2 = Integer.toString(second);
        String str3 = Integer.toString(third);
        return str1 + ':' + str2 + ":" + str3;
    }
    
}