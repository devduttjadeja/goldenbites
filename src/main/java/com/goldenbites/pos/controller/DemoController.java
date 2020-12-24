package com.goldenbites.pos.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldenbites.pos.dao.OtpHandlerRepository;
import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.OtpHandler;
import com.goldenbites.pos.model.User;

@Controller
public class DemoController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    JavaMailSender emailSender;
	
	@Autowired 
	OtpHandlerRepository otpHandlerRepository;

	@RequestMapping("/")
	public String displayLoginPage() {
		otpHandlerRepository.deleteAll();
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		otpHandlerRepository.deleteAll();
		return "login";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPasswordForm(Model model) {
		return "forgotPassword";
	}
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam("userEmail") String userEmail, Model model) {
		
		if(userRepository.findByUserEmail(userEmail) != null) {
			
			Random rnd = new Random();
			int OTP = 100000 + rnd.nextInt(900000);
			
			String emailbody = "otp: " + OTP;
	        sendSimpleMessage(userEmail, "Goldenbites Forgot Password", emailbody);
	        
	        OtpHandler otpHandler = new OtpHandler();
	        otpHandler.setUserName(userEmail);
	        otpHandler.setOtp(OTP);
	        otpHandlerRepository.save(otpHandler);
	        
	        model.addAttribute("userEmail", userEmail);
			return "User/otpForm";
	        
		}else {
			model.addAttribute("message", "User Email does not exist.");
			return "forgotPassword";
		}
	}
	
	@PostMapping("/forgot-password/enterOtp/{userEmail}")
	public String submitOtp(@PathVariable("userEmail") String userEmail, @RequestParam("otp") String otp, Model model) {
		
		if (otp.matches("[0-9]+") && otp.length() == 6) {
			if(otpHandlerRepository.findByUserEmail(userEmail) != null) {
				OtpHandler otpHandler = otpHandlerRepository.findByUserEmail(userEmail);
				if(otpHandler.getOtp() == Integer.parseInt(otp)) {
					otpHandlerRepository.deleteById(otpHandler.getOtpHandlerId());
					model.addAttribute("userEmail", userEmail);
					return "User/changePassword";
				}else {
//					otpHandlerRepository.deleteById(otpHandler.getOtpHandlerId());
					model.addAttribute("message", "Invalid OTP");
					model.addAttribute("userEmail", userEmail);
					return "User/otpForm";
				}
			}else {
				return "redirect:/login";
			}
		}else {
			model.addAttribute("message", "Invalid OTP");
			model.addAttribute("userEmail", userEmail);
			return "User/otpForm";
		}
		
		
		
	}
	
	@PostMapping("/change-password/{userEmail}")
	public String changePassword(@PathVariable("userEmail") String userEmail, @RequestParam("password") String password, 
			Model model) {
		
		User user = userRepository.findByUserEmail(userEmail);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		
		return "redirect:/login";
		
	}
	
	public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("goldenbitespos@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
