package com.javlom3.javabistrot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // risorse accessibili a tutti
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                
                // Pagine pubbliche del ristorante
                .requestMatchers("/", "/home", "/menu", "/prenota").permitAll()
                
                // Pagine di autenticazione - accessibili a tutti
                .requestMatchers("/auth/login", "/auth/login-error").permitAll()
                
                // Aggiunta prenotazioni API - accessibile a chiunque (pubblico)
                .requestMatchers("/api/bookings/addbooking").permitAll()
                
                // Gestione staff - solo MAITRE
                .requestMatchers("/api/users/**").hasRole("MAITRE")
                .requestMatchers("/staff/**").hasRole("MAITRE")
                
                // Gestione menu - solo MAITRE
                .requestMatchers("/menu/manage", "/menu/add", "/menu/update", "/menu/toggle", "/menu/delete").hasRole("MAITRE")
                
                // Gestione prenotazioni - assegnazione camerieri solo MAITRE
                .requestMatchers("/bookings/assign-waiter", "/bookings/remove-waiter").hasRole("MAITRE")
                
                // Gestione prenotazioni (escluso addbooking) - WAITER e MAITRE
                .requestMatchers("/api/bookings/**").hasAnyRole("WAITER", "MAITRE")
                .requestMatchers("/bookings/**").hasAnyRole("WAITER", "MAITRE")
                
                // Tutto il resto richiede autenticazione
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/auth/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        
        return http.build();
    }

}