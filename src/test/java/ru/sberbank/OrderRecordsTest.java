package ru.sberbank;

import org.junit.Test;
import ru.sberbank.model.Operation;
import ru.sberbank.model.Order;
import ru.sberbank.model.OrderRecords;
import ru.sberbank.model.Stock;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests of OrderRecords class
 */
public class OrderRecordsTest {
    /**
     * Test for deserialization from string
     * @throws Exception
     */
    @Test
    public void readFromString() throws Exception {
        String content =
                "C8\tb\tC\t15\t4\n" +
                "C2\ts\tA\t14\t5";

        OrderRecords orderRecords = OrderRecords.deserialize(new StringReader(content));
        assertNotNull(orderRecords != null);
        assertEquals("Wrong amount of records", 2, orderRecords.getOrders().size());

        Order order1 = orderRecords.getOrders().get(0);
        assertEquals("Wrong name value", "C8", order1.getClientName());
        assertEquals("Wrong operation", Operation.BUY, order1.getOperationType());
        assertEquals("Wrong stock", new Stock("C"), order1.getStock());
        assertEquals("Wrong stock price", 15L, order1.getPriceOfStock());
        assertEquals("Wrong amount of stocks", 4L, order1.getAmountOfStocks());

        Order order2 = orderRecords.getOrders().get(1);
        assertEquals("Wrong name value", "C2", order2.getClientName());
        assertEquals("Wrong operation", Operation.SALE, order2.getOperationType());
        assertEquals("Wrong stock", new Stock("A"), order2.getStock());
        assertEquals("Wrong stock price", 14L, order2.getPriceOfStock());
        assertEquals("Wrong amount of stocks", 5L, order2.getAmountOfStocks());
    }
}
