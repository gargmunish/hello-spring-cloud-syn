package helloworld;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {

        return "home-homecontroller";
    }
    
    @RequestMapping("/search")
    public String home2(Model model) {

        return "home-homecontroller-search";
    }
    
    @RequestMapping("/getFromFoundry")
    public String home3(Model model) {
    	RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://springcloud.local.pcfdev.io/search", String.class);
        result = result+"fromFoundry";
        return result;
    }
    
    @RequestMapping("/getFromOutoFFoundry")
    public String home4(Model model) {
    	RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://192.168.1.69:8082/search", String.class);
        result = result+"fromoutOfFoundry";
        return result;
    }
}

