package education.diplom.service;

import education.diplom.exceptions.DiplomException;
import education.diplom.model.FileList;
import education.diplom.model.FileMsg;
import education.diplom.model.LoginMsg;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CloudService {

    public String uploadFile(String authToken, String fileName, FileMsg fileMsg) {
        var path = getRootPath(authToken).concat(fileName);
        System.out.println(path);
        String res;
        var file = new File(path);
        if (!file.exists()){
            System.out.println("создал");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (var fileWriter = new FileWriter(path)) {
            if (fileMsg.hash().equals(String.valueOf(fileMsg.file().hashCode()))) {
                res = "OK";
            } else {
                res = "NOT OK";
            }
            System.out.println(res);
            fileWriter.write(fileMsg.file());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DiplomException("Something went wrong");
        }
    }

    private String getRootPath(String authToken) {
        var root = "files/";
        try{
            var q =SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get();
            var w =SecurityContextHolder.getContext().getAuthentication();
            System.out.println(q);
            System.out.println(q.getAuthority());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        var dir = new File(root);
        if (!dir.exists()){
            System.out.println("создал папку");
            dir.mkdir();
        }


        return root;
    }

    public LoginMsg autorisation(String login, String password) {
        return new LoginMsg("Token");
    }

    public String logout(String authToken) {
        return "logout";
    }

    public String deleteFile(String authToken, String fileName) {
        var path = getRootPath(authToken).concat(fileName);
        var file = new File(path);
        if (!file.exists()) {
            throw new DiplomException("Error input data");
        }
        if (file.delete()) {
            return "OK";
        } else {
            throw new RuntimeException("Error deleting file");
        }
    }

    public FileMsg getFile(String authToken, String fileName) {
        var path = getRootPath(authToken).concat(fileName);
        var file = new File(path);
        if (!file.exists()) {
            throw new DiplomException("Error input data");
        }
        try (var fileReader = new FileReader(file)) {
            char[] fileContext = new char[(int) file.length()];
            fileReader.read(fileContext);
            String fileString = String.valueOf(fileContext);
            return new FileMsg(String.valueOf(fileString.hashCode()), fileString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error upload file");
        }
    }

    public String editFile(String authToken, String fileName, String name) {
        var root = getRootPath(authToken);
        var path = root.concat(fileName);
        var file = new File(path);
        var newFile = new File(root.concat(name));
        if (!file.exists()) {
            throw new DiplomException("Error input data");
        }

        if (file.renameTo(newFile)) {
            return "OK";
        } else {
            throw new RuntimeException("Error edit file");
        }
    }

    public List<FileList> getFiles(String authToken, int limit) {
        var root = getRootPath(authToken);
        var dir = new File(root);
        return Arrays.stream(dir.list())
                .limit(limit)
                .map((i)->new FileList(i,(int) (new File(root.concat(i))).length()))
                .toList();
    }

}
