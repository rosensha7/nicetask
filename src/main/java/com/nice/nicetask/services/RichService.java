package com.nice.nicetask.services;


import com.nice.nicetask.beans.Person;
import com.nice.nicetask.exceptions.FinancialInfoInvalidException;
import com.nice.nicetask.exceptions.IdNotFoundException;
import com.nice.nicetask.exceptions.PersonsAlreadyExistsException;
import com.nice.nicetask.exceptions.RestTemplateException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RichService {

    boolean isRich(Person person) throws FinancialInfoInvalidException, RestTemplateException;

    List<Person> getAllRichPersons();

    Person getRichPersonById(int id) throws IdNotFoundException;

    void addRichPerson(Person person) throws PersonsAlreadyExistsException;

}
