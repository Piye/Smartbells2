package teameleven.smartbells2.ViewLayer.login;

/**
 * Base credentials class to describe a users credentials.
 * Created by Brian McMahon on 22/10/2015.
 */
public class Credentials {
    private String userName;
    private String email;
    private String password;

    /**
     * sets the username
     * @param userName : username to be set
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    /**
     * sets the email
     * @param email : the email to be set
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * sets the password
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * returns the userName for the current user
     * @return the userName
     */
    public String getUserName(){
        return userName;
    }

    /**
     * returns the current users email
     * @return the email
     */
    public String getEmail(){
        return email;
    }

    /**
     * returns the current users password
     * @return the password
     */
    public String getPassword(){
        return password;
    }
}
