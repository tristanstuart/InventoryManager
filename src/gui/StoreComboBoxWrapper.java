package gui;

import stocks.StockManager;
import stocks.Store;

/**
 * Wrapper class for Store objects so that the toString() returns something visually usable for the JComboBox in {@link CreateOutgoingTransactionGUI}
 *
 */
public class StoreComboBoxWrapper
{
	private Store store;

	public StoreComboBoxWrapper(Store s)
	{
		store = s;
	}

	public Store getStore()
	{
		return store;
	}

	public String toString()
	{
		return store.getName();
	}

	public static StoreComboBoxWrapper[] wrapStoreListForDisplay()
	{
		Store[] storeList = StockManager.getInstance().getStoreList();

		StoreComboBoxWrapper[] list = new StoreComboBoxWrapper[storeList.length];
		for (int i = 0; i < list.length; i++)
			list[i] = new StoreComboBoxWrapper(storeList[i]);
		return list;
	}
}
