package edu.schoo21.openprojectback.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.schoo21.openprojectback.models.dto.CatDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
    private String address;
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
        this.address = owner.getAddress();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id) && Objects.equals(name, cat.name) && Objects.equals(sex, cat.sex) && Objects.equals(breed, cat.breed) && Objects.equals(age, cat.age) && Objects.equals(price, cat.price) && Objects.equals(passport, cat.passport) && Objects.equals(vaccination, cat.vaccination) && Objects.equals(certificates, cat.certificates) && Objects.equals(info, cat.info) && Objects.equals(photo, cat.photo) && Objects.equals(address, cat.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, breed, age, price, passport, vaccination, certificates, info, photo, address);
    }
}
