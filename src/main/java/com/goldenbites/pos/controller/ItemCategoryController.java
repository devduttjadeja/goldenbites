package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.ItemCategoryRepository;
import com.goldenbites.pos.model.ItemCategory;

@Controller
public class ItemCategoryController {

	@Autowired
	ItemCategoryRepository itemCategoryRepository;	
	
	@GetMapping("home/addItemCategory")
	public String displayItemCategoryForm(Model model) {
		model.addAttribute("itemCategory", new ItemCategory());
		return "Menu/addItemCategory";
	}

	@PostMapping("home/addItemCategory")
	public String saveItemCategory(@ModelAttribute ItemCategory itemCategory, Model model) {
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		itemCategory.setItemCategoryCreatedDate(now);
		itemCategoryRepository.save(itemCategory);
		model.addAttribute("itemCategory", new ItemCategory());
		return "Menu/addItemCategory";
	}
}
