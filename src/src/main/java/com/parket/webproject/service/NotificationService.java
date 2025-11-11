package com.parket.webproject.service;

import com.parket.webproject.domain.Notification;
import com.parket.webproject.domain.User;

import java.util.List;

public interface NotificationService {
    //1개 구매했을 때
    void createNotification(User user, String message);
    //3개 구매했을 때 여러명한테 알려야함
    void createNotification(List<User> users, String message);
    // 알림조회
    List<Notification> getRecentNotification(User user);
    //읽지 않은 알림 수 조회
    long getUnreadCount(User user);
    // 알림 읽었을 때
    void allAsRead(User user);
}
