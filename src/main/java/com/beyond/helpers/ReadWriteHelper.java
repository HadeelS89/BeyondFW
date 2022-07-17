package com.beyond.helpers;


import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class ReadWriteHelper {

    public static String ReadData(String par) {
        File file = new File("src/main/resources/config.properties");

        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (Throwable e) {
            e.printStackTrace(System.out);
            Assert.fail("\nPlease check config file if exist\n");
        }
        Properties prop = new Properties();

        // load properties file
        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            Assert.fail("\nPlease check config file Inputs\n");
        }

        return prop.getProperty(par);
    }
    public static String readCommand(String command) {
        File file = new File("src/main/resources/commands.properties");

        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (Throwable e) {
            e.printStackTrace(System.out);
            Assert.fail("\nPlease check config file if exist\n");
        }
        Properties prop = new Properties();

        // load properties file
        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            Assert.fail("\nPlease check config file Inputs\n");
        }

        return prop.getProperty(command);
    }


    public static String[][] readCSVFile(String fileName, int linesToRead, int columnsToRead) {
        //Possible future implementation: Make separators as input. However this looks better for reusability
        String line = "";
        String csvSplitBy = ",";
        String[] currentLine = null;
        String[][] finalResult = new String[linesToRead][columnsToRead];
        try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/" + fileName + ".csv"))) {
            int j = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                currentLine = line.split(csvSplitBy);
                for (int i = 0; i < currentLine.length; i++) {
                    finalResult[j][i] = currentLine[i];
                }
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    public static String[][] readCSVFile(String fileName, int linesToRead) {
        //Possible future implementation: Make separators as input. However this looks better for reusability
        String line = "";
        String csvSplitBy = ",";
        String[] currentLine = null;
        String[][] finalResult = new String[linesToRead][1];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int j = 0;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                currentLine = line.split(csvSplitBy);
                for (int i = 0; i < currentLine.length; i++) {
                    finalResult[j][i] = currentLine[i];
                }
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalResult;
    }

    public static void writeCSVFile(String filePath, String[] data) throws IOException {
        //Possible future implementation: Make separators as input. However this looks better for reusability
        String csvSplitBy = ",";
        BufferedWriter br = new BufferedWriter(new FileWriter(filePath));
        StringBuilder sb = new StringBuilder();
        int len = data.length;
        for (int i = 0; i < len; i++) {
            sb.append(data[i]);
            sb.append("\n");
        }
        br.write(sb.toString());
        br.close();


    }

    public static String readCredentialsXMLFile(String credentialsType, String tag) {

        String value = "";

        try {
            //creating a constructor of file class and parsing an XML file
            File file = new File(System.getProperty("user.dir") +
                    "/src/main/resources/DataProvider/credentialsData.xml");
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println( "Root element: " + doc.getDocumentElement().getNodeName() );
            NodeList nodeList = doc.getElementsByTagName(credentialsType);
            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                //System.out.println( "\nNode Name :" + node.getNodeName() );
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    switch (tag) {
                        case "username":
                            value = eElement.getElementsByTagName("username").item(0).getTextContent();
                            break;
                        case "password":
                            value = eElement.getElementsByTagName("password").item(0).getTextContent();
                            break;
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }



    public static void writeIntoXMLFileCreatedHRApp(String tagName, String requestID) {
        String xmlFilePath = System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/HEApplications.xml";

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFilePath);


            Node class1 = doc.getFirstChild();

            NodeList list = class1.getChildNodes();
            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (tagName.equalsIgnoreCase(node.getNodeName())) {


                    node.setTextContent(requestID);

                }


            }
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));


            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void writeCSVFirstCell(String content) {
        try (PrintWriter writer = new PrintWriter(new File("src/main/resources/DataProvider/ActiveProgram.csv"))) {
            writer.write(content);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }



    public static String readFromExcel(String fileName, String sheetName, String headerName) {
        ReadFromExcel excelFile = null;
        try {
            excelFile = new ReadFromExcel("src/main/resources/DataProvider/" + fileName + ".xlsx");
        } catch (IOException e) {

        }
        final String value = excelFile.getData1(sheetName, headerName);

        return value;
    }

    public static String readFromExcel(String fileName, String sheetName, String headerName, int Row) {
        ReadFromExcel excelFile = null;
        try {
            excelFile = new ReadFromExcel("src/main/resources/DataProvider/" + fileName + ".xlsx");
        } catch (IOException e) {

        }

        final String value = excelFile.getDataWithoutRand(sheetName, headerName, Row);

        return value;
    }


    public static void writeEnvironment(String environment) {
        String xmlFilePath = System.getProperty("user.dir") +
                "/src/main/resources/DataProvider/environment.xml";


        try {
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            //doc.getDocumentElement().normalize();

            // root element
            Element root = doc.createElement("class");
            doc.appendChild(root);

            // employee element
            Element employee = doc.createElement("environment");
            root.appendChild(employee);

            // firstname element
            Element firstName = doc.createElement("title");
            firstName.appendChild(doc.createTextNode(environment));
            employee.appendChild(firstName);

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    public static String readEnvironment() {

        String value = "";

        try {
            //creating a constructor of file class and parsing an XML file
            File file = new File(System.getProperty("user.dir") +
                    "/src/main/resources/DataProvider/environment.xml");
            //an instance of factory that gives a document builder
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //an instance of builder to parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //System.out.println( "Root element: " + doc.getDocumentElement().getNodeName() );
            NodeList nodeList = doc.getElementsByTagName("environment");
            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                //System.out.println( "\nNode Name :" + node.getNodeName() );
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    value = eElement.getElementsByTagName("title").item(0).getTextContent();
                    break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    public static void clearTheFile(String fileName) throws IOException {
        FileWriter fwOb = new FileWriter(fileName, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();

    }




    public static void main (String args[]) throws IOException {


}
}
