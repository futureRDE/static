package com.example.demoelasticsearch1.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @GetMapping({"/","/index"})
    public String index() {
        return "index";
    }


}
