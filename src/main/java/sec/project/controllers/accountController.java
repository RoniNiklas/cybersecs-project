package sec.project.controllers;

//Roni
import org.springframework.beans.factory.annotation.Autowired;
import sec.project.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sec.project.dao.AccountDB;

@Controller
public class accountController {

    @Autowired
    AccountDB accodb;

    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }

    @PostMapping("login")
    public String userLogin(RedirectAttributes redirectAttributes,
            @RequestParam String username, @RequestParam String pw) {
        if (accodb.checkLogin(username, pw)) {
            return "redirect:/users/" + username;
        }
        redirectAttributes.addFlashAttribute("error", "Wrong login credentials");
        return "redirect:/login";
    }

    @GetMapping("/users/{username}")
    public String userGet(RedirectAttributes redirectAttributes, Model model,
            @PathVariable String username) {
        if (accodb.contains(username)) {
            Account acco = accodb.getUser(username);
            model.addAttribute("acco", acco);
            return "acco";
        }
        redirectAttributes.addFlashAttribute("error", "There is no user with that username");
        return "redirect:/error";
    }

    @PostMapping("users")
    public String userCreate(RedirectAttributes redirectAttributes,
            @RequestParam String username, @RequestParam String pw, @RequestParam String cardData) {
        if (!accodb.contains(username)) {
            accodb.save(username, pw, cardData);
            return "redirect:/users/" + username;
        }
        redirectAttributes.addFlashAttribute("error", "That username is already taken");
        return "redirect:/login";
    }
}
