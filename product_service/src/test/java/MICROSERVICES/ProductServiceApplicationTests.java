package MICROSERVICES;

import MICROSERVICES.dto.ProductRequest;

import MICROSERVICES.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // автоматически настраиваем MockMvc
@Testcontainers
class ProductServiceApplicationTests {
	@Container // junit5 поймет что это монгодб контейнер
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.5"));
	@Autowired//Класс MockMvc предназначен для тестирования контроллеров. Он позволяет тестировать контроллеры без запуска http-сервера.
	private MockMvc mockMvc; // Для имитации HTTP-запросов потребуется экземпляр MockMvc

	@Autowired
	private ProductRepository productRepository;

	static {
		mongoDBContainer.start();
	}
	// objectMapper данный метод из библиотеки jackson помогает конвертировать Pojo объекты в JSON, ObjectMapper поможет преобразить объект ProductRequest в JSON строку
	// POJO (англ. Plain Old Java Object) — «старый добрый Java-объект», простой Java-объект, не унаследованный от какого-то специфического объекта и не ...
	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void shouldCreateProduct() throws Exception { // метод для фиктивного тестового создания продукта
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest); // преобразуем productRequest в JSON строку для метода .content()
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product") // отправляем фиктивный пост запрос на создание продукта
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString)) // метод .content() проводит только строку JSON
				.andExpect(status().isCreated()); // добавляем метод andExpect где говорим что ожидаем увидеть что все создалось успешно status().isCreated()
//		Assertions.assertEquals(1, productRepository.findAll().size());  // покажет тру, если при тестировании создался один объект в дб, данный метод аналогичен методу ниже Assertions.assertTrue(productRepository.findAll().size() == 1)
//		Assertions.assertTrue(productRepository.findAll().size() == 1); // данный метод(Assertions.assertTrue) говорит выведи тру если productRepository.findAll().size() == 1
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.url", mongoDBContainer::getReplicaSetUrl); // смотри курс 32м:22с
	}

	private ProductRequest getProductRequest(){ // создадим копию нашего продуктового запроса для тестов
		return ProductRequest.builder()
				.name("Iphone 10 pro max")
				.description("Iphone 10 pro max description....")
				.price(BigDecimal.valueOf(79900)) // переведем из BigDecimal в integer
				.build();
	}

}
