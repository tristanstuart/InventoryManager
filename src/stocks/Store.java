package stocks;

/**
 * Represents a store location that is part of the franchise.
 */
public class Store
{
	private static int GLOBAL_STORE_ID_COUNTER = 0;
	/**
	 * Unique store ID
	 */
	private int ID;

	/**
	 * Name of Store
	 */
	private String name;

	/**
	 * Address of Store
	 */
	private String address;

	/**
	 * Creates a store using the next available store id. This should be the default way of creating a new store.
	 */
	public Store(String n, String a)
	{
		name = n;
		address = a;
		ID = GLOBAL_STORE_ID_COUNTER++;
	}

	public int getID()
	{
		return ID;
	}

	public String getName()
	{
		return name;
	}

	public String getAddress()
	{
		return address;
	}

	@Override
	public String toString()
	{
		return this.getID() + "," + this.getName() + "," + this.getAddress();
	}

	public String[] getGUIData()
	{
		return new String[] { this.getName(), this.getID() + "", this.getAddress() };
	}
}