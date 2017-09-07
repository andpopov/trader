package ru.sberbank.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Client class
 */
public class Client {
    /**
     * Name of client
     */
    private final String name;

    /**
     * Client's balance
     */
    private long balance;

    /**
     * Balance currency
     */
    private Currency currency;

    /**
     * Map of stock to amount of stock the same type
     */
    private final Map<Stock, Long> stockAmountMap = new HashMap<>();

    public Client(String name, long balance) {
        this.name = name;
        this.balance = balance;
        this.currency = Currency.DOLLAR;
    }

    public Client(String name, long balance, Currency currency) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Map<Stock, Long> getStockAmountMap() {
        return stockAmountMap;
    }

    public void setStockAmount(Stock stock, long amount) {
        stockAmountMap.put(stock, amount);
    }

    public Long getStockAmount(Stock stock) {
        return stockAmountMap.get(stock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return !(name != null ? !name.equals(client.name) : client.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                ", stockAmountMap=" + stockAmountMap +
                '}';
    }
}
