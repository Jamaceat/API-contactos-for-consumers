package co.com.cetus.learning.crudjpa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Configuration
@Slf4j
public class SpringSecurityConfig {

        private UserDetailsService userDetailsService;

        AuthenticationManager auth;

        @Bean
        public AuthenticationManager authManager(AuthenticationConfiguration authconf) throws Exception {
                this.auth = authconf.getAuthenticationManager();
                return this.auth;

        }

        SpringSecurityConfig(UserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
        }

        @Autowired
        public void configurerGlobal(AuthenticationManagerBuilder build,
                        BCryptPasswordEncoder passwordEncoder)
                        throws Exception {
                log.info("Apunto de usar el build");

                build.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);

        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                log.info("Autorizando");
                http.csrf(cus -> cus.disable())
                                .authorizeHttpRequests(aut -> aut
                                                .requestMatchers(HttpMethod.POST, "/contactos")
                                                .hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/contactos/**")
                                                .hasAnyRole("ADMIN", "OPERADOR")
                                                .requestMatchers("/contactos").authenticated()
                                                .anyRequest().permitAll())
                                .addFilter(new JWTAuthorizationFilter(this.auth));
                SecurityFilterChain filterChain = http.build();
                log.info("-------------------------------------------------------------");
                for (Filter fil : filterChain.getFilters()) {
                        log.info("Filtro " + fil);
                }
                log.info("-------------------------------------------------------------");
                return filterChain;
        }

}
