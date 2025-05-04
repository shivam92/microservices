package com.smartmind.gateway;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	
	/*
	 * this routes is use full when api gatway expose diff uri path instead of original MS
	 * client will call via gateway/<api uri path> then it will rewrite path with actual API uri path of MS
	 * gateway/accounts/ will change to lb://ACCOUNTS/accounts
	 * lb represent the load balance url 
	 * */
	@Bean
	public RouteLocator apiGatewayCustomRoutes(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
						.route(p -> p
								.path("/gateway/accounts/**")
								.filters( f -> f.rewritePath("/gateway/accounts/(?<segment>.*)","/${segment}")
										.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://ACCOUNTS"))
					.route(p -> p
							.path("/gateway/loans/**")
							.filters( f -> f.rewritePath("/gateway/loans/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://LOANS"))
					.route(p -> p
							.path("/gateway/cards/**")
							.filters( f -> f.rewritePath("/gateway/cards/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://CARDS")).build();


	}

}
