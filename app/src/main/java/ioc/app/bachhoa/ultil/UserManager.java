package ioc.app.bachhoa.ultil;

import ioc.app.bachhoa.model.Employee;

public class UserManager {

    private static UserManager instance;
    private Employee currentUser;

    private UserManager() {
        // private constructor to prevent instantiation
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setUser(Employee user) {
        this.currentUser = user;
    }

    public Employee getUser() {
        return currentUser;
    }

    public void clearUser() {
        currentUser = null;
    }
}
