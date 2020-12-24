package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.CustomerRepository;
import com.goldenbites.pos.dao.OrderRepository;
import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.Customer;
import com.goldenbites.pos.model.User;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/home/registerCustomer")
    public String displayCustomerRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "Customer/customerRegistration";
    }

    @PostMapping("/home/registerCustomer")
    public String registerCustomer(@ModelAttribute Customer customer, Model model) {

    	customer.setCustomerEmail(customer.getCustomerEmail().toLowerCase());
    	customer.setCustomerName(customer.getCustomerName().toLowerCase());
    	
    	if(customerRepository.findByCustomerEmail(customer.getCustomerEmail()) != null || 
    			customerRepository.findByCustomerCode(customer.getCustomerCode()) != null) {
    		model.addAttribute("message", "Customer already exist.");
    		model.addAttribute("customer", new Customer());
            return "Customer/customerRegistration";

    	}
    	
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        customer.setCustomerCreatedDate(now);
        customerRepository.save(customer);

        String emailbody = "Hi " + customer.getCustomerName() + "," + "\n\n" + "You have become a prime member of goldenbites"
                + "\n" + "Thanks for the Registration" + "\n\n" + "Thanks & Regards\n\n" + "Team GoldenBites";
        sendSimpleMessage(customer.getCustomerEmail(), "Goldenbites Customer Registration", emailbody);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(customer.getCustomerEmail(), passwordEncoder.encode("123"), customer.getCustomerEmail(), "CUSTOMER");
        userRepository.save(user);
        
        model.addAttribute("customer", new Customer());
        return "redirect:/home/viewCustomer";
    }

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("goldenbitespos@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @GetMapping("/home/viewCustomer")
    public String viewAllCustomer(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "Customer/viewCustomer";
    }

    @GetMapping("/home/viewCustomer/customerDelete/{id}")
    public String deleteCustomer(@PathVariable("id") String id, Model model) {
    	userRepository.deleteByUserName(customerRepository.findByCustomerId(id).getCustomerEmail());
    	orderRepository.deleteByOrderCustomerCode(customerRepository.findByCustomerId(id).getCustomerCode());
      
    	customerRepository.deleteById(id);
        
        model.addAttribute("customers", customerRepository.findAll());
        return "redirect:/home/viewCustomer";
    }


    @GetMapping("/home/viewCustomer/customerEdit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Customer customer = customerRepository.findByCustomerId(id);
        model.addAttribute("customer", customer);
        return "Customer/updateCustomer";
    }

    @PostMapping("/home/viewCustomer/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") String id, @ModelAttribute Customer customer, BindingResult result,
                                 Model model) {
    	
    	customer.setCustomerName(customer.getCustomerName().toLowerCase());
    	
    	customer.setCustomerCode(customerRepository.findByCustomerId(id).getCustomerCode());
    	customer.setCustomerEmail(customerRepository.findByCustomerId(id).getCustomerEmail().toLowerCase());
    	String email = customerRepository.findByCustomerId(id).getCustomerEmail();
    	
    	if(!customer.getCustomerEmail().equals(email) && (customerRepository.findByCustomerEmail(customer.getCustomerEmail()) != null)) {    		    		    			
    		return "redirect:/home/viewCustomer/customerEdit/"+id; 	
    	}
    	
    	User user = userRepository.findByUserName(customer.getCustomerEmail());
    	
    	if(user == null) {
    		
    		String emailbody = "Hi " + customer.getCustomerName() + "," + "\n\n" + "You have become a prime member of goldenbites"
                    + "\n" + "Thanks for the Registration" + "\n\n" + "Thanks & Regards\n\n" + "Team GoldenBites";
            sendSimpleMessage(customer.getCustomerEmail(), "Goldenbites Customer Registration", emailbody);

    		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User newUser = new User(customer.getCustomerEmail(), passwordEncoder.encode("123"), customer.getCustomerEmail(), "CUSTOMER");
            userRepository.save(newUser);
            userRepository.deleteByUserName(email);
    	}else {
    		user.setUserName(customer.getCustomerEmail());
    	}
    	
        customer.setCustomerId(id);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        customer.setCustomerCreatedDate(now);
        customerRepository.save(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return "redirect:/home/viewCustomer";
    }

}
