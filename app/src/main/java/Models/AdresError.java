package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class AdresError {
    @SerializedName("title")
    @Expose
    private List<String> title = null;


    @SerializedName("description")
    @Expose
    private List<String> description = null;


    @SerializedName("name")
    @Expose
    private List<String> name = null;

    @SerializedName("phone")
    @Expose
    private List<String> phone = null;


    @SerializedName("postCode")
    @Expose
    private List<String> postCode = null;

    @SerializedName("countryId")
    @Expose
    private List<String> countryId = null;


    @SerializedName("city")
    @Expose
    private List<String> city = null;


    @SerializedName("town")
    @Expose
    private List<String> town = null;


    @SerializedName("isDefault")
    @Expose
    private List<String> isDefault = null;


    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public List<String> getPostCode() {
        return postCode;
    }

    public void setPostCode(List<String> postCode) {
        this.postCode = postCode;
    }

    public List<String> getCountryId() {
        return countryId;
    }

    public void setCountryId(List<String> countryId) {
        this.countryId = countryId;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }

    public List<String> getTown() {
        return town;
    }

    public void setTown(List<String> town) {
        this.town = town;
    }

    public List<String> getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(List<String> isDefault) {
        this.isDefault = isDefault;
    }
}
