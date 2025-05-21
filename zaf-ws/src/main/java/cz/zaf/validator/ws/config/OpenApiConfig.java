package cz.zaf.validator.ws.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class OpenApiConfig {
	
	@Value("${zaf.server.url}:null")
	String serverUrl;

	@Value("${zaf.server.name}:null")
	String serverName;

	@Bean
	public OpenAPI customOpenAPI() {
		OpenAPI result = new OpenAPI().info(new Info().title("Another Spring Boot API").version("1.0").description("Detailed API documentation"));
		if(StringUtils.isNotEmpty(serverUrl)) {
			result.addServersItem(new Server().url(serverUrl).description(StringUtils.isEmpty(serverName) ? "Main server" : serverName));
		}
		return result;
	}
}
