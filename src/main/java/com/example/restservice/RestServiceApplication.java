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
    	System.out.println(System.getProperty("user.dir"));
    	System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("window-size=1920x1080");
        options.addArguments("--disable-gpu");
        options.setBinary("GOOGLE_CHROME_BIN");
		WebDriver driver = new ChromeDriver(options);
		System.out.println("Chrome instance created!");
        return driver;
 
    }

}
