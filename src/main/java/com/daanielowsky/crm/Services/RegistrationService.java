package com.daanielowsky.crm.Services;

import com.daanielowsky.crm.DTO.CustomerDTO;
import com.daanielowsky.crm.Entities.Activity;
import com.daanielowsky.crm.Entities.Customer;
import com.daanielowsky.crm.Repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Slf4j
public class RegistrationService {

    private CustomerRepository customerRepository;
    private ActivityService activityService;

    public RegistrationService(CustomerRepository customerRepository, ActivityService activityService) {
        this.customerRepository = customerRepository;
        this.activityService = activityService;
    }

    public void registeringCustomer(CustomerDTO customerDTO){

        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(customerDTO, Customer.class);

        customerRepository.save(customer);
        Activity activity = new Activity();
        activity.setMessage("Customer has been created.");
        activityService.createActivity(customer.getId(), activity);

        log.info("Created new customer with ID: " + customer.getId() +
                "\nImię: " + customer.getName() +
                "\nNazwisko: " + customer.getSurname() +
                "\nEmail: " + customer.getEmail() +
                "\nNumer Telefonu: " + customer.getPhoneNumber() +
                "\nKod Pocztowy: " + customer.getPostCode() +
                "\nMiejscowość: " + customer.getCity() +
                "\nNotatka: " + customer.getNote());

    }
}
