package ru.yandex.qatools.allure;

import com.jcg.jaxb.json.Serializer;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.experimental.LifecycleListener;
import ru.yandex.qatools.allure.model.TestCaseResult;

public class Alluring extends LifecycleListener {
    private TestCaseResult testCaseResult;
    private Serializer serializer = new Serializer();

    Allure allure() {
        return Allure.LIFECYCLE;

    }

    public void fire(TestCaseStartedEvent event) {
         testCaseResult = allure().getTestCaseStorage().get();
    }

    public void fire(TestCaseFinishedEvent event) {
        serializer.shoot(testCaseResult);
    }
}
