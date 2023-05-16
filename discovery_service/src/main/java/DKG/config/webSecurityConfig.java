package DKG.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableWebSecurity
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

    //Стандартной и наиболее распространенной реализацией является DaoAuthenticationProvider, который извлекает сведения о пользователе из простого пользовательского DAO,
    // доступного только для чтения, UserDetailsService.
    // Эта служба сведений о пользователе имеет доступ только к имени пользователя, чтобы получить полную сущность пользователя, что достаточно для большинства сценариев.

    @Value("${eureka.username}") // application.properties
    public String username;

    @Value("${eureka.password}")// application.properties
    public String password;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // в учебных целях, нарочно не кодируем пароль и оставляем его открытым
                .withUser(username).password(password).authorities("USER"); // установили пароль и имя пользователя для того что бы зайти в дискавери сервер
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic(); // установили защиту и теперь без авторизации нельзя зайти в дискавери сервер и управлять нашими микросервисами
    }
}
