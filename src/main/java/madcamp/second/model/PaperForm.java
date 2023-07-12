package madcamp.second.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PaperForm {
    private final Long receiverId;
    private final String text;

    public PaperForm(Long receiverId, String text) {
        this.receiverId = receiverId;
        this.text = text;
    }

    public static LoginForm fromJson(Map<String, Object> json) {
        String email = (String) json.get("receiverId");
        String password = (String) json.get("text");
        return new LoginForm(email, password);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("receiverId", receiverId);
        json.put("text", text);
        return json;
    }
}

