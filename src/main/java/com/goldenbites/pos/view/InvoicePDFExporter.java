package com.goldenbites.pos.view;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.goldenbites.pos.model.Customer;
import com.goldenbites.pos.model.Order;
import com.goldenbites.pos.model.OrderSummary;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class InvoicePDFExporter {

	private Order order;
	private ArrayList<OrderSummary> orderSummaryList;
	private Customer customer;

	public InvoicePDFExporter(Order order, ArrayList<OrderSummary> orderSummaryList, Customer customer) {
		this.order = order;
		this.orderSummaryList = orderSummaryList;
		this.customer = customer;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		font.setColor(Color.WHITE);
		font.setSize(10);

		cell.setPhrase(new Phrase("Item Name", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Item Quantity", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tax1", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Tax2", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table) {
		for (OrderSummary orderSummary : orderSummaryList) {
			table.addCell(orderSummary.getItemName());
			table.addCell(String.valueOf(orderSummary.getItemQuantity()));
			table.addCell(String.valueOf(orderSummary.getItemTotalTax1()));
			table.addCell(String.valueOf(orderSummary.getItemTotalTax2()));
			table.addCell(String.valueOf((orderSummary.getItemTotalPrice())));
		}

	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER);
		font.setSize(10);
		font.setColor(Color.BLACK);

		Paragraph title = new Paragraph("Invoice", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(title);

		String str = "OrderDate:" + order.getOrderDate() + "\n" + "Name:" + customer.getCustomerName() + "\n" + "Phone:"
				+ customer.getCustomerPhone() + "\n" + "Email:" + customer.getCustomerEmail() + "\n";

		Paragraph header = new Paragraph(str, font);
		header.setAlignment(Paragraph.ALIGN_LEFT);

		document.add(header);

		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.5f, 1.5f, 1.5f, 1.5f, 1.5f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		String fstr = "Total Tax::" + order.getOrderTaxTotal() + "\n" + "Total (Tax exclusive) :" + order.getOrderTotal() + "\n" + "Final Total (Tax inclusive) :"
				+ order.getOrderFinalTotal();

		Paragraph footer = new Paragraph(fstr, font);
		footer.setAlignment(Paragraph.ALIGN_LEFT);

		document.add(footer);

		document.close();

	}

}
