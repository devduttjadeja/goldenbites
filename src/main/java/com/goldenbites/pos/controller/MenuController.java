package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.ItemRepository;
import com.goldenbites.pos.model.Item;

@Controller
public class MenuController {

	@Autowired
	ItemRepository itemRepository;
	
	final Double tax1 = 5.0;
	final Double tax2 = 9.75;
	final int precision = 2;

	@GetMapping("/viewItems")
	public String viewItems(Model model) {
		model.addAttribute("items", itemRepository.findAll());
		return "Menu/viewItems";
	}

	@GetMapping("/menuHome")
	public String viewMenu(Model model) {
		// model.addAttribute("items", itemRepository.findAll());
		return "Menu/menuHome";
	}

	@GetMapping("/addItem")
	public String displayItemForm(Model model) {
		//model.addAttribute("items", item);
		model.addAttribute("item", new Item());
		return "Menu/addItem";
	}
	
	@PostMapping("/addItem")
	public String saveItem(@ModelAttribute Item item, Model model) {
		//model.addAttribute("items", item);
		
		Double tax1 = 5.0;
		Double tax2 = 9.75;
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
	    item.setItemCreateOrUpdateDate(now);
	    
	    if(item.getItemIsTaxable().equalsIgnoreCase("yes")) {
	    	item.setItemTax1(DoubleRounder.round((tax1 / 100) * item.getItemPrice(), precision));
	    	item.setItemTax2(DoubleRounder.round((tax2 / 100) * item.getItemPrice(), precision));
	    }else {
	    	item.setItemTax1(0.0);
	    	item.setItemTax2(0.0);
	    }
	    
		itemRepository.save(item);
		model.addAttribute("item", new Item());
		return "redirect:/viewItems";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteItem(@PathVariable ("id") String id, Model model) {
	    
		itemRepository.deleteById(id);
	    model.addAttribute("items", itemRepository.findAll());
	    return "redirect:/viewItems";
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    Item item = itemRepository.findByItemId(id);

	    model.addAttribute("item", item);
	    return "Menu/updateItem";
	}
	
	
	@PostMapping("/updateItem/{id}")
	public String updateItem(@PathVariable("id") String id, @ModelAttribute Item item, 
	  BindingResult result, Model model) {     
	    item.setItemId(id);
	    Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
	    item.setItemCreateOrUpdateDate(now);
	    
	    if(item.getItemIsTaxable().equalsIgnoreCase("yes")) {
	    	item.setItemTax1(DoubleRounder.round((tax1 / 100) * item.getItemPrice(), precision));
	    	item.setItemTax2(DoubleRounder.round((tax2 / 100) * item.getItemPrice(), precision));
	    }else {
	    	item.setItemTax1(0.0);
	    	item.setItemTax2(0.0);
	    }
	    itemRepository.save(item);
	    model.addAttribute("items", itemRepository.findAll());
	    return "redirect:/viewItems";
	}
}
