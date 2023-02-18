package Labs.lab3_300286634;
public class Rational {

    private int numerator;
    private int denominator;

    // constructors

    public Rational(int numerator) {
	     this.numerator = numerator;
       this.denominator = 1;
       reduce();
    }

    public Rational(int numerator, int denominator) {
	     this.numerator = numerator;
       this.denominator = denominator;
       reduce();
    }

    // getters

    public int getNumerator() {
	     return numerator;
    }

    public int getDenominator() {
      return denominator;
    }

    // instance methods

    public Rational plus(Rational other) {
      int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
      int newDenominator = this.denominator * other.denominator;
      return new Rational(newNumerator, newDenominator);
    }
  

    public static Rational plus(Rational a, Rational b) {
    	int sum = (a.numerator * b.denominator) + (b.numerator * a.denominator);
      int den = (a.denominator*b.denominator);
      return new Rational(sum,den);
    }

    // Transforms this number into its reduced form

    private void reduce() {
      int greatest = gcd(this.numerator,this.denominator);
      this.numerator = this.numerator/(greatest);
      this.denominator = this.denominator/(greatest);
    }

    // Euclid's algorithm for calculating the greatest common divisor
    private int gcd(int a, int b) {
      // Note that the loop below, as-is, will time out on negative inputs.
      // The gcd should always be a positive number.
      // Add code here to pre-process the inputs so this doesn't happen.
      a = Math.abs(a); b = Math.abs(b);
    	while (a != b)
    	    if (a > b)
    		     a = a - b;
    	    else
    		     b = b - a;
    	return a;
    }

    public int compareTo(Rational other) {
      int num1 = this.numerator * other.denominator; 
      int num2 = other.numerator * this.denominator;
      int sum2 = num1-num2;
      return sum2;
      
    }

    public boolean equals(Rational other) {
      return (this.numerator/this.denominator) == (other.numerator/other.denominator);
    }

    public String toString() {
    	String result;
    	if (denominator == 1) {
    	    result = "" + this.numerator;
    	} else {
    	    result = "" + this.numerator + '/' + this.denominator;
    	}
    	return result;
    }

}