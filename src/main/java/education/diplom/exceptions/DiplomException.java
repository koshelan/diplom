package education.diplom.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DiplomException extends RuntimeException{
    public DiplomException(String message) {
        super(message);
    }
}
