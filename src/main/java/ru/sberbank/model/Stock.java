package ru.sberbank.model;

/**
 * Stock
 */
public class Stock {
    /**
     * Name of stock
     */
    private final String name;

    public Stock(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        return !(name != null ? !name.equals(stock.name) : stock.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                '}';
    }
}
