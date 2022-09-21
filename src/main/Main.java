package main;

import java.util.Scanner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.GUIManager;
import main.util.ReportInput;
import main.util.StockInput;
import main.util.TransactionInput;
import stocks.StockManager;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

/**
 * 
 */
public class Main
{
	private Main()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception
	{
		Main main = new Main();
		main.runGUI();
	}

	/**
	 * Runs the program in GUI mode.
	 */
	public void runGUI()
	{
		GUIManager.getInstance().createMainGUI();
	}

	/**
	 * Runs the basic program loop for interacting with the system.
	 */
	public void runInputLoop()
	{
		Scanner sc = new Scanner(System.in);

		StockManager systemInventory = StockManager.getInstance();
		TransactionsManager transactionsManager = TransactionsManager.getInstance();

		String input = "";
		while (!input.equals("x"))
		{
			printMenu();
			System.out.print("Input an action: ");
			input = sc.nextLine();

			if (input.equals("p"))
				systemInventory.addProduct(StockInput.doNewProductInput(sc));
			else if (input.equals("s"))
				systemInventory.addStore(StockInput.doNewStoreInput(sc));
			else if (input.equals("i"))
			{
				IncomingTransaction it = TransactionInput.doNewIncomingTransaction(sc);
				transactionsManager.addTransaction(it);
				systemInventory.saveUpdatedStock();
			} else if (input.equals("o"))
			{
				OutgoingTransaction ot = TransactionInput.doNewOutgoingTransaction(sc);
				transactionsManager.addTransaction(ot);
				systemInventory.saveUpdatedStock();
			} else if (input.equals("r"))
			{
				ReportInput.getReportDetails(sc);
			} else if (input.equals("x"))
				;
			else
			{
				System.out.println("Invalid input.");
			}
		}
		System.out.println("Exiting program...");
	}

	/**
	 * Prints menu of possible actions, convenience method used by {@link #runInputLoop()}.
	 */
	private void printMenu()
	{
		System.out.println("\n\nInventory Management System Menu");
		System.out.println("--------------------------------");
		System.out.println("p: Add Product");
		System.out.println("s: Add Store");
		System.out.println("i: Perform Incoming Transaction");
		System.out.println("o: Perform Outgoing Transaction");
		System.out.println("r: Generate Reports");
		System.out.println("x: Exit Program");

		System.out.println();
	}
}