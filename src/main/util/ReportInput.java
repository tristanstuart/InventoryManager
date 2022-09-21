package main.util;

import java.util.Scanner;

import reports.AllItemsEnteredReport;
import reports.AllTransactionForProductReport;
import reports.AvailableItemsReport;
import reports.HighVolumeReport;
import reports.ProductByStoreReport;
import reports.TransactionsByMonthReport;
import stocks.Product;
import stocks.StockManager;

/**
 * Helper class containing methods related to input for the reporting section of the program
 */
public class ReportInput
{

	/**
	 * Runs the reporting section of the program. This method contains the main loop
	 * 
	 * @param sc
	 *            object from which to get user input
	 */
	public static void getReportDetails(Scanner sc)
	{
		StockManager systemInventory = StockManager.getInstance();

		String input = "";
		while (!input.equals("x"))
		{
			printReportSubmenu();
			System.out.print("Input an action: ");
			input = sc.nextLine();
			System.out.println();

			if (input.equals("a"))
			{
				new AllItemsEnteredReport().printReport();
			} else if (input.equals("v"))
			{
				new AvailableItemsReport().printReport();
			} else if (input.equals("s"))
			{
				FilterCheck filter = new FilterCheck(-1)// -1 will be the value returned if the user selects all stores
				{
					@Override
					public boolean isValid(int i)
					{
						return i == this.getLowerBound() || systemInventory.findStoreByID(i) != null;
					}
				};
				int store = InputHelper.allOrNum(sc, filter, "Enter a store ID to report on ", "Invalid input ");// returns a valid store id to filter by, or -1 which means all stores

				new ProductByStoreReport(store).printReport();

			} else if (input.equals("t"))
			{
				System.out.print("Incoming or outgoing transactions? (i for incoming, o for outgoing): ");
				String incomingOrOutgoing = sc.nextLine();
				while (!incomingOrOutgoing.equals("i") && !incomingOrOutgoing.equals("o"))
				{
					System.out.print("Invalid input. (i for incoming, o for outgoing): ");
					incomingOrOutgoing = sc.nextLine();
				}
				// input must be 'i' or 'o' to exit loop.

				FilterCheck filter = new FilterCheck(0)// 0 will be the value returned if the user selects all months, 1-12 represent months
				{
					@Override
					public boolean isValid(int i)
					{
						return super.isValid(i) && i <= 12;
					}
				};
				int month = InputHelper.allOrNum(sc, filter, "Enter the number representation of a month, 1=January, 12=December ", "Invalid input ");// returns a valid store id to filter by, or -1 which means all stores

				// subtract 1 from month so that it's on the same range as Date object months.
				new TransactionsByMonthReport(incomingOrOutgoing.charAt(0), month - 1).printReport();

			} else if (input.equals("p"))
			{
				System.out.print("Incoming or outgoing transactions? (i for incoming, o for outgoing): ");
				String incomingOrOutgoing = sc.nextLine();
				while (!incomingOrOutgoing.equals("i") && !incomingOrOutgoing.equals("o"))
				{
					System.out.print("Invalid input. (i for incoming, o for outgoing): ");
					incomingOrOutgoing = sc.nextLine();
				}
				// input must be 'i' or 'o' to exit loop.

				FilterCheck modifiedFilter = new FilterCheck(0)
				{
					public boolean isValid(int i)
					{
						return systemInventory.findProductByID(i) != null;
					}
				};

				int validID = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a product: ", "Invalid selection, select a valid product: ");
				Product p = systemInventory.findProductByID(validID);

				new AllTransactionForProductReport(incomingOrOutgoing.charAt(0), p).printReport();
			} else if (input.equals("h"))
			{
				System.out.print("Incoming or outgoing transactions? (i for incoming, o for outgoing): ");
				String incomingOrOutgoing = sc.nextLine();
				while (!incomingOrOutgoing.equals("i") && !incomingOrOutgoing.equals("o"))
				{
					System.out.print("Invalid input. (i for incoming, o for outgoing): ");
					incomingOrOutgoing = sc.nextLine();
				}
				// input must be 'i' or 'o' to exit loop.

				new HighVolumeReport(incomingOrOutgoing.charAt(0)).printReport();
			} else if (input.equals("x"))
				;
			else
			{
				System.out.println("Invalid input.");
			}
		}
		System.out.println("Returning to main menu...");
	}

	private static void printReportSubmenu()
	{
		System.out.println("\n\nInventory Management Report Submenu");
		System.out.println("--------------------------------");
		System.out.println("a: All Products Report");
		System.out.println("v: Available Products Report");
		System.out.println("s: Store Products Report(all stores or one)");
		System.out.println("t: All Transactions Report (all or monthly)");
		System.out.println("p: Product Transactions Report");
		System.out.println("h: High Volume Products Report (incoming or outgoing)");
		System.out.println("x: Return to Main Menu");

		System.out.println();
	}
}
