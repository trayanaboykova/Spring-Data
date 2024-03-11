package org.automappingobjects_exercise.service.dto;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class GameAddDTO {
    private String title;
    private double price;
    private double size;
    private String trailer;
    private String thumbnail;
    private String description;
    private LocalDate releaseDate;
}
