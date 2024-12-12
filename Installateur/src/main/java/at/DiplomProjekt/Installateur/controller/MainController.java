package at.DiplomProjekt.Installateur.controller;

import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("")
    public String viewHomePage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){

        String userName = userDetails.getFirstName() + " " + userDetails.getLastName();
        model.addAttribute("userName", userName);

        return "dashboard";
    }

    @GetMapping("/admin/login")
    public String viewAdminLoginPage(){
        return "login";
    }

    @GetMapping("/user/login")
    public String viewUserLoginPage(){
        return "login";
    }



    @Controller
    static class FaviconController {

        @GetMapping("favicon.ico")
        @ResponseBody
        void returnNoFavicon() {
        }
    }


}

