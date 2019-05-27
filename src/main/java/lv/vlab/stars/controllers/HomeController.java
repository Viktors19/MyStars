package lv.vlab.stars.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping(path="/")
    public String index () {
        return "index";
    }

    @GetMapping(path="/angular")
    public String index2 () {
        return "index2";
    }

    @GetMapping(path="/isready")
    public @ResponseBody String isReady () {
        return "Ready";
    }
}