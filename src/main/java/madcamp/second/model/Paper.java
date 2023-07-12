package madcamp.second.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Paper {
    @GeneratedValue
    @Id
    private Long id;

    private Long farmId;

    private String text;

    private Long senderId;


}
