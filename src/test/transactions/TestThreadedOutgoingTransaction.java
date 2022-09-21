package test.transactions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import stocks.Product;
import stocks.Store;
import transactions.ThreadedOutgoingTransaction;

public class TestThreadedOutgoingTransaction
{
	@Test
	public void testUpdateProductStock()
	{
		int startAmt = 10;
		Product p1 = new Product("test product", startAmt);
		int addedAmt = 5;
		ThreadedOutgoingTransaction it = new ThreadedOutgoingTransaction(0, new Store("", ""), new Date());
		it.addProduct(p1, addedAmt);
		it.updateProductStock();

		// wait for thread to finish updating product
		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		assertEquals(startAmt - addedAmt, p1.getCount(), "Product stock was not updated properly!");
	}

}
