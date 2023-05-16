package DKG.inventory_service;

import DKG.inventory_service.model.Inventory;
import DKG.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

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
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean // Чтобы сообщить Spring Boot о нашем интерфейсе commandlineRunner, мы можем либо реализовать его и добавить аннотацию @Component над классом, либо создать его bean-компонент с помощью @bean.
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){ // CommandLineRunner - это функциональный интерфейс Spring Boot, который используется для запуска кода при запуске приложения.
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setQuantity(100);
			inventory.setSkuCode("APPLE_IPHONE_13");

			Inventory inventory1 = new Inventory();
			inventory1.setQuantity(0);
			inventory1.setSkuCode("APPLE_IPHONE_10_RED");

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
