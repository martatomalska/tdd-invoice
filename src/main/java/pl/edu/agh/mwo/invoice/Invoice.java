package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	private Map<Product, Integer> products = new LinkedHashMap<Product, Integer>();
	
	private int number;
	private static Integer nextNumber = 1;
	
	public Invoice(){
		this.number = nextNumber++;
	}

	public void addProduct(Product product) {
		addProduct(product, 1);
	}

	public void addProduct(Product product, Integer quantity) {
		if (product == null || quantity <= 0) {
			throw new IllegalArgumentException();
		}
		if (this.products.containsKey(product)){
			Integer currentQuantity = this.products.get(product);
			this.products.put(product, currentQuantity + quantity);
		} else {
		products.put(product, quantity);
		}
	}

	public BigDecimal getNetTotal() {
		BigDecimal totalNet = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalNet = totalNet.add(product.getPrice().multiply(quantity));
		}
		return totalNet;
	}

	public BigDecimal getTaxTotal() {
		return getGrossTotal().subtract(getNetTotal());
	}

	public BigDecimal getGrossTotal() {
		BigDecimal totalGross = BigDecimal.ZERO;
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
		}
		return totalGross;
	}

	public Integer getNumber() {
		return this.number;	
	}

	public String getAsText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Faktura nr: " + this.number);
		for (Product product : products.keySet()) {
			BigDecimal quantity = new BigDecimal(products.get(product));
			sb.append("\n");
			sb.append(product.getName());
			sb.append(" ");
			sb.append(quantity);
			sb.append(" ");
			sb.append(product.getPrice());
		}
		sb.append("\nLiczba pozycji: ");
		sb.append(this.products.size());
		return sb.toString();
	}
}
