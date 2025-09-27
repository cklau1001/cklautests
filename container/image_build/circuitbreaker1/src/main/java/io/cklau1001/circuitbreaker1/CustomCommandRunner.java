package io.cklau1001.circuitbreaker1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Component
public class CustomCommandRunner implements CommandLineRunner {

    @Value("${app.username:NO_USERNAME}")
    private String appUsername;

    @Value("${app.password:NO_PASSWORD}")
    private String appPassword;

    @Override
    public void run(String[] args) throws IOException {

        showEnvironment();
        // readSecretByFileRead();
        readSecretBySpringCloud();
    }

    public void showEnvironment() {

        System.out.println("========= Listing environment variables =================");
        Map<String, String> envVars = System.getenv();
        envVars.forEach((key, value) -> {
            System.out.printf("%s=%s%n", key, value);
        });

        System.out.println("========= Listing System properties =================");
        Properties p = System.getProperties();
        p.list(System.out);

    }

    public void readSecretBySpringCloud() {

        System.out.println("========= Reading secrets by Spring Cloud =================");

        System.out.println("Username=" + appUsername);
        System.out.println("Password=" + appPassword);
    }

    public void readSecretByFileRead() throws IOException {

        if (System.getenv("IN_KUBE") == null || !System.getenv("IN_KUBE").equals("enabled")) {
            System.out.println("[readSecret]: IN_KUBE not set or not enabled - SKIP");
            return;
        }
		/*
		https://www.baeldung.com/java-try-with-resources
		 */

        System.out.println("========= Reading secrets by File Read =================");
        final FileReader userReader = new FileReader("/etc/secret/app.username");
        final FileReader passwordReader = new FileReader("/etc/secret/app.password");

        try (userReader; passwordReader) {
            BufferedReader bufferedReader = new BufferedReader(userReader);
            String username = bufferedReader.readLine();
            System.out.println("username=" + username);
            bufferedReader = new BufferedReader(passwordReader);
            String password = bufferedReader.readLine();
            System.out.println("password=" + password);

        }
    }

}
