package ru.yandex.qatools.allure;

import com.jcg.jaxb.json.RESTHeartClient;
import lombok.extern.java.Log;
import ru.yandex.qatools.allure.events.TestCaseFinishedEvent;
import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.experimental.LifecycleListener;
import ru.yandex.qatools.allure.model.TestCaseResult;
import ru.yandex.qatools.allure.model.TestSuiteResult;


@Log
public class AllureEventSender extends LifecycleListener {


    Allure allure() {
        return Allure.LIFECYCLE;
    }

    // Suite

    private TestSuiteResult testSuite;

//    @Override
//    public void fire(TestSuiteEvent event) {
//        testSuite = allure().getTestSuiteStorage().get(event.getUid());
//    }
//
//    @Override
//    public void fire(TestSuiteFinishedEvent event) {
//        restHeartClient.save(testSuite);
//    }

    // Test

    private TestCaseResult testCaseResult;

    @Override
    public void fire(TestCaseStartedEvent event) {
        testCaseResult = allure().getTestCaseStorage().get();
    }

    @Override
    public void fire(TestCaseFinishedEvent event) {
        System.out.println("Saving(this=" + hashCode() + " " + testCaseResult.hashCode() + "): " + testCaseResult.getName());
//        new RuntimeException("Saving: " + testCaseResult.getName()).printStackTrace(System.out);
        RESTHeartClient.getInstance().save(testCaseResult);
    }
}