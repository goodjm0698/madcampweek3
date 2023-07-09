package madcamp.second.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LoginForm {
    private final String email;
    private final String password;

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginForm fromJson(Map<String, Object> json) {
        String email = (String) json.get("email");
        String password = (String) json.get("password");
        return new LoginForm(email, password);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("email", email);
        json.put("password", password);
        return json;
    }
}
