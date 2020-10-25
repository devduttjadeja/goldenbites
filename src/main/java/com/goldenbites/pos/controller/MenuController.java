package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import com.goldenbites.pos.dao.ItemCategoryRepository;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.goldenbites.pos.dao.ItemRepository;
import com.goldenbites.pos.model.Item;

@Controller
public class MenuController {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemCategoryRepository itemCategoryRepository;

    final Double tax1 = 5.0;
    final Double tax2 = 9.75;
    final int precision = 2;

    @GetMapping("/home/viewItems")
    public String viewItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "Menu/viewItems";
    }

    @GetMapping("/home/viewItems/addItem")
    public String displayItemForm(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categories", itemCategoryRepository.findAll());
        return "Menu/addItem";
    }

    @PostMapping("/home/viewItems/addItem")
    public String saveItem(@ModelAttribute Item item, Model model) {
        if(itemRepository.findByItemName(item.getItemName()) != null){
            model.addAttribute("message", "Item already exist.");
            model.addAttribute("item", new Item());
            model.addAttribute("categories", itemCategoryRepository.findAll());
            return "Menu/addItem";
        }
//

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        item.setItemCreateOrUpdateDate(now);

        double itemTax1 = 0;
        double itemTax2 = 0;

        if (item.getItemIsTaxable().equalsIgnoreCase("yes")) {
            itemTax1 = DoubleRounder.round((tax1 / 100) * item.getItemPrice(), precision);
            itemTax2 = DoubleRounder.round((tax2 / 100) * item.getItemPrice(), precision);
            item.setItemTax1(itemTax1);
            item.setItemTax2(itemTax2);
        } else {
            item.setItemTax1(0.0);
            item.setItemTax2(0.0);
        }

        item.setItemTaxTotal(DoubleRounder.round(itemTax1 + itemTax2, precision));
        itemRepository.save(item);
        model.addAttribute("item", new Item());
        return "redirect:/home/viewItems";
    }

    @GetMapping("/home/viewItems/delete/{id}")
    public String deleteItem(@PathVariable("id") String id, Model model) {

        itemRepository.deleteById(id);
        model.addAttribute("items", itemRepository.findAll());
        return "redirect:/home/viewItems";
    }

    @GetMapping("/home/viewItems/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Item item = itemRepository.findByItemId(id);
        model.addAttribute("item", item);
        return "Menu/updateItem";
    }


    @PostMapping("/home/viewItems/updateItem/{id}")
    public String updateItem(@PathVariable("id") String id, @ModelAttribute Item item,
                             BindingResult result, Model model) {
        item.setItemId(id);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        item.setItemCreateOrUpdateDate(now);

        double itemTax1 = 0;
        double itemTax2 = 0;

        if (item.getItemIsTaxable().equalsIgnoreCase("yes")) {
            itemTax1 = DoubleRounder.round((tax1 / 100) * item.getItemPrice(), precision);
            itemTax2 = DoubleRounder.round((tax2 / 100) * item.getItemPrice(), precision);
            item.setItemTax1(itemTax1);
            item.setItemTax2(itemTax2);
        } else {
            item.setItemTax1(0.0);
            item.setItemTax2(0.0);
        }

        item.setItemTaxTotal(DoubleRounder.round(itemTax1 + itemTax2, precision));
        itemRepository.save(item);
        model.addAttribute("items", itemRepository.findAll());
        return "redirect:/home/viewItems";
    }
}
