package test.stocks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import stocks.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest
{
	private static Product p;
	private static String testName = "testProductName";
	private static int testAmt = 10;
	private static int testSetAmt = 5;
	private static int testIncrement = 2;
	private static int testDecrement = 4;

	@Test
	@Order(0)
	/**
	 * Tests that the Product constructor sets all the fields correctly, including ID, and
	 * checks if the toString and getGUIData methods return the correct information
	 */
	public void testProduct()
	{
		p = new Product(testName, testAmt);
		assertEquals(testName, p.getName(), "Product name is not what was provided to the Product(name, amt) constructor!");
		assertEquals(testAmt, p.getCount(), "Product amount is not what was provided to the Product(name, amt) constructor!");
		// cant test ID since it's not directly set

		assertEquals((p.getID() + "," + testName + "," + testAmt), p.toString(), "toString representation of store does not match parameters");
		assertArrayEquals(new String[] { testName, p.getID() + "", testAmt + "" }, p.getGUIData(), "GUIData representation of store does not match parameters");
	}

	@Test
	@Order(1)
	public void testSetCount()
	{
		p.setCount(testSetAmt);
		assertEquals(testSetAmt, p.getCount(), "Product setCount() failed to properly set value");
		System.out.println(p.getCount());
	}

	@Test
	@Order(2)
	public void testIncrementCount()
	{
		p.incrementCount(testIncrement);
		System.out.println(p.getCount());
		assertEquals(testSetAmt + testIncrement, p.getCount(), "Product incrementCount() failed to properly modify value");
		testSetAmt += testIncrement;
	}

	@Test
	@Order(3)
	public void testDecrementCount()
	{
		p.decrementCount(testDecrement);
		assertEquals(testSetAmt - testDecrement, p.getCount(), "Product decrementCount() failed to properly modify value");
		testSetAmt -= testDecrement;
	}

	@Test
	@Order(4)
	public void testDecrementCountBelowZero()
	{
		p.decrementCount(testDecrement);
		assertEquals(0, p.getCount(), "Product decrementCount() decremented count should be below zero but actual product amount was not!");
	}
}
