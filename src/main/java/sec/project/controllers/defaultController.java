
package sec.project.controllers;

//Roni

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class defaultController {

    @GetMapping("*")
    public String redirect() {
        return "redirect:/login";
    }
    @GetMapping("/error")
    public String errorPage(RedirectAttributes redirectAttributes, Model model) {
        return "error";
    }
}
