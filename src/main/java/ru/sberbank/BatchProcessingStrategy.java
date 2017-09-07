package ru.sberbank;

import ru.sberbank.model.ClientRecords;
import ru.sberbank.model.OrderRecords;

import java.util.ArrayList;
import java.util.List;

/**
 * Processing strategy (abstract class)
 */
abstract public class BatchProcessingStrategy {
    /**
     * Incoming clients's set
     */
    protected ClientRecords clientRecords;

    /**
     * Incoming orders's set
     */
    protected OrderRecords orderRecords;

    /**
     * Processing errors
     */
    protected final List<String> errors = new ArrayList<>();

    public BatchProcessingStrategy(ClientRecords clientRecords, OrderRecords orderRecords) {
        this.clientRecords = clientRecords;
        this.orderRecords = orderRecords;
    }

    /**
     * Processing algorithm
     */
    abstract public void process();

    /**
     * Processing errros getter
     * @return error list
     */
    abstract public List<String> getErrors();

    /**
     * Resulting set of clients after processing of orders
     * @return
     */
    abstract public ClientRecords getResult();
}
