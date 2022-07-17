package com.beyond.common;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.beyond.helpers.ActionsHelper;
import com.beyond.helpers.ReadWriteHelper;
import com.beyond.reporting.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Listeners(com.beyond.reporting.Listeners.class)
public class Base {
    public static boolean isBase=false;
    public static WebDriver driver;
    public static String environment = "";
    public static String downloadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\DataProvider";


   // mvn clean install -Dfile=WebTest.xml -Dproject_name=byonH -Dimage_name=testH -Dbuild_number=12 -DenvUrl=https://www.google.com/


    @Parameters({"browserType","url"})
    @BeforeMethod(enabled = false)

    public void setUpBrowser(@Optional("optional")
                                   String browserType,@Optional("optional") String url) {
        if(!isBase) {

            String envUrl = System.getProperty("envUrl");
            System.out.println("-----" + envUrl);

            if (!browserType.equals("optional")) {
                initiateDriver(OsValidator.getDeviceOs(), browserType);
                // initiateDriver();
            } else {
                initiateDriver(OsValidator.getDeviceOs(), ReadWriteHelper.ReadData("browser"));
            }
            // this to determine the url needed
            // if it not created as param on xml file then use the else
            if (url.equals("optional")) {// nul default
                driver.navigate().to(ReadWriteHelper.ReadData("InfillURL"));


                ReadWriteHelper.writeEnvironment(environment);
                System.out.println("environment = " + environment);
            } else {// from pom.xm then mvn -D

                driver.navigate().to(envUrl);
            }

        }
}


    public WebDriver initiateDriver()
    {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    public WebDriver initiateDriver(String deviceOsType, String driverType) {
        String browser = driverType.toLowerCase();

        switch (browser) {
            case "firefox":
                try {

                    setFireFoxBrowser(deviceOsType);
                    FirefoxOptions options = new FirefoxOptions();
                    options.setAcceptInsecureCerts(true);
                    if (ReadWriteHelper.ReadData("headless").equalsIgnoreCase("true")) {
                        options.addArguments("--headless");

                    }
                    driver = new FirefoxDriver(options);
                } catch (Throwable e) {
                    e.printStackTrace(System.out);
                    Assert.fail("Please check Browser is exist Browser Unable to start");
                }
                break;
            case "chrome":

            case "Edge":
                try {
                   // WebDriverManager.chromedriver().setup();
                    DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome();
                    handlSSLErr.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    setChromeBrowser(deviceOsType);
                    Map<String, Object> prefs = new HashMap<>();
                    //Put this into prefs map to switch off browser notification
                    prefs.put("profile.default_content_setting_values.notifications", 2);

                    prefs.put("download.default_directory", downloadDir);
                    //Create chrome options to set this prefs
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", prefs);
                    options.addArguments("--disable-web-security");
                    options.addArguments("--allow-running-insecure-content");
                    options.setAcceptInsecureCerts(true);
                    options.addArguments("--no-sandbox");
                    if (ReadWriteHelper.ReadData("headless").equalsIgnoreCase("true")) {
                        options.addArguments("--headless");
                        options.addArguments("window-size=1920x1080");
                    }
                   // driver = new ChromeDriver(options);

                     driver = new ChromeDriver(options);

                    if (ReadWriteHelper.ReadData("headless").equalsIgnoreCase("true")) {
                        Dimension targetSize = new Dimension(1440, 1080); //your screen resolution here
                        driver.manage().window().setSize(targetSize);
                    }


                } catch (Throwable e) {
                    e.printStackTrace(System.out);
                    Assert.fail("Please check Browser is exist Browser Unable to start");
                }
                break;
            case "ie": {
                try {
                    System.setProperty(ReadWriteHelper.ReadData("IEDriverPath"),
                            ReadWriteHelper.ReadData("IEBrowserPathWindows"));
                    driver = new InternetExplorerDriver();
                } catch (Throwable e) {
                    e.printStackTrace(System.out);
                    Assert.fail("Please check Browser is exist Browser Unable to start");
                }
                break;
            }
            case "safari": {
                try {
                    System.setProperty(ReadWriteHelper.ReadData("SafariDriverPath"),
                            ReadWriteHelper.ReadData("SafariBrowserPath"));

                    driver = new SafariDriver();
                } catch (Throwable e) {
                    e.printStackTrace(System.out);
                    Assert.fail("Please check Browser is exist Browser Unable to start");
                }
            }
        }

        driver.manage().window().maximize();
        return driver;
    }

    public DriverType getBrowser() {
        String browserName = ReadWriteHelper.ReadData("browser");

        if (browserName == null || browserName.equalsIgnoreCase("chrome"))
            return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox"))
            return DriverType.FIREFOX;
        else if (browserName.equals("iexplorer") || browserName.equalsIgnoreCase("internetExplorer") ||
                browserName.equalsIgnoreCase("ie"))
            return DriverType.INTERNETEXPLORER;
        else if (browserName.equalsIgnoreCase("safari")) return DriverType.Safari;
        else
            throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " +
                    browserName);
    }


