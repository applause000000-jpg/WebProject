package com.parket.webproject.service;

import com.parket.webproject.domain.Notification;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    // 하나만 구매했을 때
    @Override
    public void createNotification(User user, String message) {
        if(user == null) return;
        Notification noti=Notification.builder()
                .user(user)
                .message(message)
                .isRead(false)
                .build();
        notificationRepository.save(noti);
    }

    // 여러개 구매했을 때
    @Override
    public void createNotification(List<User> users, String message) {
        if(users == null || users.isEmpty()) return;
        for(User user:users){
            createNotification(user,message);
        }
    }

    @Override
    public List<Notification> getRecentNotification(User user) {
        return notificationRepository.findTop5ByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public long getUnreadCount(User user) {
        return notificationRepository.countUnreadByUser(user);
    }

    @Override
    public void allAsRead(User user) {
        notificationRepository.allAsReadByUser(user.getId());
    }


}

