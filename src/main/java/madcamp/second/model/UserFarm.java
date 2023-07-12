package madcamp.second.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class UserFarm {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long farmId;
}
