package result;

/**
 * The LoginResult class stores the data for the result body for a login request
 */
public class LoginResult extends Result { //TODO: Find a better name because this is also the register result
    /**
     * The authentication token generated for this login
     */
    private String authtoken;
    /**
     * The username of the person logging in
     */
    private String username;
    /**
     * The password of the person logging in
     */
    private String password;

    /**
     * Creates a LoginResult object
     *
     * @param message the message to return in case of an error
     * @param success indicates whether the request was successful
     * @param authtoken the authentication token
     * @param username the username
     * @param password the password
     */
    public LoginResult(String message, boolean success, String authtoken, String username, String password) {
        super(message, success);
        this.authtoken = authtoken;
        this.username = username;
        this.password = password;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
