package reports;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.Product;
import stocks.StockManager;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

public class ProductByStoreReport implements Report
{

	private int store;

	private Map<Product, Integer> productList;

	public ProductByStoreReport(int s)
	{
		store = s;
		productList = new HashMap<Product, Integer>();
		streamCollectProductsSentToStore();
	}

	/**
	 * 
	 */
	public void printReport()
	{
		System.out.println(generateReport());
	}

	public String generateReport()
	{
		String s = "Store Products Report For " + (store == -1 ? "All Stores" : "Store " + StockManager.getInstance().findStoreByID(store).getName()) + ":\nProducts appear in the format name(id): amount";
		for (Product p : productList.keySet())
			s += (p.getName() + "(" + p.getID() + "): " + productList.get(p) + "\n");
		return s;
	}

	private void streamCollectProductsSentToStore()
	{
		TransactionsManager.getInstance().getOutgoingTransactions().stream().filter(i -> store == -1 || i.getTransactionStoreID() == store).forEach(i -> Arrays.stream(i.getProductListForTransaction()).forEach(p -> productList.put(p, i.getNumProductInTransaction(p) + (productList.get(p) != null ? productList.get(p) : 0))));
	}

	@Deprecated
	/**
	 * Old method used to collect relevant transactions see {@link #streamCollectTransactions(TransactionsManager)} for new method.
	 * 
	 * @param transactionsManager
	 *            Transaction manager containing the transactions to be reported on.
	 */
	private void collectProductsSentToStore(TransactionsManager transactionsManager)
	{
		List<OutgoingTransaction> outgoing = transactionsManager.getOutgoingTransactions();
		for (OutgoingTransaction transaction : outgoing)
			if (store == -1 || (store != -1 && transaction.getTransactionStoreID() == store))
			{
				Product[] productsInTransaction = transaction.getProductListForTransaction();
				for (Product newProduct : productsInTransaction)
				{
					if (productList.get(newProduct) != null)
						productList.put(newProduct, productList.get(newProduct) + transaction.getNumProductInTransaction(newProduct));
					else
						productList.put(newProduct, transaction.getNumProductInTransaction(newProduct));
				}
			}
	}
}