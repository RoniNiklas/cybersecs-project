
package sec.project.controllers;

//Roni

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class defaultController {

    @GetMapping("*")
    public String redirect() {
        return "redirect:/login";
    }
    @GetMapping("/error")
    public String errorPage(Model model) {
        return "error";
    }
}
