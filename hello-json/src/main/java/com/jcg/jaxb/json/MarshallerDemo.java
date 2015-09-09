package com.jcg.jaxb.json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * @author ashraf_sarhan
 *
 */
public class MarshallerDemo {

    /**
     * @param args
     * @throws JAXBException
     * Marshaller POJO to JSON using EclipseLink MOXy
     */
    public static void main(String[] args) throws JAXBException {

        // Creating a new employee pojo object with data
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Ashraf");
        List skills = new ArrayList();
        skills.add("java");
        skills.add("sql");
        employee.setSkills(skills);

        // Create a JaxBContext
        JAXBContext jc = JAXBContext.newInstance(Employee.class);

        // Create the Marshaller Object using the JaxB Context
        Marshaller marshaller = jc.createMarshaller();

        // Set the Marshaller media type to JSON or XML
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");

        // Set it to true if you need to include the JSON root element in the JSON output
        marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);

        // Set it to true if you need the JSON output to formatted
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Marshal the employee object to JSON and print the output to console
        marshaller.marshal(employee, System.out);
    }

}