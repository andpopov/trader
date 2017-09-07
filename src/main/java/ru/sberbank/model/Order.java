package ru.sberbank.model;

public class Order {
    private final String clientName;
    private final Operation operationType;
    private final Stock stock;
    private final long priceOfStock;
    private final long amountOfStocks;

    public Order(String clientName, Operation operationType, Stock stock, long priceOfStock, long amountOfStocks) {
        this.clientName = clientName;
        this.operationType = operationType;
        this.stock = stock;
        this.priceOfStock = priceOfStock;
        this.amountOfStocks = amountOfStocks;
    }

    public String getClientName() {
        return clientName;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public Stock getStock() {
        return stock;
    }

    public long getPriceOfStock() {
        return priceOfStock;
    }

    public long getAmountOfStocks() {
        return amountOfStocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (priceOfStock != order.priceOfStock) return false;
        if (amountOfStocks != order.amountOfStocks) return false;
        if (clientName != null ? !clientName.equals(order.clientName) : order.clientName != null) return false;
        if (operationType != order.operationType) return false;
        return !(stock != null ? !stock.equals(order.stock) : order.stock != null);

    }

    @Override
    public int hashCode() {
        int result = clientName != null ? clientName.hashCode() : 0;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (int) (priceOfStock ^ (priceOfStock >>> 32));
        result = 31 * result + (int) (amountOfStocks ^ (amountOfStocks >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "clientName='" + clientName + '\'' +
                ", operationType=" + operationType +
                ", stock=" + stock +
                ", priceOfStock=" + priceOfStock +
                ", amountOfStocks=" + amountOfStocks +
                '}';
    }
}
