package ru.sberbank;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.sberbank.model.Client;
import ru.sberbank.model.ClientRecords;
import ru.sberbank.model.Stock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BatchProcessorTest {
    private static Path tmpDir = null;
    private static Path clientsPath = null;
    private static Path ordersPath = null;
    private static Path resultPath = null;

    @BeforeClass
    public static void init() throws IOException {
        tmpDir = Files.createTempDirectory("tmp");
        clientsPath = Files.createTempFile(tmpDir, "clients", "");
        ordersPath = Files.createTempFile(tmpDir, "orders", "");
        resultPath = Files.createTempFile(tmpDir, "result", "");
    }

    @AfterClass
    public static void clear() throws IOException {
        if(tmpDir != null) {
            Files.deleteIfExists(clientsPath);
            Files.deleteIfExists(ordersPath);
            Files.deleteIfExists(resultPath);
            Files.deleteIfExists(tmpDir);
        }
    }

    @Test
    public void saleTest() throws Exception {
        writeToFile(clientsPath,
                "C1\t1000\t100\t200\t300\t400\n" +
                "C2\t4000\t100\t200\t300\t400"
        );

        writeToFile(ordersPath,
                "C1\ts\tA\t10\t1\n" +
                "C1\ts\tB\t20\t2\n" +
                "C1\ts\tC\t30\t3\n" +
                "C1\ts\tD\t40\t4\n"
        );

        Configuration configuration = new Configuration(
                clientsPath.toFile().getAbsolutePath(),
                ordersPath.toFile().getAbsolutePath(),
                resultPath.toFile().getAbsolutePath(),
                "ru.sberbank.SimpleBatchProcessingStrategy"
        );

        new BatchProcessor().process(configuration);

        ClientRecords resultClientRecords = ClientRecords.deserialize(resultPath.toFile());
        assertNotNull(resultClientRecords);
        assertEquals(2, resultClientRecords.getClients().size());
        Client client = resultClientRecords.getClients().get(0);
        assertEquals("Wrong client's name", "C1", client.getName());
        assertEquals("Wrong client's balance", 1000L + 10*1L + 20*2 + 30*3 + 40*4, client.getBalance());
        assertEquals("Wrong client's stock amount for A", 100 - 1, (long)client.getStockAmount(new Stock("A")));
        assertEquals("Wrong client's stock amount for B", 200 - 2, (long)client.getStockAmount(new Stock("B")));
        assertEquals("Wrong client's stock amount for C", 300 - 3, (long)client.getStockAmount(new Stock("C")));
        assertEquals("Wrong client's stock amount for D", 400 - 4, (long)client.getStockAmount(new Stock("D")));
    }

    @Test
    public void buyTest() throws Exception {
        writeToFile(clientsPath,
                "C1\t1000\t100\t200\t300\t400\n" +
                "C2\t4000\t100\t200\t300\t400"
        );

        writeToFile(ordersPath,
                "C1\tb\tA\t10\t1\n" +
                "C1\tb\tB\t20\t2\n" +
                "C1\tb\tC\t30\t3\n" +
                "C1\tb\tD\t40\t4\n"
        );

        Configuration configuration = new Configuration(
                clientsPath.toFile().getAbsolutePath(),
                ordersPath.toFile().getAbsolutePath(),
                resultPath.toFile().getAbsolutePath(),
                "ru.sberbank.SimpleBatchProcessingStrategy"
        );

        new BatchProcessor().process(configuration);

        ClientRecords resultClientRecords = ClientRecords.deserialize(resultPath.toFile());
        assertNotNull(resultClientRecords);
        assertEquals(2, resultClientRecords.getClients().size());
        Client client = resultClientRecords.getClients().get(0);
        assertEquals("Wrong client's name", "C1", client.getName());
        assertEquals("Wrong client's balance", 1000L - 10*1L - 20*2 - 30*3 - 40*4, client.getBalance());
        assertEquals("Wrong client's stock amount for A", 100 + 1, (long)client.getStockAmount(new Stock("A")));
        assertEquals("Wrong client's stock amount for B", 200 + 2, (long)client.getStockAmount(new Stock("B")));
        assertEquals("Wrong client's stock amount for C", 300 + 3, (long)client.getStockAmount(new Stock("C")));
        assertEquals("Wrong client's stock amount for D", 400 + 4, (long)client.getStockAmount(new Stock("D")));
    }

    private void writeToFile(Path path, String str) throws IOException {
        Files.write(path, str.getBytes());
    }
}
