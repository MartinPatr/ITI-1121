public class Account {
    double balance;
    
    public Account(){
        this.balance = 0;
    }

    public double getBalance(){
        return this.balance;
    }

    public double deposit(double amountAdded){
         double newAmount = this.balance += amountAdded;
         System.out.println("new balance =" + newAmount + "$");
         return newAmount;
    } 
    
    public double withdraw(double amountRemoved) throws NotEnoughMoneyException{
        if (getBalance() < amountRemoved){
            throw new NotEnoughMoneyException(getBalance(),amountRemoved);
        }
        double newAmount = this.balance -= amountRemoved;
        System.out.println("new balance =" + newAmount + "$");
        return newAmount;
    } 


}


