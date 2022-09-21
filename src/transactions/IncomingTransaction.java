package transactions;

import java.util.Date;

import stocks.Product;

public class IncomingTransaction extends Transaction
{
	/**
	 * Loops all products added to the transaction and increments their each of their stock counts as specified in the transaction.
	 */

	public IncomingTransaction(Date date)
	{
		super(date);
	}

	protected IncomingTransaction(int id, Date date)
	{
		super(id, date);
	}

	public IncomingTransaction()
	{
		super();
	}

	@Override
	public void updateProductStock()
	{
		for (Product p : productList.keySet())
			p.incrementCount(productList.get(p));
	}
}