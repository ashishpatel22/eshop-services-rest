package com.akp.service;


import com.akp.config.SpringSecurityConfig;
import com.akp.controller.LoginController;
import com.akp.model.Customer;
import com.akp.model.Region;
import com.akp.model.Role;
import com.akp.model.User;
import com.akp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Aashish Patel
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LoginController.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@WebMvcTest(LoginController.class)
@Import(SpringSecurityConfig.class)

public class UserServiceTest {

    @Autowired
    LoginController loginController;
    @MockBean
    UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        Optional<User> testUser = getMockUser();
        given(userService.findByUsername("testadmin")).willReturn(getMockUser());
       SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.get().getUsername(), testUser.get().getPassword()));
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        /*mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();*/
    }

    @Test
    public void loginUser() throws Exception {
        this.mockMvc.perform(get("/api/rest/login").with(httpBasic("testadmin", "password")).accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
    }


    public void login() throws Exception {


        // when + then
        this.mockMvc.perform(get("/api/rest/login").with(httpBasic("testadmin", "password")))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"email\": \"ashish.patel22@gmail.com\",\n" +
                        "    \"username\": \"ashish.patel\",\n" +
                        "    \"active\": 1,\n" +
                        "    \"roles\": [\n" +
                        "        {\n" +
                        "            \"id\": 1,\n" +
                        "            \"role\": \"ROLE_ADMIN\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 2,\n" +
                        "            \"role\": \"ROLE_USER\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"customer\": {\n" +
                        "        \"id\": \"CUSTID_1\",\n" +
                        "        \"firstName\": \"Ashish\",\n" +
                        "        \"lastName\": \"Patel\",\n" +
                        "        \"address\": \"F902 Amanora Hadapsar Pune 411028\",\n" +
                        "        \"region\": {\n" +
                        "            \"id\": \"NO\",\n" +
                        "            \"description\": \"NO_DESC\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}"));
    }

    private Optional<User> getMockUser() {
        // given
        User user = new User();

        Region region = new Region();
        region.setId("NO");
        region.setDescription("NO_DESC");

        Role role1 = new Role();
        role1.setId(1L);
        role1.setRole("ROLE_ADMIN");

        Role role2 = new Role();
        role1.setId(2L);
        role1.setRole("ROLE_USER");

        List<Role> roles = new ArrayList<Role>();
        roles.add(role1);
        roles.add(role2);

        Customer customer = new Customer();
        customer.setFirstName("Ashish");
        customer.setLastName("Patel");
        customer.setAddress("F902 Amanora Hadapsar Pune 411028");
        customer.setId("CUSTID_1");
        customer.setRegion(region);

        user.setActive(1);
        user.setEmail("ashish.patel22@gmail.com");
        user.setId(1L);
        user.setUsername("ashish.patel");
        user.setPassword("password");
        user.setCustomer(customer);
        user.setRoles(roles);
        user.setCustomer(customer);

        return Optional.of(user);
    }

    /*@Configuration
    @EnableJdbcHttpSession
    static class Config extends WebSecurityConfigurerAdapter {
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("testadmin").password("password").roles("ROLE_ADMIN");
        }
    }*/
}
