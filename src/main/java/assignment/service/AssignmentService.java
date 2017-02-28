package assignment.service;


import assignment.model.User;

/**
 * Created by hbilici on 2017-02-28.
 */
public interface AssignmentService {

    String register(User user);

    String login(User user);

    String getUserProfile();
}
