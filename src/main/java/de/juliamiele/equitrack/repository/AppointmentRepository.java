package de.juliamiele.equitrack.repository;

import de.juliamiele.equitrack.model.Appointment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    @EntityGraph(attributePaths = "horse")
    List<Appointment> findAllByOrderByAppointmentDateAsc();

    @EntityGraph(attributePaths = "horse")
    List<Appointment> findByHorseIdOrderByAppointmentDateAsc(
        Long horseId
    );
}