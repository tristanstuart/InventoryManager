package transactions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import stocks.Product;

/**
 * Abstract class that {@link IncomingTransaction} and {@link OutgoingTransaction} use as a parent.
 */
public abstract class Transaction
{
	private static int GLOBAL_TRANSACTION_ID_COUNTER = 0;
	/**
	 * Unique id for the transaction.
	 */
	private int ID;

	/**
	 * Date of the transaction.
	 */
	private Date date;

	/**
	 * List of products included in the transaction.
	 */
	protected Map<Product, Integer> productList;

	/**
	 * Default constructor
	 */
	public Transaction()
	{
		this(new Date());
	}

	public Transaction(Date date)
	{
		productList = new HashMap<Product, Integer>();
		this.date = date;
		ID = GLOBAL_TRANSACTION_ID_COUNTER++;
	}

	protected Transaction(int id, Date d)
	{
		productList = new HashMap<Product, Integer>();
		date = d;
		ID = id;
	}

	/**
	 * Appends a product and a given amount to the transaction.
	 * 
	 * @param pr
	 *            The product to be added to a transaction.
	 * @param numberOfItems
	 *            The amount of the given product to be added to a transaction.
	 */
	public void addProduct(Product pr, int numberOfItems)
	{
		productList.put(pr, numberOfItems);
	}

	/**
	 * Gets the list of products in the transaction.
	 * 
	 * @return a list of products that are in the transaction.
	 */
	public Product[] getProductListForTransaction()
	{
		Product products[] = new Product[productList.keySet().size()];
		int i = 0;
		for (Product p : productList.keySet())
			products[i++] = p;
		return products;
	}

	/**
	 * Gets the amount of a given product in the transaction.
	 * 
	 * @param p
	 *            the product to search for in the transaction.
	 * @return The amount of the given product in the transaction, or -1 if the product isn't listed.
	 */
	public int getNumProductInTransaction(Product p)
	{
		Integer amt = productList.get(p);
		if (amt != null)
			return amt;
		return -1;
	}

	public int getMonth()
	{
		return date.getMonth();
	}

	public String getDate()
	{
		return date.toString();
	}

	public int getID()
	{
		return ID;
	}

	public String toString()
	{
		String str = "";
		String common = this.getID() + "," + this.getDate() + "," + productList.size() + "," + System.lineSeparator();
		for (Product p : productList.keySet())
			str += p.getID() + "," + productList.get(p) + "," + System.lineSeparator();
		return common + str;
	}

	public abstract void updateProductStock();
}