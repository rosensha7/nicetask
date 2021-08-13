package com.nice.nicetask.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Person {

    private int id;
    private PersonalInfo personalInfo;
    private FinancialInfo financialInfo;
}
