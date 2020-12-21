package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import com.goldenbites.pos.model.Item;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.ItemCategoryRepository;
import com.goldenbites.pos.model.ItemCategory;

@Controller
public class ItemCategoryController {

	@Autowired
	ItemCategoryRepository itemCategoryRepository;

	@GetMapping("home/viewItemCategory")
	public String viewItemCategory(Model model) {
		model.addAttribute("categories", itemCategoryRepository.findAll());
		return "Menu/viewItemCategory";
	}

	@GetMapping("home/viewItemCategory/addItemCategory")
	public String displayItemCategoryForm(Model model) {
		model.addAttribute("itemCategory", new ItemCategory());
		return "Menu/addItemCategory";
	}

	@PostMapping("home/viewItemCategory/addItemCategory")
	public String saveItemCategory(@ModelAttribute ItemCategory itemCategory, Model model) {
		itemCategory.setItemCategoryName(itemCategory.getItemCategoryName().toLowerCase());
		String categoryName = itemCategory.getItemCategoryName();
		if(itemCategoryRepository.findByItemCategoryName(categoryName) != null) {
			model.addAttribute("message", "Item Category already exist.");
			model.addAttribute("itemCategory", new ItemCategory());
			return "Menu/addItemCategory";
		}
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		itemCategory.setItemCategoryCreatedDate(now);
		itemCategoryRepository.save(itemCategory);
		return "redirect:/home/viewItemCategory";
	}

	@GetMapping("/home/viewItemCategory/delete/{id}")
	public String deleteItemCategory(@PathVariable("id") String id, Model model) {
		itemCategoryRepository.deleteById(id);
		model.addAttribute("categories", itemCategoryRepository.findAll());
		return "redirect:/home/viewItemCategory";
	}

	@GetMapping("/home/viewItemCategory/edit/{id}")
	public String editItemCategory(@PathVariable("id") String id, Model model) {
		ItemCategory itemCategory = itemCategoryRepository.findByItemCategoryId(id);
		model.addAttribute("itemCategory", itemCategory);
		return "Menu/updateItemCategory";
	}

	@PostMapping("/home/viewItemCategory/updateItemCategory/{id}")
	public String updateItemCategory(@PathVariable("id") String id, @ModelAttribute ItemCategory itemCategory, Model model) {
		
		itemCategory.setItemCategoryName(itemCategory.getItemCategoryName().toLowerCase());
		itemCategory.setItemCategoryId(id);
		itemCategoryRepository.save(itemCategory);
		model.addAttribute("categories", itemCategoryRepository.findAll());
		return "redirect:/home/viewItemCategory";
	}

}
