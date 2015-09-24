package ru.yandex.qatools.allure;

import com.jcg.jaxb.json.RESTHeartClient;
import ru.yandex.qatools.allure.events.TestCaseFinishedEvent;
import ru.yandex.qatools.allure.events.TestCaseStartedEvent;
import ru.yandex.qatools.allure.events.TestSuiteFinishedEvent;
import ru.yandex.qatools.allure.experimental.LifecycleListener;
import ru.yandex.qatools.allure.model.TestCaseResult;

public class AllureEventSender extends LifecycleListener {

    Allure allure() {
        return Allure.LIFECYCLE;
    }

    // FIXME Suits will not be saved in future.. all the information will be injected in the TestCaseResult

    @Override
    public void fire(TestSuiteFinishedEvent event) {
        RESTHeartClient.getInstance().save(event.getTestSuite());
    }

    // Test

    private TestCaseResult testCaseResult;

    @Override
    public void fire(TestCaseStartedEvent event) {
        testCaseResult = allure().getTestCaseStorage().get();
    }

    @Override
    public void fire(TestCaseFinishedEvent event) {
        RESTHeartClient.getInstance().save(testCaseResult);
    }
}