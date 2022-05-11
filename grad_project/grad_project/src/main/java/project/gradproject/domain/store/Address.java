package project.gradproject.domain.store;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String state;
    private String city;
    private String town;
    private String street;
    private String detailAddress;

    protected Address(){}

    public Address(String state, String city,String town, String street, String detailAddress) {
        this.state=state;
        this.city = city;
        this.street = street;
        this.detailAddress = detailAddress;
        this.town = town;

    }


}
