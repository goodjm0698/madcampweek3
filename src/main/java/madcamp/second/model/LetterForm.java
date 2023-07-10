package madcamp.second.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LetterForm {
    private final String email;
    private final String password;
    private final String username;

    public LetterForm(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static LoginForm fromJson(Map<String, Object> json) {
        String email = (String) json.get("email");
        String password = (String) json.get("password");
        String usename = (String) json.get("username");
        return new LoginForm(email, password);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("email", email);
        json.put("password", password);
        json.put("username", username);
        return json;
    }
}