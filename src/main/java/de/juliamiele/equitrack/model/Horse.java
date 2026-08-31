package de.juliamiele.equitrack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;




@Entity
public class Horse {
    
@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;



@NotBlank(message = "Bitte gib einen Namen ein")
private String name;

@Past(message = "Das Geburtsdatum muss in der Vergangenheit liegen")
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
private LocalDate birthDate;

private String breed;

private String sex;

private String color;

@Positive(message = "Das Stockmaß muss über 0 cm liegen")
private Integer heightInCentimeters;

@Size(max = 500, message = "Die Beschreibung darf maximal 500 Zeichen lang sein")
private String notes;

public Long getId(){
    return id;
}

public void setId(Long id){
    this.id = id;

}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public LocalDate getBirthDate() {
    return birthDate;
}

public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
}

public String getBreed() {
    return breed;
}

public void setBreed(String breed) {
    this.breed = breed;
}

public String getSex() {
    return sex;

}
public void setSex(String sex) {
    this.sex = sex;
}

public String getColor() {
    return color;
}

public void setColor(String color) {
    this.color = color;
}

public Integer getHeightInCentimeters() {
    return heightInCentimeters;
}

public void setHeightInCentimeters(Integer heightInCentimeters) {
    this.heightInCentimeters = heightInCentimeters;
}   

public String getNotes() {
    return notes;
}

public void setNotes(String notes) {
    this.notes = notes;
}
}
