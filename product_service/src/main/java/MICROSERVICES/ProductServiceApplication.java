package MICROSERVICES;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//Eureka Server — это приложение, которое содержит информацию обо всех клиентских сервисных приложениях.
//Каждый микросервис регистрируется на сервере Eureka, и Eureka знает все клиентские приложения, работающие на каждом порту и IP-адресе.
//Eureka Server также известен как Discovery Server.


//Если простыми словами, то — это сервер имен или реестр сервисов. Обязанность — давать имена каждому микросервису.


//Регистрирует микросервисы и отдает их ip другим микросервисам.
//
//Таким образом, каждый сервис регистрируется в Eureka и отправляет эхо-запрос серверу Eureka, чтобы сообщить, что он активен.
//Для этого сервис должен быть помечен как @EnableEurekaClient, а сервер @EnableEurekaServer.

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
