package madcamp.second.controller;

import madcamp.second.model.LoginForm;
import madcamp.second.model.User;
import madcamp.second.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import madcamp.second.serviceImp.KakaoService;

import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:5000")
public class MainController {
    @Autowired
    KakaoService kakaoService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/test")
    @ResponseBody
    public String testConnect()
    {
        return "connection established";
    }

    @GetMapping("/kakao/sign_in")
            public String kakaoSignIn(@RequestParam("code") String code)
    {
        Map<String, Object> result = kakaoService.execKakaoLogin(code);

        return "redirect:webauthcallback://success?customToken="+result.get("customToken").toString();
    }

    @GetMapping("/")
    public String mainPage(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof AnonymousAuthenticationToken)
        {
            model.addAttribute("user", null);
            return "index";
        }

        Long id = (Long) authentication.getPrincipal();

        User user = userService.getUserById(id);
        user.setPassword(null);
        model.addAttribute("user", user);

        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken)
            return "loginPage";

        return "redirect:/";
    }

    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm)
    {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        try
        {
            UsernamePasswordAuthenticationToken token = userService.login(email, password);
            Authentication authentication = authenticationManager.authenticate(token);
            String body = userService.generateToken(token);

            return ResponseEntity.ok(body);
        }
        catch (Exception e)
        {
            String error = "Login failed";
            e.printStackTrace();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/signup")
    public String getSignupPage()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken)
            return "signupPage";
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(User user)
    {
        try
        {
            userService.signup(user);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("/updateProfile")
    public String editProfilePage(Model model)
    {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "editProfilePage";
    }

    @PostMapping("/updateProfile")
    public String editProfile(User user)
    {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setId(id);
        userService.editUser(user);
        return "redirect:/";
    }

    @GetMapping("/withdrawProfile")
    public String withdrawProfilePage()
    {
        Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(id!=null)
        {
            userService.withdrawProfile(id);
        }
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}
