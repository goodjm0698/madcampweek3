package madcamp.second.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class LetterForm {


    private Long receiverId;

    private double posX;

    private double posY;

    private String text;

    public static LetterForm fromJson(Map<String, Object> json) {
        Long receivedId = (Long) json.get("receivedId");
        String text = (String) json.get("text");
        double posX = (Double) json.get("posX");
        double posY = (Double) json.get("posY");
        return new LetterForm(receivedId, posX, posY, text);
    }
}