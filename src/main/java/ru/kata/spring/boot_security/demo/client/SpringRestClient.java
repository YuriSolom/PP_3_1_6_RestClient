package ru.kata.spring.boot_security.demo.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.entity.Employee;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SpringRestClient {
    private static final String GET_EMPLOYEES_ENDPOINT_URL = "http://94.198.50.185:7081/api/users";
    private static final String CREATE_EMPLOYEE_ENDPOINT_URL = "http://94.198.50.185:7081/api/users";
    private static final String UPDATE_EMPLOYEE_ENDPOINT_URL = "http://94.198.50.185:7081/api/users";
    private static final String DELETE_EMPLOYEE_ENDPOINT_URL = "http://94.198.50.185:7081/api/users/{id}";
    private static RestTemplate restTemplate = new RestTemplate();
    String cookies;

    public static void main(String[] args) {
        SpringRestClient springRestClient = new SpringRestClient();
        springRestClient.getEmployees();
        springRestClient.createEmployee();
        springRestClient.updateEmployee();
        springRestClient.deleteEmployee();
    }

    private void getEmployees() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(GET_EMPLOYEES_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class);
        cookies = result.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";"));
        System.out.println(result);
    }


    private void createEmployee() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookies);
            Employee newEmployee = new Employee("James", "Brown", 33);
            HttpEntity<Employee> entity = new HttpEntity<>(newEmployee, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(CREATE_EMPLOYEE_ENDPOINT_URL, HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookies);
            Employee updatedEmployee = new Employee(3, "Thomas", "Shelby", 33);
            HttpEntity<Employee> entity = new HttpEntity<>(updatedEmployee, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(UPDATE_EMPLOYEE_ENDPOINT_URL, HttpMethod.PUT, entity, String.class);
            System.out.println(response.getBody());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void deleteEmployee() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookies);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Map<String, String> params = new HashMap<>();
            params.put("id", "3");
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(DELETE_EMPLOYEE_ENDPOINT_URL, HttpMethod.DELETE, entity, String.class, params);
            System.out.println(response.getBody());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}