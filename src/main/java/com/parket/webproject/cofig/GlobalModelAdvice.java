package com.parket.webproject.cofig;

import com.parket.webproject.cofig.author.PrincipalDetails;
import com.parket.webproject.domain.User;
import com.parket.webproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@RequiredArgsConstructor
// 헤더 알림 관련 config
public class GlobalModelAdvice {
    private final NotificationService notificationService;

    @ModelAttribute
    public void addAttributes(Model model, @AuthenticationPrincipal PrincipalDetails principal) {
        if(principal!=null && principal.getUser()!=null){
            User user=principal.getUser();
            long unreadCount = notificationService.getUnreadCount(user);
            model.addAttribute("alarmCount", unreadCount);
            model.addAttribute("notifications", notificationService.getRecentNotification(user));
        }
    }
}
