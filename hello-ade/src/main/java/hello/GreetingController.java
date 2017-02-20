package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {
	
    @RequestMapping("/greeting")
    @ResponseBody
    public Greeting greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, @RequestParam(value="salutation", required=false, defaultValue="Hello") String salutation, Model model) {
        Greeting g = new Greeting();
        g.setName(name);
        g.setSalutation(salutation);    	
		
        return g;
    }

}
