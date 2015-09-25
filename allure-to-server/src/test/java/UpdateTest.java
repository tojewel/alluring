import com.jcg.jaxb.json.RESTHeartClient;
import ru.yandex.qatools.allure.model.Execution;
import ru.yandex.qatools.allure.model.TestCaseResult;

public class UpdateTest {
    public static void main(String sp[]) {
        Execution e = new Execution();
        RESTHeartClient.get().save(e);

        e.update(new TestCaseResult());
        RESTHeartClient.get().save(e);
    }
}
