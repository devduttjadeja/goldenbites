package com.goldenbites.pos.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.servlet.http.HttpServletResponse;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
import com.goldenbites.pos.view.InvoicePDFExporter;
import com.lowagie.text.DocumentException;

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
	
	String orderText = null;

	@GetMapping("/home")
	public String homePage(Model model) {
		return "home";
	}

	@GetMapping("/categories")
	public String categoryDisplay(Model model) {
		return "Place Order/categories";
	}

	@GetMapping("/home/cart")
	public String itemDisplay(Model model) {
		OrderCreation orderCreation = new OrderCreation();

		for (int i = 0; i < itemRepository.findAll().size(); i++) {
			orderCreation.addOrderSummary(new OrderSummary());
		}

		model.addAttribute("orderCreation", orderCreation);
		model.addAttribute("items", itemRepository.findAll());
		return "Place Order/cart";
	}

	@PostMapping("/home/itemsListForPlaceOrder/plus")
	public void itemShow(@RequestBody String test, Model model) {

		String text = null;

//		JSONArray array = null;
		
		try {
			text = URLDecoder.decode(test, "UTF-8");
//			array = new JSONArray(text);
			System.out.println(text);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(test);

		orderText = text;
	}
	
	
	@PostMapping("/home/itemsListForPlaceOrder/add")
	public String addItemsForPlaceOrder(@ModelAttribute OrderCreation orderCreation, Model model) {
		ArrayList<OrderSummary> list = orderCreation.getOrderSummaryList();
		double orderTotal = 0, orderTax1 = 0, orderTax2 = 0, orderTaxTotal = 0, orderFinalTotal = 0;

		double itemTotalPrice = 0, itemTotalTax1 = 0, itemTotalTax2 = 0;


		ArrayList<OrderSummary> orderSummaries = new ArrayList<OrderSummary>();
		
		
		JSONArray array = new JSONArray(orderText);
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject object = array.getJSONObject(i);
			String itemName = object.getString("name");
			double itemQuantity = object.getDouble("quantity");

			Item item = itemRepository.findByItemName(itemName);

			OrderSummary orderSummary = new OrderSummary();
			orderSummary.setItemId(item.getItemId());
			orderSummary.setItemName(item.getItemName());
			orderSummary.setItemQuantity(itemQuantity);

			itemTotalPrice = item.getItemPrice() * itemQuantity;
			orderSummary.setItemTotalPrice(itemTotalPrice);
			orderTotal = orderTotal + itemTotalPrice;

			itemTotalTax1 = item.getItemTax1() * itemQuantity;
			orderSummary.setItemTotalTax1(itemTotalTax1);
			orderTax1 = orderTax1 + itemTotalTax1;

			itemTotalTax2 = item.getItemTax2() * itemQuantity;
			orderSummary.setItemTotalTax2(itemTotalTax2);
			orderTax2 = orderTax2 + itemTotalTax2;
			
			orderSummaries.add(orderSummary);

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
		
		for (OrderSummary orderSummary : orderSummaries) {
			orderSummary.setOrderId(orderId);
			orderSummaryRepository.save(orderSummary);
		}

		
		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);

		
		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);

		return "Place Order/orderSummary";
	}


	/*
	 * @PostMapping("/home/itemsListForPlaceOrder/add") public String
	 * addItemsForPlaceOrder(@ModelAttribute OrderCreation orderCreation, Model
	 * model) { ArrayList<OrderSummary> list = orderCreation.getOrderSummaryList();
	 * double orderTotal = 0, orderTax1 = 0, orderTax2 = 0, orderTaxTotal = 0,
	 * orderFinalTotal = 0;
	 * 
	 * double itemTotalPrice = 0, itemTotalTax1 = 0, itemTotalTax2 = 0;
	 * 
	 * for (OrderSummary orderSummary : list) { if (orderSummary.getItemQuantity()
	 * != 0) { String itemId = orderSummary.getItemId(); Item item =
	 * itemRepository.findByItemId(itemId);
	 * 
	 * orderSummary.setItemName(item.getItemName());
	 * 
	 * itemTotalPrice = item.getItemPrice() * orderSummary.getItemQuantity();
	 * orderSummary.setItemTotalPrice(itemTotalPrice); orderTotal = orderTotal +
	 * itemTotalPrice;
	 * 
	 * itemTotalTax1 = item.getItemTax1() * orderSummary.getItemQuantity();
	 * orderSummary.setItemTotalTax1(itemTotalTax1); orderTax1 = orderTax1 +
	 * itemTotalTax1;
	 * 
	 * itemTotalTax2 = item.getItemTax2() * orderSummary.getItemQuantity();
	 * orderSummary.setItemTotalTax2(itemTotalTax2); orderTax2 = orderTax2 +
	 * itemTotalTax2; } }
	 * 
	 * orderTaxTotal = orderTax1 + orderTax2; orderFinalTotal = orderTotal +
	 * orderTaxTotal; Order order = new Order();
	 * 
	 * Calendar calendar = Calendar.getInstance(); Date now = calendar.getTime();
	 * order.setOrderDate(now); order.setOrderTax1(DoubleRounder.round(orderTax1,
	 * precision)); order.setOrderTax2(DoubleRounder.round(orderTax2, precision));
	 * order.setOrderTaxTotal(DoubleRounder.round(orderTaxTotal, precision));
	 * order.setOrderTotal(DoubleRounder.round(orderTotal, precision));
	 * order.setOrderFinalTotal(DoubleRounder.round(orderFinalTotal, precision));
	 * 
	 * orderRepository.save(order); String orderId = order.getOrderId();
	 * 
	 * for (OrderSummary orderSummary : list) { if (orderSummary.getItemQuantity()
	 * != 0) { orderSummary.setOrderId(orderId);
	 * orderSummaryRepository.save(orderSummary); } }
	 * 
	 * ArrayList<OrderSummary> orderSummaryList =
	 * orderSummaryRepository.findAllByOrderId(orderId);
	 * 
	 * model.addAttribute("orderSummaryList", orderSummaryList);
	 * model.addAttribute("order", order);
	 * 
	 * return "Place Order/orderSummary"; }
	 */
	@GetMapping("/home/itemsListForPlaceOrder/orderSummary")
	public String orderSummaryDisplay(Model model) {
		// model.addAttribute("user", new User());
		return "Place Order/orderSummary";
	}

	@GetMapping("/home/itemsListForPlaceOrder/orderSummary/delete/{id}")
	public String orderSummaryDelete(@PathVariable("id") String id, Model model) {

		OrderSummary orderSummary = orderSummaryRepository.findByOrderSummaryId(id);
		String orderId = orderSummary.getOrderId();

		Order order = orderRepository.findByOrderId(orderId);

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		order.setOrderDate(now);
		order.setOrderTax1(DoubleRounder.round(order.getOrderTax1() - orderSummary.getItemTotalTax1(), precision));
		order.setOrderTax2(DoubleRounder.round(order.getOrderTax2() - orderSummary.getItemTotalTax2(), precision));
		order.setOrderTaxTotal(DoubleRounder.round(
				order.getOrderTaxTotal() - (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2()),
				precision));
		order.setOrderTotal(DoubleRounder.round(order.getOrderTotal() - orderSummary.getItemTotalPrice(), precision));
		order.setOrderFinalTotal(DoubleRounder.round(order.getOrderFinalTotal() - (orderSummary.getItemTotalTax1()
				+ orderSummary.getItemTotalTax2() + orderSummary.getItemTotalPrice()), precision));

		orderRepository.save(order);
		orderSummaryRepository.deleteById(id);

		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);

		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);
		return "Place Order/orderSummary";
	}

	@GetMapping("/home/itemsListForPlaceOrder/orderSummary/edit/{id}")
	public String showUpdateOrderForm(@PathVariable("id") String id, Model model) {
		OrderSummary orderSummary = orderSummaryRepository.findByOrderSummaryId(id);
		model.addAttribute("orderSummary", orderSummary);
		return "Place Order/updateOrderSummary";
	}

	@PostMapping("/home/itemsListForPlaceOrder/orderSummary/update/{id}")
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
		itemTotalPrice = item.getItemPrice() * orderSummary.getItemQuantity();
		orderSummary.setItemTotalPrice(itemTotalPrice);

		itemTotalTax1 = item.getItemTax1() * orderSummary.getItemQuantity();
		orderSummary.setItemTotalTax1(itemTotalTax1);

		itemTotalTax2 = item.getItemTax2() * orderSummary.getItemQuantity();
		orderSummary.setItemTotalTax2(itemTotalTax2);

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();

		order.setOrderDate(now);
		order.setOrderTax1(DoubleRounder
				.round(order.getOrderTax1() - orderSummaryOld.getItemTotalTax1() + itemTotalTax1, precision));
		order.setOrderTax2(DoubleRounder
				.round(order.getOrderTax2() - orderSummaryOld.getItemTotalTax2() + itemTotalTax2, precision));
		order.setOrderTaxTotal(DoubleRounder.round(
				order.getOrderTaxTotal() - (orderSummaryOld.getItemTotalTax1() + orderSummaryOld.getItemTotalTax2())
						+ (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2()),
				precision));
		order.setOrderTotal(DoubleRounder
				.round(order.getOrderTotal() - orderSummaryOld.getItemTotalPrice() + itemTotalPrice, precision));
		order.setOrderFinalTotal(DoubleRounder.round(order.getOrderFinalTotal()
				- (orderSummaryOld.getItemTotalTax1() + orderSummaryOld.getItemTotalTax2()
						+ orderSummaryOld.getItemTotalPrice())
				+ (orderSummary.getItemTotalTax1() + orderSummary.getItemTotalTax2()
						+ orderSummary.getItemTotalPrice()),
				precision));

		orderRepository.save(order);
		orderSummaryRepository.save(orderSummary);

		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(orderId);

		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);

		return "Place Order/orderSummary";
	}

	@GetMapping("/home/itemsListForPlaceOrder/orderSummary/customerSelection/{id}")
	public String customerSelectionDisplay(@PathVariable("id") String orderId, Model model) {
		model.addAttribute("customers", customerRepository.findAll());
		Order order = orderRepository.findByOrderId(orderId);
		model.addAttribute("order", order);
		return "Place Order/customerSelection";
	}

	@GetMapping("/home/itemsListForPlaceOrder/orderSummary/customerSelection/add/{orderId}")
	public String customerSelectionAddToOrder(@PathVariable("orderId") String orderId,
			@RequestParam(value = "customerCode") String customerCode, Model model) {
		Order order = orderRepository.findByOrderId(orderId);
		Customer customer = customerRepository.findByCustomerCode(customerCode);
		if (customer != null) {
			order.setOrderCustomerCode(customerCode);
			orderRepository.save(order);
			return "Place Order/PaymentOptions";
		} else {
			return "redirect:/home/itemsListForPlaceOrder/orderSummary/customerSelection/" + orderId;
		}
	}

	@GetMapping("/home/itemsListForPlaceOrder/orderSummary/invoice")
	public String invoiceDisplay(Model model) {
		Order order = orderRepository.findFirstByOrderByOrderDateDesc();
		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(order.getOrderId());
		Customer customer = customerRepository.findByCustomerCode(order.getOrderCustomerCode());

		model.addAttribute("orderSummaryList", orderSummaryList);
		model.addAttribute("order", order);
		model.addAttribute("customer", customer);
		return "Place Order/invoice";
	}

	@GetMapping("/home/viewOrders/invoice/{id}")
	public String invoiceDisplayForOneOrder(@PathVariable("id") String orderId, Model model) {
		Order order = orderRepository.findByOrderId(orderId);
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

	@GetMapping("/invoice/export/pdf/{id}")
	public void exportToPDF(@PathVariable("id") String orderId, Model model, HttpServletResponse response)
			throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		Order order = orderRepository.findByOrderId(orderId);
		ArrayList<OrderSummary> orderSummaryList = orderSummaryRepository.findAllByOrderId(order.getOrderId());
		Customer customer = customerRepository.findByCustomerCode(order.getOrderCustomerCode());

		InvoicePDFExporter exporter = new InvoicePDFExporter(order, orderSummaryList, customer);
		exporter.export(response);

	}

}