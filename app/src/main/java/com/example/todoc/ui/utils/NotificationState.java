package com.example.todoc.ui.utils;

public enum NotificationState {

    ERROR_NO_TASK_NAME("Merci de renseigner une tâche");

    private final String notificationMessage;


    NotificationState(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    @Override
    public String toString() {
        return "NotificationState{" +
                "notificationMessage='" + notificationMessage + '\'' +
                '}';
    }
}
