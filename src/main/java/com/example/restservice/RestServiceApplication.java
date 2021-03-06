package com.example.restservice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestServiceApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }
    @Bean
    public WebDriver webDriver() {
    	System.setProperty("webdriver.chrome.driver",System.getenv("GOOGLE_CHROME_BIN"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("window-size=1920x1080");
        //options.setBinary(System.getenv("GOOGLE_CHROME_BIN"));
		WebDriver driver = new ChromeDriver(options);
		System.out.println("Chrome instance created!");
        return driver;
 
    }

}
