package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import reports.AllItemsEnteredReport;
import reports.AllTransactionForProductReport;
import reports.AvailableItemsReport;
import reports.HighVolumeReport;
import reports.ProductByStoreReport;
import reports.ReportThread;
import reports.TransactionsByMonthReport;
import stocks.Product;
import stocks.StockManager;
import transactions.TransactionsManager;

public class CreateReportsGUI extends JDialog
{
	// variables are categorized by report, separated by newlines
	private ArrayList<JLabel> spacers;
	private JPanel panel, leftHalf, rightHalf;

	private JCheckBox allItemsReport;

	private JCheckBox availableItemsReport;

	private JCheckBox highVolumeReport;
	private JRadioButton highVolumeIncoming, highVolumeOutgoing;

	private JCheckBox productByStoreReport;
	private JCheckBox productByStoreAllStoresSelector;
	private JComboBox<StoreComboBoxWrapper> productByStoreSelector;

	private JCheckBox allTransactionsForProductReport;
	private JRadioButton allTransactionsForProductIncoming, allTransactionsForProductOutgoing;
	private JComboBox<ProductComboBoxWrapper> allTransactionsForProductSelector;

	private JCheckBox transactionByMonthReport;
	private JRadioButton transactionByMonthIncoming, transactionByMonthOutgoing;
	private JComboBox<String> transactionByMonthSelector;

	private JButton generateButton;

	public CreateReportsGUI()
	{
		super(GUIManager.getInstance().getMainMenuGUI(), "Report Selection", true);
		createGUI();
	}

