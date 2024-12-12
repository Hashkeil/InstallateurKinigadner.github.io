package at.DiplomProjekt.Installateur.customer;
import at.DiplomProjekt.Installateur.customer.dto.CustomerFormRequest;
import at.DiplomProjekt.Installateur.customer.dto.CustomerResponse;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();

        return customerRepository.save(customer);
    }

    @Override
    public Customer findOrCreateCustomer(CustomerFormRequest customerModel) {
        Optional<Customer> optionalCustomer = findCustomerByEmailAndPhone(customerModel.getEmail(), customerModel.getPhone());
        return optionalCustomer.orElseGet(() -> {
            Customer customer = new Customer();
            customer.setCompanyName(customerModel.getCompanyName());
            customer.setFirstName(customerModel.getFirstName());
            customer.setLastName(customerModel.getLastName());
            customer.setEmail(customerModel.getEmail());
            customer.setPhone(customerModel.getPhone());
            customer.setAddress(customerModel.getAddress());
            customer.setCity(customerModel.getCity());
            customer.setZip(customerModel.getZip());

            return createCustomer(customer);
        });
    }

    @Override
    public Customer editCustomerInfo(Customer customer) throws EntityNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isPresent()) {
            return customerRepository.save(customer);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + customer.getId());
        }
    }


    public void deleteCustomer(Long id) throws EntityNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + id);
        }
    }
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer findCustomerById(Long id) throws EntityNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElseThrow(() -> new EntityNotFoundException("Customer does not exist with ID: " + id));
    }

    @Override
    public Optional<Customer> findCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public Optional<Customer> findCustomerByEmailAndPhone(String email, String phone) {
        return customerRepository.findByEmailAndPhone(email, phone);
    }
}
