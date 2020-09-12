package com.goldenbites.pos.controller;

import java.util.ArrayList;
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
import com.goldenbites.pos.dao.OrderRepository;
import com.goldenbites.pos.dao.OrderSummaryRepository;
import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.Item;
import com.goldenbites.pos.model.Order;
import com.goldenbites.pos.model.OrderCreation;
import com.goldenbites.pos.model.OrderSummary;

@Controller
public class PlaceOrderController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderSummaryRepository orderSummaryRepository;

	@GetMapping("/home")
	public String homePage(Model model) {
		// model.addAttribute("user", new User());
		return "home";
	}

	@GetMapping("/categories")
	public String categoryDisplay(Model model) {
		// model.addAttribute("user", new User());
		return "Place Order/categories";
	}

	@GetMapping("/itemsListForPlaceOrder")
	public String itemDisplay(Model model) {
		OrderCreation orderCreation = new OrderCreation();

		for (int i = 0; i < itemRepository.findAll().size(); i++) {
			orderCreation.addOrderSummary(new OrderSummary());
		}

		model.addAttribute("orderCreation", orderCreation);
		model.addAttribute("items", itemRepository.findAll());
		return "Place Order/itemsListForPlaceOrder";
	}

	@PostMapping("/itemsListForPlaceOrder/add")
	public String addItemsForPlaceOrder(@ModelAttribute OrderCreation orderCreation, Model model) {
		ArrayList<OrderSummary> list = orderCreation.getOrderSummaryList();
		double orderTotal = 0, orderTax1 = 0, orderTax2 = 0, orderTaxTotal = 0, orderFinalTotal = 0;

		double itemTotalPrice = 0, itemTotalTax1 = 0, itemTotalTax2 = 0;

		for (OrderSummary orderSummary : list) {
			if (orderSummary.getItemQuantity() != 0) {
				String itemId = orderSummary.getItemId();
				Item item = itemRepository.findByItemId(itemId);

				orderSummary.setItemName(item.getItemName());

				itemTotalPrice = item.getItemPrice() * orderSummary.getItemQuantity();
				orderSummary.setItemTotalPrice(itemTotalPrice);
				orderTotal = orderTotal + itemTotalPrice;

				itemTotalTax1 = item.getItemTax1() * orderSummary.getItemQuantity();
				orderSummary.setItemTotalTax1(itemTotalTax1);
				orderTax1 = orderTax1 + itemTotalTax1;

				itemTotalTax2 = item.getItemTax2() * orderSummary.getItemQuantity();
				orderSummary.setItemTotalTax2(itemTotalTax2);
				orderTax2 = orderTax2 + itemTotalTax2;
			}
		}

		orderTaxTotal = orderTax1 + orderTax2;
		orderFinalTotal = orderTotal + orderTaxTotal;
		Order order = new Order();

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		order.setOrderDate(now);
		order.setOrderTax1(orderTax1);
		order.setOrderTax2(orderTax2);
		order.setOrderTaxTotal(orderTaxTotal);
		order.setOrderTotal(orderTotal);
		order.setOrderFinalTotal(orderFinalTotal);

		orderRepository.save(order);
		String orderId = order.getOrderId();

		for (OrderSummary orderSummary : list) {
			if (orderSummary.getItemQuantity() != 0) {
				orderSummary.setOrderId(orderId);
				orderSummaryRepository.save(orderSummary);
			}
		}

		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);

		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);

		return "Place Order/orderSummary";
	}

	@GetMapping("/orderSummary")
	public String orderSummaryDisplay(Model model) {
		// model.addAttribute("user", new User());
		return "Place Order/orderSummary";
	}

	@GetMapping("/orderSummary/delete/{id}")
	public String orderSummaryDelete(@PathVariable("id") String id, Model model) {

		OrderSummary orderSummary = orderSummaryRepository.findByOrderSummaryId(id);
		String orderId = orderSummary.getOrderId();
		
		Order order = orderRepository.findByOrderId(orderId);
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		order.setOrderDate(now);
		order.setOrderTax1(order.getOrderTax1() - orderSummary.getItemTotalTax1());
		order.setOrderTax2(order.getOrderTax2() - orderSummary.getItemTotalTax2());
		order.setOrderTaxTotal(order.getOrderTaxTotal() - (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2()));
		order.setOrderTotal(order.getOrderTotal() - orderSummary.getItemTotalPrice());
		order.setOrderFinalTotal(order.getOrderFinalTotal() - (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2() + orderSummary.getItemTotalPrice()));
		
		
		orderSummaryRepository.deleteById(id);

		

		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);
		

		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);

		return "Place Order/orderSummary";
	}

	@GetMapping("/customerSelection")
	public String customerSelection(Model model) {
		// model.addAttribute("user", new User());
		return "Place Order/customerSelection";
	}

	@GetMapping("/payment")
	public String paymentOptions(Model model) {
		// model.addAttribute("user", new User());
		return "Place Order/paymentOptions";
	}

	@GetMapping("/exit")
	public String exitDisplay(Model model) {
		// model.addAttribute("user", new User());
		return "Place Order/exit";
	}

}