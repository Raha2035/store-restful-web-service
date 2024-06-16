package com.msho.store.rest.sebservice.storerestfulwebservice.config;

import com.msho.store.rest.sebservice.storerestfulwebservice.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/users/create-user")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
            requests.requestMatchers("/users/create-user").permitAll()
                    /*.requestMatchers("/users/all-users").authenticated()*/
                    .anyRequest().authenticated();
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain)http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


/*
        "/users/all-users",
        "/users/get-user/{id}",
        "/users/delete-user/{id}",
        "/users/{userId}/all-orders",
        "/users/{userId}/create-order",
        "/users/{userId}/delete-order/{id}",
        "/users/{userId}/update-order/{id}",
        "/categories/all-categories",
        "/categories/get-category/{id}",
        "/categories/create-category",
        "/categories/delete-category/{id}",
        "/categories/update-category/{id}",
        "/categories/{categoryId}/products/create-product",
        "/categories/{categoryId}/products/all-products",
        "/categories/{categoryId}/products/get-product/{productId}",
        "/categories/{categoryId}/products/update-product/{productId}",
        "/categories/{categoryId}/products/delete-product/{productId}",
        "/categories/{categoryId}/products/delete-all-products",
        "/stores/all-stores",
        "/stores/get-store/{id}",
        "/stores/create-store",
        "/stores/delete-store/{id}",
        "/stores/update-store/{id}",
        "/stores/{id}/all-products",
        "/stores/{id}/create-products",
        "/stores/{storeId}/products/delete-product/{productId}",
        "/stores/{storeId}/products/delete-all-products")
*/