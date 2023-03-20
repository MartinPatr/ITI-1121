public class NotEnoughMoneyException extends IllegalStateException {

    double balance;
    double amount;

    public NotEnoughMoneyException(double balance, double amount){
        super("you have not enought money to witdraw " + amount + "$");
        this.balance = balance;
        this.amount = amount;
    }

    public double getAmount(){
        return this.amount;
    }
    public double getBalance(){
        return this.balance;
    }
    public double getMissingAmount(){
        return this.amount - this.balance;
    }





}
