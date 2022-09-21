package gui;

import stocks.Product;
import stocks.StockManager;

/**
 * Wrapper class for Store objects so that the toString() returns something visually usable for the JComboBox in {@link CreateOutgoingTransactionGUI}
 *
 */
public class ProductComboBoxWrapper
{
	private Product product;

	public ProductComboBoxWrapper(Product s)
	{
		product = s;
	}

	public Product getProduct()
	{
		return product;
	}

	public String toString()
	{
		return product.getName();
	}

	public static ProductComboBoxWrapper[] wrapProductListForDisplay()
	{
		Product[] productList = StockManager.getInstance().getProductList();

		ProductComboBoxWrapper[] list = new ProductComboBoxWrapper[productList.length];
		for (int i = 0; i < list.length; i++)
			list[i] = new ProductComboBoxWrapper(productList[i]);
		return list;
	}
}
