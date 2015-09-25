package ru.yandex.qatools.allure.model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusCounts", propOrder = {

})
@Getter
// TODO USe XmlAdapter later on to convert Map to this
public class StatusCounts {

    private int FAILED;
    private int BROKEN;
    private int PASSED;
    private int SKIPPED;
    private int CANCELED;
    private int PENDING;
    private int UNKNOWN;

    public void update(TestCaseResult testCase) {
        if (testCase.getStatus() == null) {
            UNKNOWN++;
        } else {
            switch (testCase.getStatus()) {
                case FAILED:
                    FAILED++;
                    break;
                case BROKEN:
                    BROKEN++;
                    break;
                case PASSED:
                    PASSED++;
                    break;
                case SKIPPED:
                    SKIPPED++;
                    break;
                case CANCELED:
                    CANCELED++;
                    break;
                case PENDING:
                    PENDING++;
                    break;
                default:
                    UNKNOWN++;
                    break;
            }
        }
    }
}