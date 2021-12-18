package education.diplom.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ErrorMsg {


    private String massage;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
