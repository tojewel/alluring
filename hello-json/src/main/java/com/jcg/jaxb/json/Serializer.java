package com.jcg.jaxb.json;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import ru.yandex.qatools.allure.model.TestCaseResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

public class Serializer {
    private final Map<Class<?>, Marshaller> marshallers = new HashMap<Class<?>, Marshaller>() {
        @Override
        public Marshaller get(Object key) {
            Marshaller marshaller = super.get(key);
            if (marshaller == null) {
                createMarshaller((Class) key);
            }
            return marshaller;
        }
    };

    public Serializer() {
        createMarshaller(TestCaseResult.class);
    }

    private Marshaller createMarshaller(Class clazz) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(clazz).createMarshaller();
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            return marshaller;
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String toJson(Object dataObject) {
        return null; // marshallers.get(dataObject.getClass()).ma
    }

    public void shoot(TestCaseResult testCaseResult) {
        try {
            System.out.println("\n\n" + ReflectionToStringBuilder.toString(testCaseResult,
                    ToStringStyle.MULTI_LINE_STYLE));
            marshallers.get(testCaseResult.getClass()).marshal(testCaseResult, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
