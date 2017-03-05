package assignment.controller;

import assignment.model.User;
import assignment.model.UserProfile;
import assignment.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(method = POST, value = "/register")
    public ResponseEntity register(@RequestBody User user) {
        ResponseEntity response;

        try {
            List<String> register = assignmentService.register(user);
            response = new ResponseEntity<>(register, HttpStatus.OK);

        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = POST, value = "/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        ResponseEntity<String> response;

        try {
            String loginToken = assignmentService.login(user);
            response = new ResponseEntity<>(loginToken, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = POST, value = "/profile")
    public ResponseEntity getUserProfile() {
        ResponseEntity response;

        try {
            UserProfile userProfile = assignmentService.getUserProfile();
            if (userProfile != null) {
                response = new ResponseEntity<>(userProfile, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
