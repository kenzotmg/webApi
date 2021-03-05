package com.example.restservice;


import java.util.LinkedList;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {

	@Autowired
	WebDriver driver;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/")
	public String produto(@RequestParam("produto")String nomeProduto) throws JsonProcessingException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
		Produto produto = new Produto();
		LinkedList<Produto> resultados = new LinkedList<>();
		resultados = produto.getProdutos(nomeProduto, driver);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultados);
	}
	
}
