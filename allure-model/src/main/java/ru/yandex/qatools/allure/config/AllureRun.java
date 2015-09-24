package ru.yandex.qatools.allure.config;

import lombok.Data;
import ru.yandex.qatools.commons.model.Environment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "test-run", propOrder = {

})
@Data
public class AllureRun {
    private final String id;

    private String userId;
    private Environment environment;

    public AllureRun() {
        id = UUID.randomUUID().toString();
    }
}