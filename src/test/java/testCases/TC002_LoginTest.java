package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups={"Sanity","Master"})
	public void verify_Login() {
		logger.info("***** Starting TC_002_LoginTest *****");

		try {
			// Home Page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Login Page
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("pawwsord"));
			lp.clickLogin();

			// MyAccount Page

			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountExists();

			Assert.assertEquals(targetPage, true, "Login Failed");
//			Assert.assertTrue(targetPage);
		} catch (Exception e) {
			Assert.fail();
		}

		logger.info("***** Finished TC_002_LoginTest *****");
	}
}
