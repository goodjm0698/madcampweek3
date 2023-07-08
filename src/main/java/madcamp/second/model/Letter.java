package madcamp.second.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Letter {
    @Id
    @GeneratedValue
    Long id;
    Long senderId;
    Long receiverId;
    String text;
    LocalDate date;
    int isAno;
}
