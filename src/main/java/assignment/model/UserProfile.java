package assignment.model;

/**
 * Created by hbilici on 2017-02-28.
 */
public class UserProfile {

    private String mail;
    private String username;

    public UserProfile(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
