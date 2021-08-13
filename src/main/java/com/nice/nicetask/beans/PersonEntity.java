package com.nice.nicetask.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @Column(updatable = false)
    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private int cash;
    private int numberOfAssets;
}
