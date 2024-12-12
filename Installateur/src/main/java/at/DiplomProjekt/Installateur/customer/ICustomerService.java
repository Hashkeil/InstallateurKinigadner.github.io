package at.DiplomProjekt.Installateur.customer;

import at.DiplomProjekt.Installateur.customer.dto.CustomerFormRequest;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {


    Customer findCustomerById(Long id);
    Customer createCustomer(Customer customer);
    Customer editCustomerInfo(Customer customer);
    Optional<Customer> findCustomerByPhone(String phone);
    Optional<Customer> findCustomerByEmail(String email);
    Customer findOrCreateCustomer(CustomerFormRequest customerModel);
    Optional<Customer> findCustomerByEmailAndPhone(String email, String phone);
}
