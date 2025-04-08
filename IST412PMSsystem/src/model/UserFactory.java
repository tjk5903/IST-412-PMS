package model;

import java.util.ArrayList;

public class UserFactory {
    public static User createUser(String role, String name, String contact) {
        if ("Doctor".equalsIgnoreCase(role)) {
            return new Doctor(1, name, "General Medicine", contact, new ArrayList<>());
        } else if ("Patient".equalsIgnoreCase(role)) {
            return new Patient(1, name, 30, "Male", contact, "123 Address", new ArrayList<>());
        } else if ("Admin".equalsIgnoreCase(role)) {
            return new Admin(name, contact);
        }
        return null;
    }
}
