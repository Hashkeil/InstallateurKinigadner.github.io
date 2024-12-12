package at.DiplomProjekt.Installateur.config;

import at.DiplomProjekt.Installateur.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
@EnableWebMvc
public class WebSecurityConfig implements WebMvcConfigurer {

    private final CustomUserDetailsService customUserDetailsService;
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, DataSource dataSource) {
        this.customUserDetailsService = customUserDetailsService;
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);
        return userDetailsManager;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);

    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/","classpath:/java/" };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/home", "/register", "/static/**", "/templates/**", "/webjars/**", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Definiert die benutzerdefinierte Anmeldeseite
                        .defaultSuccessUrl("/dashboard", true) // Leitet nach erfolgreicher Anmeldung auf das Dashboard um
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout") // Leitet nach erfolgreicher Abmeldung auf die Anmeldeseite um
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret")
                        .userDetailsService(customUserDetailsService)
                );

        return http.build();
    }}


