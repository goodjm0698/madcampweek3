package madcamp.second.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import madcamp.second.model.Farm;
import madcamp.second.model.User;
import madcamp.second.security.JwtTokenUtil;
import madcamp.second.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FarmController {
    @Autowired
    FarmService farmService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("farm_list")
    public ResponseEntity<String> getFarmList(@RequestHeader("Authorization") String token)
    {
        try
        {
            if(!jwtTokenUtil.validateToken(token))  throw new BadCredentialsException("You are not enrolled or jwt is not valid");

            List<Farm> farms = farmService.getFarmList();
            String json = objectMapper.writeValueAsString(farms);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Cannot load farm");
    }

    @GetMapping("get_user_of_farm")
    public ResponseEntity<String> getUserWithFarm(@RequestHeader("Authorization") String token, @RequestParam Long farmId)
    {
        try
        {
            if(!jwtTokenUtil.validateToken(token))  throw new BadCredentialsException("Invalid User");

            List<User> users = farmService.getUserListWithFarm(farmId);
            String json = objectMapper.writeValueAsString(users);
            return ResponseEntity.ok(json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Cannot load user list of farm");
    }
}