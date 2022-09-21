package main.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class containing miscellaneous convenience methods for helping with command line text input.
 *
 */
public class InputHelper
{
	/**
	 * Promps the user for an integer number input using the enterMessage. Continues to re-prompt the user with errorMessage until
	 * valid input is provided. The FilterCheck object is used to validate input more strictly, such as specifying the
	 * input must be greater than some number x.
	 * 
	 * @param sc
	 *            the object used to get input from the user
	 * @param fc
	 *            the filter used to further restrict valid numerical inputs
	 * @param enterMessage
	 *            prompt the user will see to enter input
	 * @param errorMessage
	 *            prompt shown when the user enters invalid input
	 * @return an integer that is constrained as specified by the FilterCheck.
	 */
	public static int getIntegerInput(Scanner sc, FilterCheck fc, String enterMessage, String errorMessage)
	{
		int amt = fc.getLowerBound() - 1;
		System.out.print(enterMessage);
		try // initial read
		{
			amt = sc.nextInt(); // try to read int
		} catch (InputMismatchException ex)
		{
			amt = fc.getLowerBound() - 1;
		}
		sc.nextLine();
		while (!fc.isValid(amt))
		{
			System.out.print(errorMessage);
			try
			{
				amt = sc.nextInt(); // to catch non-integer inputs
			} catch (InputMismatchException ex)
			{
				amt = fc.getLowerBound() - 1;
			}
			sc.nextLine(); // consume the leftover new line
		}
		return amt;
	}

	public static boolean getYorN(Scanner sc, String outputMessage)
	{
		System.out.println(outputMessage + "(y or n): ");
		String input = sc.nextLine();

		return input.trim().equals("y");
	}

	/**
	 * Checks if a string is composed of only numeric digits.
	 * Note: This method returns false for negative numbers!!
	 * 
	 * @param s
	 *            the string to check.
	 * @return false if any character in the string is not a digit, otherwise true.
	 */
	public static boolean isNumber(String s)
	{
		for (char c : s.toCharArray())
			if (!Character.isDigit(c))
				return false;
		return true;
	}

	/**
	 * Prompts the user to enter a valid number or the letter 'a' for all.
	 * This method returns the lower bound of the provided FilterCheck when the user enters 'a'.
	 * Otherwise the method returns the user's entered number.
	 * 
	 * @param sc
	 *            used for getting user input
	 * @param fc
	 *            constrains the integer input
	 * @param enterMessage
	 *            message printed to prompt user the first time
	 * @param errorMessage
	 *            message printed to tell user there was an error with their input
	 * @return if the user entered 'a' then the lower bound of the FilterCheck is returned, otherwise the number the user entered is returned.
	 */
	public static int allOrNum(Scanner sc, FilterCheck fc, String enterMessage, String errorMessage)
	{
		System.out.print(enterMessage + "(enter 'a' for all, or a valid number): ");
		String input = sc.nextLine();
		int numInput = fc.getLowerBound() - 1;
		if (input.equals("a"))
			numInput = fc.getLowerBound();
		else if (isNumber(input))
			numInput = Integer.parseInt(input);
		else
			numInput = fc.getLowerBound() - 1;
		if (!fc.isValid(numInput))
		{
			System.out.print(errorMessage + "(enter 'a' for all, or a valid number): ");
			input = sc.nextLine();
		}

		while (!fc.isValid(numInput))
		{
			System.out.println(numInput + " " + fc.isValid(numInput));
			if (input.equals("a"))
				numInput = fc.getLowerBound();
			else if (isNumber(input))
				numInput = Integer.parseInt(input);
			else
				numInput = fc.getLowerBound() - 1;
			if (!fc.isValid(numInput))
			{
				System.out.print(errorMessage + "(enter 'a' for all, or a valid number): ");
				input = sc.nextLine();
			}
		}
		return numInput;
	}
}
