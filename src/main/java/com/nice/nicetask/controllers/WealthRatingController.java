package com.nice.nicetask.controllers;

import com.nice.nicetask.beans.Person;
import com.nice.nicetask.services.RichService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("wealth-rating")
@RequiredArgsConstructor
public class WealthRatingController {

    private final RichService richService;

    @PostMapping("evaluate")
    public ResponseEntity<?> evaluatePerson(@RequestBody Person person) throws Exception {
        //check if this person is rich
        boolean isRich = richService.isRich(person);

        //if the person is rich, save to DB
        if (isRich) { richService.addRichPerson(person); }

        //return response accordingly
        return ResponseEntity.created(URI.create("/wealth-rating/evaluate")).body(
                isRich ?
                        "This person is RICH!!!" :
                        "This person is not rich."
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllRich() throws Exception {
        List<Person> richPersons = richService.getAllRichPersons();
        return ResponseEntity.ok().body(richPersons);
    }

    @GetMapping("single/{id}")
    public ResponseEntity<?> getRichPersonById(@PathVariable int id) throws Exception {
        Person person = richService.getRichPersonById(id);
        return ResponseEntity.ok().body(person);
    }
}
