package com.nice.nicetask.repo;

import com.nice.nicetask.beans.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RichPersonRepository extends JpaRepository<PersonEntity, Integer> {

    PersonEntity findById(int id);

}
