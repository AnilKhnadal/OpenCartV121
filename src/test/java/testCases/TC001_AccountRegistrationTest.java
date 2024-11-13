package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {

		logger.info("***** Starting TC001_AccountRegistrationTest *****");

		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on MyAccont Link");

			hp.clickRegister();
			logger.info("Clicked on Register Link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

			logger.info("Providing Customer Details....");

			regpage.setFirstName(radomString().toUpperCase()); // randomly generated the FirstName
			regpage.setLastName(radomString().toUpperCase()); // randomly generated the LastName
			regpage.setEmail(radomString() + "@gmail.com"); // randomly generated the email
			regpage.setTelephone(radomNumber());

			String password = radomAlphaNumber(); // Bcz Password and confirmPassword should be same

			regpage.setPassword(password);
			regpage.setCnfmPassword(password);
			regpage.setSubscribe();
			regpage.setPolicy();
			regpage.setContinue();

			logger.info("Validating Expected Result");

			String confmssg = regpage.getConfirmationMsg();

			if (confmssg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test Failed...");
				logger.debug("Debug Logs...");
				Assert.assertTrue(false);
			}
//			Assert.assertEquals(confmssg, "Your Account Has Been Created!!!");
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
}
