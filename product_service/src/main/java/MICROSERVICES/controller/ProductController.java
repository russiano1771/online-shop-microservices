package MICROSERVICES.controller;

import MICROSERVICES.dto.ProductRequest;
import MICROSERVICES.dto.ProductResponse;
import MICROSERVICES.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor// генерирует конструктор с нужными полями
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HttpStatus.CREATED указывает на то, что запрос был выполнен успешно и привел к созданию ресурса.
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
        // причина по которой мы возвращаем продукт не напрямую, а через создание нового объекта ProductResponse,
        // заключается в том что это считается хорошей практикой, когда ты не подвергаешь взаимодействию с внешним миром свой основной объект Product
        public List<ProductResponse> getAllProduct(){
        return productService.getAllProduct();
    }

    }

