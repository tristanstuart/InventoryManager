package reports;

import stocks.Product;
import stocks.StockManager;

/**
 * 
 */
public class AllItemsEnteredReport implements Report
{

	/**
	 * 
	 */
	@Override
	public void printReport()
	{
		System.out.println(generateReport());
	}

	@Override
	public String generateReport()
	{
		String s = "All Products Report:\nProducts appear in the format: name(id): amount" + "\n";
		for (Product p : StockManager.getInstance().getProductList())
			s += p.getName() + "(" + p.getID() + "): " + p.getCount() + "\n";
		return s;
	}
}