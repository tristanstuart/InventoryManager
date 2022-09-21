package reports;

import java.util.Arrays;

import stocks.Product;
import stocks.StockManager;

public class AvailableItemsReport implements Report
{

	@Override
	public void printReport()
	{
		System.out.println(generateReport());
	}

	@Override
	public String generateReport()
	{
		String s = "Available Products Report:\nProducts appear in the format: name(id): amount\n";
		Product[] products = Arrays.stream(StockManager.getInstance().getProductList()).filter(i -> i.getCount() > 0).toArray(Product[]::new);
		for (Product p : products)
			s += p.getName() + "(" + p.getID() + "): " + p.getCount() + "\n";
		return s;
	}
}