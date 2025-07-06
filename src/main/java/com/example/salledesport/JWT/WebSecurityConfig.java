package com.example.salledesport.JWT;

import com.example.salledesport.services.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private AuthEntryPointJwt unauthorizedhandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsServiceImp);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
        throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedhandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()

                .requestMatchers("/api/auth/**").permitAll()

                //Client
                .requestMatchers("/addClient/**").permitAll()
                .requestMatchers("/saveClient/**").permitAll()
                .requestMatchers("/allClient/**").permitAll()
                .requestMatchers("deleteClient/{id}/**").permitAll()
                .requestMatchers("editClient/{id}/**").permitAll()
                .requestMatchers("updateClient/{id}/**").permitAll()


                //Abonnement
                .requestMatchers("/allAbonnement/**").permitAll()
                .requestMatchers("/addAbonnement/**").permitAll()
                .requestMatchers("/saveAbonnement/**").permitAll()
                .requestMatchers("/updateAbonnement/{id}/**").permitAll()
                .requestMatchers("/editAbonnement/{id}/**").permitAll()
                .requestMatchers("/deleteAbonnement/{id}/**").permitAll()
                .requestMatchers("/new/{abonnementId}/**").permitAll()

                //Subscribe
                .requestMatchers("/addSubscribe/**").permitAll()
                .requestMatchers("/my_subscriptions/**").permitAll()
                .requestMatchers("/{id}/**").permitAll()
                .requestMatchers("/delete/{id}/**").permitAll()

                .requestMatchers("/auth/**").permitAll()



                .requestMatchers("/login", "/register").permitAll()



                .requestMatchers("/admin/**").hasAuthority("admin")  // Admin access
                .requestMatchers("/coach/**").hasAuthority("coach")  // Coach access
                .requestMatchers("/client/**").hasAuthority("client")  // Client access
                .requestMatchers("/addAbonnement").hasAuthority("admin")

                .requestMatchers("/signup/**").permitAll()
                .requestMatchers("/signin/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .anyRequest().authenticated();
        




        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
