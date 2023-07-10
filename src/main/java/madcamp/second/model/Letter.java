package madcamp.second.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Letter {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "text")
    private String text;

    @Column(name = "generated_date")
    private LocalDate generatedDate;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "is_ano")
    private int isAno;

    @Column(name = "pos_x")
    private int posX;

    @Column(name = "pos_y")
    private int posY;

    @Column(name = "img_type")
    private int imgType;
}
