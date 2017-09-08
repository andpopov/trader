package ru.sberbank;

import org.junit.Test;
import ru.sberbank.model.Client;
import ru.sberbank.model.ClientRecords;
import ru.sberbank.model.Stock;

import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests of ClientRecord class
 */
public class ClientRecordsTest {
    /**
     * Test for deserialization from string
     * @throws Exception
     */
    @Test
    public void readFromString() throws Exception {
        String content =
                "C1\t1000\t130\t240\t760\t320\n" +
                "C2\t4350\t370\t120\t950\t560";

        ClientRecords clientRecords = ClientRecords.deserialize(new StringReader(content));
        assertNotNull(clientRecords != null);
        assertEquals("Wrong amount of records", 2, clientRecords.getClients().size());

        Client client1 = clientRecords.getClients().get(0);
        assertEquals("Wrong name value", "C1", client1.getName());
        assertEquals("Wrong balance value", 1000L, client1.getBalance());
        assertEquals("Wrong stock A amount", Long.valueOf(130L), client1.getStockAmount(new Stock("A")));
        assertEquals("Wrong stock B amount", Long.valueOf(240L), client1.getStockAmount(new Stock("B")));
        assertEquals("Wrong stock C amount", Long.valueOf(760L), client1.getStockAmount(new Stock("C")));
        assertEquals("Wrong stock D amount, Long.valueOf(320L), client1.getStockAmount(new Stock("D")));

        Client client2 = clientRecords.getClients().get(1);
        assertEquals("Wrong name value", "C2", client2.getName());
        assertEquals("Wrong balance value", 4350L, client2.getBalance());
        assertEquals("Wrong stock A amount", Long.valueOf(370L), client2.getStockAmount(new Stock("A")));
        assertEquals("Wrong stock B amount", Long.valueOf(120L), client2.getStockAmount(new Stock("B")));
        assertEquals("Wrong stock C amount", Long.valueOf(950L), client2.getStockAmount(new Stock("C")));
        assertEquals("Wrong stock D amount", Long.valueOf(560L), client2.getStockAmount(new Stock("D")));
    }

    /**
     * Test for serialization to string
     * @throws Exception
     */
    @Test
    public void writeToString() throws Exception {
        String content =
                "C1\t1000\t130\t240\t760\t320\n" +
                "C2\t4350\t370\t120\t950\t560";

        ClientRecords clientRecords = ClientRecords.deserialize(new StringReader(content));
        assertNotNull(clientRecords != null);
        assertEquals("Wrong amount of records", 2, clientRecords.getClients().size());

        StringWriter stringWriter = new StringWriter();
        clientRecords.serialize(stringWriter);

        assertEquals(content, stringWriter.toString());
    }
}
