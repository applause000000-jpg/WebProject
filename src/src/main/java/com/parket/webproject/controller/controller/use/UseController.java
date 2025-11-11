package com.parket.webproject.controller.controller.use;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/use")
public class UseController {

    @GetMapping("/list")
    public String list() {
        return "use/list";

    }

    @GetMapping("/list2")
    public String list2() {
        return "use/list2";
    }

    @GetMapping("/write")
    public String write() {
        return "use/write";
    }

}
