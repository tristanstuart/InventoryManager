package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stocks.StockManager;
import stocks.Store;

public class CreateStoreGUI extends JDialog
{
	private JPanel panel;
	private JLabel nameLabel, countLabel;
	private JTextField nameField, addressField;
	private JButton cancelButton, confirmButton;

	public CreateStoreGUI()
	{
		super(GUIManager.getInstance().getMainMenuGUI(), "Add New Store", true);
		setupUI();
	}

	private void setupUI()
	{
		panel = new JPanel();
		setContentPane(panel);

		nameField = new JTextField();
		addressField = new JTextField();

		nameLabel = new JLabel("Name:");
		nameLabel.setLabelFor(nameField);
		countLabel = new JLabel("Address:");
		countLabel.setLabelFor(addressField);

		confirmButton = new JButton("Okay");
		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (nameField.getText().length() == 0)
					JOptionPane.showMessageDialog(panel, "The store name cannot be empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
				else if (addressField.getText().length() == 0)
					JOptionPane.showMessageDialog(panel, "The store address cannot be empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
				else// store needs to be created after we're sure it will be successful so that ID numbers dont get messed up
				{ // by store that are created but never added to the stores list in StockManager
					StockManager.getInstance().addStore(new Store(nameField.getText(), addressField.getText()));
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
		panel.add(addressField);
		panel.add(confirmButton);
		panel.add(cancelButton);

		panel.setPreferredSize(new Dimension(350, 90));
	}
}
