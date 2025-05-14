package cz.zaf.validator.ws;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZafApplication {

	public static void main(String[] args) {
		var app = new SpringApplication(ZafApplication.class);
		var props = new HashMap<String, Object>();
		props.put("spring.config.name", "zaf-ws");
		app.setDefaultProperties(props);
		app.run(args);
	}

}
