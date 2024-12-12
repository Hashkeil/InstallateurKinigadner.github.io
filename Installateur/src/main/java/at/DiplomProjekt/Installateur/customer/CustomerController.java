package at.DiplomProjekt.Installateur.customer;

import at.DiplomProjekt.Installateur.customer.dto.CustomerFormRequest;
import at.DiplomProjekt.Installateur.customer.dto.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new CustomerFormRequest());
        return "create_customer";
    }

    @PostMapping("")
    public String create(@Valid @ModelAttribute("customer") CustomerFormRequest formModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            model.addAttribute("errorMessages", errorMessages);

            // Return back to the form with errors
            return "create_customer";
        }

        customerService.findOrCreateCustomer(formModel);
        return "redirect:/customers/list";
    }

    @GetMapping("/list")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers_list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers/list";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "update_customer";
        } else {
            return "redirect:/customers/list";
        }
    }


    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id,
                                 @Valid @ModelAttribute("customer") CustomerFormRequest formModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "update_customer";
        }

        Customer customer = customerService.findCustomerById(id);
        if (customer != null) {
            // Update customer information from formModel
            customer.setCompanyName(formModel.getCompanyName());
            customer.setFirstName(formModel.getFirstName());
            customer.setLastName(formModel.getLastName());
            customer.setEmail(formModel.getEmail());
            customer.setPhone(formModel.getPhone());
            customer.setAddress(formModel.getAddress());
            customer.setCity(formModel.getCity());
            customer.setZip(formModel.getZip());

            customerService.editCustomerInfo(customer);
        }
        return "redirect:/customers/list";
    }


}
