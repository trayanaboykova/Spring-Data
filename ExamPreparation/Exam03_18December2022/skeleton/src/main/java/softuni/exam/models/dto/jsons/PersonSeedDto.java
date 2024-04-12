package softuni.exam.models.dto.jsons;

import com.google.gson.annotations.Expose;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class PersonSeedDto implements Serializable {
    @Expose
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @Expose
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;
    @Expose
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;
    @Expose
    @Size(min = 2, max = 13)
    private String phone;
    @Expose
    @NotNull
    private String statusType;
    @Expose
    @NotNull
    private String country;

    public PersonSeedDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
