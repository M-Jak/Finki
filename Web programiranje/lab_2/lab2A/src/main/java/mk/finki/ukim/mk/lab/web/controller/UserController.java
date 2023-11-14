package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.model.TicketOrder;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.MovieService;
import mk.finki.ukim.mk.lab.service.TicketOrderService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/movies/users")
public class UserController {
    private final TicketOrderService ticketOrderService;
    private final UserService userService;
    private final MovieService movieService;

    @GetMapping
    public String getUsersPage(HttpServletRequest req, Model model){
        List<User> users = userService.listAll();
        model.addAttribute("users",users);
        String username = req.getParameter("username");
        if(username != null && !username.isEmpty()) {
            User u = userService.findUser(username);
            model.addAttribute("currentUserTickets", u.getOrders());
            model.addAttribute("selectedUser", u);
        }
        return "userHistory";
    }

    @GetMapping("/edit-order/{id}")
    public String editOrder(@PathVariable Long id, Model model){
        Optional<TicketOrder> o = ticketOrderService.findById(id);
        if(o.isPresent()){
            TicketOrder order = o.get();
            model.addAttribute("order", order);
            List<Movie> movies = movieService.findAll();
            model.addAttribute("movies",movies);
            return "editOrder";
        }
        return "userHistory";
    }

    @PostMapping
    public String postSomething(@RequestParam(required = false) String username){
        if(username != null)
            return "redirect:/movies/users?&username="+username;
        else return "redirect:/movies/users";
    }
    @PostMapping("/edited")
    public String editedMovie(@RequestParam Long id,@RequestParam String movTitle, @RequestParam Long numTickets){
        ticketOrderService.save(id,numTickets,movTitle);
        return "redirect:/userHistory";
    }
}
