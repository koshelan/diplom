package education.diplom.service;

import education.diplom.model.FileMsg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


class CloudServiceTest {
    public static final String TOKEN = "token";

    private static CloudService cloudService = new CloudService();

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
        var testFile = new File("src/test/resources/test.txt");
        String file = "";
        try (var reader = new BufferedReader(new FileReader(testFile))) {
            var x = new char[(int) testFile.length()];
            reader.read(x);
            file = String.valueOf(x);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        var msg = new FileMsg(String.valueOf(file.hashCode()), file);
        Assertions.assertEquals("OK", cloudService.uploadFile(TOKEN, testFile.getName(), msg));
    }

    @Test
    void deleteFile() {
    }

    @Test
    void getFile() {
    }

    @Test
    void editFile() {
        var oldFileName = "test1.txt";
        var newFileName = "testfile.txt";
        Assertions.assertEquals("OK", cloudService.editFile(TOKEN, oldFileName, newFileName));
        Assertions.assertTrue((new File("files/" + newFileName)).exists());
    }

    @Test
    void getFiles() {
        var limit = 100;
        var c = cloudService.getFiles(TOKEN, limit);
        System.out.println(c);
        Assertions.assertTrue(c.size() <= limit);
        limit = 0;
        c = cloudService.getFiles(TOKEN, limit);
        System.out.println(c);
        Assertions.assertTrue(c.size() <= limit);
    }

}