package com.example.demobook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.firewall.HttpFirewall;
//import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); // esto que pega no es necesario, vamos a sobreescribir
        http
                .authorizeRequests()
                //.antMatchers("/api/books").permitAll() // solo permite el entrypoint especificado sin logearse
                //.antMatchers("/api/books").hasRole("USER")
                //.antMatchers("").hasAnyAuthority()
                //.antMatchers("").hasAnyRole()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic(); // method chaining concatenacion de metodo: obj.metodo1().metodo2()
    }


//    @Bean
//    public HttpFirewall looseHttpFirewall(){
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        firewall.setAllowSemicolon(true); //permite punto y coma
//        return firewall;
//    }

    // Ignora la seguridad en el perfil de test
    @Bean
    @Profile("test")
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // enable in memory based authentication with a user named
        // user and admin
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("password")).roles("USER", "ADMIN");
        // otro user ADMIN
        //.and()
        //.withUser("admin").password("password").roles("USER", "ADMIN");
    }

}
