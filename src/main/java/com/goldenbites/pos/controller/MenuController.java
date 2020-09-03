package com.goldenbites.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goldenbites.pos.dao.ItemRepository;
import com.goldenbites.pos.dao.OrderRepository;


@Controller

public class MenuController {

	@Autowired
	  ItemRepository itemRepository;
	
	@GetMapping("/menu")
	public String categoryDisplay(Model model) {
		model.addAttribute("items", itemRepository.findAll());
		return "Menu/menu";
	}
}
