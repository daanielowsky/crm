package com.daanielowsky.crm.Controllers;

import com.daanielowsky.crm.DTO.CustomerDTO;
import com.daanielowsky.crm.DTO.EmployeeDTO;
import com.daanielowsky.crm.DTO.ItemDTO;
import com.daanielowsky.crm.DTO.OfferDTO;
import com.daanielowsky.crm.Entities.Employee;
import com.daanielowsky.crm.Entities.Item;
import com.daanielowsky.crm.Enums.Producers;
import com.daanielowsky.crm.Enums.Roles;
import com.daanielowsky.crm.Services.EmployeeService;
import com.daanielowsky.crm.Services.ItemService;
import com.daanielowsky.crm.Services.RegistrationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegisterController {

    private RegistrationService registrationService;
    private EmployeeService employeeService;
    private ItemService itemService;

    public RegisterController(RegistrationService registrationService, EmployeeService employeeService, ItemService itemService) {
        this.registrationService = registrationService;
        this.employeeService = employeeService;
        this.itemService = itemService;
    }

    @GetMapping("/customer")
    public String customerRegistration(Model model){
        model.addAttribute("customer", new CustomerDTO());
        model.addAttribute("employees", employeeService.getSalesRepresentativeForCustomerRegistration());
        return "customer-registration";
    }

    @PostMapping("/customer")
    public String registeringCustomer(@Valid @ModelAttribute("customer") CustomerDTO customerDTO, BindingResult result, Model model) throws IllegalAccessException{
        if (result.hasErrors()){
            log.warn("There are " + result.getErrorCount() + " errors in registration form. Forwarding back to registration form.");
            model.addAttribute("employees", employeeService.getSalesRepresentativeForCustomerRegistration());
            return "customer-registration";
        }

        registrationService.registeringCustomer(customerDTO);

        return "redirect:/";
    }

    @GetMapping("/employee")
    public String employeeRegistration(Model model){

        model.addAttribute("employee", new EmployeeDTO());
        model.addAttribute("roles", Roles.values());
        return "employee-registration";

    }

    @PostMapping("/employee")
    public String registeringEmployee(@Valid @ModelAttribute EmployeeDTO employeeDTO, BindingResult result, Model model){
        if(result.hasErrors()){
            log.warn("There are " + result.getErrorCount() + " errors in registration form. Forwarding back to registration form.");
            model.addAttribute("employees", employeeService.getSalesRepresentativeForCustomerRegistration());
            return "employee-registration";
        }

        registrationService.registeringEmployee(employeeDTO);
        return "redirect:/";
    }

    @GetMapping("/item")
    public String registerItem(Model model){
        model.addAttribute("item", new ItemDTO());
        model.addAttribute("producers", Producers.values());
        return "item-registration";
    }

    @PostMapping("/item")
    public String registeringItem(@Valid @ModelAttribute ItemDTO itemDTO, BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("producers", Producers.values());
            return "item-registration";
        }

        itemService.registerItem(itemDTO);

        return "redirect:/items";
    }

    @GetMapping("/offer")
    public String registerOffer(Model model){
        model.addAttribute("offer", new OfferDTO());
        model.addAttribute("items", itemService.getListOfItems());
        return "offer-registration";
    }
}
