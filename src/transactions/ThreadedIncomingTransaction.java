package transactions;

import java.util.Date;

import stocks.Product;

/**
 * Functions the same as IncomingTransaction except an ID must be specified instead of being automatically chosen.
 * Also when the Transaction is completed, the Product updates are dont simultaneously using multi-threading.
 */
public class ThreadedIncomingTransaction extends IncomingTransaction
{
	public ThreadedIncomingTransaction(int i, Date d)
	{
		super(i, d);
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
					p.incrementCount(productList.get(p));
				}
			};
			thread.start();
		}
	}
}
