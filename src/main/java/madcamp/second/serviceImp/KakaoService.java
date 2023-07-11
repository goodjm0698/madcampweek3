package madcamp.second.serviceImp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import madcamp.second.model.User;
import madcamp.second.security.JwtTokenUtil;
import madcamp.second.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.*;

@Service
public class KakaoService {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    String signupOrLogin(Map<String, Object> userInfo)
    {
        try
        {
            Long id = (Long) userInfo.get("id");
            String nickname = (String) userInfo.get("nickname");
            String accessToken = (String) userInfo.get("access_token");

            User user = userService.getUserById(id);
            UsernamePasswordAuthenticationToken token;
            if(user == null)
            {
                User newUser = new User();
                newUser.setId(id);
                newUser.setUsername(nickname);
                newUser.setEmail(accessToken.substring(3));
                newUser.setPassword(accessToken);
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority("USER"));

                token = new UsernamePasswordAuthenticationToken(user.getId(), null, roles);
                userService.signup(newUser);
                return jwtTokenUtil.generateToken(token);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public String execKakaoLogin(String authorize_code)
    {
        String accessToken = getAccessToken(authorize_code);

        Map<String, Object> userInfo = getUserInfo(accessToken);

        return signupOrLogin(userInfo);
    }

    public Map<String, Object> getUserInfo(String access_token)
    {
        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("access_token", access_token);

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try
        {
            URL url = new URL(reqURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null)
            {
                result += line;
            }

            JsonElement element = JsonParser.parseString(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            Long id = element.getAsJsonObject().get("id").getAsLong();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("id", id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return userInfo;
    }

    public String getAccessToken(String authorize_code)
    {
        String accessToken = "";
        String refreshToken = "";

        String reqUrl = "https://kauth.kakao.com/oauth/token";

        try
        {
            URL url = new URL(reqUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=61cf0a365da5ecf4a1c4bd3d12aed9ab");
            sb.append("&redirect_ur=http://168.131.151.213:4040/kakao/sign_in");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null)
            {
                result += line;
            }

            System.out.println(result);

            JsonElement element = JsonParser.parseString(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return accessToken;
    }
}
