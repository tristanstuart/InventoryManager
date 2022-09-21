package main.util;

/**
 * Used by {@link InputHelper#getIntegerInput(java.util.Scanner, FilterCheck, String, String)} to constrain which integer inputs are valid.
 *
 */
public class FilterCheck
{
	/**
	 * The lower boundary of valid integer inputs.
	 */
	private int lowerBound;

	public FilterCheck(int i)
	{
		lowerBound = i;
	}

	public int getLowerBound()
	{
		return lowerBound;
	}

	/**
	 * Determines if the provided integer passes the filter.
	 * 
	 * @param i
	 *            input integer to test against filter.
	 * @return true if the integer is vald, otherwise false.
	 */
	public boolean isValid(int i)
	{
		return i >= lowerBound;
	}
}
