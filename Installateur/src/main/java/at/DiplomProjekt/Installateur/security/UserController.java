package at.DiplomProjekt.Installateur.security;

import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.dto.AccountViewResponse;
import at.DiplomProjekt.Installateur.product.ProductNotFoundException;
import at.DiplomProjekt.Installateur.util.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private IAuthenticationFacade authenticationFacade;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationFacade authenticationFacade, UserService userService) {

        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String dashboard(ModelMap modelMap, HttpServletRequest request) {
        Authentication authentication = authenticationFacade.getAuthentication();

        if (authentication!= null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            logger.info("Granted Authorities: {}", userDetails.getAuthorities());

            userDetails.getUser().getRoles().forEach(role -> {
                logger.info("Role: {}", role.getName());
                role.getPrivileges().forEach(privilege -> {
                    logger.info("privileges: {}", privilege.getName());
                });
            });

            logger.info("Is user in Admin role? {}", request.isUserInRole("ADMIN"));
            modelMap.put("userName", userDetails.getFirstName() + " " + userDetails.getLastName());
            logger.info("Currently Logged in User: {}", userDetails.getFirstName() + " " + userDetails.getLastName());
            return "dashboard";
        } else {

            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage;
        String successMessage;
        if(error!= null) {
            errorMessage = "Username or Password is incorrect!";
            model.addAttribute("errorMessage", errorMessage);
        }
        if(logout!= null) {
            successMessage = "You have been successfully logged out";
            model.addAttribute("successMessage", successMessage);
        }

        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutForm(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!= null){
            new SecurityContextLogoutHandler().logout((HttpServletRequest) request, (HttpServletResponse) response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registration", new UserFormModel());
        return "registration";
    }

    @PostMapping("/register")
    public String submitRegistrationForm(
            @Valid @ModelAttribute("registration") UserFormModel userFormModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e -> {
                FieldError fieldError = (FieldError) e;
                logger.info("Object name: {}", fieldError.getField());
                logger.info("Default Message: {}", e.getDefaultMessage());
                ValidationError regError = new ValidationError();
                regError.setFieldName(fieldError.getField());
                regError.setHasErrors(true);
                regError.setErrorMessage(fieldError.getDefaultMessage());

                redirectAttributes.addFlashAttribute(regError.getFieldName(), regError);
            });

            return "redirect:/users/list";
        }

        try {
            userFormModel.setAdmin(true);
            User user = userService.createUser(userFormModel);
            redirectAttributes.addFlashAttribute("successMessage", "Registration Successful! Please log in.");
            return "redirect:/login";
        } catch (EmailExistsException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (RoleNotFoundException e) {
            logger.error(e.getMessage());
        }

        return "redirect:/users/list";
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @GetMapping("/admin/users/create")
    public String showUserForm(Model model) {
        model.addAttribute("user", new UserFormModel());

        CustomUserDetails userDetails = (CustomUserDetails) authenticationFacade.getAuthentication().getPrincipal();
        userDetails.getUser().getRoles().forEach(role -> logger.info("Role Name: {}", role.getName()));

        return "user_form";
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PostMapping("/create")
    public String createUser(
            @Valid
            @ModelAttribute("user") UserFormModel userFormModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            ModelMap model,
            HttpServletRequest request
    ) {
        if(bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(e -> {
                FieldError fieldError = (FieldError) e;
                logger.info("Object name: {}", fieldError.getField());
                logger.info("Default Message: {}", e.getDefaultMessage());
                ValidationError regError = new ValidationError();
                regError.setFieldName(fieldError.getField());
                regError.setHasErrors(true);
                regError.setErrorMessage(fieldError.getDefaultMessage());

                redirectAttributes.addFlashAttribute(regError.getFieldName(), regError);

            });

            return "redirect:/users/create";
        }

        // If validation passes, save user
        try {
            User user = userService.createUser(userFormModel);
        } catch (EmailExistsException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/create";
        } catch (RoleNotFoundException e) {
            logger.error(e.getMessage());
        }

        redirectAttributes.addFlashAttribute("successMessage", "User Creation Successful!");
        return "redirect:/create";
    }

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user_list";
    }




    @GetMapping("/update/{userId}")
    public String updateUserForm(@PathVariable Long userId, Model model) {
        // Retrieve user by ID from the database
        Optional<User> userOptional = userService.getUserById(userId);

        // Check if user exists
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Add the user to the model to pre-fill the update form
            model.addAttribute("user", user);
        } else {

            return "redirect:/users/list?error=UserNotFound";
        }

        return "update_user_form";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") Long userId, @ModelAttribute("user") User updatedUser) {
        try {
            Optional<User> optionalUser = userService.getUserById(userId);

            // Check if the user exists
            if (optionalUser.isPresent()) {

                User existingUser = optionalUser.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setEnabled(updatedUser.isEnabled());


                userService.updateUser(existingUser);

                return "redirect:/users/list";
            } else {
                // Handle the case where the user is not found
                return "redirect:/users/list?error=UserNotFound";
            }
        } catch (EmailExistsException e) {
            // Handle the case where the email already exists
            return "redirect:/users/list?error=" + e.getMessage();
        } catch (Exception e) {
            return "redirect:/users/list?error=InternalServerError";
        }
    }



    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long userId) {
        try {
            userService.deleteUser(userId);
            return "redirect:/users/list";
        } catch (Exception e) {
            return "redirect:/users/list?error=UserNotFound";
        }
    }



    @PostMapping("/update-settings")
    public String updateUserSettings(@Valid @ModelAttribute("userSettings") UserFormModel updatedSettings, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/settings";
        }

        try {
            userService.updateUserSettings(updatedSettings);
            redirectAttributes.addFlashAttribute("successMessage", "Settings updated successfully!");
            return "redirect:/settings";
        } catch (Exception e) {
            // Handle other exceptions
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update settings!");
            return "redirect:/settings";
        }
    }



}