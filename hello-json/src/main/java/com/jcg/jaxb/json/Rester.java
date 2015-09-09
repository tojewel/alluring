package com.jcg.jaxb.json;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import ru.yandex.qatools.allure.model.TestCaseResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Rester {
    private Marshaller marshaller;

    public Rester() {
        try {
            JAXBContext jc = JAXBContext.newInstance(TestCaseResult.class);

            // Create the Marshaller Object using the JaxB Context
            marshaller = jc.createMarshaller();

            // Set the Marshaller media type to JSON or XML
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");

            // Set it to true if you need to include the JSON root element in the JSON output
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);

            // Set it to true if you need the JSON output to formatted
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void shoot(TestCaseResult testCaseResult) {
        try {
            System.out.println("\n Alluring..... ");
            marshaller.marshal(testCaseResult, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
