package edu.schoo21.openprojectback.models.dto;

import lombok.*;

import javax.persistence.*;

@Data
public class CatDto {
    private String name;
    private Boolean sex;
    private String breed;
    private Integer age;
    private Integer price;
    private Boolean passport;
    private Boolean vaccination;
    private Boolean certificates;
    private String info;
    private String photo;
}
