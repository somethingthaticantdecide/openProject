package edu.schoo21.openprojectback.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.schoo21.openprojectback.models.dto.CatDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
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
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    public Cat(CatDto catDto, User owner) {
        this.name = catDto.getName();
        this.sex = catDto.getSex();
        this.breed = catDto.getBreed();
        this.age = catDto.getAge();
        this.price = catDto.getPrice();
        this.passport = catDto.getPassport();
        this.vaccination = catDto.getVaccination();
        this.certificates = catDto.getCertificates();
        this.info = catDto.getInfo();
        this.photo = catDto.getPhoto();
        this.owner = owner;
    }

    public Cat(String name, Boolean sex, String breed, Integer age, Integer price, Boolean passport, Boolean vaccination, Boolean certificates, String info, String photo) {
        this.name = name;
        this.sex = sex;
        this.breed = breed;
        this.age = age;
        this.price = price;
        this.passport = passport;
        this.vaccination = vaccination;
        this.certificates = certificates;
        this.info = info;
        this.photo = photo;
    }
}
