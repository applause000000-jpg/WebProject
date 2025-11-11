package com.parket.webproject.controller.controller.noti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noti")
public class NotiContoller {

    @GetMapping("/list")
    public String showDomesticList() {
        return "noti/list";
    }
    @GetMapping("/detail")
    public String detail() {
        return "noti/detail";
    }
}
