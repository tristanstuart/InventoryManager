package test.transactions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import stocks.Product;
import transactions.ThreadedIncomingTransaction;

public class TestThreadedIncomingTransaction
{
	@Test
	public void testUpdateProductStock()
	{
		int startAmt = 10;
		Product p1 = new Product("test product", startAmt);
		int addedAmt = 5;
		ThreadedIncomingTransaction it = new ThreadedIncomingTransaction(0, new Date());
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

		assertEquals(startAmt + addedAmt, p1.getCount(), "Product stock was not updated properly!");
	}

}
