package jd.demo.se.bypkg.util;

import java.util.Arrays;

import jd.util.lang.Console;

public class ArraysTest {
/*
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

@Test
	public void testSortIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortIntArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortLongArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortShortArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortShortArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortCharArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortCharArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortFloatArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortFloatArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortDoubleArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortCharArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortCharArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortShortArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortShortArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortIntArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortLongArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortFloatArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortFloatArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortDoubleArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortTArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortTArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortTArrayComparatorOfQsuperT() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSortTArrayIntIntComparatorOfQsuperT() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortObjectArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortTArrayComparatorOfQsuperT() {
		fail("Not yet implemented");
	}

	@Test
	public void testSortTArrayIntIntComparatorOfQsuperT() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixTArrayBinaryOperatorOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixTArrayIntIntBinaryOperatorOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixLongArrayLongBinaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixLongArrayIntIntLongBinaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixDoubleArrayDoubleBinaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixDoubleArrayIntIntDoubleBinaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixIntArrayIntBinaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelPrefixIntArrayIntIntIntBinaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchLongArrayLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchLongArrayIntIntLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchIntArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchIntArrayIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchShortArrayShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchShortArrayIntIntShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchCharArrayChar() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchCharArrayIntIntChar() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchByteArrayByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchByteArrayIntIntByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchDoubleArrayDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchDoubleArrayIntIntDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchFloatArrayFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchFloatArrayIntIntFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchObjectArrayObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchObjectArrayIntIntObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchTArrayTComparatorOfQsuperT() {
		fail("Not yet implemented");
	}

	@Test
	public void testBinarySearchTArrayIntIntTComparatorOfQsuperT() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsLongArrayLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsIntArrayIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsShortArrayShortArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsCharArrayCharArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsByteArrayByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsBooleanArrayBooleanArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsDoubleArrayDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsFloatArrayFloatArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObjectArrayObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillLongArrayLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillLongArrayIntIntLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillIntArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillIntArrayIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillShortArrayShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillShortArrayIntIntShort() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillCharArrayChar() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillCharArrayIntIntChar() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillByteArrayByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillByteArrayIntIntByte() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillBooleanArrayBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillBooleanArrayIntIntBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillDoubleArrayDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillDoubleArrayIntIntDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillFloatArrayFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillFloatArrayIntIntFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillObjectArrayObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFillObjectArrayIntIntObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfTArrayInt() {
		Runnable[] arr = new Runnable[0];
		Console.ln(arr.getClass().getComponentType());
		Console.ln(Arrays.copyOf(arr, 0));
	}
	

	@Test
	public void testCopyOfUArrayIntClassOfQextendsT() {
		
	}

	/*@Test
	public void testCopyOfByteArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfShortArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfIntArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfLongArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfCharArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfFloatArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfDoubleArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfBooleanArrayInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeTArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeUArrayIntIntClassOfQextendsT() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeShortArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeIntArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeLongArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeCharArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeFloatArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeDoubleArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testCopyOfRangeBooleanArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAsList() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeShortArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeCharArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeBooleanArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeFloatArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCodeObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeepHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeepEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeepEquals0() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringShortArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringCharArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringBooleanArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringFloatArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testToStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeepToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAllTArrayIntFunctionOfQextendsT() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSetAllTArrayIntFunctionOfQextendsT() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAllIntArrayIntUnaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSetAllIntArrayIntUnaryOperator() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAllLongArrayIntToLongFunction() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSetAllLongArrayIntToLongFunction() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAllDoubleArrayIntToDoubleFunction() {
		fail("Not yet implemented");
	}

	@Test
	public void testParallelSetAllDoubleArrayIntToDoubleFunction() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorTArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorTArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorIntArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorLongArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSpliteratorDoubleArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamTArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamTArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamIntArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamIntArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamLongArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamDoubleArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testStreamDoubleArrayIntInt() {
		fail("Not yet implemented");
	}
*/
}
