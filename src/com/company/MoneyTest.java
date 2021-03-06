package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void addition() {
        Bank bank = new Bank();
        Money five = Money.dollar(5);

        Expression sum = five.plus(five);
        Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10),  reduced);
    }

    @Test
    void additionDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);

        Expression fiveDollar = Money.dollar(5);
        Expression tenFranc = Money.franc(10);
        Expression sum = fiveDollar.plus(tenFranc);
        Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), reduced);
    }

    @Test
    void plusReturnSum() {
        Money five  = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);
    }

    @Test
    void reduceSum() {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7), result);
    }

    @Test
    void reduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        assertEquals(Money.dollar(1), result);
    }

    @Test
    void reduceMoneyDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        assertEquals(Money.dollar(1), result);
    }

    @Test
    void sumPlusMoney() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(15), result);
    }

    @Test
    void sumtimes() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(20), result);
    }

    @Test
    void multiplication() {
        Money five  = Money.dollar(5);

        assertEquals(new Money(10, "USD"), five.times(2));
        assertEquals(new Money(15, "USD"), five.times(3));
    }

    @Test
    void identityRate() {
        assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    void currency() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    @Test
    void equality() {
        assertEquals(new Money(5, "USD"), new Money(5, "USD"));
        assertNotEquals(new Money(5, "USD"), new Money(6, "USD"));

        assertNotEquals(new Money(5, "USD"), new Money(5, "CHF"));

        assertEquals(Money.dollar(5), Money.dollar(5));
        assertNotEquals(Money.dollar(5), Money.dollar(6));
    }

}