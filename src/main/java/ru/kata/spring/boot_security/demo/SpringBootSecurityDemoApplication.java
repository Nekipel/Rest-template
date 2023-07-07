package ru.kata.spring.boot_security.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import ru.kata.spring.boot_security.demo.model.User;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	static RestTemplate restTemplate = new RestTemplate();
	static String baseUrl = "http://94.198.50.185:7081/api/users";
	private static String cookie;
	private static String result = "";
	public static void main(String[] args) {
//		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
		useExchangeMethodOfRestTemplate();
	}

	public static void useExchangeMethodOfRestTemplate() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		getUsersByExchangeMethod(requestEntity);

		User user = new User();
		user.setId(3L);
		user.setName("James");
		user.setLastName("Brown");
		user.setAge((byte) 33);
		headers.add("Cookie", cookie);
		HttpEntity<Object> requestEntityPost = new HttpEntity<>(user,headers);
		addUserByExchangeMethod(requestEntityPost);
		user.setName("Thomas");
		user.setLastName("Shelby");
		HttpEntity<Object> requestEntityPut = new HttpEntity<>(user,headers);
		updateUserByExchangeMethod(requestEntityPut);
		HttpEntity<Object> requestEntityDelete = new HttpEntity<>(headers);
		deleteUserByExchangeMethod(requestEntityDelete);
		System.out.println(result);
	}

	public static void getUsersByExchangeMethod(HttpEntity<Object> requestEntity){
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				baseUrl,
				HttpMethod.GET,
				requestEntity,
				String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		System.out.println("Status code " + statusCode);

		String users =  responseEntity.getBody();
		System.out.println("response body " + users);

		HttpHeaders responseHeaders = responseEntity.getHeaders();
		cookie = responseHeaders.getFirst("Set-Cookie");
		System.out.println("response Headers " + responseHeaders);
		System.out.println("response Cookie " + cookie);
	}

	public static void addUserByExchangeMethod(HttpEntity<Object> requestEntity){
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				baseUrl,
				HttpMethod.POST,
				requestEntity,
				String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		System.out.println("Status code " + statusCode);

		result  +=  responseEntity.getBody();
		System.out.println("response body " + result);

		HttpHeaders responseHeaders = responseEntity.getHeaders();
		System.out.println("response Headers " + responseHeaders);
	}

	public static void updateUserByExchangeMethod(HttpEntity<Object> requestEntity){
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				baseUrl,
				HttpMethod.PUT,
				requestEntity,
				String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		System.out.println("Status code " + statusCode);

		result  +=  responseEntity.getBody();
		System.out.println("response body " + result);

		HttpHeaders responseHeaders = responseEntity.getHeaders();
		System.out.println("response Headers " + responseHeaders);
	}

	public static void deleteUserByExchangeMethod(HttpEntity<Object> requestEntity){
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				baseUrl + "/3",
				HttpMethod.DELETE,
				requestEntity,
				String.class);
		HttpStatus statusCode = responseEntity.getStatusCode();
		System.out.println("Status code " + statusCode);

		result  +=  responseEntity.getBody();
		System.out.println("response body " + result);

		HttpHeaders responseHeaders = responseEntity.getHeaders();
		System.out.println("response Headers " + responseHeaders);
	}

}
