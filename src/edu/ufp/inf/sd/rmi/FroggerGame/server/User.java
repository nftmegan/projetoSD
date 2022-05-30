package edu.ufp.inf.sd.rmi.FroggerGame.server;

import java.io.Serializable;

/**
 *
 * @author rmoreira
 */
public class User implements Serializable {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + '}';
    }

    /**
     * @return the uname
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param uname the uname to set
     */
    public void setUsername(String uname) {
        this.username = uname;
    }

    /**
     * @return the pword
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param pword the pword to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
