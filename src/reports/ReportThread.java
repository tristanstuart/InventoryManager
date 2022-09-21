package reports;

/**
 * Implements the Runnable interface. Is used to generate reports in a multi-threaded environment.
 * Call using a thread to generate the stored Report. getOutput() can be used to retrieve the
 * report information.
 */
public class ReportThread implements Runnable
{
	private Report report;
	private String output;

	public ReportThread(Report r)
	{
		report = r;
	}

	@Override
	public void run()
	{
		output = report.generateReport();
	}

	public String getOutput()
	{
		return output;
	}
}
