package com.example.hibernate;

import javax.persistence.*;

//mysql> CREATE TABLE loan (
//    ->     id BIGINT NOT NULL AUTO_INCREMENT,
//        ->     loan_amount DOUBLE NOT NULL,
//    ->     interest_rate DOUBLE NOT NULL,
//    ->     years INT NOT NULL,
//    ->     PRIMARY KEY (id)
//    -> );

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "loan_amount", nullable = false)
    private double loanAmount;

    @Column(name = "interest_rate", nullable = false)
    private double interestRate;

    @Column(name = "years", nullable = false)
    private int years;

    public Loan() {}

    public Loan(double loanAmount, double interestRate, int years) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.years = years;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getYears() {
        return years;
    }
}
