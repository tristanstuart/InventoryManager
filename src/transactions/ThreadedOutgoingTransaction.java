package transactions;

import java.util.Date;

import stocks.Product;
import stocks.Store;

/**
 * Functions the same as OutgoingTransaction except an ID must be specified instead of being automatically chosen.
 * Also when the Transaction is completed, the Product updates are dont simultaneously using multi-threading.
 */
public class ThreadedOutgoingTransaction extends OutgoingTransaction
{
	public ThreadedOutgoingTransaction(int id, Store s, Date date)
	{
		super(id, s, date);
	}

	@Override
	public void updateProductStock()
	{
		for (Product p : productList.keySet())
		{
			Thread thread = new Thread()
			{
				public void run()
				{
					p.decrementCount(productList.get(p));
				}
			};
			thread.start();
		}
	}
}
