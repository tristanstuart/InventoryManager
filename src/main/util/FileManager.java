package main.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stocks.Product;
import stocks.StockManager;
import stocks.Store;
import transactions.IncomingTransaction;
import transactions.OutgoingTransaction;

public class FileManager
{

	static String incomingTransactionsFile = "incoming_transactions.txt";
	static String outgoingTransactionsFile = "outgoing_transactions.txt";
	static String productsFile = "products.txt";
	static String storessFile = "stores.txt";
	static String DELIMITER = ",";

	public static FileManager instance;

	public static FileManager getInstance()
	{
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	private FileManager()
	{

	}

	public String readFile(Path filePath) throws IOException
	{
		String content = "";
		if (Files.notExists(filePath))
		{
			Files.createFile(filePath);
		} else
		{
			content = Files.readString(filePath);
		}
		return content;
	}

	public void writeFile(Path filePath, String text) throws IOException
	{
		if (Files.notExists(filePath))
		{
			Files.createFile(filePath);
		}

		Files.writeString(filePath, text);
	}

	public List<Product> readProducts()
	{
		List<Product> products = new ArrayList<Product>();
		try
		{
			String content = readFile(Paths.get(productsFile));
			final String[] lines = content.split(System.lineSeparator());
			for (final String line : lines)
			{
				final String[] col = line.split(DELIMITER);
				Product p = null;
				if (col.length < 3)
				{
					break;
				}
				try
				{
					p = new Product(col[1], Integer.parseInt(col[2]));
				} catch (NumberFormatException nfe)
				{
					System.out.println("Number format error.");
				}
				if (p != null)
				{
					products.add(p);
				}
			}
		} catch (IOException e)
		{
			System.out.println("File read error: " + productsFile);
			e.printStackTrace();
		}

		return products;
	}

	public void writeProducts(List<Product> products)
	{
		try
		{
			String content = "";
			for (Product p : products)
			{
				content += p.toString() + "," + System.lineSeparator();
			}
			writeFile(Paths.get(productsFile), content);
		} catch (IOException e)
		{
			System.out.println("File read error: " + productsFile);
			e.printStackTrace();
		}
	}

	public List<Store> readStores()
	{
		List<Store> stores = new ArrayList<Store>();
		try
		{
			String content = readFile(Paths.get(storessFile));
			final String[] lines = content.split(System.lineSeparator());
			for (final String line : lines)
			{
				final String[] col = line.split(DELIMITER);
				Store s = null;
				if (col.length < 3)
				{
					break;
				}
				try
				{
					s = new Store(col[1], col[2]);
				} catch (NumberFormatException nfe)
				{
					System.out.println("Number format error.");
				}
				if (s != null)
				{
					stores.add(s);
				}
			}
		} catch (IOException e)
		{
			System.out.println("File read error: " + storessFile);
			e.printStackTrace();
		}
		return stores;
	}

	public void writeStores(List<Store> stores)
	{
		try
		{
			String content = "";
			for (Store s : stores)
			{
				content += s.toString() + "," + System.lineSeparator();
			}
			writeFile(Paths.get(storessFile), content);
		} catch (IOException e)
		{
			System.out.println("File read error: " + storessFile);
			e.printStackTrace();
		}
	}

	public List<IncomingTransaction> readIncomingTransactions()
	{
		List<IncomingTransaction> its = new ArrayList<IncomingTransaction>();
		try
		{
			String content = readFile(Paths.get(incomingTransactionsFile));
			final String[] lines = content.split(System.lineSeparator());

			IncomingTransaction it = null;
			for (final String line : lines)
			{
				final String[] col = line.split(DELIMITER);
				if (col.length < 2)
				{
					// do nothing
				} else if (col.length == 2)
				{
					try
					{
						Product pr = StockManager.getInstance().findProductByID(Integer.parseInt(col[0]));
						it.addProduct(pr, Integer.parseInt(col[1]));
					} catch (NumberFormatException nfe)
					{
						System.out.println("Number format error.");
					}
				} else
				{
					it = new IncomingTransaction(new Date(col[1]));
					its.add(it);
				}
			}
		} catch (IOException e)
		{
			System.out.println("File read error: " + incomingTransactionsFile);
			e.printStackTrace();
		}

		return its;
	}

	public void writeIncomingTransactions(List<IncomingTransaction> its)
	{
		try
		{
			String content = "";
			for (IncomingTransaction it : its)
			{
				content += it.toString() + System.lineSeparator();
			}
			writeFile(Paths.get(incomingTransactionsFile), content);
		} catch (IOException e)
		{
			System.out.println("File read error: " + incomingTransactionsFile);
			e.printStackTrace();
		}
	}

	public List<OutgoingTransaction> readOutgoingTransactions()
	{
		List<OutgoingTransaction> ots = new ArrayList<OutgoingTransaction>();
		try
		{
			String content = readFile(Paths.get(outgoingTransactionsFile));
			final String[] lines = content.split(System.lineSeparator());

			OutgoingTransaction ot = null;
			for (final String line : lines)
			{
				final String[] col = line.split(DELIMITER);
				if (col.length < 2)
				{
					// do nothing
				} else if (col.length == 2)
				{
					try
					{
						Product pr = StockManager.getInstance().findProductByID(Integer.parseInt(col[0]));
						ot.addProduct(pr, Integer.parseInt(col[1]));
					} catch (NumberFormatException nfe)
					{
						System.out.println("Number format error.");
					}
				} else
				{
					try
					{
						Store s = StockManager.getInstance().findStoreByID(Integer.parseInt(col[0]));
						ot = new OutgoingTransaction(s, new Date(col[2]));
						ots.add(ot);
					} catch (NumberFormatException nfe)
					{
						System.out.println("Number format error.");
					}
				}
			}
		} catch (IOException e)
		{
			System.out.println("File read error: " + outgoingTransactionsFile);
			e.printStackTrace();
		}

		return ots;
	}

	public void writeOutgoingTransactions(List<OutgoingTransaction> ots)
	{
		try
		{
			String content = "";
			for (OutgoingTransaction ot : ots)
			{
				content += ot.toString() + System.lineSeparator();
			}
			writeFile(Paths.get(outgoingTransactionsFile), content);
		} catch (IOException e)
		{
			System.out.println("File read error: " + outgoingTransactionsFile);
			e.printStackTrace();
		}
	}
}