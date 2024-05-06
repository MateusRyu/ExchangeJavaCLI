package br.dev.ryu.ExchangeJavaCLI.model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigurationManager {
    String configFilePath;
    File configFile;
    Gson gson;

    public ConfigurationManager(String configFilePath) {
        this.configFilePath = configFilePath;
        this.configFile = new File(configFilePath);
        this.gson = new Gson();
    }

    public ApiConfig getApiConfig() {
        ApiConfig config;
        if (this.configFile.exists()) {
            try {
                Scanner scanner = new Scanner(this.configFile);
                StringBuilder json = new StringBuilder();
                scanner.forEachRemaining(json::append);
                config = gson.fromJson(json.toString(), ApiConfig.class);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("ConfigFileNotExist");
        }
        return config;
    }


    public void setApiConfig(ApiConfig apiConfig) {
        String json = this.gson.toJson(apiConfig);
        try (FileWriter writer = new FileWriter(this.configFilePath)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Could not write the config file");
        }
    }
}
