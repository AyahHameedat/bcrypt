package com.example.bcrypt.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class homeController {

    @GetMapping("/")
    public String getHomePage()
    {
        return "index";
    }

    @GetMapping("/secret")
    public String getHomePageWithSecret(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        String username = session.getAttribute("username").toString();

        model.addAttribute("username",username);
        return "indexSecret";

    }


    @GetMapping("/postSecret")
    public String getPostPageWithSecret(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();
        String username = session.getAttribute("textContent").toString();

        model.addAttribute("textContent",username);
        return "userPage";

    }


}
