package toy.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String handleRequest(Model model)
            throws Exception {

        System.out.println("/test");

        String message = "aaaaaa";
        model.addAttribute("message", message);
        return "hello";
    }


}
