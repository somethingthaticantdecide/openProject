package edu.schoo21.openprojectback.models.response;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.User;
import lombok.Data;

@Data
public class CatProfileResponse {
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
    private Long owner_id;
    private Long owner_phoneNumber;
    private String owner_mail;
    private String owner_address;
    private String owner_avatar;
    private Float owner_ranking;
    private String owner_name;

    public CatProfileResponse(Cat cat) {
        this.id = cat.getId();
        this.name = cat.getName();
        this.sex = cat.getSex();
        this.breed = cat.getBreed();
        this.age = cat.getAge();
        this.price = cat.getPrice();
        this.passport = cat.getPassport();
        this.vaccination = cat.getVaccination();
        this.certificates = cat.getCertificates();
        this.info = cat.getInfo();
        this.photo = cat.getPhoto();
        if (cat.getOwner() != null) {
            User owner = cat.getOwner();
            this.owner_id = owner.getId();
            this.owner_phoneNumber =  owner.getPhoneNumber();
            this.owner_name = owner.getName();
            this.owner_mail = owner.getMail();
            this.owner_avatar = owner.getAvatar();
            this.owner_ranking = owner.getRanking();
            this.owner_address = owner.getAddress();
        }
    }
}
