package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Used in the {@link MenuView} to help with display of stores and products.
 */
public class LabelDisplay extends JPanel
{
	private JLabel[] info;

	public LabelDisplay(String title, String[] data)
	{
		info = new JLabel[data.length + 2];
		info[0] = new JLabel(title + ":");
		info[1] = new JLabel();// padding
		for (int i = 2; i < info.length; i++)
			info[i] = new JLabel(data[i - 2]);

		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;

		add(info[0], constraints);

		constraints.gridy = 1;
		constraints.insets = new Insets(0, 0, 0, 10);
		for (int i = 1; i < info.length; i++)
		{
			add(info[i], constraints);
			if (i == info.length - 1)
				constraints.insets = new Insets(0, 10, 0, 0);
			else
				constraints.insets = new Insets(0, 10, 0, 10);
			constraints.gridx += 1;
			constraints.weightx = 1.0;
		}
	}
}
