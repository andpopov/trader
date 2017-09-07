package ru.sberbank;

public class Configuration {
    private String clientsFilePath;
    private String ordersFilePath;
    private String resultFilePath;
    private String batchProcessorStrategyClass;

    public Configuration(String clientsFilePath, String ordersFilePath, String resultFilePath, String batchProcessorStrategyClass) {
        this.clientsFilePath = clientsFilePath;
        this.ordersFilePath = ordersFilePath;
        this.resultFilePath = resultFilePath;
        this.batchProcessorStrategyClass = batchProcessorStrategyClass;
    }

    public String getClientsFilePath() {
        return clientsFilePath;
    }

    public void setClientsFilePath(String clientsFilePath) {
        this.clientsFilePath = clientsFilePath;
    }

    public String getOrdersFilePath() {
        return ordersFilePath;
    }

    public void setOrdersFilePath(String ordersFilePath) {
        this.ordersFilePath = ordersFilePath;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }

    public void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public String getBatchProcessorStrategyClass() {
        return batchProcessorStrategyClass;
    }

    public void setBatchProcessorStrategyClass(String batchProcessorStrategyClass) {
        this.batchProcessorStrategyClass = batchProcessorStrategyClass;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "clientsFilePath='" + clientsFilePath + '\'' +
                ", ordersFilePath='" + ordersFilePath + '\'' +
                ", resultFilePath='" + resultFilePath + '\'' +
                ", batchProcessorStrategyClass='" + batchProcessorStrategyClass + '\'' +
                '}';
    }
}
