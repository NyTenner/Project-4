package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Nye Tenerelli
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {

    AssociativeArray<K, V> temp = new AssociativeArray<>();
    for (int i = 0; i < this.pairs.length; i++) {
        if (temp.size < this.size){
            temp.expand();
        }
        if (this.pairs[i] == null) {
            temp.pairs[i] = null;
        }else {
        temp.pairs[i] = this.pairs[i].clone();
        temp.size++;
        }
    }
    return temp; // STUB
  } // clone()

  /**public class AssociativeArray {
    
}

   * Convert the array to a string.
   */
  public String toString() {
    System.out.print("{");
    for (int i = 0; i < this.pairs.length; i++) {
      if (this.pairs[i] == null) {
        return"}";
      }
      System.out.print(this.pairs[i].value + ", ");
    }
    return "}"; // STUB
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.this.pairs[size-1] = null;
   */
  public void set(K key, V value) throws NullKeyException {
    if (size == 0)  {
        this.pairs[0] = new KVPair<>(key, value);
        this.size++;
    }
    else {
      for (int i = 0; i < this.pairs.length; i++) {
        if (null == this.pairs[i]){
          this.pairs[i] = new KVPair<>(key, value);
          size++;
          i = this.pairs.length;
        }else if ((i == this.pairs.length-1) && !key.equals(this.pairs[i].key))  {
          expand();
          this.pairs[i] = new KVPair<>(key, value);
          size++;
          i = this.pairs.length;
        }else if (key.equals(this.pairs[i].key)){
          this.pairs[i].value = value;
          i = this.pairs.length;
        }
      }
    }
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key is null or does not 
   *                              appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    try{
      for (int i = 0; i < this.pairs.length; i++) {
        if (this.pairs[i] == null) {
          throw new KeyNotFoundException();
        } else if (key.equals(this.pairs[i].key)){
          if (this.pairs[i].value == null) {
            throw new KeyNotFoundException();
          }
          return this.pairs[i].value;
        }
      } // find(K)
    } catch (Exception e) {
      throw new KeyNotFoundException();
    }
    return null;
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key.
   */
  public boolean hasKey(K key) {
    try {
      find(key);
      return true;
    } catch (KeyNotFoundException e) {
      return false;
    }
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key) {
    for (int i = 0; i < this.pairs.length; i++) {
      if (this.pairs[i] == null) {
        return;
      } else if (key.equals(this.pairs[i].key)){
        if (i == size-1){
          this.pairs[size-1] = null;
          size--;
          return;
        }else{
          //System.out.println("-----" + this.pairs[i] + "-----");
          this.pairs[i] = this.pairs[size-1].clone();
          //System.out.println("-----" + this.pairs[i] + "-----");
          this.pairs[size-1] = null;
          size--;
          return;
        }
      }
    }  // find(K)
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  int find(K key) throws KeyNotFoundException {
    try {
      for (int i = 0; i < this.pairs.length; i++) {
        if (key.equals(this.pairs[i].key)){
          return i;
        }
      } // find(K)
    } catch (Exception e) {
      throw new KeyNotFoundException();
    }
    return -1;
  }

} // class AssociativeArray

  