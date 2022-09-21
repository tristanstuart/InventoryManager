package reports;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stocks.Product;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;
import transactions.TransactionsManager;

public class HighVolumeReport implements Report
{

	private char incomingOrOutgoing;
	private String output;

	public HighVolumeReport(char inOrOut)
	{
		output = "";
		incomingOrOutgoing = inOrOut;
		streamCollectProductVolumes();
	}

	@Override
	public void printReport()
	{
		System.out.println(generateReport());
	}

	@Override
	public String generateReport()
	{
		return "High Volume " + (incomingOrOutgoing == 'i' ? "Incoming" : "Outgoing") + " Items:\nProducts appear in the format: name(id): amount\n" + output;
	}

	private void streamCollectProductVolumes()
	{
		TransactionsManager transactionsManager = TransactionsManager.getInstance();// convenience variable for multiple accesses

		final Map<Product, Integer> volumeReport = new HashMap<Product, Integer>();
		(incomingOrOutgoing == 'i' ? transactionsManager.getIncomingTransactions() : transactionsManager.getOutgoingTransactions()).stream().forEach(i -> Arrays.stream(i.getProductListForTransaction()).forEach(p -> volumeReport.put(p, i.getNumProductInTransaction(p) + (volumeReport.get(p) != null ? volumeReport.get(p) : 0))));

		while (!volumeReport.isEmpty())
		{
			Product highest = null;
			for (Product p : volumeReport.keySet())
				if (highest == null)
					highest = p;
				else if (volumeReport.get(p) > volumeReport.get(highest))
					highest = p;
			output += highest.getName() + "(" + highest.getID() + "): " + volumeReport.remove(highest) + "\n";
		}
	}

	@Deprecated
	/**
	 * Old helper method used to collect product data from transactions. See {@link #streamCollectProductVolumes(TransactionsManager)} for new method.
	 * 
	 * @param transactionsManager
	 *            Transaction manager that contains the transactions being reported on.
	 */
	private void collectProductVolumes(TransactionsManager transactionsManager)
	{
		Map<Product, Integer> volumeReport = new HashMap<Product, Integer>();
		if (incomingOrOutgoing == 'i')
		{
			List<IncomingTransaction> incoming = transactionsManager.getIncomingTransactions();
			for (IncomingTransaction it : incoming)
				for (Product p : it.getProductListForTransaction())
				{
					int amt = it.getNumProductInTransaction(p);
					if (volumeReport.get(p) != null)
					{
						int volume = volumeReport.get(p);
						volumeReport.put(p, volume + amt);
					} else
						volumeReport.put(p, amt);
				}
		} else // incomingOrOutgoing must be 'o'
		{
			List<OutgoingTransaction> outgoing = transactionsManager.getOutgoingTransactions();
			for (OutgoingTransaction ot : outgoing)
				for (Product p : ot.getProductListForTransaction())
				{
					int amt = ot.getNumProductInTransaction(p);
					if (volumeReport.get(p) != null)
					{
						int volume = volumeReport.get(p);
						volumeReport.put(p, volume + amt);
					} else
						volumeReport.put(p, amt);
				}
		}

		while (!volumeReport.isEmpty())
		{
			Product highest = null;
			for (Product p : volumeReport.keySet())
				if (highest == null)
					highest = p;
				else if (volumeReport.get(p) > volumeReport.get(highest))
					highest = p;
			output += highest.getName() + "(" + highest.getID() + "): " + volumeReport.remove(highest) + "\n";
		}
	}
}
