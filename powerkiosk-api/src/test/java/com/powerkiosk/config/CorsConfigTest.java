package com.powerkiosk.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CorsConfigTest.class)
@ComponentScan(basePackageClasses = {CorsConfig.class, CorsConfigTest.HelloWorldController.class})
public class CorsConfigTest {

    private static final String HELLO_WORLD_BODY = "Hello World.";

    @Autowired
    protected MockMvc mvc;

    @Test
    public void corsTest() throws Exception {
        final String originUrl = "http://some-origin/";

        mvc.perform(get("/test").header(HttpHeaders.ORIGIN, originUrl))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"))
                .andExpect(content().string(HELLO_WORLD_BODY));

    }

    @RestController
    @RequestMapping("test")
    public static class HelloWorldController {
        @GetMapping
        public ResponseEntity<String> helloWorld() {
            return new ResponseEntity<>(HELLO_WORLD_BODY, HttpStatus.OK);
        }

    }

}