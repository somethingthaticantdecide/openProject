package edu.schoo21.openprojectback.specification;

import edu.schoo21.openprojectback.models.Cat;
import edu.schoo21.openprojectback.models.Cat_;
import org.springframework.data.jpa.domain.Specification;

public class CatSpecifications {

    public static Specification<Cat> likeAddress(String address) {
        return address == null ? null : ((root, query, cb) -> cb.like(root.get(Cat_.ADDRESS), "%" + address + "%"));
    }

    public static Specification<Cat> likeBreed(String breed) {
        return breed == null ? null : ((root, query, cb) -> cb.like(root.get(Cat_.BREED), "%" + breed + "%"));
    }

    public static Specification<Cat> equalAge(Integer age) {
        return age == null ? null : ((root, query, cb) -> cb.equal(root.get(Cat_.AGE), age));
    }

    public static Specification<Cat> equalSex(Boolean sex) {
        return sex == null ? null : ((root, query, cb) -> cb.equal(root.get(Cat_.SEX), sex));
    }

    public static Specification<Cat> equalPassport(Boolean passport) {
        return passport == null ? null : ((root, query, cb) -> cb.equal(root.get(Cat_.PASSPORT), passport));
    }

    public static Specification<Cat> equalVaccination(Boolean vaccination) {
        return vaccination == null ? null : ((root, query, cb) -> cb.equal(root.get(Cat_.VACCINATION), vaccination));
    }

    public static Specification<Cat> equalCertificates(Boolean certificate) {
        return certificate == null ? null : ((root, query, cb) -> cb.equal(root.get(Cat_.CERTIFICATES), certificate));
    }

    public static Specification<Cat> priceFrom(Integer price) {
        return price == null ? null : ((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Cat_.PRICE), price));
    }

    public static Specification<Cat> priceTo(Integer price) {
        return price == null ? null : ((root, query, cb) -> cb.lessThanOrEqualTo(root.get(Cat_.PRICE), price));
    }
}