    private void setChromeBrowser(String deviceOsType) {
        if (deviceOsType.equalsIgnoreCase("mac")) {

            WebDriverManager.chromedriver().setup();
           // System.setProperty(ReadWriteHelper.ReadData("ChromeDriverPath"),
             //       ReadWriteHelper.ReadData("ChromeDriverLinkMac"));
        } else if (deviceOsType.equalsIgnoreCase("windows")) {
            WebDriverManager.chromedriver().setup();
        } else if (deviceOsType.equalsIgnoreCase("Linux")) {
            System.setProperty(ReadWriteHelper.ReadData("ChromeDriverPath"),
                    ReadWriteHelper.ReadData("chromeDriverLinkLinux"));
        }
    }

    private void setFireFoxBrowser(String deviceOsType) {
        if (deviceOsType.equalsIgnoreCase("mac")) {
            System.setProperty(ReadWriteHelper.ReadData("FireFoxDriverPath"),
                    ReadWriteHelper.ReadData("FireFoxBrowserPathMac"));
        } else if (deviceOsType.equalsIgnoreCase("windows")) {
            System.setProperty(ReadWriteHelper.ReadData("FireFoxDriverPath"),
                    ReadWriteHelper.ReadData("FireFoxBrowserPathWindows"));
        }
    }


    @AfterSuite(enabled = true)
    public void stopDriver_push_Report_toS3() throws InterruptedException {
        try {
          //  driver.quit();
        } catch (Throwable e) {
            e.getStackTrace();
        }

try {
    ActionsHelper.isFileDownloaded(ExtentManager.reportFileName,"false");
    Thread.sleep(15000);
}catch (Exception e)
{

    Thread.sleep(10000);
    ActionsHelper.isFileDownloaded(ExtentManager.reportFileName,"false");
}


        System.out.println(ExtentManager.reportFileName);
        String reportPath = "src/main/resources/Reports/" + ExtentManager.reportFileName;

        // uncomment AWS righter
        AWSCredentials credentials = new BasicAWSCredentials(
                ReadWriteHelper.readCredentialsXMLFile("SWS_S3", "username"),
                ReadWriteHelper.readCredentialsXMLFile("SWS_S3", "password")
        );
        final AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();
         String dockerImageNumber = System.getProperty("image_name","test");//TAG//this need to be added
         String projectName = System.getProperty("project_name","test");//image name
         String  jenkins_build=System.getProperty("build_number","12");//

         String reportFileNameNew =String.format("%s/%s/%s.html",
                projectName.substring( projectName.lastIndexOf("/") + 1),dockerImageNumber,jenkins_build);

        s3client.putObject("bl-mlops-qa-reports-website",
                "reports/"+ reportFileNameNew,
                new File(reportPath));

        System.out.println("s3 report "+reportFileNameNew);

    }

}
/*
export JAVA_HOME=$(/usr/libexec/java_home -v 11.0.15.1)
 export M2_HOME=/usr/local/Cellar/maven/3.8.6
 export PATH=$PATH:$M2_HOME/bin

 */