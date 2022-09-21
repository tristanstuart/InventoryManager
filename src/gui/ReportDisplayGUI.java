package gui;

import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import reports.ReportThread;

public class ReportDisplayGUI extends JDialog
{

	private JPanel panel;
	private JTabbedPane tabbedPane;

	public ReportDisplayGUI(Map<String, ReportThread> reports)
	{
		super(GUIManager.getInstance().getMainMenuGUI(), "Generated Reports", true);
		createGUI(reports);
	}

	private void createGUI(Map<String, ReportThread> reports)
	{
		panel = new JPanel();
		tabbedPane = new JTabbedPane();

		Set<String> titles = reports.keySet();

		titles.forEach(t -> tabbedPane.addTab(t, null, addNewPanelWithText(reports.get(t)), ""));
		panel.add(tabbedPane);
		add(panel);
	}

	/**
	 * Sets up a JTextArea with the provided report's output and adds it to the JTabbedPane.
	 * 
	 * @param t
	 *            ReportThread containing the output for the report to be displayed
	 * @return a formatted JTextArea containing the provided report's output
	 */
	private JTextArea addNewPanelWithText(ReportThread t)
	{
		JTextArea area = new JTextArea();
		area.setText(t.getOutput());
		area.setEditable(false);
		return area;
	}
}
