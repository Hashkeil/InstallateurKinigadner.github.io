package at.DiplomProjekt.Installateur.security;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class UserFormModel {
    private Long id;

    @NotEmpty
    @Size(max = 20, message = "First name cannot be greater than 20 characters.")
    private String firstName;

    @NotEmpty
    @Size(max = 20, message = "Last name cannot be greater than 20 characters.")
    private String lastName;

    @NotEmpty
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String firstPassword;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String confirmPassword;

    private boolean admin = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @AssertTrue(message = "Passwords do not match. Please try again.")
    public boolean getIsPasswordMatch() {
        if (firstPassword == null || confirmPassword == null) {
            return false;
        }

        // Both passwords are not null, proceed with comparison
        return firstPassword.equals(confirmPassword);
    }

}
