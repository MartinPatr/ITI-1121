public class Dictionary implements Map<String, Integer> {

    private final static int INITIAL_CAPACITY = 10;
    private final static int INCREMENT = 5;
    private int count;

    private Pair[] elems;

    public int getCount() {
      return count;
    }

    public int getCapacity() {
      return elems.length;
    }

    public Dictionary() {
        elems = new Pair[INITIAL_CAPACITY];
        count = 0;
    }

    @Override
    public void put(String key, Integer value) {
        if (key == null || value == null){
            throw new NullPointerException("Error: Key or value is null");
        }
        if (count == elems.length) {
            increaseCapacity();
        }
        elems[(getCapacity()- ++count)] = new Pair(key, value);
    }

    private void increaseCapacity() {
        Pair[] temp = new Pair[getCapacity() + INCREMENT];
        for (int i = (getCount()-1); i >= 0; i--){
            temp[i] = elems[i];
        }
        
        elems = temp;
        count = getCount();
    }

    @Override
    public boolean contains(String key) {
        if (key == null){
            throw new NullPointerException("Throwing null pointer exception");
        }
        for (int i = (getCapacity()-getCount()); i < getCapacity(); i++){
            if (elems[i] != null && key.equals(elems[i].getKey())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer get(String key) {
        int temp = 1;
        if (key == null){
            throw new NullPointerException("Throwing null pointer exception");
        }
        if (getCapacity() == getCount()){
            temp = 0;
        }
        for (int i = (getCapacity()-(getCount()+temp)); i < getCapacity(); i++){

            if (elems[i] != null && key.equals(elems[i].getKey())){
                return elems[i].getValue();
            }
        }
        throw new java.util.NoSuchElementException("Throwing no such element exception");
    }

    @Override
    public void replace(String key, Integer value) {
        if (key == null || value == null){
            throw new NullPointerException("Throwing null pointer exception");
        }
        if (contains(key)){
            for (int i = (getCapacity()-getCount()); i < getCapacity(); i++){
                if (key.equals(elems[i].getKey())){
                    elems[i].setValue(value);
                }
            }
        }
        else{
            throw new java.util.NoSuchElementException("Throwing no such element exception");
        }
    }

    @Override
    public Integer remove(String key) {
        if (key == null){
            throw new NullPointerException("Throwing null pointer exception");
        }
        if (contains(key)){
            for (int i = (getCapacity()-getCount()); i < getCapacity(); i++){
                if (key.equals(elems[i].getKey())){
                     Integer saved = elems[i].getValue();
                     elems[i] = null;
                     count--;
                     return saved;
                }
            }
        }
        else{
            throw new java.util.NoSuchElementException("Throwing no such element exception");
        }
        return null;
    }

    @Override
    public String toString() {
      String res;
      res = "Dictionary: {elems = [";
      for (int i = count-1; i >= 0 ; i--) {
          res += elems[i];
          if(i > 0) {
              res += ", ";
          }
      }
      return res +"]}";
    }

}