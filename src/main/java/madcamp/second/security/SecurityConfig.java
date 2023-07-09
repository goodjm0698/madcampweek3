package madcamp.second.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/signup/**", "/login/**", "/auth/**", "/").permitAll()
                        .anyRequest().authenticated()
                );
//
//        http
//                .formLogin(login -> login
//                        .permitAll()
//                        .loginProcessingUrl("/auth")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                );

        http
                .logout(logout -> logout
                        .logoutUrl("logout")
                        .logoutSuccessUrl("/")
                );

        http.csrf().disable();

//        http
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return (web) -> web.ignoring().antMatchers("/css/**", "/image/**");
    }
}
