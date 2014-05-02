package com.amortization;//
// Exercise Details:
// Build an amortization schedule program using Java. 
// 
// The program should prompt the user for
//		the amount he or she is borrowing,
//		the annual percentage rate used to repay the loan,
//		the term, in years, over which the loan is repaid.  
// 
// The output should include:
//		The first column identifies the payment number.
//		The second column contains the amount of the payment.
//		The third column shows the amount paid to interest.
//		The fourth column has the current balance.  The total payment amount and the interest paid fields.
// 
// Use appropriate variable names and comments.  You choose how to display the output (i.e. Web, console).  
// Amortization Formula
// This will get you your monthly payment.  Will need to update to Java.
// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
// 
// Where:
// P = Principal
// I = Interest
// J = Monthly Interest in decimal form:  I / (12 * 100)
// N = Number of months of loan
// M = Monthly Payment Amount
// 
// To create the amortization table, create a loop in your program and follow these steps:
// 1.      Calculate H = P x J, this is your current monthly interest
// 2.      Calculate C = M - H, this is your monthly payment minus your monthly interest, so it is the amount of principal you pay for that month
// 3.      Calculate Q = P - C, this is the new balance of your principal of your loan.
// 4.      Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
// 

import com.amortization.beans.AmortizationSchedule;
import com.amortization.impl.AmortizationSchedulerImpl;
import com.amortization.interfaces.AmortizationScheduler;
import com.amortization.util.AmortizationHelper;
import com.amortization.util.ConsoleOutputPrinter;

/**
 * Created by nikned
 * Runner class to execute Amortization process
 */
public class RunAmortizationSchedule {

    private static final double monthlyInterestDivisor = 12d * 100d;
    private static AmortizationScheduler amortizationScheduler;


    public RunAmortizationSchedule(AmortizationSchedule input) throws IllegalArgumentException {

        if (AmortizationHelper.isValidInput(input)) {
            throw new IllegalArgumentException();
        }

    }


    public static void main(String[] args) {

        //Read Input from console and set values to appropriate getter/setters

        AmortizationSchedule input = new AmortizationScheduleConsoleInputReader().readUserInputFromConsole();

        //call executor
        execute(input);


    }

    public static void execute(AmortizationSchedule input) {
        //Generate Amortization Schedule

        try {
            //initialize  amortizationschedule
            RunAmortizationSchedule execute = new RunAmortizationSchedule(input);
            amortizationScheduler = new AmortizationSchedulerImpl();

            //validate and set monthly interest and payment amount

            //calculate monthly interest before performing amortization
            input.setMonthlyInterest(amortizationScheduler.calculateMonthlyInterest(input.getApr(), monthlyInterestDivisor));

            //calculate monthly payment  before performing amortization
            input.setMonthlyPaymentAmount(amortizationScheduler.calculateMonthlyPayment(input, monthlyInterestDivisor));

            amortizationScheduler.outputAmortizationSchedule(input);

        } catch (IllegalArgumentException e) {
            ConsoleOutputPrinter.print("Unable to process the values entered. Terminating program.\n", e.getMessage());
        }
    }


}

