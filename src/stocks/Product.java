package stocks;

/**
 * Represents a product within the inventory system.
 */
public class Product
{
	private static int GLOBAL_PRODUCT_ID_COUNTER = 0;

	/**
	 * Unique product id
	 */
	private int ID;

	/**
	 * Name of product
	 */
	private String name;

	/**
	 * Amount of product available
	 */
	private int count;

	/**
	 * Creates a product using the next available product id. This should be the default way of creating a new product.
	 */
	public Product(String n, int amt)
	{
		name = n;
		count = amt;
		ID = GLOBAL_PRODUCT_ID_COUNTER++;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int ct)
	{
		count = ct;
	}

	/**
	 * Increases the count of this product by ct.
	 * 
	 * @param ct
	 *            amount to increase product count by.
	 */
	public void incrementCount(int ct)
	{
		count += ct;
	}

	/**
	 * Decreases the count of this product by ct.
	 * <p>
	 * <u><b>NOTE: Product amount cannot go below 0! If this method
	 * would make the product count negative, it will instead be set to 0.</b></u>
	 * 
	 * @param ct
	 *            amount to decrease product count by.
	 */
	public void decrementCount(int ct)
	{
		count -= ct;
		if (count < 0)
			count = 0;
	}

	public int getID()
	{
		return ID;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return this.getID() + "," + this.getName() + "," + this.getCount();
	}

	public String[] getGUIData()
	{
		return new String[] { this.getName(), this.getID() + "", this.getCount() + "" };
	}
}