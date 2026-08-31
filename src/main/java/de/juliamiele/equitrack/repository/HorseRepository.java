package de.juliamiele.equitrack.repository;
import de.juliamiele.equitrack.model.Horse;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HorseRepository extends JpaRepository<Horse, Long> {
    
}
