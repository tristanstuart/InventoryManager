package test.stocks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import stocks.Store;

public class StoreTest
{
	@Test
	/**
	 * Tests that the Store constructor sets all the fields correctly, including ID, and
	 * checks if the toString and getGUIData methods return the correct information
	 */
	public void testStore()
	{
		String name = "asdf";
		String address = "sdkfj";
		Store s = new Store(name, address);

		assertEquals(name, s.getName(), "Store name is not what was provided to the Store(name, amt) constructor!");
		assertEquals(address, s.getAddress(), "Store amount is not what was provided to the Store(name, amt) constructor!");
		// cant test id because it's not directly set

		assertEquals((s.getID() + "," + name + "," + address), s.toString(), "toString representation of store does not match parameters");
		assertArrayEquals(new String[] { name, s.getID() + "", address }, s.getGUIData(), "GUIData representation of store does not match parameters");
	}
}
