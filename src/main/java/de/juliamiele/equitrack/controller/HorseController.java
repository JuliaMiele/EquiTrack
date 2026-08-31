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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

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


    @GetMapping("/{id}/edit")
    public String showEditForm(
        @PathVariable Long id, 
        Model model) {

            Horse horse = horseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
              "Pferd nicht gefunden" 
             ));

             model.addAttribute("horse", horse);

        return "horses/form";
    }

    @PostMapping("/{id}/delete")
    public String deleteHorse(
        @PathVariable Long id) {

        if (!horseRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Pferd nicht gefunden"
            );
        }
        horseRepository.deleteById(id);
        return "redirect:/horses";
    }
}