package test.transactions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.util.FileManager;
import stocks.StockManager;
import stocks.Store;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

public class TestTransactionManager
{
	private static TransactionsManager tm;

	@BeforeAll
	public static void init()
	{
		FileManager.getInstance().writeIncomingTransactions(new ArrayList<IncomingTransaction>());
		FileManager.getInstance().writeOutgoingTransactions(new ArrayList<OutgoingTransaction>());
		tm = TransactionsManager.getInstance();
		StockManager.getInstance();
	}

	@Test
	public void testAddIncomingTransaction()
	{
		IncomingTransaction it = new IncomingTransaction();
		System.out.println("incoming transaction test " + it.getID() + Arrays.toString(it.getProductListForTransaction()));
		tm.addTransaction(it);
		IncomingTransaction[] list = tm.getIncomingTransactions().stream().filter(i -> i.getID() == it.getID()).toArray(IncomingTransaction[]::new);
		assertFalse(list.length > 1, "Found more than one transaction with matching ID to added transaction!");
		assertFalse(list.length < 1, "Found no transactions matching the added transaction!");
		assertEquals(it, list[0], "Incoming transaction with matching ID was not the same as added transaction!");
	}

	@Test
	public void testAddOutgoingTransaction()
	{
		Store s = new Store("a", "b");
		StockManager.getInstance().addStore(s);
		OutgoingTransaction it = new OutgoingTransaction(s);
		System.out.println("outgoing transaction test " + it.getID() + Arrays.toString(it.getProductListForTransaction()));
		tm.addTransaction(it);
		OutgoingTransaction[] list = tm.getOutgoingTransactions().stream().filter(i -> i.getID() == it.getID()).toArray(OutgoingTransaction[]::new);
		assertFalse(list.length > 1, "Found more than one transaction with matching ID to added transaction!");
		assertFalse(list.length < 1, "Found no transactions matching the added transaction!");
		assertEquals(it, list[0], "Outgoing transaction with matching ID was not the same as added transaction!");
	}
}
