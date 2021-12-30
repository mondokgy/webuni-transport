package hu.webuni.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	public static void main(String[] args) {
		
		SpringApplication.run(TransportApplication.class, args);
		
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Start Transport application.");
		log.info("End Transport application.");
	}

}
