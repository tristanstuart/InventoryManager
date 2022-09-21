package gui;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import reports.ReportThread;

/**
 * Implements Singleton design pattern. call {@link #getInstance()} to use.
 * Mostly contains methods to create various GUI windows used by the program.
 */
public class GUIManager
{
	private static GUIManager instance;
	private static MenuView mainMenu;

	public static GUIManager getInstance()
	{
		if (instance == null)
			instance = new GUIManager();

		return instance;
	}

	public JFrame getMainMenuGUI()
	{
		if (mainMenu == null)
			mainMenu = new MenuView();
		return mainMenu;
	}

	public void updateMenuView()
	{
		mainMenu.updateLabelDisplays();
	}

	public void createMainGUI()
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				getMainMenuGUI().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				mainMenu.pack();
				mainMenu.setLocationRelativeTo(null);
				mainMenu.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	public void spawnCreateProductGUI()
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				CreateProductGUI frame = new CreateProductGUI();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(mainMenu);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	public void spawnCreateStoreGUI()
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				CreateStoreGUI frame = new CreateStoreGUI();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(mainMenu);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	public void spawnCreateIncomingTransactionGUI()
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				CreateIncomingTransactionGUI frame = new CreateIncomingTransactionGUI();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(mainMenu);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	public void spawnCreateOutgoingTransactionGUI()
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				CreateOutgoingTransactionGUI frame = new CreateOutgoingTransactionGUI();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(mainMenu);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	public void spawnCreateReportsGUI()
	{
		GUIManager instance = this;
		Runnable r = new Runnable()
		{
			public void run()
			{
				CreateReportsGUI frame = new CreateReportsGUI();
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(mainMenu);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Creates and shows a new ReportDisplayGUI
	 * 
	 * @param reports
	 *            The title of each report mapped to a ReportThread for each report, all to be added to the GUI
	 */
	public void spawnReportDisplayGUI(Map<String, ReportThread> reports)
	{
		Runnable r = new Runnable()
		{
			public void run()
			{
				ReportDisplayGUI frame = new ReportDisplayGUI(reports);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(mainMenu);
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Takes a series of requested reports and generates them. Also calls {@link #spawnReportDisplayGUI(Map)} after generation is complete.
	 * 
	 * @param generatedReports
	 *            Map of reports to be generated, keys should be report titles, values should be a new, not yet started, ReportThread.
	 */
	public void generateReports(Map<String, ReportThread> generatedReports)
	{
		Thread[] threadList = new Thread[generatedReports.size()];
		int i = 0;
		for (ReportThread r : generatedReports.values())// create and start a thread for each report to generate
		{
			Thread t = new Thread(r);
			threadList[i++] = t;
			t.start();
		}

		boolean running = true;
		while (running)// wait for all the threads to finish
		{
			running = false;
			for (Thread r : threadList)
				if (r.isAlive())
				{
					running = true;
					continue;
				}
		}

		spawnReportDisplayGUI(generatedReports);
	}
}
