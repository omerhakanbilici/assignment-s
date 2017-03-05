package assignment.service.impl;

import assignment.model.User;
import assignment.model.UserProfile;
import assignment.security.Jwt;
import assignment.service.AssignmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hbilici on 2017-02-28.
 */
@Service
public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private static ConcurrentHashMap<String, User> registeredUserMap = new ConcurrentHashMap<>();

    @Override
    public List<String> register(User user) {
        List<String> response = new ArrayList<>();

        if (isValidUser(user, response)) {
            registeredUserMap.put(user.getUsername(), user);
            response.add("Successfully registered. Please login with your username and password.");
        }

        return response;
    }

    @Override
    public String login(User loginUser) {

        // TODO boyle username password kontrolu olmaz
        if (registeredUserMap.containsKey(loginUser.getUsername())) {
            User user = registeredUserMap.get(loginUser.getUsername());
            if (user.getPassword().equals(loginUser.getPassword())) {
                return Jwt.generateToken(user);
            } else {
                return "wrong password";
            }
        } else {
            return "username not found";
        }
    }

    @Override
    public UserProfile getUserProfile() {
        User currentUser = getCurrentUser();
//        User currentUser = registeredUserMap.get("ahmet");

        if (currentUser != null) {
            return new UserProfile(currentUser.getUsername(), currentUser.getEmail());
        } else {
            return null;
        }
    }


    @Override
    public User getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                String username = ((User) authentication.getPrincipal()).getUsername();
                return getByUsername(username);
            }
        } catch (Exception e) {
            logger.error("Error at getCurrentUser : ", e);
        }
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return registeredUserMap.get(username);
    }


    private boolean isValidUser(User user, List<String> retVal) {
        boolean isValidToRegister = true;
        if (registeredUserMap.get(user.getUsername()) != null) {
            retVal.add("User exist. Try login.");
            isValidToRegister = false;
        } else {
            if (!isValidUsername(user)) {
                retVal.add("Username should be at least 3 characters.");
                isValidToRegister = false;
            }
            if (!isValidPassword(user)) {
                retVal.add("Password needs to be at least 7 characters including at least 1 number and 1 upper case letter.");
                isValidToRegister = false;
            }
            if (!isValidEmail(user)) {
                retVal.add("Email needs to be a well-formatted.");
                isValidToRegister = false;
            }
        }
        return isValidToRegister;
    }

    private boolean isValidUsername(User user) {
        String pattern = ".{3,}";
        String username;
        if (StringUtils.isNotBlank(user.getUsername())) {
            username = user.getUsername();
        } else {
            return false;
        }
        return username.matches(pattern);
    }

    private boolean isValidPassword(User user) {
        String pattern = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{7,}";
        String password;
        if (StringUtils.isNotBlank(user.getPassword())) {
            password = user.getPassword();
        } else {
            return false;
        }
        return password.matches(pattern);
    }

    private boolean isValidEmail(User user) {
        boolean isValid = true;
        String email;
        if (StringUtils.isNotBlank(user.getEmail())) {
            email = user.getEmail();
        } else {
            return false;
        }

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            isValid = false;
        }
        return isValid;
    }

    public static ConcurrentHashMap<String, User> getRegisteredUserMap() {
        return registeredUserMap;
    }

    public static void setRegisteredUserMap(ConcurrentHashMap<String, User> registeredUserMap) {
        AssignmentServiceImpl.registeredUserMap = registeredUserMap;
    }

}
