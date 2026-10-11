package com.cibertec.msresolucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsResolucionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsResolucionApplication.class, args);
	}
}
