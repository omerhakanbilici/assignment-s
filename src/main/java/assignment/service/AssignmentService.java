package assignment.service;


import assignment.model.User;
import assignment.model.UserProfile;

import java.util.List;

/**
 * Created by hbilici on 2017-02-28.
 */
public interface AssignmentService {

    List<String> register(User user);

    String login(User user);

    UserProfile getUserProfile();

    User getCurrentUser();

    User getByUsername(String username);
}
