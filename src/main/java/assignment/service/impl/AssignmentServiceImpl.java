package assignment.service.impl;

import assignment.model.User;
import assignment.service.AssignmentService;
import org.springframework.stereotype.Service;

/**
 * Created by hbilici on 2017-02-28.
 */
@Service
public class AssignmentServiceImpl implements AssignmentService {

    public static User registeredUser;

    @Override
    public String register(User user) {
        registeredUser = user;
        return "token";
    }

    @Override
    public String login(User user) {
        return null;
    }

    @Override
    public String getUserProfile() {
        return null;
    }

}
