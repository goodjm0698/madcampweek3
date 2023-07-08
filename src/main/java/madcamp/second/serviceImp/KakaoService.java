package madcamp.second.serviceImp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class KakaoService {
    public Map<String, Object> execKakaoLogin(String authorize_code)
    {
        String accessToken = getAccessToken(authorize_code);

        Map<String, Object> userInfo = getUserInfo(accessToken);

        return userInfo;
    }

    public Map<String, Object> getUserInfo(String access_token)
    {
        Map<String, Object> userInfo = new HashMap<>();

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try
        {
            URL url = new URL(reqURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", "Bearer" + access_token);

            int responseCode = connection.getResponseCode();

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

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
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
            sb.append("&client_id=03121ea92a07aca04abc58e321f84cef");
            sb.append("&redirect_ur=http://ipaddress/sign_in");
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
