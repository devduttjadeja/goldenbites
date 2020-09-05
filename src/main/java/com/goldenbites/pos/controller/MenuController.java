package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping("/viewitems")
	public String viewItems(Model model) {
		model.addAttribute("items", itemRepository.findAll());
		return "Menu/viewItems";
	}

	@GetMapping("/menuhome")
	public String viewMenu(Model model) {
		// model.addAttribute("items", itemRepository.findAll());
		return "Menu/menuhome";
	}

	@GetMapping("/additem")
	public String displayItemForm(Model model) {
		//model.addAttribute("items", item);
		model.addAttribute("item", new Item());
		return "Menu/additem";
	}
	@PostMapping("/additem")
	public String saveItem(@ModelAttribute Item item, Model model) {
		//model.addAttribute("items", item);
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
	    item.setItemCreatedDate(now);
		itemRepository.save(item);
		model.addAttribute("item", new Item());
		return "Menu/additem";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteItem(@PathVariable ("id") String id, Model model) {
	    
		itemRepository.deleteById(id);
	    model.addAttribute("items", itemRepository.findAll());
	    return "Menu/viewItems";
	}
}