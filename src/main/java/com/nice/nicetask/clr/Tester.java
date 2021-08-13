package com.nice.nicetask.clr;

import com.nice.nicetask.beans.FinancialInfo;
import com.nice.nicetask.beans.Person;
import com.nice.nicetask.beans.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Tester implements CommandLineRunner {

    private final RestTemplate restTemplate;

    private final String uri_evaluate = "http://localhost:8081/wealth-rating/evaluate";
    private final String uri_getAll = "http://localhost:8081/wealth-rating/all";
    private final String uri_getOne = "http://localhost:8081/wealth-rating/single/{id}";

    @Override
    public void run(String... args) throws Exception {
        ArrayList<Person> persons = new ArrayList<>();

        //mock data for testing
        persons.add(Person.builder()
                .id(1)
                .personalInfo(new PersonalInfo("Shay", "Rosen", "Netanya"))
                .financialInfo(new FinancialInfo(10000, 30))
                .build());

        persons.add(Person.builder()
                .id(2)
                .personalInfo(new PersonalInfo("Bill", "Gates", "Washington"))
                .financialInfo(new FinancialInfo(1600000000, 50))
                .build());

        persons.add(Person.builder()
                .id(3)
                .personalInfo(new PersonalInfo("Jeff", "Bezos", "San Francisco"))
                .financialInfo(new FinancialInfo(1800000000, 55))
                .build());


        System.out.println("***TESTING WITH REST TEMPLATE*** \n");

        //evaluate list of persons
        System.out.println("Evaluate all hardcoded persons: ");
        persons.forEach(person -> {
            String responseEvaluation = restTemplate.postForObject(uri_evaluate, person, String.class);
            System.out.println("Evaluating " + person.getPersonalInfo().getFirstName() + " " + person.getPersonalInfo().getLastName() + ": ");
            System.out.println(responseEvaluation + "\n");
        });

        //get rich person by id
        System.out.println("Get rich person with id 2:");
        Map<String, String> paramsId = new HashMap<String, String>();
        paramsId.put("id","2");
        ResponseEntity<Person> responseOnePerson = restTemplate.getForEntity(uri_getOne, Person.class, paramsId);
        System.out.println(responseOnePerson.getBody());

        //get all rich persons
        System.out.println("\nGet all rich people:");
        ResponseEntity<Person[]> responseAllRichPersons = restTemplate.getForEntity(uri_getAll, Person[].class);
        Arrays.asList(responseAllRichPersons.getBody()).forEach(System.out::println);

        //evaluate a person with invalid information (expecting bad request)
        Person invalidPerson = Person.builder()
                .id(2) //this id already exists, so this person object is invalid
                .personalInfo(new PersonalInfo("John", "Smith", "Jerusalem"))
                .financialInfo(new FinancialInfo(1000000000, 50))
                .build();

        System.out.println("\nTesting invalid person evaluation: ");
        try{
            System.out.println(restTemplate.postForObject(uri_evaluate, invalidPerson, String.class));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
