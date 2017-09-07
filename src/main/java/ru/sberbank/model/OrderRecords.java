package ru.sberbank.model;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Stores orders
 */
public class OrderRecords {
    private static Logger logger = Logger.getLogger("trader." + OrderRecords.class.getSimpleName());

    /**
     * Orders
     */
    private final List<Order> orders;


    public OrderRecords() {
        orders = new ArrayList<Order>();
    }

    public OrderRecords(final List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Read orders from file
     * @param file
     * @return
     * @throws Exception
     */
    public static OrderRecords deserialize(File file) throws Exception {
        if(!file.exists()) {
            String errorMessage = "Cannot read file: file " + file.getAbsolutePath() + " does not exists";
            logger.error(errorMessage);
            throw new Exception(errorMessage);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            return deserialize(new InputStreamReader(fis));
        }
    }

    /**
     * Read orders from Reader's source
     * @param input
     * @return
     * @throws Exception
     */
    public static OrderRecords deserialize(Reader input) throws Exception {
        List<Order> orders = new ArrayList<Order>();

        BufferedReader reader = new BufferedReader(input);
        String line;
        while( (line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            if(tokenizer.countTokens() != 5) {
                String errorMessage = "Wrong file content. Cannot parse line " + line;
                logger.error(errorMessage);
                throw new Exception(errorMessage);
            }
            String clientName = tokenizer.nextToken();
            Operation operation = Operation.parse(tokenizer.nextToken());
            Stock stock = new Stock(tokenizer.nextToken());
            long priceOfStock = Long.valueOf((String) tokenizer.nextElement());
            long amountOfStocks = Long.valueOf((String) tokenizer.nextElement());
            orders.add(new Order(clientName, operation, stock, priceOfStock, amountOfStocks));
        }

        return new OrderRecords(orders);
    }
}
