package com.example.restservice;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Produto {
	private String nome;
	private String marca;
	private String preco;
	private String imgUrl;
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Produto(String nome,String marca, String imgUrl,String preco) {
		this.nome = nome;
		this.marca = marca;
		this.imgUrl = imgUrl;
		this.preco = preco;
	}
	
	public Produto() {
	}

	public String getPreco() {
		return preco;
	}

	public String getNome() {
		return nome;
	}

	public String getMarca() {
		return marca;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	@SuppressWarnings("rawtypes") 
	public LinkedList getProdutos(String produto, WebDriver driver) throws InterruptedException { 
		LinkedList<Produto> produtos = new LinkedList<>();
		
		String url = "https://www.supermercadonow.com/produtos/supermercado-hirota-aclimacao/";
		driver.get(url);
		
		//PROCURAR SEARCH BAR
		WebElement buscarProduto = driver.findElement(By.xpath("//body/div[@id='__next']/div[2]/div[1]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]"));
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(buscarProduto)); 
		wait.until(ExpectedConditions.elementToBeClickable(buscarProduto));
		
		//BUSCAR PRODUTO
		buscarProduto.sendKeys(produto);
		
		
		//ESPERAR A PAGINA CARREGAR COMPLETAMENTE
		boolean pageLoaded = false;
		while(pageLoaded == false) {
			WebElement mainDiv = driver.findElement(By.xpath("//body/div[@id='__next']/div[2]/div[1]/div[1]"));
			if(mainDiv.getAttribute("class").contains("WebHeader") == true) {
				pageLoaded = true;
			}
			Thread.sleep(200);
		}
		
		//CHECAR SE HOUVE RESULTADOS
		boolean isEmpty = driver.findElement(By.xpath("//body/div[@id='__next']/div[2]/div[1]/div[3]/div[1]")).getAttribute("class").contains("Empty");
		
		if(isEmpty != true) {
			//PEGAR A DIV DOS RESULTADOS
				WebElement divResultados = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.styles__ProductsContainer-sc-5q6t96-3")));
				List<WebElement> resultadosIndividuais = divResultados.findElements(By.cssSelector("div[wrap=wrap] > div"));
				
				//ITERAR PELA LISTA DE RESULTADOS
				for(WebElement item : resultadosIndividuais) {
					Produto product = new Produto();
					boolean hasPrice = false;
					boolean promocao = false;
					
					//SELECIONA PRIMEIRA DIV DO PRODUTO PARA USAR COMO FILTRO DE PRODUTO DISPONIVEL E PROMOÇÃO
					List<WebElement> childDivs = item.findElement(By.cssSelector("div:nth-child(2)")).findElements(By.cssSelector("div:first-child > *"));
					if(childDivs.size() > 0) {
						hasPrice = true;
						List<WebElement> childDivsPromocao = item.findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("div:first-child")).findElements(By.xpath("*"));
						if(childDivsPromocao.size() > 2) {
							promocao = true;
						}
					}
					
					if (hasPrice == true) {
						//PEGANDO URL DA IMAGEM DO PRODUTO E NOME DO PRODUTO
						String _nomeProduto = item.findElement(By.cssSelector("img")).getAttribute("alt");
						String imgUrl = item.findElement(By.cssSelector("img")).getAttribute("src");
						String marca =	item.findElement(By.cssSelector("div:nth-child(5)")).findElement(By.cssSelector("div:nth-child(1)")).findElement(By.cssSelector("span")).getText();
						product.setNome(_nomeProduto);
						product.setMarca(marca);
						product.setImgUrl(imgUrl);
						
						
						//PEGANDO PRECO DO PRODUTO
						if(promocao == true) {
							String precoPromocao = item.findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("div:first-child")).findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("span")).getText();
							String precoPromocao2 = item.findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("div:first-child")).findElement(By.cssSelector("div:nth-child(3)")).findElement(By.cssSelector("span")).getText();
							if(precoPromocao2.charAt(0) != '/') {
								precoPromocao += "/";
								product.setPreco(precoPromocao+precoPromocao2);
							}
							product.setPreco(precoPromocao+precoPromocao2);
						}else {
							String preco = item.findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("div:first-child")).findElement(By.cssSelector("div:first-child")).findElement(By.cssSelector("span")).getText();
							String preco2 = item.findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("div:first-child")).findElement(By.cssSelector("div:nth-child(2)")).findElement(By.cssSelector("span")).getText();
							if(preco2.charAt(0) != '/') {
								preco += "/";
								product.setPreco(preco+preco2);
							}
							product.setPreco(preco+preco2);
						}
						produtos.addLast(product);
					}
				}
			}
		return produtos;
}
	 
	
}
