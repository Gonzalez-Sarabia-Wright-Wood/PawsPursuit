package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.PetRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {

    private PetRepository petDao;

    public PetController(PetRepository petDao){
        this.petDao = petDao;
    }

    @GetMapping(path = "/Pets")
    public String petsIndex(Model model) {

        model.addAttribute("pets", petDao.findAll());
        return "/Pets/index";
    }
    @GetMapping(path = "/Pets/{id}")
    public String onePet(@PathVariable Long id, Model model) {
        Pet onePet = petDao.findById(id).get();
        model.addAttribute("onePet", onePet);
        return "/Pets/show";
    }
    @GetMapping("/create_pet")
    public String createPet(Model model){
        model.addAttribute("pet", new Pet());
        return "/Pets/create";
    }

    @PostMapping("/create_pet")
    public String savePet(@ModelAttribute Pet pet){
        petDao.save(pet);
        return "redirect:/Users/profile";
    }

    @GetMapping("/delete_pet")
    public String deletePetGet(){
        return "redirect:/Users/profile";
    }
    @PostMapping("/delete_pet")
    public String deletePetPost(@RequestParam Long id){
        petDao.deleteById(id);
        return "redirect:/Users/profile";
    }

    @GetMapping(path = "/Pets/{id}/edit")
    public String editPet(Model model, @PathVariable Long id) {
        Pet pet = petDao.findById(id).get();

        model.addAttribute("pet", pet);

        return "Pets/edit";
    }


}