	/**
	 * separates the reports into halves across the window, half on the left, half on the right
	 * 
	 */
	private void createGUI()
	{
		StockManager inventory = StockManager.getInstance();
		TransactionsManager tm = TransactionsManager.getInstance();

		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints panelConstraints = new GridBagConstraints();

		spacers = new ArrayList<JLabel>();

		leftHalf = new JPanel();
		rightHalf = new JPanel();
		generateButton = new JButton("Generate Reports");
		generateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String error = getErrorForGeneratingReports();// checks for any possible error with all selected reports
				if (error != null)// null if no errors
					JOptionPane.showMessageDialog(panel, error, "ERROR", JOptionPane.ERROR_MESSAGE);
				else
				{
					GUIManager.getInstance().generateReports(getReportMap());
					dispose();
				}
			}
		});
		panelConstraints.gridx = 0;
		panelConstraints.insets = new Insets(5, 5, 5, 5);
		panelConstraints.anchor = GridBagConstraints.NORTH;
		panel.add(leftHalf, panelConstraints);
		panelConstraints.gridx = 1;
		panel.add(rightHalf, panelConstraints);

		setContentPane(panel);

		allItemsReport = new JCheckBox("All Items Report");
		availableItemsReport = new JCheckBox("Available Items Report");

		highVolumeReport = new JCheckBox("High Volume Items Report");

		highVolumeIncoming = new JRadioButton("Incoming Transactions");
		highVolumeOutgoing = new JRadioButton("Outgoing Transactions");
		ButtonGroup highVolumeButtonGroup = new ButtonGroup();
		highVolumeButtonGroup.add(highVolumeIncoming);
		highVolumeButtonGroup.add(highVolumeOutgoing);
		highVolumeIncoming.setEnabled(false);
		highVolumeOutgoing.setEnabled(false);
		highVolumeReport.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean enabled = highVolumeReport.isSelected();
				highVolumeIncoming.setEnabled(enabled);
				highVolumeOutgoing.setEnabled(enabled);
			}
		});

		productByStoreReport = new JCheckBox("Product at Stores Report");
		productByStoreAllStoresSelector = new JCheckBox("Use All Stores");
		productByStoreSelector = new JComboBox<StoreComboBoxWrapper>(StoreComboBoxWrapper.wrapStoreListForDisplay());
		productByStoreAllStoresSelector.setEnabled(false);
		productByStoreSelector.setEnabled(false);
		productByStoreAllStoresSelector.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean enabled = productByStoreAllStoresSelector.isSelected();
				productByStoreSelector.setEnabled(!enabled);
			}
		});
		productByStoreReport.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean enabled = productByStoreReport.isSelected();
				productByStoreAllStoresSelector.setEnabled(enabled);
				productByStoreSelector.setEnabled(enabled);
			}
		});

		allTransactionsForProductReport = new JCheckBox("Transactions for a Product Report");
		allTransactionsForProductIncoming = new JRadioButton("Incoming Transactions");
		allTransactionsForProductOutgoing = new JRadioButton("Outgoing Transactions");
		allTransactionsForProductSelector = new JComboBox<ProductComboBoxWrapper>(ProductComboBoxWrapper.wrapProductListForDisplay());
		ButtonGroup allTransactionForProductButtonGroup = new ButtonGroup();
		allTransactionForProductButtonGroup.add(allTransactionsForProductIncoming);
		allTransactionForProductButtonGroup.add(allTransactionsForProductOutgoing);
		allTransactionsForProductIncoming.setEnabled(false);
		allTransactionsForProductOutgoing.setEnabled(false);
		allTransactionsForProductSelector.setEnabled(false);
		allTransactionsForProductReport.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean enabled = allTransactionsForProductReport.isSelected();
				allTransactionsForProductIncoming.setEnabled(enabled);
				allTransactionsForProductOutgoing.setEnabled(enabled);
				allTransactionsForProductSelector.setEnabled(enabled);
			}
		});

		transactionByMonthReport = new JCheckBox("Transactions Filtered by Month Report");
		transactionByMonthIncoming = new JRadioButton("Incoming Transactions");
		transactionByMonthOutgoing = new JRadioButton("Outgoing Transactions");
		transactionByMonthSelector = new JComboBox<String>(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "All Months" });
		ButtonGroup transactionsByMonthButtonGroup = new ButtonGroup();
		transactionsByMonthButtonGroup.add(transactionByMonthIncoming);
		transactionsByMonthButtonGroup.add(transactionByMonthOutgoing);
		transactionByMonthIncoming.setEnabled(false);
		transactionByMonthOutgoing.setEnabled(false);
		transactionByMonthSelector.setEnabled(false);
		transactionByMonthReport.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean enabled = transactionByMonthReport.isSelected();
				transactionByMonthIncoming.setEnabled(enabled);
				transactionByMonthOutgoing.setEnabled(enabled);
				transactionByMonthSelector.setEnabled(enabled);
			}
		});

		/*=============================================LEFT HALF LAYOUT======================================================*/
		leftHalf.setLayout(new GridBagLayout());
		GridBagConstraints leftConstraints = new GridBagConstraints();
		Insets defaultInsets = leftConstraints.insets;
		Insets spacerInsets = new Insets(5, 0, 5, 0);
		Insets subItemInsets = new Insets(0, 20, 0, 0);

		/*---------------------------------------------ALL ITEMS REPORT------------------------------------------------------*/
		leftConstraints.anchor = GridBagConstraints.WEST;
		leftConstraints.gridx = 0;
		leftConstraints.gridy = 0;
		leftConstraints.weightx = 1.0;
		leftHalf.add(allItemsReport, leftConstraints);

		leftConstraints.gridy++;
		leftConstraints.insets = spacerInsets;
		leftHalf.add(createSpacer(), leftConstraints);

		/*--------------------------------------------HIGH VOLUME REPORT-----------------------------------------------------*/
		leftConstraints.gridy++;
		leftConstraints.insets = defaultInsets;
		leftHalf.add(highVolumeReport, leftConstraints);

		leftConstraints.gridy++;
		leftConstraints.insets = subItemInsets;
		leftHalf.add(highVolumeIncoming, leftConstraints);

		leftConstraints.gridy++;
		leftHalf.add(highVolumeOutgoing, leftConstraints);

		leftConstraints.gridy++;
		leftConstraints.insets = spacerInsets;
		leftHalf.add(createSpacer(), leftConstraints);

		/*---------------------------------------ALL TRANSACTIONS FOR PRODUCT------------------------------------------------*/
		leftConstraints.gridy++;
		leftConstraints.insets = defaultInsets;
		leftHalf.add(allTransactionsForProductReport, leftConstraints);

		leftConstraints.gridy++;
		leftConstraints.insets = subItemInsets;
		leftHalf.add(allTransactionsForProductIncoming, leftConstraints);

		leftConstraints.gridy++;
		leftHalf.add(allTransactionsForProductOutgoing, leftConstraints);

		leftConstraints.gridy++;
		leftHalf.add(allTransactionsForProductSelector, leftConstraints);

		leftConstraints.gridy++;
		leftConstraints.insets = spacerInsets;
		leftHalf.add(createSpacer(), leftConstraints);
		leftConstraints.gridy++;
		leftHalf.add(createSpacer(), leftConstraints);

		leftConstraints.gridy++;
		leftConstraints.insets = new Insets(0, 10, 0, 10);
		leftHalf.add(generateButton, leftConstraints);

		/*==========================================RIGHT HALF LAYOUT========================================================*/
		rightHalf.setLayout(new GridBagLayout());
		GridBagConstraints rightConstraints = new GridBagConstraints();
		defaultInsets = rightConstraints.insets;

		/*------------------------------------------AVAILABLE ITEMS REPORT---------------------------------------------------*/
		rightConstraints.anchor = GridBagConstraints.WEST;
		rightConstraints.gridx = 0;
		rightConstraints.gridy = 0;
		rightConstraints.weightx = 1.0;
		rightHalf.add(availableItemsReport, rightConstraints);

		rightConstraints.gridy++;
		rightConstraints.insets = spacerInsets;
		rightHalf.add(createSpacer(), rightConstraints);

		/*------------------------------------------PRODUCT BY STORE REPORT--------------------------------------------------*/
		rightConstraints.gridy++;
		rightConstraints.insets = defaultInsets;
		rightHalf.add(productByStoreReport, rightConstraints);

		rightConstraints.gridy++;
		rightConstraints.insets = subItemInsets;
		rightHalf.add(productByStoreAllStoresSelector, rightConstraints);

		rightConstraints.gridy++;
		rightHalf.add(productByStoreSelector, rightConstraints);

		rightConstraints.gridy++;
		rightConstraints.insets = spacerInsets;
		rightHalf.add(createSpacer(), rightConstraints);

		/*---------------------------------------TRANSACTIONS BY MONTH REPORT------------------------------------------------*/
		rightConstraints.gridy++;
		rightConstraints.insets = defaultInsets;
		rightHalf.add(transactionByMonthReport, rightConstraints);

		rightConstraints.gridy++;
		rightConstraints.insets = subItemInsets;
		rightHalf.add(transactionByMonthIncoming, rightConstraints);

		rightConstraints.gridy++;
		rightHalf.add(transactionByMonthOutgoing, rightConstraints);

		rightConstraints.gridy++;
		rightHalf.add(transactionByMonthSelector, rightConstraints);
	}

	/**
	 * Checks all selected reports for potential errors.
	 * 
	 * @return an error message if there is one, otherwise null
	 */
	private String getErrorForGeneratingReports()
	{
		boolean noneSelected = true;
		if (highVolumeReport.isSelected())
		{
			noneSelected = false;
			if (!highVolumeIncoming.isSelected() && !highVolumeOutgoing.isSelected())
				return "Please select either incoming or outgoing transactions for the High Volume Products Report!";
		} else if (allTransactionsForProductReport.isSelected())
		{
			noneSelected = false;
			if (!allTransactionsForProductIncoming.isSelected() && !allTransactionsForProductOutgoing.isSelected())
				return "Please select either incoming or outgoing transactions for the Transactions for a Product Report!";
			else if (allTransactionsForProductSelector.getSelectedIndex() == -1)
				return "Please select a product for the the Transactions for a Product Report!";
		} else if (productByStoreReport.isSelected())
		{
			noneSelected = false;
			if (!productByStoreAllStoresSelector.isSelected() && productByStoreSelector.getSelectedIndex() == -1)
				return "Please select either all stores or a valid store for the Products at a Store Report!";
		} else if (transactionByMonthReport.isSelected())
		{
			noneSelected = false;
			if (!transactionByMonthIncoming.isSelected() && !transactionByMonthOutgoing.isSelected())
				return "Please select either incoming or outgoing transactions for the Transactions Filtered by Month Report!";
			else if (transactionByMonthSelector.getSelectedIndex() == -1)
				return "Please selected a valid month for the Transactions Filtered by Month Report!";
		}

		if (noneSelected && !allItemsReport.isSelected() && !availableItemsReport.isSelected())
			return "Please select at least one report to generate!";

		return null;
	}

	/**
	 * Gathers selected reports and creates the associated ReportThread objects.
	 * The created ReportThread objects are not run, just created.
	 * 
	 * @return a map of Report titles and their associated ReportThreads.
	 */
	private Map<String, ReportThread> getReportMap()
	{
		Map<String, ReportThread> generatedReports = new HashMap<String, ReportThread>();
		if (allItemsReport.isSelected())
			generatedReports.put(allItemsReport.getText(), new ReportThread(new AllItemsEnteredReport()));
		if (availableItemsReport.isSelected())
			generatedReports.put(availableItemsReport.getText(), new ReportThread(new AvailableItemsReport()));
		if (highVolumeReport.isSelected())
			generatedReports.put(highVolumeReport.getText(), new ReportThread(new HighVolumeReport(highVolumeIncoming.isSelected() ? 'i' : 'o')));
		if (productByStoreReport.isSelected())
		{
			int storeID = -1;
			if (!productByStoreAllStoresSelector.isSelected())
				storeID = ((StoreComboBoxWrapper) productByStoreSelector.getSelectedItem()).getStore().getID();
			generatedReports.put(productByStoreReport.getText(), new ReportThread(new ProductByStoreReport(storeID)));
		}
		if (transactionByMonthReport.isSelected())
		{
			int month = transactionByMonthSelector.getSelectedIndex();
			if (month == 12)
				month = -1;
			generatedReports.put(transactionByMonthReport.getText(), new ReportThread(new TransactionsByMonthReport(transactionByMonthIncoming.isSelected() ? 'i' : 'o', month)));
		}
		if (allTransactionsForProductReport.isSelected())
		{
			Product product = ((ProductComboBoxWrapper) allTransactionsForProductSelector.getSelectedItem()).getProduct();
			generatedReports.put(allTransactionsForProductReport.getText(), new ReportThread(new AllTransactionForProductReport(allTransactionsForProductIncoming.isSelected() ? 'i' : 'o', product)));
		}

		return generatedReports;
	}

	/**
	 * convenience method for UI spacers
	 * 
	 * @return a textless JLabel
	 */
	private JLabel createSpacer()
	{
		JLabel l = new JLabel();
		spacers.add(l);
		return l;
	}
}
