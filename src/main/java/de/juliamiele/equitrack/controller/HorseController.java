package de.juliamiele.equitrack.controller;

import de.juliamiele.equitrack.model.Horse;
import de.juliamiele.equitrack.repository.HorseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/horses")
public class HorseController {
    private final HorseRepository horseRepository;

    public HorseController(HorseRepository horseRepository) {
        this.horseRepository = horseRepository;
    }

    @GetMapping
    public String showHorseList(Model model) {
        model.addAttribute("horses", horseRepository.findAll());
        return "horses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("horse", new Horse());
        return "horses/form";
    }

    @PostMapping
    public String createHorse(
        @Valid Horse horse, 
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "horses/form";
        }
        horseRepository.save(horse);
        return "redirect:/horses";
    }
}