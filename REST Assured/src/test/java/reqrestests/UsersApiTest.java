package reqrestests;

import com.reqres.api.model.SingleUserResponse;
import com.reqres.api.model.UsersListResponse;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import reqrestests.core.ReqresBaseApiTest;
import testframework.PropertiesManager;

@Epic("Reqres")
@Feature("Users")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersApiTest extends ReqresBaseApiTest {

    private String pickedUserId;
    private String pickedUserEmail;
    private String newUserId;
    private String newUserName;
    private String newUserEmail;

    private int page() {
        return Integer.parseInt(PropertiesManager.getConfigProperties().getProperty("page","1"));
    }

    private String invalidId() {
        return PropertiesManager.getConfigProperties().getProperty("invalidUserId","6699");
    }

    @Test @Order(1) @Story("List users")
    @DisplayName("GET /users?page=n -> 200 and extract user id/email")
    void listUsers_ok_extractFields() {
        UsersListResponse body = users.listUsers(page())
                .extract().as(UsersListResponse.class);

        assertThat(body.data).isNotEmpty();

        List<Integer> ids = body.data.stream().map(u -> u.id).collect(Collectors.toList());
        assertThat(ids).isSortedAccordingTo(Comparator.naturalOrder());

        pickedUserId = String.valueOf(body.data.get(0).id);
        pickedUserEmail = body.data.get(0).email;
    }

    @Test @Order(2) @Story("Get user by id")
    @DisplayName("GET /users/{id} -> 200 and email matches")
    void getUser_ok_emailMatches() {
        SingleUserResponse body = users.getUser(pickedUserId)
                .statusCode(200)
                .extract().as(SingleUserResponse.class);

        assertThat(body.data.email).isEqualTo(pickedUserEmail);
    }

    @Test @Order(3) @Story("Get user by id (404)")
    @DisplayName("GET /users/{id} -> 404 and empty body")
    void getUser_404_emptyBody() {
        String text = users.getUser(invalidId())
                .statusCode(404)
                .extract().asString().trim();

        assertThat(text.isEmpty() || text.equals("{}")).isTrue();
    }

    @Test @Order(4) @Story("Create user")
    @DisplayName("POST /users -> 201 + echo fields + id + createdAt")
    void createUser_201_echoFields() {
        String rand = Long.toString(System.currentTimeMillis(), 36);
        newUserName  = "petya_" + rand;
        newUserEmail = "petya_qa@" + rand;

        Map<String,Object> payload = Map.of("name", newUserName, "email", newUserEmail);

        Map<String,Object> body = users.createUser(payload)
                .statusCode(201)
                .extract().as(Map.class);

        assertThat(body.get("name")).isEqualTo(newUserName);
        assertThat(body.get("email")).isEqualTo(newUserEmail);
        assertThat(body.get("id")).isNotNull();
        assertThat(body.get("createdAt")).isInstanceOf(String.class);

        newUserId = String.valueOf(body.get("id"));
    }

    @Test @Order(5) @Story("Delete user")
    @DisplayName("DELETE /users/{id} -> 204 empty body")
    void deleteUser_204_empty() {
        String resp = users.deleteUser(newUserId)
                .statusCode(204)
                .extract().asString();

        assertThat(resp).isBlank();
    }
}
