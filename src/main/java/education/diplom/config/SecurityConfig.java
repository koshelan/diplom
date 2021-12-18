package education.diplom.config;

import education.diplom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
private final UserRepository userRepository;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var users = userRepository.findAll();
        System.out.println(users);
        users.stream().forEach(user -> {
            try {
                auth
                        .inMemoryAuthentication()
                        .withUser(user.getLogin())
                        .password(user.getPassword())
                        .roles(String.valueOf(user.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http//.formLogin().and()
                .csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST,"/file").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT,"/file").permitAll()
                .and()
                .authorizeRequests().anyRequest().permitAll()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
//                        .and()
//                .authorizeRequests().antMatchers("/file").permitAll();
//                .and()
//                .authorizeRequests().antMatchers("/logout").authenticated()
//                .and()
//                .authorizeRequests().anyRequest().permitAll();
    }
}
