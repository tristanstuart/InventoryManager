package stocks;

import java.util.List;

import main.util.FileManager;

/**
 * Manages the {@link Product} and {@link Store} information within the inventory system.
 */
public class StockManager
{
	private static StockManager instance;

	/**
	 * Stores a list of all products in the system
	 */
	private List<Product> products;

	/**
	 * Stores a list of all store locations in the system
	 */
	private List<Store> stores;

	public static StockManager getInstance()
	{
		if (instance == null)
			instance = new StockManager();
		return instance;
	}

	/**
	 * Default constructor
	 */
	private StockManager()
	{
		FileManager fm = FileManager.getInstance();
		products = fm.readProducts();
		stores = fm.readStores();
	}

	/**
	 * Appends a {@link Product} to the list of products.
	 * 
	 * @param p
	 *            The product to be appended to the list.
	 */
	public void addProduct(Product p)
	{
		products.add(p);
		saveUpdatedStock();
	}

	/**
	 * Creates a new store from command-line input and adds it to the store list.
	 */
	public void addStore(Store s)
	{
		stores.add(s);
		FileManager.getInstance().writeStores(stores);
	}

	/**
	 * Gets the current list of all products in the system.
	 * 
	 * @return the current list of all products in the system.
	 */
	public Product[] getProductList()
	{
		return products.toArray(Product[]::new);
	}

	/**
	 * Gets the current list of all stores in the system.
	 * 
	 * @return the current list of all stores in the system.
	 */
	public Store[] getStoreList()
	{
		return stores.toArray(Store[]::new);
	}

	/**
	 * Searches the product list for the product with a matching ID.
	 * 
	 * @param id
	 *            the id of the product to find.
	 * @return the matching product, or null if none exists.
	 */
	public Product findProductByID(int id)
	{
		for (Product p : products)
			if (p.getID() == id)
				return p;
		return null;
	}

	/**
	 * Searches the store list for the store with a matching ID.
	 * 
	 * @param id
	 *            the id of the store to find.
	 * @return the matching store, or null if none exists.
	 */
	public Store findStoreByID(int id)
	{
		for (Store p : stores)
			if (p.getID() == id)
				return p;
		return null;
	}

	public void saveUpdatedStock()
	{
		FileManager.getInstance().writeProducts(products);
	}
}