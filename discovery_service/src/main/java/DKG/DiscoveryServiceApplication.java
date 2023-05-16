package DKG;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//Eureka Server — это приложение, которое содержит информацию обо всех клиентских сервисных приложениях.
//Каждый микросервис регистрируется на сервере Eureka, и Eureka знает все клиентские приложения, работающие на каждом порту и IP-адресе.
//Eureka Server также известен как Discovery Server.


//Если простыми словами, то — это сервер имен или реестр сервисов. Обязанность — давать имена каждому микросервису.


//Регистрирует микросервисы и отдает их ip другим микросервисам.
//
//Таким образом, каждый сервис регистрируется в Eureka и отправляет эхо-запрос серверу Eureka, чтобы сообщить, что он активен.
//Для этого сервис должен быть помечен как @EnableEurekaClient, а сервер @EnableEurekaServer.

// При подключении микросервисов к Discovery Server они узнают где находятся другие микросервисы (в каком реестре, IP и URL)
// и создают копии данных о других микросервисах, и даже если отключиться от Discovery Server то все равно они смогут взаимодействовать между собой,
// так как у них будет копия данных друг о друге


@SpringBootApplication //Аннотация @SpringBootApplication включает автоконфигурацию , сканирование компонентов и конфигурацию Spring Boot.
@EnableEurekaServer // данная аннотация указывает на то что это EurekaServer
// Eureka Server — это service discovery (обнаружение сервисов) для ваших микросервисов. Клиентские приложения могут самостоятельно регистрироваться в нем, а другие микросервисы могут обращаться к Eureka Server для поиска необходимых им микросервисов
public class DiscoveryServiceApplication {
    public static void main(String[] args) {
        {
            SpringApplication.run(DiscoveryServiceApplication.class, args);
        }
    }
}
