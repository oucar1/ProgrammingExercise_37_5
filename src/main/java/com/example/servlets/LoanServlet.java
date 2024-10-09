package com.example.servlets;

import com.example.hibernate.Loan;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class LoanServlet extends HttpServlet {

    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        // Initialize Hibernate configuration
        factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Loan.class)
                .buildSessionFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        double loanAmount = Double.parseDouble(request.getParameter("loanAmount"));
        double interestRate = Double.parseDouble(request.getParameter("interestRate")) / 100; // Convert interest rate to percentage
        int years = Integer.parseInt(request.getParameter("years"));

        // Calculate monthly payment
        double monthlyInterestRate = interestRate / 12; // Monthly interest rate
        int numberOfMonths = years * 12; // Total number of months
        double monthlyPayment = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
        double totalPayment = monthlyPayment * numberOfMonths;

        // Create Loan object (if you want to save it to the database)
        Loan loan = new Loan(loanAmount, interestRate, years);

        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();
            session.save(loan);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        // Display the results to the user
        response.setContentType("text/html");
        response.getWriter().println("<h1>Loan Payment Details</h1>");
        response.getWriter().println("<p>Loan Amount: " + loanAmount + "</p>");
        response.getWriter().println("<p>Annual Interest Rate: " + String.format("%.2f", (interestRate * 100)) + "%</p>");
        response.getWriter().println("<p>Number of Years: " + years + "</p>");
        response.getWriter().println("<p>Monthly Payment: " + String.format("%.2f", monthlyPayment) + "</p>");
        response.getWriter().println("<p>Total Payment: " + String.format("%.2f", totalPayment) + "</p>");
    }

    @Override
    public void destroy() {
        // Close the Hibernate session factory
        factory.close();
    }
}