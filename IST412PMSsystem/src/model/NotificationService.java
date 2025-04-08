package model;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<String> notifications = new ArrayList<>();

    public void sendNotification(User user, String message) {
        String notification = "To " + user.getName() + ": " + message;
        notifications.add(notification);
        System.out.println(notification); // For simplicity, we're just printing it here
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
