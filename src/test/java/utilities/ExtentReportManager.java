package utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {

		/*
		 * SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt=new
		 * Date(); String currentDatetimestamp=df.format(dt);
		 */

		// Or

		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp

		repName = "Test-Report-" + timestamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Specify location of the report

		sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); // Title of the Report
		sparkReporter.config().setReportName("OpenCart Functional Testing"); // Name of the Report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browssr", browser);

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display group in report
		test.log(Status.PASS, result.getName() + " get succesfully executed");
	}

	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display group in report

		test.log(Status.FAIL, result.getName() + " get failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display group in report

		test.log(Status.FAIL, result.getName() + " get skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReoprt = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReoprt.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//
//			URL url = new URI("file:///" + System.getProperty("user.dir") + "\\reoprts\\" + repName).toURL();
//			// Create the email message
//			ImageHtmlEmail email = new ImageHtmlEmail();
//			email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			email.setHostName("smtp.googleemail.com");
//			email.setSmtpPort(465);
//			email.setAuthenticator(new DefaultAuthenticator("anilkhanadal@gmail.com", "Khanadal@123"));
//			email.setSSLOnConnect(true);
//			email.setFrom("anilkhanadal@gmail.com"); // sender
//			email.setSubject("Test Results");
//			email.setMsg("Please Find the Attached Report...");
//			email.addTo("anilkumarn.provab@gmail.com"); // Reciever
//			email.attach(url, "extent report", "please check report...");
//			email.send();
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
		
	/*	try {
            // Construct the file URL properly (use forward slashes for file paths in URI)
            URL url = new URI("file:///C:/Users/Ganga/eclipse-workspace/OpenCartV121/reports/Test-Report-2024.11.12.15.41.23.html").toURL();

            // Create the email message
            HtmlEmail email = new HtmlEmail();
            
            // Email configurations
            email.setHostName("smtp.googleemail.com"); // Correct SMTP server
            email.setSmtpPort(465); // Gmail's SMTP port for SSL
            email.setAuthenticator(new DefaultAuthenticator("anilkhanadal@gmail.com", "Khanadal@123"));
            email.setSSLOnConnect(true); // Enable SSL
            email.setFrom("anilkhanadal@gmail.com"); // sender email
            email.setSubject("Test Results");
            email.setMsg("Please Find the Attached Report...");

            // Add recipient
            email.addTo("anilkumarn.provab@gmail.com"); // receiver email
            
            // Create an EmailAttachment for the report file
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(url.getPath());  // The file path of the report
            attachment.setName(repName);        // File name that will appear in the email
            attachment.setDescription("Please check the attached report...");

            // Attach the report to the email
            email.attach(attachment);

            // Send the email
            email.send();

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } */

	}

}
