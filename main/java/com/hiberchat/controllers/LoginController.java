package com.hiberchat.controllers;

import com.hiberchat.exceptions.UserException;
import com.hiberchat.models.User;
import com.hiberchat.models.enums.UserStatus;
import com.hiberchat.services.UserService;
import com.hiberchat.tempmodels.TempUser;
import com.hiberchat.utils.ErrorCodes;
import com.hiberchat.utils.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return URL.LOGIN;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPage(Model model) {
        model.addAttribute("tempUser", new TempUser());
        return URL.REGISTRATION;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createAccount(@ModelAttribute TempUser tempUser, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        if (!tempUser.getPassword().equals(tempUser.getRepeatPassword())) {
            model.addAttribute("error", "Passwords mismatch");
            return URL.ERROR;
        }

        try {
            LocalDate.parse(tempUser.getBirthDate(), formatter);
        } catch (DateTimeParseException ex) {
            model.addAttribute("error", "Not all fields are filled");
            return URL.ERROR;
        }

        User user = new User().new Builder().setNickname(tempUser.getNickname())
                .setEmail(tempUser.getEmail())
                .setPassword(passwordEncoder.encode(tempUser.getPassword()))
                .setUserStatus(UserStatus.NO)
                .setRegistrationDate(LocalDate.now())
                .setBirthDate(LocalDate.parse(tempUser.getBirthDate(), formatter))
                .setCountry(tempUser.getCountry())
                .setCity(tempUser.getCity())
                .build();

        try {
            userService.addUser(user);
        } catch (UserException ex) {
            if (ex.getErrorCode() == ErrorCodes.NO_CONTENT) {
                model.addAttribute("error", "Not all fields are filled");
                return URL.ERROR;
            }
            if (ex.getErrorCode() == ErrorCodes.CONFLICT) {
                model.addAttribute("error", "User already exist");
                return URL.ERROR;
            }
        }

        return URL.LOGIN;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return URL.LOGIN;
    }
}
