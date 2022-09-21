package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stocks.Product;
import stocks.StockManager;

public class CreateProductGUI extends JDialog
{
	private JPanel panel;
	private JLabel nameLabel, countLabel;
	private JTextField nameField, countField;
	private JButton cancelButton, confirmButton;

	public CreateProductGUI()
	{
		super(GUIManager.getInstance().getMainMenuGUI(), "Add New Product", true);
		setupUI();
	}

	private void setupUI()
	{
		panel = new JPanel();
		setContentPane(panel);

		nameField = new JTextField();
		countField = new JTextField();
		countField.addKeyListener(new KeyAdapter()// limit to numbers to avoid error checking later
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
					e.consume();
			}
		});

		nameLabel = new JLabel("Name:");
		nameLabel.setLabelFor(nameField);
		countLabel = new JLabel("Amount:");
		countLabel.setLabelFor(countField);

		confirmButton = new JButton("Okay");
		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (nameField.getText().length() == 0)// cant be blank
					JOptionPane.showMessageDialog(panel, "The product name cannot be empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
				else if (countField.getText().length() == 0 || Integer.parseInt(countField.getText()) == 0)// cant be blank but also cant be 0
					JOptionPane.showMessageDialog(panel, "The product must have an amount greater than 0!", "ERROR", JOptionPane.ERROR_MESSAGE);
				else// product needs to be created after we're sure it will be successful so that ID numbers dont get messed up
				{ // by products that are created but never added to the products list in StockManager
					StockManager.getInstance().addProduct(new Product(nameField.getText(), Integer.parseInt(countField.getText())));
					GUIManager.getInstance().updateMenuView();
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

		panel.setLayout(new GridLayout(3, 2));
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(countLabel);
		panel.add(countField);
		panel.add(confirmButton);
		panel.add(cancelButton);

		panel.setPreferredSize(new Dimension(350, 90));// makes everything look less cramped
	}
}
