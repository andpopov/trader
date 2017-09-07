package ru.sberbank.model;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Stores clients
 */
public class ClientRecords {
    private static Logger logger = Logger.getLogger("trader." + ClientRecords.class.getSimpleName());

    /**
     * Clients
     */
    private final List<Client> clients;


    public ClientRecords() {
        clients = new ArrayList<Client>();
    }

    public ClientRecords(final List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    /**
     * Read clients from file
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static ClientRecords deserialize(File file) throws Exception {
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
     * Read clients from Reader's source
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static ClientRecords deserialize(Reader input) throws Exception {
        List<Client> clients = new ArrayList<Client>();

        BufferedReader reader = new BufferedReader(input);
        String line;
        while( (line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            if(tokenizer.countTokens() != 6) {
                String errorMessage = "Wrong file content. Cannot parse line " + line;
                logger.error(errorMessage);
                throw new Exception(errorMessage);
            }
            String clientName = tokenizer.nextToken();
            long balance = Long.valueOf((String) tokenizer.nextElement());
            long amountOfStockA = Long.valueOf((String) tokenizer.nextElement());
            long amountOfStockB = Long.valueOf((String) tokenizer.nextElement());
            long amountOfStockC = Long.valueOf((String) tokenizer.nextElement());
            long amountOfStockD = Long.valueOf((String) tokenizer.nextElement());
            Client client = new Client(clientName, balance);
            client.setStockAmount(new Stock("A"), amountOfStockA);
            client.setStockAmount(new Stock("B"), amountOfStockB);
            client.setStockAmount(new Stock("C"), amountOfStockC);
            client.setStockAmount(new Stock("D"), amountOfStockD);
            clients.add(client);
        }

        return new ClientRecords(clients);
    }

    /**
     * Write clients to file
     *
     * @param file
     * @throws Exception
     */
    public void serialize(File file) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            serialize(new OutputStreamWriter(fos));
        }
    }

    /**
     * Write clients to Writer
     *
     * @param output
     * @throws Exception
     */
    public void serialize(Writer output) throws Exception {
        Writer writer = new BufferedWriter(output);
        StringBuilder builder = new StringBuilder();
        for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
            Client client = it.next();
            builder.append(client.getName()).append('\t').
                    append(client.getBalance()).append('\t').
                    append(client.getStockAmount(new Stock("A"))).append('\t').
                    append(client.getStockAmount(new Stock("B"))).append('\t').
                    append(client.getStockAmount(new Stock("C"))).append('\t').
                    append(client.getStockAmount(new Stock("D"))).append(it.hasNext() ? '\n' : "");
            writer.write(builder.toString());
            builder.setLength(0);
        }
        writer.flush();
    }
}
