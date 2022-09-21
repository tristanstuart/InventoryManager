package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import stocks.StockManager;
import transactions.ThreadedIncomingTransaction;
import transactions.TransactionsManager;

public class CreateIncomingTransactionGUI extends JDialog
{
	private JPanel panel;
	private JLabel idLabel, dateLabel;
	private JTextField idField;
	private JSpinner dateSpinner;
	private JButton cancelButton, confirmButton;

	private JScrollPane scrollableProductList;
	private TransactionProductListGUI productListGUI;

	public CreateIncomingTransactionGUI()
	{
		super(GUIManager.getInstance().getMainMenuGUI(), "Add New Incoming Transaction", true);
		setupUI();
	}

	private void setupUI()
	{
		panel = new JPanel();
		add(panel);

		idField = new JTextField();
		idField.addKeyListener(new KeyAdapter()// limit ID field to only accepting numbers and backspaces, avoids tedious input checks later
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
					e.consume();
			}
		});
		dateSpinner = new JSpinner();
		Calendar cal = new GregorianCalendar();
		Date currentDate = cal.getTime();
		cal.add(Calendar.YEAR, -10);
		Date startDate = cal.getTime();
		cal.add(Calendar.YEAR, 20);
		Date endDate = cal.getTime();
		dateSpinner.setModel(new SpinnerDateModel(currentDate, startDate, endDate, Calendar.YEAR));
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));

		idLabel = new JLabel("ID:");
		idLabel.setLabelFor(idField);
		dateLabel = new JLabel("Date:");
		dateLabel.setLabelFor(dateSpinner);

		confirmButton = new JButton("Okay");
		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String products = productListGUI.verifyProducts();
				if (idField.getText().length() == 0)// error if id field is empty
					JOptionPane.showMessageDialog(panel, "The transaction must have an ID!", "ERROR", JOptionPane.ERROR_MESSAGE);
				else if (products != null)// verifyProducts() returns errors, or null if no errors
					JOptionPane.showMessageDialog(panel, products, "ERROR", JOptionPane.ERROR_MESSAGE);
				else
				{
					ThreadedIncomingTransaction it = new ThreadedIncomingTransaction(Integer.parseInt(idField.getText()), (Date) dateSpinner.getValue());
					productListGUI.addProductsToTransaction(it);

					TransactionsManager.getInstance().addTransaction(it);
					StockManager.getInstance().saveUpdatedStock();// save changes to file
					GUIManager.getInstance().updateMenuView();// update the main menu window with changes

					dispose();
				}
			}
		});
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});

		productListGUI = new TransactionProductListGUI();
		scrollableProductList = new JScrollPane(productListGUI);
		scrollableProductList.setPreferredSize(new Dimension(300, 150));// specifically set a size so the scrollPane works
		scrollableProductList.getVerticalScrollBar().setUnitIncrement(15);

		panel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 5, 0, 0);
		panel.add(idLabel, c);

		c.gridx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		panel.add(idField, c);

		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 5, 0, 0);
		panel.add(dateLabel, c);

		c.gridx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		panel.add(dateSpinner, c);

		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		panel.add(scrollableProductList, c);

		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridy = 3;
		panel.add(confirmButton, c);

		c.gridx = 1;
		panel.add(cancelButton, c);
	}
}
