
public class Cashier  {
    Queue<Customer> queue;
    Customer currentCustomer;
    int totalCustomerWaitTime;
    int customerServed;
    int totalItemsServed;

    public Cashier() {
        queue = new ArrayQueue<Customer>();
        totalCustomerWaitTime = 0;
        customerServed = 0;
        totalItemsServed = 0;
    }

    public void addCustomer(Customer customer) {
        queue.enqueue(customer);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public int getTotalCustomerWaitTime() {
        return totalCustomerWaitTime;
    }

    public int getTotalCustomersServed() {
        return customerServed;
    }

    public int getTotalItemsServed() {
        return totalItemsServed;
    }

    public void serveCustomers(int currentTime) {
        if (currentCustomer == null) {
            if (queue.isEmpty()) {
                return;
            }
            currentCustomer = queue.dequeue();
        }
        currentCustomer.serve();
        totalItemsServed++;
        if (currentCustomer.getNumberOfItems() == 0) {
            totalCustomerWaitTime += currentTime - currentCustomer.getArrivalTime();
            customerServed++;
            currentCustomer = null;
        }
    }
}