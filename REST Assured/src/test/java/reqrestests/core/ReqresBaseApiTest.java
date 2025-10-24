package reqrestests.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reqres.api.UsersService;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class ReqresBaseApiTest {

    protected UsersService users;

    @BeforeAll
    static void attachAllureAndConfigureJackson() {
        RestAssured.filters(
                new AllureRestAssured()
                        .setRequestAttachmentName("API Request")
                        .setResponseAttachmentName("API Response")
        );

        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> {
                    ObjectMapper om = new ObjectMapper();
                    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    return om;
                })
        );
    }

    @BeforeEach
    void setUp() {
        users = new UsersService();
    }
}
