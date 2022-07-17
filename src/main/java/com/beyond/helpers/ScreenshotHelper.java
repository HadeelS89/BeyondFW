package com.beyond.helpers;

import com.beyond.common.Base;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScreenshotHelper  extends Base{

    public ScreenshotHelper(WebDriver driver) {
this.driver=driver;
    }


    public static void takeSnapShot( String fileWithPath) {

        //Example usage:         ScreenshotHelper.takeSnapShot(driver, "C:\\Users\\HamzahAlrawi\\Desktop\\MyImage.png");
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {

        }
    }


    public static String takeScreenShot() throws UnsupportedEncodingException {
        //this.driver;



        TakesScreenshot scrShot = ((TakesScreenshot) driver);

        return encodeFileToBase64Binary(scrShot.getScreenshotAs(OutputType.FILE));
    }

    public static  String encodeFileToBase64Binary(File file) {

        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[(int) file.length()];
        try {
            fileInputStreamReader.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }
//
//    public static String takeScreenShot(ITestResult result){
//
//        String path="src/main/resources/Reports/";
//        String imageName= result.getName();
//        TakesScreenshot ts = ((TakesScreenshot) Base.driver);
//        File srcFile = ts.getScreenshotAs(OutputType.FILE);
//
//        try {
//            // FileUtils.copyFile(srcFile, new File("./ScreenShots/"+result.getName()+".jpg"));
//            FileUtils.copyFile(srcFile, new File( path+
//                    ExtentManager.reportFileName+result.getName()+".jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return path + "_" + imageName + "." + "jpeg"; }
//}
}