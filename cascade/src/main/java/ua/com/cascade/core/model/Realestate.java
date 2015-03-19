package ua.com.cascade.core.model;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Realestate {

    @GraphId
    public Long id;

    public String address;

    public String realestateOwnerName;

    public String realestateOwnerDob;

    public String area;

    public float price;

    public Realestate() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRealestateOwnerName() {
        return realestateOwnerName;
    }

    public void setRealestateOwnerName(String realestateOwnerName) {
        this.realestateOwnerName = realestateOwnerName;
    }

    public String getRealestateOwnerDob() {
        return realestateOwnerDob;
    }

    public void setRealestateOwnerDob(String realestateOwnerDob) {
        this.realestateOwnerDob = realestateOwnerDob;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
