package hu.webuni.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.transport.service.InitDbService;

@SpringBootApplication
public class TransportApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger("LOG");
	
	@Value("${transport.db.init:false}")
	boolean dbInit;

	@Autowired
	InitDbService initDbService;
	
	public static void main(String[] args) {
		
		SpringApplication.run(TransportApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Start Transport application.");
		
		if(dbInit) {
			log.info("DB init property is true");
			initDbService.clearDb();
			initDbService.insertTestData();
			log.info("DB init success.");
		}else {
			log.info("DB init property is false");
		}
		
		log.info("End Transport application.");
	}

}
