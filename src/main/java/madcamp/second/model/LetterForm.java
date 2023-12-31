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


//    private Long receiverId;
//
//    private double posX;
//
//    private double posY;

    private String text;

    private String writtenDate;

    private String emotion;

    private String weather;

//    private int isAno;

    public static LetterForm fromJson(Map<String, Object> json) {
        // Long receivedId = (Long) json.get("receivedId");
        String text = (String) json.get("text");
        String writtenDate = (String) json.get("writtenDate");
        String emotion = (String) json.get("emotion");
        String weather = (String) json.get("weather");
        // double posX = (Double) json.get("posX");
        // double posY = (Double) json.get("posY");
        // int isAno = (int) json.get("isAno");
        return new LetterForm(text,writtenDate,emotion,weather);
    }


}
//안써도 될 듯