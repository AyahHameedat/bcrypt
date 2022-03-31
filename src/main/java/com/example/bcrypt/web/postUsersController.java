package com.example.bcrypt.web;


import com.example.bcrypt.domain.postModel;
import com.example.bcrypt.domain.userModel;
import com.example.bcrypt.infrastructure.postRepo;
import com.example.bcrypt.infrastructure.userRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class postUsersController {
    @Autowired
    userRepo userRepo;
    postRepo postRepo;


    @GetMapping("/login")
    public String getLogIn()
    {
        return "login";
    }

    @PostMapping("/login")
    public RedirectView logInUser(String username, String password)
    {
        userModel userFromDb = userRepo.findByUsername(username);

        if ((userFromDb == null) || (!BCrypt.checkpw(password, userFromDb.getPassword()))) {
            return new RedirectView("/login");
        }

        return new RedirectView("/userPage");
    }

    @GetMapping("/loginSecret")
    public String getLogInSecret()
    {
        return "loginSecret";
    }


    @GetMapping("/signUp")
    public String getSignUp()
    {
        return "signUp";
    }


    @PostMapping("/loginSecret")
    public RedirectView logInUserSecret(HttpServletRequest request, String username, String password)
    {
        userModel userFromDb = userRepo.findByUsername(username);
        if ((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {
            return new RedirectView("/loginSecret");
        }

        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        return new RedirectView("/secret");
    }


    @PostMapping("/logOutSecret")
    public RedirectView logOutUserSecret(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.invalidate();

        return new RedirectView("/loginSecret");
    }


    @PostMapping("/signUp")
    public RedirectView signUpUser(Model model, String username, String password)
    {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        userModel newUser = new userModel(username, hashedPassword);

        userRepo.save(newUser);
        return new RedirectView("/login");
    }


    @PostMapping("/userPage")
    public RedirectView userPageSecret(HttpServletRequest request, String textContent)
    {
//        postModel userFromDb = postRepo.findByUserTextContent(textContent);

        HttpSession session = request.getSession();
        session.setAttribute("textContent", textContent);

        return new RedirectView("/userPage");
    }

}
