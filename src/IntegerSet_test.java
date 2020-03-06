import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import junit.framework.TestCase;


public class IntegerSet_test extends TestCase {

    private IntegerSet isA;
    private IntegerSet isB;
    private Field setField;    // Field referencing the boolean[] containing the IntegerSet data

    public static String newline = System.getProperty("line.separator");
    private ByteArrayOutputStream capturedOut;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Capture the output
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));

        isA = new IntegerSet();
        isB = new IntegerSet();

        // Populate setField with a reference to the boolean[] containing the IntegerSet data
        for (Field fld : isA.getClass().getDeclaredFields()) {
            if (fld.getType().equals(boolean[].class)) {
                fld.setAccessible(true);
                setField = fld;

//				// For logging/debugging
//				System.err.println(fld.getName() + ": " + fld.getType().isArray() + ": " + fld.getType().getComponentType());
//				// This is how you'd retrieve a reference to an instance of the array itself
//				boolean[] booly = (boolean[])fld.get(isA);
            }
        }
    }

    public void testIntegerSetBackingArraySize() {
        /* The setUp method will have already validated the constructor...
         * Just ensure that the array is properly initialized.
         */
        final int expectedArraySize = 101;
        try {
            assertEquals("The backing array \"set\" is not the correct size", expectedArraySize, Array.getLength(setField.get(isA)));
        } catch (Exception e) {
            assertThrowableTestFailure(e);
        }
    }

    public void testUnion() {
        isA.insertElement(27);
        isA.insertElement(28);
        isB.insertElement(28);
        isB.insertElement(29);
        IntegerSet unionSet = IntegerSet.union(isA, isB);
        boolean[] expectedArr = new boolean[101];
        expectedArr[27] = true;
        expectedArr[28] = true;
        expectedArr[29] = true;
        try {
            int arrLen = Array.getLength(setField.get(isA));
            for (int targetNum = 0; targetNum < arrLen; targetNum++) {
                if (expectedArr[targetNum] == true) {
                    assertTrue(targetNum + " was unexpectedly absent from the union", (Boolean)Array.get(setField.get(unionSet), targetNum));
                } else {
                    assertFalse(targetNum + " was unexpectedly present in the union", (Boolean)Array.get(setField.get(unionSet), targetNum));
                }
            }
        } catch (Exception e) {
            assertThrowableTestFailure(e);
        }
//		// If the toString could be trusted to function as expected the test below would be a little more straightforward...
//		assertEquals("The intersection of two sets was not properly determined and/or displayed ", "{ 27 28 29 }" , IntegerSet.union(isA, isB).toString());
    }

    public void testIntersection() {
        isA.insertElement(27);
        isA.insertElement(28);
        isB.insertElement(28);
        isB.insertElement(29);
        IntegerSet intersectionSet = IntegerSet.intersection(isA, isB);
        boolean[] expectedArr = new boolean[101];
        expectedArr[28] = true;
        try {
            int arrLen = Array.getLength(setField.get(isA));
            for (int targetNum = 0; targetNum < arrLen; targetNum++) {
                if (expectedArr[targetNum] == true) {
                    assertTrue(targetNum + " was unexpectedly absent from the intersection", (Boolean)Array.get(setField.get(intersectionSet), targetNum));
                } else {
                    assertFalse(targetNum + " was unexpectedly present in the intersection", (Boolean)Array.get(setField.get(intersectionSet), targetNum));
                }
            }
        } catch (Exception e) {
            assertThrowableTestFailure(e);
        }
//		// If the toString could be trusted to function as expected the test below would be a little more straightforward...
//		assertEquals("The intersection of two sets was not properly determined and/or displayed ", "{ 28 }" , IntegerSet.intersection(isA, isB).toString());
    }

    public void testInsertElement() {
        final int targetNum = 57;
        try {
            assertFalse("All elements in the empty set's backing array should be false", (Boolean)Array.get(setField.get(isA), targetNum));
            isA.insertElement(targetNum);
            assertTrue(targetNum + " was added to the set but isn't set (true) in its backing array", (Boolean)Array.get(setField.get(isA), targetNum));
        } catch (Exception e) {
            assertThrowableTestFailure(e);
        }
    }

    public void testDeleteElement() {
        final int targetNum = 57;
        try {
            isA.insertElement(targetNum);
            assertTrue(targetNum + " was added to the set but isn't set (true) in its backing array", (Boolean)Array.get(setField.get(isA), targetNum));
            isA.deleteElement(targetNum);
            assertFalse(targetNum + " was deleted from the set but wasn't removed from its backing array (i.e.: set false)", (Boolean)Array.get(setField.get(isA), targetNum));
        } catch (Exception e) {
            assertThrowableTestFailure(e);
        }
    }

    public void testToStringEmpty() {
        assertTrue("toString output for the empty set is non-compliant", isA.toString().contains("{ --- }"));
    }

    public void testToStringSampleData() {
        isA.insertElement(5);
        isA.insertElement(10);
        assertTrue("toString output for the empty set is non-compliant", isA.toString().contains("{ 5 10 }"));
    }

    public void testIsEqualSameObject() {
        assertTrue("An IntegerSet was incorrectly identified as unequal to itself", isA.isEqualTo(isA));
    }

    public void testIsEqualEmpty() {
        assertTrue("Two empty IntegerSets were incorrectly identifed as unequal", isA.isEqualTo(isB));
    }

    public void testIsEqualEquality() {
        final int targetNum = 57;
        isA.insertElement(targetNum);
        isB.insertElement(targetNum);
        assertTrue("Two identical IntegerSets were incorrectly identifed as unequal", isA.isEqualTo(isB));
    }

    public void testIsEqualInequality() {
        final int targetNum = 57;
        isA.insertElement(targetNum);
        assertFalse("Two unequal IntegerSets were incorrectly identifed equal", isA.isEqualTo(isB));
    }

    /* method name retrieval code courtesy of: http://dev.kanngard.net/Permalinks/ID_20030114224837.html
     */
    private void assertThrowableTestFailure(Throwable thrown) {
        StackTraceElement stackTraceElements[] =
                (new Throwable()).getStackTrace();
        fail(thrown.getClass().getName() + " encountered! Unable to successfully execute test: " + stackTraceElements[1].toString());
    }
}
