package edu.ufp.inf.sd.rmi.FroggerGame.server;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class simulates a DBMockup for managing users and books.
 *
 * @author rmoreira
 *
 */
public class DBMockup {
    private final ArrayList<User> users;

    private HashMap<String, SessionRI> sessions;

    /**
     * This constructor creates and inits the database with some books and users.
     */
    public DBMockup() {
        users = new ArrayList();
        this.sessions = new HashMap<>();
    }

    /**
     * Registers a new user.
     * 
     * @param u username
     * @param p passwd
     */
    public void register(String username, String password) {
        if (!exists(username, password)) {
            users.add(new User(username, password));
        }
    }

    /**
     * Checks the credentials of an user.
     * 
     * @param u username
     * @param p passwd
     * @return
     */
    public boolean exists(String username, String password)
    {
        for (User usr : this.users)
        {
            if (usr.getUsername().compareTo(username) == 0 && usr.getPassword().compareTo(password) == 0)
                return true;
        }
        return false;
    }

    public HashMap<String, SessionRI> getSessions() {
        return sessions;
    }
}
