package ru.sberbank;

import org.apache.log4j.Logger;

public class Main
{
    private static final Logger logger = Logger.getLogger("trader.main");
    public static void main( String[] args ) throws Exception {
        if(args.length != 3) {
            usage();
            System.exit(-1);
        }

        Configuration configuration = new Configuration(args[0], args[1], args[2], "ru.sberbank.SimpleBatchProcessingStrategy");
        if(logger.isInfoEnabled()) {
            logger.info(configuration);
        }

        new BatchProcessor().process(configuration);
    }

    private static void usage() {
        System.err.println(
                "Usage: " +
                "clients_file orders_file result_file" + "\n" +
                "where" +
                "\tclients_file is file path to clients file" + "\n" +
                "\torders_file is file path to orders file" + "\n" +
                "\tresults_file is file path to result file");
    }
}
