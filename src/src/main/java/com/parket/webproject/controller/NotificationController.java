package com.parket.webproject.controller;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.Notification;
import com.parket.webproject.domain.User;
import com.parket.webproject.repository.NotificationRepository;
import com.parket.webproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @GetMapping
    public List<Notification> getNotifications(@AuthenticationPrincipal PrincipalDetails principal) {
        if(principal == null || principal.getUsername() == null) {
            return List.of();
        }
        User user = principal.getUser();
        return notificationRepository.findByUserAndIsReadFalse(user);
    }

    //알림 읽음 용
    @PostMapping("/readAll")
    public ResponseEntity<String> allAsRead(@AuthenticationPrincipal PrincipalDetails principal) {
        if (principal == null || principal.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }

        User user = principal.getUser();
        notificationService.allAsRead(user);
        return ResponseEntity.ok("OK");
    }


}
