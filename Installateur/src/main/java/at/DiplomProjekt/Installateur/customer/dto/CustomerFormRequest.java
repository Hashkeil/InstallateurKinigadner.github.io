package at.DiplomProjekt.Installateur.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CustomerFormRequest {

    @NotEmpty
    @Size(max = 20, message = "First name cannot be greater than 20 characters.")
    private String firstName;

    @NotEmpty
    @Size(max = 20, message = "Last name cannot be greater than 20 characters.")
    private String lastName;

    @NotEmpty
    @Size(max = 50, message = "Email cannot be greater than 50 characters.")
    private String email;

    @NotEmpty
    @Size(max = 12, message = "Phone number cannot be greater than 20 characters.")
    private String phone;

    @NotEmpty
    @Size(max = 60, message = "address cannot be greater than 60 characters.")
    private String address;

    @NotEmpty
    @Size(max = 20, message = "city cannot be greater than 20 characters.")
    private String city;

    @NotEmpty
    @Size(max = 12, message = "zip cannot be greater than 12 numbers.")
    private String zip;

    @Size(max = 100, message = "Company name cannot be greater than 100 characters.")
    private String companyName;



    @Override
    public String toString() {
        return "CustomerFormModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
