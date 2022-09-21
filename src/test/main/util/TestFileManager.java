package test.main.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.util.FileManager;
import stocks.Product;
import stocks.StockManager;
import stocks.Store;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;

public class TestFileManager
{
	private static FileManager files;
	private static final Random rand = new Random();

	@BeforeAll
	public static void init()
	{
		files = FileManager.getInstance();
	}

	@Test
	public void testReadWriteProducts()
	{
		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < 5; i++)
			productList.add(new Product("product" + i, i + 6));

		files.writeProducts(productList);

		List<Product> readProducts = files.readProducts();

		assertTrue(productList.size() == readProducts.size(), "Size of read product list does not equal size of written product list");

		for (int i = 0; i < productList.size(); i++)
		{
			assertTrue(productList.get(i).getName().equals(readProducts.get(i).getName()), "Product name does not match after write and read");
			assertTrue(productList.get(i).getCount() == readProducts.get(i).getCount(), "Product amount does not match after write and read");
		}
	}

	@Test
	public void testReadWriteStores()
	{
		List<Store> storeList = new ArrayList<Store>();
		for (int i = 0; i < 5; i++)
			storeList.add(new Store("store" + i, "address" + i));

		files.writeStores(storeList);

		List<Store> readStores = files.readStores();

		assertTrue(storeList.size() == readStores.size(), "Size of read store list does not equal size of written store list");

		for (int i = 0; i < storeList.size(); i++)
		{
			assertTrue(storeList.get(i).getName().equals(readStores.get(i).getName()), "Store name does not match after write and read");
			assertTrue(storeList.get(i).getAddress().equals(readStores.get(i).getAddress()), "Store address does not match after write and read");
		}
	}

	@Test
	public void testReadWriteIncomingTransactions()
	{
		int n = 0;
		Product[] p = new Product[] { new Product("product" + n++, n + 5), new Product("product" + n++, n + 5), new Product("product" + n++, n + 5) };
		Arrays.stream(p).forEach(prod -> StockManager.getInstance().addProduct(prod)); // products must exist in the stock manager to be found during re-read
		List<IncomingTransaction> transactionList = new ArrayList<IncomingTransaction>();
		for (int i = 0; i < 5; i++)
		{
			IncomingTransaction it = new IncomingTransaction();
			Arrays.stream(p).forEach(prod -> it.addProduct(prod, rand.nextInt(10) + 1));
			transactionList.add(it);
		}

		files.writeIncomingTransactions(transactionList);

		List<IncomingTransaction> readTransactions = files.readIncomingTransactions();

		assertTrue(transactionList.size() == readTransactions.size(), "Size of read incoming transaction list does not equal size of written list");

		for (int i = 0; i < transactionList.size(); i++)
		{
			assertTrue(transactionList.get(i).getDate().equals(readTransactions.get(i).getDate()), "Incoming transaction date does not match after write and read");
			Product[] productList = readTransactions.get(i).getProductListForTransaction();
			System.out.println(transactionList.get(i).getProductListForTransaction());
			System.out.println(Arrays.toString(productList));
			assertArrayEquals(transactionList.get(i).getProductListForTransaction(), productList, "Incoming Transaction product list does not match after write and read");
			for (int j = 0; j < productList.length; j++)
				assertEquals(transactionList.get(i).getNumProductInTransaction(productList[j]), readTransactions.get(i).getNumProductInTransaction(productList[j]), productList[j].getName() + " amount in the incoming transaction does not match after write and read");
		}
	}

	@Test
	public void testReadWriteOutgoingTransactions()
	{
		Store s = new Store("testStore", "asdf");
		StockManager.getInstance().addStore(s); // store must be added to stock manager to be found on re-read
		int n = 0;
		Product[] p = new Product[] { new Product("product" + n++, n + 5), new Product("product" + n++, n + 5), new Product("product" + n++, n + 5) };
		Arrays.stream(p).forEach(prod -> StockManager.getInstance().addProduct(prod)); // products must exist in the stock manager to be found during re-read
		List<OutgoingTransaction> transactionList = new ArrayList<OutgoingTransaction>();
		for (int i = 0; i < 5; i++)
		{
			OutgoingTransaction it = new OutgoingTransaction(s);
			Arrays.stream(p).forEach(prod -> it.addProduct(prod, rand.nextInt(10) + 1));
			transactionList.add(it);
		}

		files.writeOutgoingTransactions(transactionList);

		List<OutgoingTransaction> readTransactions = files.readOutgoingTransactions();

		assertTrue(transactionList.size() == readTransactions.size(), "Size of read outgoing transaction list does not equal size of written list");

		for (int i = 0; i < transactionList.size(); i++)
		{
			assertTrue(transactionList.get(i).getDate().equals(readTransactions.get(i).getDate()), "Outgoing transaction date does not match after write and read");
			assertTrue(transactionList.get(i).getTransactionStoreID() == readTransactions.get(i).getTransactionStoreID(), "Outgoing transaction store ID does not match after write and read");
			Product[] productList = readTransactions.get(i).getProductListForTransaction();
			System.out.println(transactionList.get(i).getProductListForTransaction());
			System.out.println(Arrays.toString(productList));
			assertArrayEquals(transactionList.get(i).getProductListForTransaction(), productList, "Outgoing Transaction product list does not match after write and read");
			for (int j = 0; j < productList.length; j++)
				assertEquals(transactionList.get(i).getNumProductInTransaction(productList[j]), readTransactions.get(i).getNumProductInTransaction(productList[j]), productList[j].getName() + " amount in the incoming transaction does not match after write and read");
		}
	}
}
