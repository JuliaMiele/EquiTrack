package de.juliamiele.equitrack.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppointmentTest {

    private final LocalDate today = LocalDate.of(2026, 8, 31);

    @Test
    void openPastAppointmentIsOverdue() {

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(today.minusDays(1));
        appointment.setCompleted(false);

        assertTrue(appointment.isOverdue(today));
    }

    @Test
    void futureAppointmentIsNotOverdue() {

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(today.plusDays(1));
        appointment.setCompleted(false);

        assertFalse(appointment.isOverdue(today));
    }

    @Test
    void completedPastAppointmentIsNotOverdue() {

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(today.minusDays(1));
        appointment.setCompleted(true);

        assertFalse(appointment.isOverdue(today));
    }
}