package ru.sberbank;

import org.apache.log4j.Logger;
import ru.sberbank.model.ClientRecords;
import ru.sberbank.model.OrderRecords;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;

public class BatchProcessor {
    private static final Logger logger = Logger.getLogger("trader." + BatchProcessor.class.getSimpleName());

    public void process(Configuration configuration) throws Exception {
        ClientRecords clientRecords = ClientRecords.deserialize(new File(configuration.getClientsFilePath()));
        if(logger.isDebugEnabled()) {
            logger.debug("Loaded " + (clientRecords.getClients() != null ? clientRecords.getClients().size() : 0)  + " client's records");
        }

        OrderRecords orderRecords = OrderRecords.deserialize(new File(configuration.getOrdersFilePath()));
        if(logger.isDebugEnabled()) {
            logger.debug("Loaded " + (orderRecords.getOrders() != null ? orderRecords.getOrders().size() : 0)  + " order's records");
        }

        if(logger.isInfoEnabled()) {
            logger.info("Start order processing ...");
        }

        BatchProcessingStrategy strategy = null;
        try {
            Class clazz = Class.forName(configuration.getBatchProcessorStrategyClass());
            Constructor<?> cons = clazz.getConstructor(ClientRecords.class, OrderRecords.class);
            strategy = (BatchProcessingStrategy) cons.newInstance(clientRecords, orderRecords);
        } catch (Exception e) {
            String errorMessage = "Cannot load batch processor strategy";
            logger.error(errorMessage, e);
            throw new Exception(errorMessage, e);
        }

        if(logger.isDebugEnabled()) {
            logger.debug("Loaded batch processing strategy " + strategy);
        }

        strategy.process();

        if(strategy.getErrors() != null && !strategy.getErrors().isEmpty()) {
            System.err.println("Processing errors:");
            for (String error : strategy.getErrors()) {
                System.err.println(error);
            }
            return;
        }

        ClientRecords result = strategy.getResult();
        if(result == null) {
            String errorMessage = "No results";
            logger.error(errorMessage);
            throw new Exception(errorMessage);
        }
        result.serialize(new File(configuration.getResultFilePath()));

        if(logger.isInfoEnabled()) {
            logger.info("Orders are processed");
            logger.info("Please see results in file " + configuration.getResultFilePath());
        }
    }
}
