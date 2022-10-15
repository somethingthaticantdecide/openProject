package edu.schoo21.openprojectback.models.requests;

import lombok.Data;

@Data
public class CatSearchRequest {
    private String address;
    private String breed;
    private Integer age;
    private Boolean sex;
    private Boolean passport;
    private Boolean vaccination;
    private Boolean certificates;
    private Integer priceFrom;
    private Integer priceTo;
}
