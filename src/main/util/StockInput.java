package main.util;

import java.util.Scanner;

import stocks.Product;
import stocks.Store;

/**
 * 
 * Used to contain all the command-line input methods for constructing {@link Product} and {@link Store}.
 *
 */
public class StockInput
{

	/**
	 * Constructs a product using a command-line input stream.
	 * 
	 * @param sc
	 *            The active input stream to be used in constructing the Product object, expected to be command-line.
	 * @return A {@link Product} constructed with the data provided by input from sc parameter.
	 */
	public static Product doNewProductInput(Scanner sc)
	{
		String name = "";
		System.out.print("Enter product name: ");
		while ((name = sc.nextLine().trim()).equals("") && !name.contains("|")) // values cannot contain the | character because that is used in file formatting
			System.out.print("Product name may not be empty and cannot contain the character '|'.\nPlease enter a valid product name:");

		int amt = InputHelper.getIntegerInput(sc, new FilterCheck(1), "Enter product amount: ", "The product amount must be a number and greater than zero. Please enter a valid product amount: ");

		return new Product(name, amt);
	}

	/**
	 * Constructs a store using a command-line input stream.
	 * 
	 * @param sc
	 *            The active input stream to be used in constructing the Store object, expected to be command-line.
	 * @return A {@link Store} constructed with the data provided by input from sc parameter.
	 */
	public static Store doNewStoreInput(Scanner sc)
	{
		String name = "";
		System.out.print("Enter store name: ");
		while ((name = sc.nextLine().trim()).equals("") && !name.contains("|")) // values cannot contain the | character because that is used in file formatting
			System.out.print("Store name may not be empty and cannot contain the character '|'.\nPlease enter a valid store name:");

		String addr = "";
		System.out.print("Enter store address: ");
		while ((addr = sc.nextLine().trim()).equals("") && !addr.contains("|")) // values cannot contain the | character because that is used in file formatting
			System.out.print("Store address may not be empty and cannot contain the character '|'.\nPlease enter a valid store address:");

		return new Store(name, addr);
	}
}
