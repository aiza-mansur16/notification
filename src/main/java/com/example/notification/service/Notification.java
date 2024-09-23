package com.example.notification.service;

public interface Notification<T>{
    void sendNotification(T notificationInfoDto);

}
