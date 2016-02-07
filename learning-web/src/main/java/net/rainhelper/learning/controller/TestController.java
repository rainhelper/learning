package net.rainhelper.learning.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(@RequestParam("name") String name, Model model)
            throws Exception {

        System.out.println("/test");

        String message = name;
        model.addAttribute("message", message);
        return "hello";
    }


}
