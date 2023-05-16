package MICROSERVICES.service;


import MICROSERVICES.dto.ProductRequest;
import MICROSERVICES.dto.ProductResponse;
import MICROSERVICES.model.Product;
import MICROSERVICES.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j // позволяет записывать действия в логи
@Service
@RequiredArgsConstructor // генерирует конструктор с нужными полями
public class ProductService {
    private final ProductRepository productRepository;

//    public ProductService(ProductRepository productRepository){ /// что бы не делать каждый раз такой конструктор, мы вызываем аннотацию @RequiredArgsConstructor
//        this.productRepository = productRepository;
//    }
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder() // строим объект класса продукт с помощью данных полученных с клиента из productRequest
                .name(productRequest.getName()) // получаем имя
                .description(productRequest.getDescription()) // получаем описание
                .price(productRequest.getPrice()) // получаем цену
                .build(); // в конце вызываем метод .build() что бы построить объект с полученными данными
        productRepository.save(product);// сохраняем только что сделанный объект продукта в нашу базу данных
        log.info("Product {} is saved", product.getId()); // мы получим продукт айди и он подставится вместо фигурных скобок
    }

        // причина по которой мы возвращаем продукт не напрямую, а через создание нового объекта ProductResponse,
        // заключается в том что это считается хорошей практикой, когда ты не подвергаешь взаимодействию с внешним миром свой основной объект Product

    public List<ProductResponse> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::justProductToProductForResponse).toList(); // и отавправляем в продуктКонтоллер список из ProductResponse
    }
    private ProductResponse justProductToProductForResponse(Product product){ // преобразовываем объект продукта в объект ProductResponse
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
