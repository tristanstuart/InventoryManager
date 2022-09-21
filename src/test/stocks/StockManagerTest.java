package test.stocks;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import stocks.Product;
import stocks.StockManager;
import stocks.Store;

public class StockManagerTest
{
	private static StockManager inventory;

	@BeforeAll
	public static void init()
	{
		// make sure the instance is initialized before normal testing
		inventory = StockManager.getInstance();
	}

	@Test
	/**
	 * Adds a product and then checks the list to see if it was inserted
	 */
	public void testAddProduct()
	{
		Product p = new Product("Test Product", 10);
		inventory.addProduct(p);
		// filter the productList by id for the ID of the product we added, then convert filtered elements to an array
		Product[] foundProducts = Arrays.stream(inventory.getProductList()).filter(i -> i.getID() == p.getID()).toArray(Product[]::new);
		assertFalse(foundProducts.length > 1, "Found more than one product with matching ID to added product!");
		assertFalse(foundProducts.length < 1, "Found no products matching the added product!");
		assertEquals(p, foundProducts[0], "Product with matching ID was not the same as added product!");
	}

	@Test
	/**
	 * Adds a store and then checks the list to see if it was inserted
	 */
	public void testAddStore()
	{
		Store s = new Store("Test Store", "testAddress");
		inventory.addStore(s);
		Store[] foundStores = Arrays.stream(inventory.getStoreList()).filter(i -> i.getID() == s.getID()).toArray(Store[]::new);
		assertFalse(foundStores.length > 1, "Found more than one store with matching ID to added store!");
		assertFalse(foundStores.length < 1, "Found no stores matching the ID of the added store!");
		assertEquals(s, foundStores[0], "Store with matching ID was not the same as added store!");
	}

	@Test
	/**
	 * tests findProductByID for a product in the list and not in the list
	 */
	public void testFindProductByID()
	{
		if (inventory.getProductList().length == 0)
			for (Product p : new Product[] { new Product("a", 1), new Product("b", 2), new Product("c", 3), new Product("d", 14) })
				inventory.addProduct(p);
		int idToFind = inventory.getProductList()[0].getID();

		assertEquals(idToFind, inventory.findProductByID(idToFind).getID(), "Found product id does not match product id that was searched for!");

		idToFind = -2;// impossible ID, IDs start at 0
		assertNull(inventory.findProductByID(idToFind), "findProductByID returned a product for an impossible ID, should have returned null!");
	}

	@Test
	/**
	 * tests findStoreByID for a store in the list and not in the list
	 */
	public void testFindStoreByID()
	{
		int idToFind = inventory.getStoreList()[0].getID();
		assertEquals(idToFind, inventory.findStoreByID(idToFind).getID(), "Found store id does not match store id that was searched for!");

		idToFind = -2;// impossible ID, IDs start at 0
		assertNull(inventory.findStoreByID(idToFind), "findStoreByID returned a product for an impossible ID, should have returned null!");
	}
}
