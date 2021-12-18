package education.diplom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import education.diplom.model.FileMsg;
import education.diplom.service.CloudService;
import io.restassured.path.json.mapper.factory.GsonObjectMapperFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG_VALUE;
import io.restassured.common.mapper.factory.ObjectMapperFactory;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudControllerTest {
    public static final String TOKEN = "token";

    private static CloudService cloudService = new CloudService();

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void prepare() {
        var testFile = new File("src/test/resources/test1.txt");
        try (var reader = new BufferedReader(new FileReader(testFile))) {
            var x = new char[(int) testFile.length()];
            reader.read(x);
            var file = String.valueOf(x);
            var msg = new FileMsg(String.valueOf(file.hashCode()), file);
            cloudService.uploadFile(TOKEN, testFile.getName(), msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void uploadFile() {
//        var testFile = new File("src/test/resources/test.txt");
//        System.out.println(testFile.getName());
//        String file = "";
//        try (var reader = new BufferedReader(new FileReader(testFile))) {
//            var x = new char[(int) testFile.length()];
//            reader.read(x);
//            file = String.valueOf(x);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        var msg = new FileMsg(String.valueOf(file.hashCode()), file);
//        given()
//                .contentType(MULTIPART_FORM_DATA_VALUE)
//                .header("authToken", TOKEN)
//                .header("fileName", testFile.getName())
//                .body(msg)
//                .when()
//                .post("/file")
//                .then()
//                .statusCode(200)
//                .body(equalTo("OK"));

    }

    @Test
    void deleteFile() {
    }

    @Test
    void getFile() {
    }

    @Test
    void editFile() {
    }

    @Test
    void getFiles() {
        var limit = 10;
        given()
                .header("authToken", TOKEN)
                .param("limit", limit)
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}