package com.beyond.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.beyond.common.Base;
import com.beyond.helpers.ScreenshotHelper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class Listeners extends TestListenerAdapter {


      static   String projectName2="";
   static String buildNumber="";
    static String imageNumber="";
    private  static ExtentReports extent;

    static {
        try {

            extent = ExtentManager.createInstance();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }





    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    ExceptionListner exceptionListener = new ExceptionListner();
    public static boolean CONSOLE;


    @Override
    public synchronized void onStart(ITestContext context) {
        if (CONSOLE) {

            System.out.println("Test Suite started!");
        }
        ExtentTest extentTest = extent.createTest(context.getName());
        test.set(extentTest);

       // extent.add("Executing " + context.getCurrentXmlTest().getName());
    }

    @SneakyThrows
    @Override
    public synchronized void onFinish(ITestContext results) {

        if (CONSOLE) {
            System.out.println(("Test Suite is ending!"));

        }

        extent.flush();






        /*

       // get mage on failed if web project
    if(ReadWriteHelper.ReadData("isWebProject").equalsIgnoreCase("true")) {
    // for images om web
    String imagePath = "src/main/resources/Reports/" + ActionsHelper.screenShots(ExtentManager.reportFileName);
    test.get().fail("Screen Shot : " + test.get().addScreenCaptureFromPath(imagePath));

    }



    //send email
    if (ReadWriteHelper.ReadData("SendMail").equalsIgnoreCase("true")) {
            EmailHelper.sendEmail(ReadWriteHelper.ReadData("EmailsTo"), reportPath);
            //  EmailHelper.sendEmail(ReadWriteHelper.ReadData("EmailsTo"),imagePath );// to send single screen shot
            EmailHelper.sendEmailScreenShots(ReadWriteHelper.ReadData("EmailsTo"));

        }


         */


/*
        s3client.putObject("bl-mlops-qa-reports-website",
                "reports/"+projectName+"/"+dockerImageNumber+"/"+jenkinsRunNumber +".html",
                new File(theNewestFile.getName(),
                        "/test-output/SparkReport/Spark.html"));


 */

    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        result.getTestClass().getXmlClass().getAllParameters();
        //result.getTestClass().getXmlClass().m_xmlTest.m_suite.m_fileNam;
        if (CONSOLE) {
            System.out.println((result.getMethod().getMethodName() + " started!"));

        }

//        ExtentTest extentTest = extent.createTest(result.getMethod().getDescription(),
//                result.getMethod().getMethodName());



    }


    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        if (CONSOLE) {
            System.out.println((result.getMethod().getMethodName() + " passed!"));
        }

        String logText1 = "<b>`Test Case: </b>" + result.getMethod().getDescription();

        test.get().pass(logText1);
    }

    @SneakyThrows
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        if (CONSOLE) {
            System.out.println((result.getMethod().getMethodName() + " failed!"));
        }
        String exceptionMessage = result.getThrowable().getMessage();

        String logText1 = "<b>Test Case: </b>" + result.getMethod().getDescription();
        String description = logText1 + "<details><summary><b><font color=red>" + "Click to see details:" +
                "</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details> \n";
        test.get().fail(description);


/*
        // take screenshot if failed
if(ReadWriteHelper.ReadData("isWebProject").equalsIgnoreCase("true")) {
    TakesScreenshot ts = ((TakesScreenshot) Base.driver);
    File srcFile = ts.getScreenshotAs(OutputType.FILE);
    String base64Image = ts.getScreenshotAs(OutputType.BASE64);
//ScreenshotHelper.encodeFileToBase64Binary(srcFile);


    try {

    }

    // takeScreenShot(result);

    String imageName = ExtentManager.reportFileName + result.getName() + ".PNG";
    test.get().fail("Screen Shot : " + test.get().addScreenCaptureFromPath(imageName));



}

\
 */
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        if (CONSOLE) {
            System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
            try {
                ScreenshotHelper.takeScreenShot(
                );
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

    }
/*
    public static void main(String[] args) {
//        String reportPath = "src/main/resources/Reports/" + "QPros-Automation_Report-2020-12-30-1609364868568.html";
        String reportPath = "src/main/resources/Reports/Byond-100-latest-2022-07-13.html";

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
          String reportFileNameNew =
                "infill-advisor-ds"+"/"+"latest"+"/"+"140.html";
        //https://reports.mlops.beyond.ai/reports/infill-advisor-ds/726/120.html

//https://reports.mlops.beyond.ai/reports/registry.infill.beyond.ai/infill-advisor-ds/120.html

        s3client.putObject("bl-mlops-qa-reports-website",
                "reports/"+reportFileNameNew,
                new File(reportPath));

        System.out.println("s3 report "+reportFileNameNew);

    }


 */

    public static String takeScreenShot(ITestResult result) {

        String path = "src/main/resources/Reports/";
        String imageName = result.getName();
        TakesScreenshot ts = ((TakesScreenshot) Base.driver);
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        try {
            // FileUtils.copyFile(srcFile, new File("./ScreenShots/"+result.getName()+".jpg"));
            FileUtils.copyFile(srcFile, new File(path +
                    ExtentManager.reportFileName + result.getName() + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path + "_" + imageName + "." + "jpeg";
    }




}

