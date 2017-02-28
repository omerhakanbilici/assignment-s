package assignment.controller;

import assignment.model.User;
import assignment.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(method = POST, value = "/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        ResponseEntity<String> response;

        try {
            String register = assignmentService.register(user);
            response = new ResponseEntity<String>(register, HttpStatus.OK);

        } catch (Exception e) {
//            logger.error("error",e);
            response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = POST, value = "/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        ResponseEntity<String> response;

        try {
            String loginToken = assignmentService.login(user);
            response = new ResponseEntity<String>(loginToken, HttpStatus.OK);

        } catch (Exception e) {
//            logger.error("error",e);
            response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @RequestMapping(method = POST, value = "/profile")
    public ResponseEntity<String> getUserProfile() {
        ResponseEntity<String> response;

        try {
            String userProfile = assignmentService.getUserProfile();
            response = new ResponseEntity<String>(userProfile, HttpStatus.OK);

        } catch (Exception e) {
//            logger.error("error",e);
            response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
