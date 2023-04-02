package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> employees = parseXml("src//data.xml");
        String json = listToJson(employees);
        writeString(json);


    }

    public static List<Employee> parseXml(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));

        Node staff = doc.getDocumentElement();
        System.out.println("Корневой элемент: " + staff.getNodeName());
        NodeList employees = staff.getChildNodes();
        List<Employee> list = new ArrayList<Employee>();
        long id = 0;
        String firstName = null;
        String lastName = null;
        String country = null;
        int age = 0;
        for (int i = 0; i < employees.getLength(); i++) {

            if (Node.ELEMENT_NODE == employees.item(i).getNodeType()) {

                NodeList attributes = employees.item(i).getChildNodes();
                for (int j = 0; j < attributes.getLength(); j++) {

                    if (Node.ELEMENT_NODE == attributes.item(j).getNodeType()) {


                        switch (attributes.item(j).getNodeName()) {
                            case "id":
                                id = Long.parseLong(attributes.item(j).getTextContent());
                                System.out.println(id);
                                break;
                            case "firstName":
                                firstName = attributes.item(j).getTextContent();
                                System.out.println(firstName);
                                break;
                            case "lastName":
                                lastName = attributes.item(j).getTextContent();
                                System.out.println(lastName);
                                break;
                            case "country":
                                country = attributes.item(j).getTextContent();
                                System.out.println(country);

                                break;
                            case "age":
                                age = Integer.parseInt(attributes.item(j).getTextContent());
                                System.out.println(age);
                                break;
                        }
                    }
                }
                Employee buffer = new Employee(id, firstName,lastName,country,age);
                list.add(buffer);
            }


        }

        return list;
    }

    public static String listToJson(List list){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json){
        try (FileWriter writer = new FileWriter("data.json")){
            writer.write(json);

        }catch (IOException e){
            e.getMessage();
        }

    }
}