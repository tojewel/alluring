package ru.yandex.qatools.allure.model;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "execution", propOrder = {

})
@Getter
public class Execution {

    @XmlElement(name = "_id", required = true)
    private final String _id;

    @XmlElement(name = "start", required = true)
    private final long start;

    private long end;

    private String userId;

    private StatusCounts statusCounts = new StatusCounts();

    public Execution() {
        start = System.currentTimeMillis();
        _id = UUID.randomUUID().toString();
    }

    public void update(TestCaseResult testCase) {
        statusCounts.update(testCase);

        end = System.currentTimeMillis();
    }
}