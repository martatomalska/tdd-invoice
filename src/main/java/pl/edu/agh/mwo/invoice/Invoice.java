package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private static int previousNumber = 0;
    private final int invoiceNumber = ++previousNumber;

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (isProductExists(product.getName())) {
            products.replace(getProductByName(product.getName()),
                    quantity + getProductAmountByName(product.getName()));
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

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String printInvoice() {
        StringBuilder printedInvoice = new StringBuilder();
        printedInvoice.append("Numer faktury: ").append(getInvoiceNumber()).append("\n");
        int numberOfProducts = 0;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            printedInvoice.append(product.getName()).append(", ")
                    .append(quantity).append(", ")
                    .append(product.getPriceWithTax().multiply(quantity)).append("\n");
            numberOfProducts++;
        }
        printedInvoice.append("Liczba pozycji: ").append(numberOfProducts);
        return printedInvoice.toString();
    }

    public Product getProductByName(String name) {
        for (Product product : products.keySet()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public boolean isProductExists(String name) {
        return getProductByName(name) != null;
    }

    public int getProductAmountByName(String name) {
        if (isProductExists(name)) {
            return products.get(getProductByName(name));
        } else {
            return 0;
        }
    }
}
