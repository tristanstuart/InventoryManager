package transactions;

import java.util.Date;

import stocks.Product;
import stocks.Store;

public class OutgoingTransaction extends Transaction
{
	/**
	 * The store on which the transaction is occurring.
	 */
	private Store store;

	/**
	 * Default constructor
	 */
	public OutgoingTransaction(Store s)
	{
		super();
		store = s;
	}

	public OutgoingTransaction(Store s, Date date)
	{
		super(date);
		store = s;
	}

	protected OutgoingTransaction(int id, Store s, Date date)
	{
		super(id, date);
		store = s;
	}

	/**
	 * Loops all products added to the transaction and decrements their each of their stock counts as specified in the transaction, simulating sending that stock to a store.
	 * Note: negativity of stocks is not tracked or prevented in any way.
	 */
	@Override
	public void updateProductStock()
	{
		for (Product p : productList.keySet())
			p.decrementCount(productList.get(p));
	}

	public int getTransactionStoreID()
	{
		return store.getID();
	}

	public String toString()
	{
		return this.getTransactionStoreID() + "," + super.toString();
	}
}