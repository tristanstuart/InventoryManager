package test.transactions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stocks.Product;
import transactions.IncomingTransaction;

public class TestIncomingTransaction
{
	private IncomingTransaction it;
	private Date d;
	private Product p1;
	private int startingProductAmt = 10;
	private int transactionProductAmt = 5;

	@BeforeEach
	public void init()
	{
		d = new Date();
		p1 = new Product("Test Product", startingProductAmt);

		it = new IncomingTransaction(d);
		it.addProduct(p1, transactionProductAmt);
	}

	@Test
	public void testConstructor()
	{
		assertEquals(d.toString(), it.getDate(), "Date object provided in constructor is not the same as date object in instance");
		// cant test id because of the @BeforeEach making a new transaction each time
	}

	@Test
	public void testAddProduct()
	{
		Product[] pList = it.getProductListForTransaction();
		pList = Arrays.stream(pList).filter(i -> i.getID() == p1.getID()).toArray(Product[]::new);
		assertFalse(pList.length > 1, "Found more than one product with matching ID to added product!");
		assertFalse(pList.length < 1, "Found no products matching the added product!");
		assertEquals(p1, pList[0], "Product with matching ID was not the same as added product!");
	}

	@Test
	public void testGetNumProductInTransaction()
	{
		assertEquals(transactionProductAmt, it.getNumProductInTransaction(p1), "Added product amount did not equal the amount of product in the transaction!");
	}

	@Test
	public void testToString()
	{
		Product[] productList = it.getProductListForTransaction();

		String str = "";
		String common = it.getID() + "," + it.getDate() + "," + productList.length + "," + System.lineSeparator();
		for (Product p : productList)
			str += p.getID() + "," + it.getNumProductInTransaction(p) + "," + System.lineSeparator();

		assertEquals(common + str, it.toString(), "transaction toString() did not match expected toString()!");
	}

	@Test
	public void testUpdateProductStock()
	{
		it.updateProductStock();
		assertEquals(startingProductAmt + transactionProductAmt, p1.getCount(), "Product stock was not update on method call!");
	}
}
