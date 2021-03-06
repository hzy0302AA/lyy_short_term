package cn.edu.lyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication
@Controller
public class StudioDemoApplication {
    @RequestMapping("/")  
    public String greeting(ModelMap map) {  
        String jreVersion = System.getProperty("java.specification.version");
        map.addAttribute("jreVersion", "v" + jreVersion);
        map.addAttribute("code",  "1234567");
        return "index";
    } 

	public static void main(String[] args) {
		SpringApplication.run(StudioDemoApplication.class, args);
	}
}
