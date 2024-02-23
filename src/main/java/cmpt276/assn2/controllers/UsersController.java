package cmpt276.assn2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cmpt276.assn2.models.User;
import cmpt276.assn2.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users/view")
    public String getAllUsers(Model model) {
        System.out.println("Get all users");
        List<User> users = userRepo.findAll();
        for (User user : users) {
            System.out.println("Name: " + user.getName());
            System.out.println("Weight: " + user.getWeight());
            System.out.println("color: " + user.getColor());
            System.out.println("--------------------");
        }
        model.addAttribute("us", users);
        return "users/showAll";
    }
    
    @PostMapping("/users/add")
    public String addingStudent(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("Add user");
        String newName = newuser.get("name");
        Integer newWeight = Integer.parseInt(newuser.get("weight"));
        Integer newHeight = Integer.parseInt(newuser.get("height"));
        String newColor = newuser.get("color");
        Double newGpa= Double.parseDouble(newuser.get("gpa"));
        userRepo.save(new User(newName, newWeight, newHeight, newColor, newGpa));
        response.setStatus(201);
        return "redirect:/users/view";  
    }

    @DeleteMapping("/users/delete/{id}")
    public String deletingStudent(@PathVariable("id") Integer id, HttpServletResponse response) {
        System.out.println("Delete user");
        userRepo.deleteById(id);
        response.setStatus(201);
        return "users/showAll";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepo.findById(id).get(); // Fetch the user from the database
        model.addAttribute("user", user);
        return "edit";
    }
    
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @RequestParam Map<String, String> updatedUser, HttpServletResponse response) {
        User user = userRepo.findById(id).get();
            user.setName(updatedUser.get("name"));
            user.setWeight(Integer.parseInt(updatedUser.get("weight")));
            user.setHeight(Integer.parseInt(updatedUser.get("height")));
            user.setColor(updatedUser.get("color"));
            user.setGpa(Double.parseDouble(updatedUser.get("gpa")));
            userRepo.save(user); // Save the updated user
            response.setStatus(201);

        return "redirect:/users/view"; 
    }


}
