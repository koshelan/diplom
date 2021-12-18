package education.diplom.controller;

import education.diplom.exceptions.AuthenticationCloudException;
import education.diplom.exceptions.DiplomException;
import education.diplom.exceptions.UploadFileException;
import education.diplom.model.ErrorMsg;
import education.diplom.repository.ErrorMsgRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionConfig {
    private final ErrorMsgRepository errorMsgRepository;

    @ExceptionHandler({UploadFileException.class,DiplomException.class})
    public ResponseEntity<ErrorMsg> handleUploadException(Exception exception) {
        return ResponseEntity.status(400)
                .body(errorMsgRepository.save(ErrorMsg.builder().massage(exception.getMessage()).build()));
    }

    @ExceptionHandler({AuthenticationCloudException.class})
    public ResponseEntity<ErrorMsg> handleConverterErrors(Exception exception) {
        return ResponseEntity.status(401)
                .body(errorMsgRepository.save(ErrorMsg.builder().massage(exception.getMessage()).build()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorMsg> handleRTM(Exception exception) {
        return ResponseEntity.status(500)
                .body(errorMsgRepository.save(ErrorMsg.builder().massage(exception.getMessage()).build()));
    }

}
