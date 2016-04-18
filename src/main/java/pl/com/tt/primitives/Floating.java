package pl.com.tt.primitives;

import java.math.BigDecimal;

public class Floating {
    public static void main(String[] args) {
        changeRequest();
        candyShopFloating();
        candyShopBigDecimal();

    }

    private static void candyShopFloating() {
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = .10; funds >= price; price += .10) {
            funds -= price;
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Change: $" + funds);
    }

    private static void candyShopBigDecimal() {
        final BigDecimal TEN_CENTS = new BigDecimal(".10");
        int itemsBought = 0;

        BigDecimal funds = new BigDecimal("1.00");
        for (BigDecimal price = TEN_CENTS;
             funds.compareTo(price) >= 0;
             price = price.add(TEN_CENTS)) {
            itemsBought++;
            funds = funds.subtract(price);
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Money left over: $" + funds);
    }

    private static void changeRequest() {
        System.out.println("Change (int): " + (2 - 1)); //1
        System.out.println("Change (double): " + (2 - 1.4)); //0.6000000000000001
        System.out.println("Change (double) : " + (2.5 - 1.4)); //1.1
    }
}
