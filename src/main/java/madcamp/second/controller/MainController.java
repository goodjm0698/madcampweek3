package madcamp.second.controller;

import madcamp.second.model.User;
import madcamp.second.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import madcamp.second.serviceImp.KakaoService;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    KakaoService kakaoService;

    @Autowired
    UserService userService;

    @GetMapping("test")
    @ResponseBody
    public String testConnect()
    {
        return "connection established";
    }

    @GetMapping("kakao/sign_in")
            public String kakaoSignIn(@RequestParam("code") String code)
    {
        Map<String, Object> result = kakaoService.execKakaoLogin(code);

        return "redirect:webauthcallback://success?cusomToken="+result.get("customToken").toString();
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

    @GetMapping("/login_done")
    public String loginPage()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken)
            return "loginPage";

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signupPage()
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
