package com.parket.webproject.repository;

import com.parket.webproject.domain.Notification;
import com.parket.webproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    //최근 알림
    List<Notification> findTop5ByUserOrderByCreatedAtDesc(User user);
    //전체 안읽은 알람 갯수세기
    long countByUserAndIsReadFalse(User user);
    //안 읽은 알람만 가져오기
    List<Notification> findByUserAndIsReadFalse(User user);

    List<Notification> findByUser(User user);

    // 알람 읽음 처리
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId")
    void allAsReadByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user = :user AND n.isRead = false")
    long countUnreadByUser(@Param("user") User user);

}
