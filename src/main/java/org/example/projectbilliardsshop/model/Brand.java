package org.example.projectbilliardsshop.model;
import jakarta.persistence.*;

@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;
    private String brandName;
    private String country;
    private int foundYear;
    private String description;
    private String url;

    public Brand() {
    }

    public Brand( String brandName, String country, int foundYear, String description, String url) {
        this.brandName = brandName;
        this.country = country;
        this.foundYear = foundYear;
        this.description = description;
        this.url = url;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getFoundYear() {
        return foundYear;
    }

    public void setFoundYear(int foundYear) {
        this.foundYear = foundYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
