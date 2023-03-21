package com.commonda;

import com.commonda.model.User;
import com.commonda.service.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(UserClient.class);

    @Autowired
    UserClient client;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Main.class)
                .web(WebApplicationType.NONE)
                .run(args);

        LOG.info("Application is successfully started!");
    }

    private static boolean isStartup() {
        return Main.class.getName().equals(System.getProperty("sun.java.command")) || Main.class.getName().equals(System.getProperty("exec.mainClass"));
    }

    @Override
    public void run(String... args) throws Exception {
        if (isStartup()) {
            List<User> results = client.getUsers();
            results.stream().map(user -> "%s, %s".formatted(user.getFirstName(), user.getLastName())).forEach(System.out::println);
        } else {
            LOG.info("This is not main entry point!");
        }
    }

}
