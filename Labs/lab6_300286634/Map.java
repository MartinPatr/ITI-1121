public interface Map< K, V> {

    public V get(K key); //Returns the rightmost value associated to the key specified in the parameter.
    public boolean contains(K key);  //Returns true if there is an association for the key specified in the parameter
    public void put(K key, V value);  //Creates a new key-value association
    public void replace(K key, V value);  //Replaces the value of the rightmost occurrence of the association for the specified key
    public V remove(K key); //Removes the rightmost occurrence of the specified key and returns the value that was associated to this key

}