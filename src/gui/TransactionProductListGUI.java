package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stocks.Product;
import stocks.StockManager;
import transactions.Transaction;

/**
 * Helper GUI class used to format all products into a selectable list for use in the CreateTransaction GUIs.
 */
public class TransactionProductListGUI extends JPanel
{
	private JCheckBox[] productCheckBoxes;
	private JTextField[] productAmountFields;

	public TransactionProductListGUI()
	{
		createGUI();
	}

	private void createGUI()
	{
		Product[] productList = StockManager.getInstance().getProductList();

		productCheckBoxes = new JCheckBox[productList.length];
		productAmountFields = new JTextField[productList.length];

		setLayout(new GridLayout(productList.length, 2));

		for (int i = 0; i < productAmountFields.length; i++)
		{
			JTextField field = new JTextField("0");
			field.setEditable(false);
			field.addKeyListener(new KeyAdapter() // limit to numbers to avoid error checks later
			{
				public void keyTyped(KeyEvent e)
				{
					char c = e.getKeyChar();
					if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
						e.consume();
				}
			});

			productAmountFields[i] = field;

			JCheckBox box = new JCheckBox(productList[i].getName() + " Amount:");
			box.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					field.setEditable(box.isSelected());
				}
			});

			productCheckBoxes[i] = box;

			add(box);
			add(field);
		}

	}

	/**
	 * Checks that all selected products have non-empty and non-zero data in their associated amount fields.
	 * 
	 * @return returns null if the products are all good. Returns an error message for a specific product otherwise.
	 */
	public String verifyProducts()
	{
		boolean anySelected = false;
		for (int i = 0; i < productCheckBoxes.length; i++)
		{
			if (productCheckBoxes[i].isSelected())
			{
				anySelected = true;
				String text = productAmountFields[i].getText();
				if (text.length() == 0 || Integer.parseInt(text) == 0)
					return "Amount for " + productCheckBoxes[i].getText().substring(0, productCheckBoxes[i].getText().indexOf(':')) + " must be greater than zero!";
			}
		}
		if (!anySelected)
			return "There are no products selected in this transaction, please select at least one product!";

		return null;
	}

	/**
	 * Adds the products selected in the GUI to the provided transaction.
	 * 
	 * @param t
	 *            the transaction to add the products to.
	 */
	public void addProductsToTransaction(Transaction t)
	{
		Product[] productList = StockManager.getInstance().getProductList();

		for (int i = 0; i < productCheckBoxes.length; i++)
			if (productCheckBoxes[i].isSelected())
				t.addProduct(productList[i], Integer.parseInt(productAmountFields[i].getText()));
	}
}
