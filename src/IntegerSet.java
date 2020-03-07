import java.security.SecureRandom;
import java.util.Arrays;

/*
 * IntegerSet.java
 *
 * Create  class  IntegerSet.  Each  IntegerSet  object can hold integers in the
 * range 0-100. The set is represented by an array of  booleans.  Array  element
 * a[i]  is  true  if  integer  i  is in the set. Array element a[j] is false if
 * integer j is not in the set.  The  no‐argument  constructor  initializes  the
 * array to the "empty set" (i.e., all false values).
 *
 * Provide  the  following methods: The static method union creates a set that's
 * the set‐theoretic union of two existing sets (i.e., an  element  of  the  new
 * set's  array  is set to true if that element is true in either or both of the
 * existing sets-otherwise, the new set's element is set to false).  The  static
 * method  intersection creates a set which is the set‐theoretic intersection of
 * two existing sets (i.e., an element of the new set's array is set to false if
 * that  element  is false in either or both of the existing sets-otherwise, the
 * new set's element is set to true). Method insertElement inserts a new integer
 * k  into a set (by setting a[k] to true). Method deleteElement deletes integer
 * m (by setting a[m] to false). Method toString returns a String  containing  a
 * set  as  a  list  of numbers separated by spaces. Include only those elements
 * that are present in the set. Use  ‐‐‐  to  represent  an  empty  set.  Method
 * isEqualTo  determines  whether  two  sets  are equal. Write a program to test
 * class IntegerSet. Instantiate several IntegerSet objects. Test that all  your
 * methods work properly.
 */

/**
 *
 * @author Edgar Cole
 */
public class IntegerSet {

    boolean[] integerset = new boolean[101];

    /**
     * Creates a new instance of IntegerSet
     */

    public IntegerSet() {
        super();
    }

    /**
     * Return a new IntegerSet containing the union of the 2 IntegerSet objects
     * that were passed as arguments
     */

    public static IntegerSet union(IntegerSet set1, IntegerSet set2) {
        IntegerSet set3 = new IntegerSet();
        for (int index = 1; index <= 100; index++) {
            if (set1.integerset[index] || set2.integerset[index]) {
                set3.integerset[index] = true;
            }
        }
        return set3;
    }

    /**
     * Return a new IntegerSet containing the intersection of the two IntegerSet
     * objects that were passed as arguments
     */

    public static IntegerSet intersection(IntegerSet set1, IntegerSet set2) {
        IntegerSet set3 = new IntegerSet();
        for (int index = 1; index <= 100; index++) {
            if (set1.integerset[index] && set2.integerset[index]) {
                set3.integerset[index] = true;
            }
        }
        return set3;
    }

    /**
     * Inserts an element into the IntegerSet by setting the corresponding value
     * within the set array to true. Returns false if the value was out of range and
     * true otherwise.
     */

    public boolean insertElement(int number) {
        if (number >= 1 && number <= 100) {
            integerset[number] = true;
            return true;
        }
        return false;
    }

    /**
     * Deletes an element from the IntegerSet by setting the corresponding value
     * within the set array to false. Returns false if the value was out of range
     * and true otherwise.
     */

    public boolean deleteElement(int number) {
        if (number >= 1 && number <= 100) {
            integerset[number] = false;
            return true;
        }
        return false;
    }

    /**
     * @Override The "toString" method in the Object class Displays the integers
     *           contained by the IntegerSet separated by spaces. An empty set
     *           should be displayed as: { --- } An integer set containing 5 and 10
     *           should be displayed as: { 5 10 }
     */

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "{ --- }";
        } else {
            String stringRepresentation = "{ ";
            for (int index = 1; index <= 100; index++) {
                if (integerset[index])
                    stringRepresentation = stringRepresentation + index + " ";
            }
            stringRepresentation = stringRepresentation + "}";
            return stringRepresentation;
        }
    }

    /**
     * Returns true if the current IntegerSet contains the same integers as the
     * IntegerSet supplied as an argument
     */

    public boolean isEqualTo(IntegerSet that) {
        return Arrays.equals(this.integerset, that.integerset);
    }

    private boolean isEmpty() {
        for (int index = 1; index <= 100; index++) {
            if (integerset[index])
                return false;
        }
        return true;
    }

    private boolean contains(int number) {
        if (number >= 1 && number <= 100) {
            return integerset[number];
        }
        return false;
    }

    public static void main(String[] arguments) {
        SecureRandom randomNumberGenerator = new SecureRandom();
        int randomNumber;
        boolean[] array = new boolean[101];
        IntegerSet setA = new IntegerSet();
        IntegerSet setB = new IntegerSet();
        IntegerSet setC = new IntegerSet();
        for (int index = 1; index <= 20; index++) {
            setA.insertElement(1 + randomNumberGenerator.nextInt(100));
            setB.insertElement(1 + randomNumberGenerator.nextInt(100));
        }
        System.out.println("setA: " + setA);
        System.out.println("setB: " + setB);
        System.out.println("The union of setA and setB: " + IntegerSet.union(setA, setB));
        System.out.println("The intersection of setA and setB: " + IntegerSet.intersection(setA, setB));

        // Save a copy of setB's content in an array
        System.arraycopy(setB.integerset, 0, array, 0, setB.integerset.length);

        // Dump the content of the array
        System.out.print("The copy of setB: { ");
        for (int index = 0; index < array.length; index++) {
            if (array[index]) {
                System.out.printf("%d ", index);
            }
        }
        System.out.println("}");

        // Delete random numbers from setB
        for (int index = 1; index <= 20; index++) {
            randomNumber = 1 + randomNumberGenerator.nextInt(100);
            if (setB.contains(randomNumber) && setB.deleteElement(randomNumber)) {
                System.out.printf("Deleted %d from setB . . .%n", randomNumber);
            }
        }

        // A dump of the original content of setB as saved in "array"
        System.out.print("The original setB: { ");
        for (int index = 0; index < array.length; index++) {
            if (array[index]) {
                System.out.printf("%d ", index);
            }
        }
        System.out.println("}");

        System.out.println("setB after any deletions: " + setB);

        // Make the state of setC equal to that of setB
        System.arraycopy(setB.integerset, 0, setC.integerset, 0, setB.integerset.length);

        // Compare setB to setC
        System.out.println("setB is equal to setC: " + setB.isEqualTo(setC));

        // Compare setB to setA
        System.out.println("setB is equal to setA: " + setB.isEqualTo(setA));
    }
}
