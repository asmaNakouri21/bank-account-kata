package com.example.bank.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("W")
public class Withdraw extends Operation {

}
