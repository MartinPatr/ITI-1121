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
        if (count == elems.length) {
            increaseCapacity();
        }
        elems[(getCapacity()- ++count)] = new Pair(key, value);
    }

    private void increaseCapacity() {
        System.out.println("Increased Capacity");
        Pair[] temp = new Pair[getCapacity() + INCREMENT];
        for (int i = (getCount()-1); i >= 0; i--){
            temp[i] = elems[i];
        }
        elems = temp;
    }

    @Override
    public boolean contains(String key) {
        System.out.println("getCount: " + getCount());
        for (int i = (getCapacity()-getCount()); i < getCapacity(); i++){
            if (key.equals(elems[i].getKey())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer get(String key) {
        System.out.println("getCount: " + getCount());
        for (int i = (getCapacity()-getCount()); i < getCapacity(); i++){
            System.out.println("i: " + i);
            System.out.println(elems[i].getKey());
            if (key.equals(elems[i].getKey())){
                return elems[i].getValue();
            }
        }
        return null;
    }

    @Override
    public void replace(String key, Integer value) {
        if (contains(key)){
            for (int i = (getCapacity()-getCount()); i < getCapacity(); i++){
                if (key.equals(elems[i].getKey())){
                    elems[i].setValue(value);
                }
            }
        }
    }

    @Override
    public Integer remove(String key) {
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