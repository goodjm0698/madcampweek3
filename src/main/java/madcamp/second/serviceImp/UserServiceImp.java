package madcamp.second.serviceImp;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import madcamp.second.mapper.UserMapper;
import madcamp.second.model.User;
import madcamp.second.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getUserById(Long id)
    {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email)
    {
        return userMapper.getUserByEmail(email);
    }



    @Override
    public void signup(User user) throws BadCredentialsException
    {
        if(!user.getUsername().equals("") && !user.getEmail().equals(""))
        {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            userMapper.enrollUser(user);
        }
    }

    @Override
    public void editUser(User user)
    {
        userMapper.updateUser(user);
    }

    @Override
    public void withdrawProfile(Long id)
    {
        userMapper.withdrawUser(id);
    }

    @Override
    public PasswordEncoder passwordEncoder()
    {
        return this.passwordEncoder;
    }

    @Override
    public Authentication login(String email, String password) throws AuthenticationException
    {
        UsernamePasswordAuthenticationToken token;
        User user = userMapper.getUserByEmail(email);

        if(user!=null && passwordEncoder.matches(password, user.getPassword()))
        {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));

            token = new UsernamePasswordAuthenticationToken(user.getId(), null, roles);

            return token;
        }
        throw new BadCredentialsException("No such user or wrong password");
    }

    @Override
    public String getAccessToken(String code)
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
            sb.append("grant_type=authentication_code");

            sb.append("&client_id=61cf0a365da5ecf4a1c4bd3d12aed9ab");
            sb.append("&redirect_uri=http://localhost:8080/kakao/login_done");

            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = connection.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null)
            {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new com.google.gson.JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);

            br.close();
            bw.close();

        } catch(Exception e)
        {
            e.printStackTrace();
        }

        return accessToken;
    }

    @Override
    public List<User> getUserList()
    {
        return userMapper.getUserList();
    }
}
