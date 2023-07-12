package madcamp.second.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Paper {
    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "farm_id") // Map to the snake case column name in the database
    private Long farmId;

    private String text;

    @Column(name = "sender_id") // Map to the snake case column name in the database
    private Long senderId;
}
