package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class FuelCanister extends Product {

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        LocalDate currentDate = LocalDate.now();
        LocalDate validateDate = LocalDate.of(2020, 4, 26);
        if (currentDate.getMonth().equals(Month.APRIL) && currentDate.getDayOfMonth() == validateDate.getDayOfMonth()) {
            return getPrice().add(new BigDecimal("5.56"));
        } else {
            return super.getPriceWithTax().add(new BigDecimal("5.56"));
        }
    }
}
