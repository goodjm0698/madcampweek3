package madcamp.second.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String username;

    public String toJson()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
