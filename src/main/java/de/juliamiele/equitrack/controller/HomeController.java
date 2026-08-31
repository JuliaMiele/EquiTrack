package de.juliamiele.equitrack.controller;

import de.juliamiele.equitrack.repository.AppointmentRepository;
import de.juliamiele.equitrack.repository.HorseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;


@Controller
public class HomeController {

    private final HorseRepository horseRepository;
    private final AppointmentRepository appointmentRepository;

    public HomeController(
            HorseRepository horseRepository,
            AppointmentRepository appointmentRepository) {

        this.horseRepository = horseRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {

        LocalDate today = LocalDate.now();

        model.addAttribute(
            "horseCount",
            horseRepository.count()
        );

        model.addAttribute(
            "openAppointmentCount",
            appointmentRepository.countByCompletedFalse()
        );

        model.addAttribute(
            "overdueAppointmentCount",
            appointmentRepository
                .countByCompletedFalseAndAppointmentDateBefore(today)
        );

        model.addAttribute(
            "upcomingAppointments",
            appointmentRepository
                .findTop5ByCompletedFalseAndAppointmentDateGreaterThanEqualOrderByAppointmentDateAsc(
                    today
                )
        );

        return "home";
    }
}