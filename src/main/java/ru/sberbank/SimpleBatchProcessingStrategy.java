package ru.sberbank;

import org.apache.log4j.Logger;
import ru.sberbank.model.Client;
import ru.sberbank.model.ClientRecords;
import ru.sberbank.model.Order;
import ru.sberbank.model.OrderRecords;

import java.util.List;

/**
 * Simple processing strategy
 */
public class SimpleBatchProcessingStrategy extends BatchProcessingStrategy {
    private static final Logger logger = Logger.getLogger("trader." + SimpleBatchProcessingStrategy.class.getSimpleName());

    public SimpleBatchProcessingStrategy(ClientRecords clientRecords, OrderRecords orderRecords) {
        super(clientRecords, orderRecords);
    }

    @Override
    public void process() {
        for (final Order order : orderRecords.getOrders()) {
            if(logger.isDebugEnabled()) {
                logger.debug("Processing order " + order);
            }

            if(processOrder(order)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Order " + order + " - processed successfully");
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Order " + order + " processing error");
                }
            }
        }
    }

    private boolean processOrder(Order order) {
        for (Client client : clientRecords.getClients()) {
            if( client.getName().equals(order.getClientName())) {
                Long clientStockAmount = client.getStockAmount(order.getStock());
                long totalOrderPrice = order.getPriceOfStock() * order.getAmountOfStocks();
                switch (order.getOperationType()) {
                    case BUY:
                        if(client.getBalance() >= totalOrderPrice) {
                            client.setStockAmount(order.getStock(), clientStockAmount + order.getAmountOfStocks());
                            client.setBalance(client.getBalance() - totalOrderPrice);
                            return true;
                        } else {
                            errors.add("Order " + order + " cannot be processed because low balance of client " + client.getBalance());
                            return false;
                        }

                    case SALE:
                        if(clientStockAmount >= order.getAmountOfStocks()) {
                            client.setStockAmount(order.getStock(), clientStockAmount - order.getAmountOfStocks());
                            client.setBalance(client.getBalance() + totalOrderPrice);
                            return true;
                        }  else {
                            errors.add("Order " + order + " cannot be processed because low stock's amount(" + clientStockAmount + ") of client");
                            return false;
                        }
                }
            }
        }
        errors.add("Order " + order + " cannot be processed because client was not found");
        return false;
    }

    @Override
    public ClientRecords getResult() {
        return clientRecords;
    }

    @Override
    public List<String> getErrors() {
        return null;
    }

    @Override
    public String toString() {
        return "SimpleBatchProcessingStrategy{}";
    }
}
