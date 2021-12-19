package education.diplom.controller;

import education.diplom.model.FileList;
import education.diplom.model.FileMsg;
import education.diplom.model.LoginMsg;
import education.diplom.service.CloudService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.restassured.common.mapper.factory.ObjectMapperFactory;

import java.util.List;

@RestController
@RequestMapping("/")
public class CloudController {
    private final CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    @GetMapping("/")
    public String hello(){
        return "Hello from Cloud";
    }


    @PostMapping("/login")
    public LoginMsg autorisation(@RequestBody String login, @RequestBody String password) {
        System.out.println(login+"   "+ password);
        return cloudService.autorisation(login, password);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader String authToken) {
        return cloudService.logout(authToken);
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(
            @RequestHeader String authToken,
            @RequestHeader String fileName,
            @RequestPart FileMsg fileMsg) {
        System.out.println(fileName);
        System.out.println(fileMsg);
        return cloudService.uploadFile(authToken, fileName, fileMsg);
    }

    @DeleteMapping("/file")
    public String deleteFile(
            @RequestHeader String authToken,
            @RequestHeader String fileName) {
        return cloudService.deleteFile(authToken, fileName);
    }

    @GetMapping(value = "/file")
    public FileMsg getFile(
            @RequestHeader String authToken,
            @RequestParam String fileName) {
        return cloudService.getFile(authToken, fileName);
    }

    @PutMapping("/file")
    public String editFile(
            @RequestHeader String authToken,
            @RequestParam String fileName,
            @RequestBody String name) {
        return cloudService.editFile(authToken, fileName, name);
    }

    @GetMapping("/list")
    public List<FileList> getFiles(
            @RequestHeader String authToken,
            @RequestParam int limit) {
        var r =  cloudService.getFiles(authToken, limit);
        System.out.println(r);
        return cloudService.getFiles(authToken, limit);
    }


}
