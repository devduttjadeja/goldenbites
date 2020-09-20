package com.goldenbites.pos.controller;

import java.util.ArrayList;
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

import com.goldenbites.pos.dao.CustomerRepository;
import com.goldenbites.pos.dao.ItemRepository;
import com.goldenbites.pos.dao.OrderRepository;
import com.goldenbites.pos.dao.OrderSummaryRepository;
import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.Customer;
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
	@Autowired
	CustomerRepository customerRepository;
	
	final int precision = 2;

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
		order.setOrderTax1(DoubleRounder.round(orderTax1, precision));
		order.setOrderTax2(DoubleRounder.round(orderTax2, precision));
		order.setOrderTaxTotal(DoubleRounder.round(orderTaxTotal, precision));
		order.setOrderTotal(DoubleRounder.round(orderTotal, precision));
		order.setOrderFinalTotal(DoubleRounder.round(orderFinalTotal, precision));

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
		order.setOrderTax1(DoubleRounder.round(order.getOrderTax1() - orderSummary.getItemTotalTax1(), precision));
		order.setOrderTax2(DoubleRounder.round(order.getOrderTax2() - orderSummary.getItemTotalTax2(), precision));
		order.setOrderTaxTotal(DoubleRounder.round(order.getOrderTaxTotal() - (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2()), precision)
				);
		order.setOrderTotal(DoubleRounder.round(order.getOrderTotal() - orderSummary.getItemTotalPrice(), precision));
		order.setOrderFinalTotal(DoubleRounder.round(order.getOrderFinalTotal() - (orderSummary.getItemTotalTax1()
				+ orderSummary.getItemTotalTax2() + orderSummary.getItemTotalPrice()), precision));

		orderSummaryRepository.deleteById(id);

		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);

		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);

		return "Place Order/orderSummary";
	}

	@GetMapping("/orderSummary/edit/{id}")
	public String showUpdateOrderForm(@PathVariable("id") String id, Model model) {
		OrderSummary orderSummary = orderSummaryRepository.findByOrderSummaryId(id);
		model.addAttribute("orderSummary", orderSummary);
		return "Place Order/updateOrderSummary";
	}

	@PostMapping("/orderSummary/update/{id}")
	public String updateOrder(@PathVariable("id") String id, @ModelAttribute OrderSummary orderSummary,
			BindingResult result, Model model) {

		OrderSummary orderSummaryOld = orderSummaryRepository.findByOrderSummaryId(id);

		String orderId = orderSummaryOld.getOrderId();

		Order order = orderRepository.findByOrderId(orderId);
		orderSummary.setOrderSummaryId(id);
		orderSummary.setOrderId(orderId);

		double itemTotalPrice = 0, itemTotalTax1 = 0, itemTotalTax2 = 0;

		Item item = itemRepository.findByItemName(orderSummary.getItemName());

		orderSummary.setItemId(item.getItemId());
		//orderSummary.setItemName(item.getItemName());

		itemTotalPrice = item.getItemPrice() * orderSummary.getItemQuantity();
		orderSummary.setItemTotalPrice(itemTotalPrice);

		itemTotalTax1 = item.getItemTax1() * orderSummary.getItemQuantity();
		orderSummary.setItemTotalTax1(itemTotalTax1);

		itemTotalTax2 = item.getItemTax2() * orderSummary.getItemQuantity();
		orderSummary.setItemTotalTax2(itemTotalTax2);
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		
		order.setOrderDate(now);
		order.setOrderTax1(DoubleRounder.round(order.getOrderTax1() - orderSummaryOld.getItemTotalTax1() + itemTotalTax1, precision));
		order.setOrderTax2(DoubleRounder.round(order.getOrderTax2() - orderSummaryOld.getItemTotalTax2() + itemTotalTax2, precision));
		order.setOrderTaxTotal(DoubleRounder.round(order.getOrderTaxTotal() - (orderSummaryOld.getItemTotalTax1() + orderSummaryOld.getItemTotalTax2()) + (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2()), precision)
				);
		order.setOrderTotal(DoubleRounder.round(order.getOrderTotal() - orderSummaryOld.getItemTotalPrice() + itemTotalPrice, precision));
		order.setOrderFinalTotal(DoubleRounder.round(order.getOrderFinalTotal() - (orderSummaryOld.getItemTotalTax1()
				+ orderSummaryOld.getItemTotalTax2() + orderSummaryOld.getItemTotalPrice()) + (orderSummary.getItemTotalTax1()
						+ orderSummary.getItemTotalTax2() + orderSummary.getItemTotalPrice()), precision));

		orderSummaryRepository.save(orderSummary);

		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);
		
		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);

		return "Place Order/orderSummary";
	}

	@GetMapping("/customerSelection/{id}")
	public String customerSelectionDisplay(@PathVariable("id") String orderId, Model model) {
		model.addAttribute("customers", customerRepository.findAll());
		Order order = orderRepository.findByOrderId(orderId);
		model.addAttribute("order", order);
		return "Place Order/customerSelection";
	}
	
	@GetMapping("/customerSelection/add/{orderId}/{customerCode}")
	public String customerSelectionAddToOrder(@PathVariable("orderId") String orderId, @PathVariable("customerCode") String customerCode,
			Model model) {
		Order order = orderRepository.findByOrderId(orderId);
		order.setOrderCustomerCode(customerCode);
		orderRepository.save(order);
		return "Place Order/PaymentOptions";
	}

	@GetMapping("/invoice")
	public String invoiceDisplay(Model model) {
		Order order = orderRepository.findFirstByOrderByOrderDateDesc();
		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(order.getOrderId());
		Customer customer = customerRepository.findByCustomerCode(order.getOrderCustomerCode());
		
		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);
		model.addAttribute("customer", customer);
		
		return "Place Order/invoice";
	}
	
	@GetMapping("/exit")
	public String exitDisplay(Model model) {
		
		return "Place Order/exit";
	}

}