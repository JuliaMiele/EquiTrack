package de.juliamiele.equitrack.controller;

import de.juliamiele.equitrack.model.Appointment;
import de.juliamiele.equitrack.model.Horse;
import de.juliamiele.equitrack.repository.AppointmentRepository;
import de.juliamiele.equitrack.repository.HorseRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final HorseRepository horseRepository;

    public AppointmentController(
            AppointmentRepository appointmentRepository,
            HorseRepository horseRepository) {

        this.appointmentRepository = appointmentRepository;
        this.horseRepository = horseRepository;
    }

    @GetMapping
    public String showAppointmentList(Model model) {

        model.addAttribute(
            "appointments",
            appointmentRepository.findAllByOrderByAppointmentDateAsc()
        );

        model.addAttribute("today", LocalDate.now());

        return "appointments/list";
    }

    @GetMapping("/new")
    public String showAppointmentForm(
            @RequestParam(required = false) Long horseId,
            Model model) {

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("horses", horseRepository.findAll());
        model.addAttribute("selectedHorseId", horseId);

        return "appointments/form";
    }

    @PostMapping
    public String createAppointment(
            @Valid Appointment appointment,
            BindingResult bindingResult,
            @RequestParam(required = false) Long horseId,
            Model model) {

        Horse horse = null;

        if (horseId != null) {
            horse = horseRepository.findById(horseId).orElse(null);
        }

        if (horse == null) {
            model.addAttribute(
                "horseError",
                "Bitte wähle ein Pferd aus."
            );
        }

        if (bindingResult.hasErrors() || horse == null) {
            model.addAttribute("horses", horseRepository.findAll());
            model.addAttribute("selectedHorseId", horseId);

            return "appointments/form";
        }

        appointment.setHorse(horse);
        appointmentRepository.save(appointment);

        return "redirect:/appointments";
    }

    @PostMapping("/{id}/toggle")
    public String toggleAppointmentStatus(@PathVariable Long id) {

        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Termin nicht gefunden"
            ));

        appointment.setCompleted(!appointment.isCompleted());
        appointmentRepository.save(appointment);

        return "redirect:/appointments";
    }

    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable Long id) {

        if(!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Termin nicht gefunden"
            );
        }
        
        appointmentRepository.deleteById(id);
        
        return "redirect:/appointments";
    }
}