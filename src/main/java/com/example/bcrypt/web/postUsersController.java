package com.example.bcrypt.web;


import com.example.bcrypt.domain.postModel;
import com.example.bcrypt.domain.userModel;
import com.example.bcrypt.infrastructure.postRepo;
import com.example.bcrypt.infrastructure.userRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.file.FileStore;
import java.util.List;

@Controller
public class postUsersController {
    @Autowired
    userRepo userRepo;
    postRepo postRepo;

    public postUsersController(userRepo userRepo, postRepo postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/")
    public String getHomepage()
    {
        return "index";
    }


    @GetMapping("/signUp")
    public String getSignUp()
    {
        return "signUp";
    }

    @PostMapping("/signUp")
    public RedirectView signUpUser(Model model, String username, String password)
    {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        userModel newUser = new userModel(username, hashedPassword);

        userRepo.save(newUser);
        return new RedirectView("/loginSecret");
    }


    @GetMapping("/loginSecret")
    public String getLogInSecret()
    {
        return "loginSecret";
    }



    @PostMapping("/loginSecret")
    public RedirectView logInUserSecret(HttpServletRequest request,@ModelAttribute userModel users, String username, String password)
    {
        userModel userFromDb = userRepo.findByUsername(username);
        if ((userFromDb == null)
                || (!BCrypt.checkpw(password, userFromDb.getPassword())))
        {
            return new RedirectView("/loginSecret");
        }

        HttpSession session = request.getSession();
        session.setAttribute("username", users.getUsername());

        return new RedirectView("/userPage");
    }


    @GetMapping("/userPage")
    public String getUserPage()
    {
        return "userPage";
    }


//   Resources:  https://stackoverflow.com/questions/26337068/retrieve-logged-user-object-and-save-it-as-a-foreign-key
    //Retrieve logged user object and save it as a foreign key

    @PostMapping("/userPage")
    public RedirectView postUsers(HttpServletRequest request,@ModelAttribute postModel posts, String textContent ) {
//        User user = userManager.getUserObject(userManager.getUserId(session.getAttribute("currentUser").toString()));
        userModel users = userRepo.findByUsername(request.getSession().getAttribute("username").toString());

        System.out.println("PTS");
//        user.addToRecord(record); ** save the user id in post table
        posts.setUsermodel(users);
//        recordService.addRecord(record);   save the post in post repo = post table
        postRepo.save(posts);
//        return "redirect:/protected/purse";  i'm not sure about the return page
        return new RedirectView("index");

//        postModel postUser = postRepo.getById(Id);
////        userModel userFromDb = userRepo.findByUsername(username);
//        HttpSession session = request.getSession();
////        session.setAttribute("username", users.getUsername());
//
//        return new RedirectView("/userPage");
    }
    @GetMapping("/index")
    public String getPosts(HttpServletRequest request,@ModelAttribute postModel posts, Model model)
    {
        HttpSession session = request.getSession();
//        userModel users = userRepo.findByUsername(request.getSession().getAttribute("username").toString());
        String userName = request.getSession().getAttribute("username").toString();

        session.setAttribute("username", userName);
//        response.sendRedirect("welcome.jsp");
//        session.setAttribute("username", users.getUsername());
        model.addAttribute("PostList",postRepo.findAll());
        return ("index");
    }

}





//    @PostMapping("/userPage")
//    public RedirectView userPageSecret(HttpServletRequest request,@ModelAttribute userModel users, String textContent)
//    {
////        Post here to save it using post
//
//        userModel userFromDb = userRepo.findByUsername(users.getUsername());
////        postModel userFromDb = postRepo.save(users.getUsername().contains(userRepo.findByUsername(users.getUsername())));
//        if ((userFromDb == null)
//                || (!BCrypt.checkpw(users.getPassword(), userFromDb.getPassword())))
//        {
//            return new RedirectView("/userPage");
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute("username", users.getUsername());
//
//        return new RedirectView("/index");
//    }


//    @PostMapping("/userPage")
//    public RedirectView userPageSecret(HttpServletRequest request, @ModelAttribute postModel post)
//    {
//        userModel users = null;
//        userModel userFromDb = userRepo.findByUsername(users.getUsername());
//        post.setTextContent(String.valueOf(userFromDb));
//        postRepo.save(post);
//
////        HttpSession session = request.getSession();
////        session.setAttribute("textContent", textContent);
//
//        System.out.println("AYA");
//
//        return new RedirectView("/userPagePost");
//    }


//    @GetMapping("/index")
//    public String getPosts(HttpServletRequest request,@ModelAttribute postModel posts, Model model)
//    {
//        HttpSession session = request.getSession();
////        userModel users = userRepo.findByUsername(request.getSession().getAttribute("username").toString());
//        String userName = request.getSession().getAttribute("username").toString();
//
////        if (email == null) {
////            // user is not logged in, deal with it.
////        } else {
////            List<Chat> chats = _chatDao.getChats(email);
////            return new ModelAndView("chatView", "chats", chats);
////        }
////
////        if (userName == null) {
////            // user is not logged in, deal with it.
////            return "loginSecret";
////        } else {
////            List<posts> chats = model.addAttribute("PostList",postRepo.findAll());
//////            return new ModelAndView("chatView", "chats", chats);
////            return ("index");
////        }
//
//
//        session.setAttribute("username", userName);
////        response.sendRedirect("welcome.jsp");
////        session.setAttribute("username", users.getUsername());
//        model.addAttribute("PostList",postRepo.findAll());
//        return ("index");
//    }