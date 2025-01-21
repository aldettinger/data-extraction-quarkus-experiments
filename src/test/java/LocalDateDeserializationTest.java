import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import dev.langchain4j.internal.Json.JsonCodec;
import io.quarkiverse.langchain4j.QuarkusJsonCodecFactory;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LocalDateDeserializationTest {

    final String JSON = "{\n"
            + "  \"customerSatisfied\": true,\n"
            + "  \"customerName\": \"Sarah London\",\n"
            + "  \"customerBirthday\": {\"year\": 1986, \"month\": 7, \"day\": 10},\n"
            + "  \"summary\": \"The customer called to declare an accident on their main vehicle and was informed that all expenses related to the accident would be reimbursed.\"\n"
            + "}";

    @RegisterForReflection
    static class CustomPojoWithLocalDate {
        public boolean customerSatisfied;
        public String customerName;
        public LocalDate customerBirthday;
        public String summary;
    }

    @Test
    void localDateDeserializationShouldNotThrow() {
        JsonCodec codec = new QuarkusJsonCodecFactory().create();
        CustomPojoWithLocalDate pojo = codec.fromJson(JSON, CustomPojoWithLocalDate.class);
        assertNotNull(pojo);
    }
}
