package net.dzale.diezel;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.SQLException;

@SpringBootApplication
@EnableJpaRepositories("net.dzale.diezel.repository")
@PropertySource("classpath:diezel.properties")
public class Application {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		//startH2WebServer();
	}

	private static void startH2WebServer() {
		try {
			// http://halyph.com/blog/2015/01/22/how-to-use-embedded-h2-with-h2-console.html
			Server webServer = Server.createWebServer("-webAllowOthers", "-webPort", "8082").start(); // (4a)
			Server server = Server.createTcpServer("-tcpAllowOthers", "-tcpPort", "9092").start();    // (4b)

		} catch (SQLException ex) {
			log.warn("Error starting H2 embedded web server. Database browser unavailable.", ex);
		}
	}

}
