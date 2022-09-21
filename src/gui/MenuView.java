package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import stocks.Product;
import stocks.StockManager;
import stocks.Store;

public class MenuView extends JFrame
{
	private JButton productButton, storeButton, incomingTransactionButton, outgoingTransactionButton, reportsButton;
	private JPanel panel, productPanel, storePanel;
	private LabelDisplay[] productDisplays, storeDisplays;

	public MenuView()
	{
		super("Inventory Manager");
		createUI();
		updateLabelDisplays();
	}

	private void createUI()
	{
		final GUIManager manager = GUIManager.getInstance();
		panel = new JPanel();
		setContentPane(panel);

		productButton = new JButton("Add New Product");
		productButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manager.spawnCreateProductGUI();
			}
		});
		storeButton = new JButton("Add New Store");
		storeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manager.spawnCreateStoreGUI();
			}
		});
		incomingTransactionButton = new JButton("New Incoming Transaction");
		incomingTransactionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manager.spawnCreateIncomingTransactionGUI();
			}
		});
		outgoingTransactionButton = new JButton("New Outgoing Transaction");
		outgoingTransactionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manager.spawnCreateOutgoingTransactionGUI();
			}
		});
		reportsButton = new JButton("Generate Reports");
		reportsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				manager.spawnCreateReportsGUI();
			}
		});

		productPanel = new JPanel();
		storePanel = new JPanel();
		productPanel.setBorder(BorderFactory.createTitledBorder("Products"));
		storePanel.setBorder(BorderFactory.createTitledBorder("Stores"));

		panel.setLayout(new GridBagLayout());

		GridBagConstraints panelConstraints = new GridBagConstraints();
		GridBagConstraints buttonConstraints = new GridBagConstraints();

		panelConstraints.gridx = 0;
		panelConstraints.gridy = 0;
		panelConstraints.insets = new Insets(2, 4, 10, 4);
		panel.add(productPanel, panelConstraints);

		panelConstraints.gridx = 1;
		panel.add(storePanel, panelConstraints);

		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 1;
		panel.add(productButton, buttonConstraints);

		buttonConstraints.gridx = 1;
		panel.add(storeButton, buttonConstraints);

		buttonConstraints.gridy = 2;
		buttonConstraints.gridx = 0;
		panel.add(incomingTransactionButton, buttonConstraints);

		buttonConstraints.gridx = 1;
		panel.add(outgoingTransactionButton, buttonConstraints);

		buttonConstraints.gridy = 3;
		buttonConstraints.gridx = 0;
		panel.add(reportsButton, buttonConstraints);
	}

	/**
	 * Clears old product and store list displays, and re-populates the panels with the new store and product data.
	 * 
	 */
	public void updateLabelDisplays()
	{
		Product[] productData = StockManager.getInstance().getProductList();
		Store[] storeData = StockManager.getInstance().getStoreList();

		if (productDisplays != null)
		{
			for (int i = 0; i < productDisplays.length; i++)
				productPanel.remove(productDisplays[i]);
		}
		if (storeDisplays != null)
		{
			for (int i = 0; i < storeDisplays.length; i++)
				storePanel.remove(storeDisplays[i]);
		}

		productDisplays = new LabelDisplay[productData.length];
		storeDisplays = new LabelDisplay[storeData.length];

		productPanel.setLayout(new GridLayout(productData.length, 1));
		storePanel.setLayout(new GridLayout(storeData.length, 1));

		int longest = 0;
		for (int i = 0; i < productDisplays.length; i++)
		{
			Product p = productData[i];
			productDisplays[i] = new LabelDisplay(p.getName(), new String[] { "ID: " + p.getID(), "Amount: " + p.getCount() });
			productPanel.add(productDisplays[i]);
			int w = productDisplays[i].getWidth();
			if (w > longest)
				longest = w;
		}

		for (int i = 0; i < storeDisplays.length; i++)
		{
			Store s = storeData[i];
			storeDisplays[i] = new LabelDisplay(s.getName(), new String[] { "ID: " + s.getID(), "Address: " + s.getAddress() });
			storePanel.add(storeDisplays[i]);
			int w = storeDisplays[i].getWidth();
			if (w > longest)
				longest = w;
		}

		pack();// updates the window so new data is displayed. Also resizes everything to fit the updated components
	}
}
