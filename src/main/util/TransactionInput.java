package main.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import stocks.Product;
import stocks.StockManager;
import stocks.Store;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.Transaction;
import transactions.TransactionsManager;

/**
 * Used to contain all the command-line and file input methods for constructing {@link IncomingTransaction} and {@link OutgoingTransaction}.
 */
public class TransactionInput
{
	/**
	 * Creates an incoming transaction from input. Most of the functionality is in {@link #addProductsToTransaction(Scanner, Transaction, StockManager)}
	 * 
	 * @param sc
	 *            the object from which to get user input.
	 * @return the generated IncomingTransaction object.
	 */
	public static IncomingTransaction doNewIncomingTransaction(Scanner sc)
	{
		IncomingTransaction transaction = new IncomingTransaction();
		transaction = (IncomingTransaction) addProductsToTransaction(sc, transaction);

		return transaction;
	}

	/**
	 * Creates an outgoing transaction from input. Most of the functionality is in {@link #addProductsToTransaction(Scanner, Transaction, StockManager)}
	 * 
	 * @param sc
	 *            the object from which to get user input.
	 * @return the generated OutgoingTransaction object.
	 */
	public static OutgoingTransaction doNewOutgoingTransaction(Scanner sc)
	{
		StockManager inventory = StockManager.getInstance();
		System.out.println("List of available stores for the transaction: ");
		for (Store s : inventory.getStoreList()) // print stores in the form 'id: name' for easy selection
			System.out.println(s.getID() + ": " + s.getName());

		FilterCheck modifiedFilter = new FilterCheck(0)
		{
			public boolean isValid(int i)
			{
				return inventory.findStoreByID(i) != null;
			}
		};

		int validID = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a product: ", "Invalid selection, select a valid product from the list: ");
		Store store = inventory.findStoreByID(validID);
		OutgoingTransaction transaction = new OutgoingTransaction(store);

		transaction = (OutgoingTransaction) addProductsToTransaction(sc, transaction);

		return transaction;
	}

	/**
	 * Prompts the user to add products to a provided transaction.
	 * 
	 * @param sc
	 *            used to get input from the user.
	 * @param transaction
	 *            the transaction to which products will be added.
	 * @return the provided transaction, with the products added.
	 */
	private static Transaction addProductsToTransaction(Scanner sc, Transaction transaction)
	{
		StockManager inventory = StockManager.getInstance();

		System.out.println("List of available products for the transaction: ");
		String input = "";
		do
		{
			for (Product p : inventory.getProductList())// print products in the form "id: name" for easy selection
				System.out.println(p.getID() + ": " + p.getName());

			FilterCheck modifiedFilter = new FilterCheck(0)
			{
				public boolean isValid(int i)
				{
					return inventory.findProductByID(i) != null;
				}
			};

			int validID = InputHelper.getIntegerInput(sc, modifiedFilter, "Select a product: ", "Invalid selection, select a valid product from the list: ");
			Product p = inventory.findProductByID(validID);

			int count = InputHelper.getIntegerInput(sc, new FilterCheck(1), "Enter product amount: ", "The product amount must be a number and greater than zero. Please enter a valid product amount: ");
			transaction.addProduct(p, count);

			System.out.print("Would you like to add another product? (y/n): ");
			while ((input = sc.nextLine().trim()).equals(""))
				System.out.print("Please enter either y or n. Would you like to add another product? ");

		} while (input.equals("y"));

		return transaction;
	}

	/**
	 * Loops each line of the provided transaction file and processes the transactions. Each line must be of
	 * the format "storeID productID productCount productID productCount ...".
	 * StoreID must be a valid store ID for outgoing transactions, or the letter "i" for incoming transactions.
	 * ProductID must be a valid product ID and productCount must be the designated count for the preceding product ID.
	 * The number of productID/productCount pairs can be as many as needed, so long as they are always in pairs.
	 * <p>
	 * For example, the line "i 1 33 2 44"
	 * <p>
	 * Would result in an incoming transaction where the product with id 1 adds 33 to its stock and the product with
	 * id 2 adds 44 to its stock.
	 * <p>
	 * Similarly, "1 2 5 3 6"
	 * <p>
	 * Would result in an outgoing transaction to the store with id 1 in which product id 2 sends 5 stock and product id 3 sends 6 stock.
	 * 
	 * @param fileName
	 *            the name of the file to be processed
	 */
	public static void processTransactionFile(String fileName)
	{
		Scanner sc = null;

		try
		{
			FileInputStream fis = new FileInputStream(fileName);
			sc = new Scanner(fis);
		} catch (FileNotFoundException e)
		{
			sc = null;
		}
		if (sc == null) // something went wrong with opening output file and creating PrintWriter
		{
			System.out.println("There was an error with the provided transaction input file. Please verify the file name and try again.");
			return; // cancel attempted file write
		}

		int fileLn = 0;
		while (sc.hasNext())
		{
			fileLn++;

			String input = sc.nextLine();
			String data[] = input.split(" ");
			/* If there isnt a type indicator and at least one product id-count pair, then the transaction is malformed.
			   Also the number of elements must be odd, 1 for the type indicator and 2*n for n products, where a product is
			   a pair of entrys in the form "ID count".
			 */
			if (data.length < 3 || data.length % 2 != 1)
			{
				System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 1");
				continue;
			}

			Transaction transaction = null;
			if (data[0].trim().equals("i"))// incoming transaction type indicator
			{
				transaction = new IncomingTransaction();
			} else if (InputHelper.isNumber(data[0]))// outgoing transaction type indicator
			{
				int storeNumber = Integer.parseInt(data[0].trim());
				Store store = StockManager.getInstance().findStoreByID(storeNumber);
				if (store == null)// couldnt find store in list
				{
					System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 2");
					continue;
				}
				transaction = new OutgoingTransaction(store);
			} else// not 'i' and not a number meaning it is malformed
			{
				System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 3");
				continue;
			}
			// can assume transaction successfully setup and formatted correctly from here

			boolean productError = false;
			for (int i = 1; i < data.length; i += 2)
			{
				if (!InputHelper.isNumber(data[i]) || !InputHelper.isNumber(data[i + 1]))// stop if the product id or count arent numbers
				{
					productError = true;
					break;
				}

				int productID = Integer.parseInt(data[i].trim());
				Product product = StockManager.getInstance().findProductByID(productID);
				if (product == null)// stop if product id doesnt lead to real product
				{
					productError = true;
					break;
				}
				int productCount = Integer.parseInt(data[i + 1].trim());

				transaction.addProduct(product, productCount);// no errors, add product to transaction
			}
			if (productError)
			{
				System.out.println("Encountered malformed transaction on line " + fileLn + ". This line will be skipped. 5");
				continue;
			}
			TransactionsManager.getInstance().addTransaction(transaction);
		}
	}
}
