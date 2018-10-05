package helloworld;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {

        return "home-homecontroller";
    }
    
    @RequestMapping("/search")
    public String home(Model model) {

        return "home-homecontroller-search";
    }
}
