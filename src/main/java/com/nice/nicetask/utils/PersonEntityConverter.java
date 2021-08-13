package com.nice.nicetask.utils;

import com.nice.nicetask.beans.FinancialInfo;
import com.nice.nicetask.beans.Person;
import com.nice.nicetask.beans.PersonEntity;
import com.nice.nicetask.beans.PersonalInfo;

public class PersonEntityConverter {

    public static PersonEntity convert(Person person){
        return PersonEntity.builder()
                .id(person.getId())
                .firstName(person.getPersonalInfo().getFirstName())
                .lastName(person.getPersonalInfo().getLastName())
                .city(person.getPersonalInfo().getCity())
                .cash(person.getFinancialInfo().getCash())
                .numberOfAssets(person.getFinancialInfo().getNumberOfAssets())
                .build();
    }

    public static Person convert(PersonEntity personEntity){
        return Person.builder()
                .id(personEntity.getId())
                .personalInfo(new PersonalInfo(
                        personEntity.getFirstName(),
                        personEntity.getLastName(),
                        personEntity.getCity()))
                .financialInfo(new FinancialInfo(
                        personEntity.getCash(),
                        personEntity.getNumberOfAssets()
                ))
                .build();
    }
}
