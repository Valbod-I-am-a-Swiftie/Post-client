package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class HelloController {

    @RequestMapping("/")
    public String get() {
        return "POSHOL OTSYUDA!!\n";
    }

    @RequestMapping("/hello")
    public String getHello() {
        return "PRIVET PACANI!!!\n";
    }
}