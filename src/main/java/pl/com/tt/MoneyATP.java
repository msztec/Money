package pl.com.tt;


//import org.javamoney.moneta.Money;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.AmountFormatParams;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

public class MoneyATP {

    private static CurrencyUnit DOLLAR = Monetary.getCurrency(Locale.US);
    private static CurrencyUnit EURO = Monetary.getCurrency("EUR");

    public static void main(String[] args) {
        roundingMode();

        amountFormatting();

        MonetaryAmount euroMonetary1 = Money.of(1001234.5678, EURO);
        MonetaryAmount euroMonetary2 = Money.of(1001234.5678, EURO);
        MonetaryAmount euroMonetarySum = euroMonetary1.add(euroMonetary2);
        formattingStyles(euroMonetarySum);

        customFormatting("new " + MonetaryFormats.getAmountFormat(
                AmountFormatQueryBuilder.of(new Locale("", "INR"))
                        .set(AmountFormatParams.GROUPING_SIZES, new int[]{2, 3})
                        .set(AmountFormatParams.GROUPING_GROUPING_SEPARATORS, new char[]{',', '`'})
                        .build())
                .format(euroMonetarySum));

        standardFormatting();

        ISOcurrencies();

//        addDiffrentCurrencies();

        compareDiffrentMoneyCurrencies();
        compareSameMoneyCurrencies();
        exchangeMoney();

        fastMoneyOperation();

    }

    private static void fastMoneyOperation() {
        MonetaryAmount euroMonetary3 = FastMoney.of(1234.5678, EURO);
        MonetaryAmount euroMonetary4 = FastMoney.of(34.5678, EURO);
        System.out.printf(euroMonetary3.add(euroMonetary4).query(MonetaryFormats.getAmountFormat(Locale.GERMANY)));
    }

    private static void exchangeMoney() {
        // get the default ExchangeRateProvider (CompoundRateProvider)
        ExchangeRateProvider exchangeRateProvider = MonetaryConversions.getExchangeRateProvider();
        ExchangeRate rate = exchangeRateProvider.getExchangeRate("EUR", "USD");

        ExchangeRateProvider ecbExchangeRateProvider = MonetaryConversions.getExchangeRateProvider("ECB");
        CurrencyConversion ecbDollarConversion = ecbExchangeRateProvider.getCurrencyConversion("USD");
        System.out.println(ecbDollarConversion);

        // get the CurrencyConversion from a specific provider

        MonetaryAmount tenEuro = Money.of(10, "EUR");
        CurrencyConversion dollarConversion = MonetaryConversions.getConversion("USD");
// convert 10 euro to us dollar
        MonetaryAmount inDollar = tenEuro.with(dollarConversion); // "USD 12.537" (at the time writing)
        System.out.println(inDollar);
    }

    private static void compareDiffrentMoneyCurrencies() {
        MonetaryAmount fiveEuro = Money.of(5, EURO);

        MonetaryAmount fiveUsDollar = Money.of(5, DOLLAR);

        System.out.println(fiveEuro.equals(fiveUsDollar));
    }

    private static void compareSameMoneyCurrencies() {
        MonetaryAmount fiveEuro = Money.of(5, EURO);

        MonetaryAmount fiveUsDollar = Money.of(5, EURO);

        System.out.println(fiveEuro.equals(fiveUsDollar));
    }

    private static void addDiffrentCurrencies() {
        MonetaryAmount euroMonetary = Money.of(1234.5678, EURO);
        MonetaryAmount usdMonetary = Money.of(34.5678, DOLLAR);
        System.out.println(euroMonetary.add(usdMonetary));
    }

    private static void standardFormatting() {
        double money = 10000000.1;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);

        System.out.println(moneyString);

        System.out.println(NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(money));
    }

    private static void customFormatting(String inr) {
        System.out.println(inr);
    }

    private static void ISOcurrencies() {
        Collection<CurrencyUnit> allCurrencies = Monetary.getCurrencies();
        allCurrencies.stream().sorted((CurrencyUnit e1, CurrencyUnit e2) -> e1.getCurrencyCode().compareTo(
                e2.getCurrencyCode()))
                .forEach(e -> System.out.println(e));
        customFormatting("How much currencies do we support ? " + allCurrencies.size());
    }

    private static void formattingStyles(MonetaryAmount euroMonetarySum) {
        System.out.println("Default formatting: " + euroMonetarySum);

        System.out.println("Polish formatting: " + euroMonetarySum.query(MonetaryFormats.getAmountFormat(new Locale("pl", "PL")))); // 2 002 469,14 EUR

        System.out.println("Germany formatting: " + euroMonetarySum.query(MonetaryFormats.getAmountFormat(
                Locale.GERMANY)));  // 1.269,14 EUR

        System.out.println("Switzerland formatting: " + euroMonetarySum.query(MonetaryFormats.getAmountFormat(new Locale("de", "CH"))));   // EUR 1'269.14

        System.out.println("USA formatting: " + euroMonetarySum.query(MonetaryFormats.getAmountFormat(Locale.ENGLISH)));    // EUR1,269.14


    }

    private static void amountFormatting() {
        double result = 0.1 + 0.2 + 0.3;
        BigDecimal result2 = new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);
        NumberFormat form = NumberFormat.getCurrencyInstance(new Locale("de", "DE"));

        System.out.println("Amount: " + form.format(result2));
    }

    private static void roundingMode() {
        BigDecimal value = new BigDecimal("1.2345");
        BigDecimal scaled = value.setScale(0, RoundingMode.HALF_UP);
        System.out.println(value + " -> " + scaled);
    }
}
