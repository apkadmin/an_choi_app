package com.anchoi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@SpringBootApplication
public class SpringBootAnchoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAnchoiApplication.class, args);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowBackSlash(true);
		firewall.setAllowSemicolon(true);
		firewall.setAllowUrlEncodedDoubleSlash(true);
		firewall.setAllowUrlEncodedPercent(true);
		firewall.setAllowUrlEncodedSlash(true);
		firewall.setAllowUrlEncodedPeriod(true);
		return (web) -> web.httpFirewall(firewall);
	}
}
