package madcamp.second.model;

import lombok.Data;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Letter {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "senderId")
    private Long senderId;

    // @Column(name = "receiver_id")
    // private Long receiverId;

    @Column(name = "text")
    private String text;

    @Column(name = "writtenDate")
    private String writtenDate;

    // @Column(name = "open_date")
    // private LocalDate openDate;

    @Column(name = "emotion")
    private String emotion;

    @Column(name = "weather")
    private String weather;

    // @Column(name = "is_ano")
    // private int isAno;

    // @Column(name = "pos_x")
    // private double posX;

    // @Column(name = "pos_y")
    // private double posY;

    // @Column(name = "img_type")
    // private int imgType;
}
