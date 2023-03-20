
public class DynamicArrayStack<E> implements Stack<E> {

    // Instance variables

    private E[] elems;  // Used to store the elements of this ArrayStack
    private int top;    // Designates the first free cell
    private static final int DEFAULT_INC = 25;   //Used to store default increment / decrement
    private int totalCapacity,capacityInc = 1;


    @SuppressWarnings( "unchecked" )

    // Constructor
    public DynamicArrayStack( int capacity ) {
        if(capacity<DEFAULT_INC){
            elems = (E[]) new Object[ DEFAULT_INC ];
            totalCapacity = DEFAULT_INC;
        }else{
            elems = (E[]) new Object[ capacity ];
            totalCapacity = capacity;

        }
        this.top = 0;

    }


    public void setTop(int top) {
        this.top = top;
    }

    // Gets current capacity of the array
    public int getCapacity() {
        return elems.length;
    }

    // Returns true if this DynamicArrayStack is empty
    public boolean isEmpty() {
        return ( top == 0 );
    }

    // Returns the top element of this ArrayStack without removing it
    public E peek() {
        return elems[ top-1 ];
    }

    @SuppressWarnings( "unchecked" )

    // Removes and returns the top element of this stack
    public E pop() {
        E saved = elems[ --top ];
        elems[ top ] = null; // scrub the memory!
        if (top < (getCapacity() - DEFAULT_INC * (capacityInc-1))){
            System.out.println("Succedeed");
            E[] tempArray = (E[]) new Object[getCapacity() - DEFAULT_INC * (--capacityInc)];
            for (int i = 0; i < (top+1); i++){
                System.out.println("i:" + i);
                tempArray[i] = elems[i];
            }
            elems = tempArray;
            System.out.println("Created new Array with capacity: " + (totalCapacity - DEFAULT_INC * capacityInc) + " The top is currently: " + top);   
            capacityInc--;
        }
        return saved;

    }

    @SuppressWarnings( "unchecked" )

    // Puts the element onto the top of this stack.
    public void push( E element ) {
        if ((totalCapacity + DEFAULT_INC * (capacityInc-1))  == top){
            E[] tempArray = (E[]) new Object[totalCapacity + DEFAULT_INC * capacityInc];
        for (int i = 0; i < top; i++){
            tempArray[i] = elems[i];
        }
        elems = tempArray;
        System.out.println("Created new Array with capacity: " + (totalCapacity + DEFAULT_INC * capacityInc) + " The top is currently: " + top);   
        capacityInc++;
        elems[top++] = element;

    }
        else{
            elems[top++] = element;
        }   
    }

    @SuppressWarnings( "unchecked" )

    public void clear() {
        elems = (E[]) new Object[ totalCapacity ];
        top = 0;
    }

}