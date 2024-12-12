package at.DiplomProjekt.Installateur.customer.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomerResponse implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String zip;
    private String companyName;




}
