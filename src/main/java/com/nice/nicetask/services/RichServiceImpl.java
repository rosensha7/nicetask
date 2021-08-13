package com.nice.nicetask.services;

import com.nice.nicetask.beans.FinancialInfo;
import com.nice.nicetask.beans.Person;
import com.nice.nicetask.beans.PersonEntity;
import com.nice.nicetask.exceptions.FinancialInfoInvalidException;
import com.nice.nicetask.exceptions.IdNotFoundException;
import com.nice.nicetask.exceptions.PersonsAlreadyExistsException;
import com.nice.nicetask.exceptions.RestTemplateException;
import com.nice.nicetask.repo.RichPersonRepository;
import com.nice.nicetask.utils.PersonEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
@RequiredArgsConstructor
public class RichServiceImpl implements RichService{

    @Autowired
    private RichPersonRepository richPersonRepository;

    private final RestTemplate restTemplate;

    private final String uri_evaluateCity = "http://localhost:8082/central-bank/regional-info/evaluate?city={city}";
    private final String uri_threshold = "http://localhost:8082/central-bank/wealth-threshold";

    @Override
    public boolean isRich(Person person) throws FinancialInfoInvalidException, RestTemplateException { //calculate wealth

        int threshold = getThreshold();
        int cityEvaluation = getCityEvaluation(person.getPersonalInfo().getCity());
        FinancialInfo financialInfo = person.getFinancialInfo();

        try{
            int fortune = financialInfo.getCash() + financialInfo.getNumberOfAssets() * cityEvaluation;
            return fortune>threshold?true:false;
        }catch (Exception e){
            throw new FinancialInfoInvalidException("Couldn't calculate wealth. Financial info is invalid.");
        }
    }

    private Integer getCityEvaluation(String city) throws RestTemplateException {
        try{
            Map<String, String> params = new HashMap<String, String>();
            params.put("city", city);
            Integer result = restTemplate.getForObject(uri_evaluateCity, Integer.class, params);
            return result;
        }catch (Exception e){
            throw new RestTemplateException(e.getMessage());
        }
    }

    private int getThreshold() throws RestTemplateException{
        try{
            String threshold_str = restTemplate.getForObject(uri_threshold, String.class);
            return Integer.parseInt(threshold_str);
        }catch (Exception e){
            throw new RestTemplateException(e.getMessage());
        }
    }

    @Override
    public List<Person> getAllRichPersons() {
        List<Person> richPersons = new ArrayList<>();
        richPersonRepository.findAll().forEach(personEntity->{
            richPersons.add(PersonEntityConverter.convert(personEntity));
        });
        return richPersons;
    }

    @Override
    public Person getRichPersonById(int id) throws IdNotFoundException {
        if(richPersonRepository.existsById(id))
            return PersonEntityConverter.convert(richPersonRepository.findById(id));
        else
            throw new IdNotFoundException("A rich person with this id (" + id + ") doesn't exist!");
    }

    @Override
    public void addRichPerson(Person person) throws PersonsAlreadyExistsException {
        PersonEntity personEntity = PersonEntityConverter.convert(person);
        if(!richPersonRepository.existsById(personEntity.getId()))
            richPersonRepository.save(personEntity);
        else if(!personEntity.equals(richPersonRepository.findById(personEntity.getId())))
            throw new PersonsAlreadyExistsException("There is already a rich person with this ID in the database!");
    }

}
