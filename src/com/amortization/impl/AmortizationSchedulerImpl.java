package com.amortization.impl;

import com.amortization.beans.AmortizationSchedule;
import com.amortization.interfaces.AmortizationScheduler;
import com.amortization.util.ConsoleOutputPrinter;
import com.amortization.util.PrintFormatHelper;

/**
 * Created by nik ned.
 */
public class AmortizationSchedulerImpl implements AmortizationScheduler {

    /**
     * Calculates monthly payment amount
     *
     * @param input
     * @param monthlyInterestDivisor
     * @return
     */
    @Override
    public long calculateMonthlyPayment(AmortizationSchedule input, double monthlyInterestDivisor) {
        // M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
        //
        // Where:
        // P = Principal
        // I = Interest
        // J = Monthly Interest in decimal form:  I / (12 * 100)
        // N = Number of months of loan
        // M = Monthly Payment Amount

        // equation : 1 / (1 + J)
        double tmp = Math.pow(1d + input.getMonthlyInterest(), -1);

        // equation : Math.pow(1/(1 + J), N)
        tmp = Math.pow(tmp, input.getInitialTermMonths());

        // equation : 1 / (1 - (Math.pow(1/(1 + J), N))))
        tmp = Math.pow(1d - tmp, -1);

        // formula : M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
        double rc = input.getAmountBorrowed() * input.getMonthlyInterest() * tmp;

        long monthlyPaymentAmount = Math.round(rc);

        // monthly payment should not be greater than the amount borrowed

        if (monthlyPaymentAmount > input.getAmountBorrowed()) {
            throw new IllegalArgumentException();
        }


        return monthlyPaymentAmount;
    }


    /**
     * Generates amortization schedule table headers and table items
     */
    @Override
    public void outputAmortizationSchedule(AmortizationSchedule input) throws Exception {

        // The output should include:
        //	The first column identifies the payment number.
        //	The second column contains the amount of the payment.
        //	The third column shows the amount paid to interest.
        //	The fourth column has the current balance.  The total payment amount and the interest paid fields.

        PrintFormatHelper printHealper = new PrintFormatHelper();
        ConsoleOutputPrinter.printf(printHealper.getHeaderFormatString(),
                "PaymentNumber", "PaymentAmount", "PaymentInterest",
                "CurrentBalance", "TotalPayments", "TotalInterestPaid");

        generateAmortizationScheduleTable(input, printHealper);
    }

    /**
     * Calculates monthly interest formula (APR/monthly interest divisor)
     *
     * @param apr
     * @param monthlyInterestDivisor
     * @return
     */
    @Override
    public double calculateMonthlyInterest(double apr, double monthlyInterestDivisor) {
        return apr / monthlyInterestDivisor;
    }

    /**
     * Calculates amortization and generates amortization schedule table .
     *
     * @param input
     * @param printFormatHelper
     */
    @Override
    public void generateAmortizationScheduleTable(AmortizationSchedule input, PrintFormatHelper printFormatHelper) throws Exception {

        // To create the amortization table, create a loop in your program and follow these steps:
        // 1.      Calculate H = P x J, this is your current monthly interest
        // 2.      Calculate C = M - H, this is your monthly payment minus your monthly interest, so it is the amount of principal you pay for that month
        // 3.      Calculate Q = P - C, this is the new balance of your principal of your loan.
        // 4.      Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
        //

        if (input == null) {
            new NullPointerException(AmortizationSchedule.class.getName() + "is not initialized.");
        }
        if (printFormatHelper == null) {
            new NullPointerException(PrintFormatHelper.class.getName() + "is not initialized.");
        } else if (input != null && printFormatHelper != null) {


            long balance = input.getAmountBorrowed();
            int paymentNumber = 0;
            long totalPayments = 0;
            long totalInterestPaid = 0;


            ConsoleOutputPrinter.printf(printFormatHelper.getLineItemFormatString(), paymentNumber++, 0d, 0d,
                    ((double) input.getAmountBorrowed()) / 100d,
                    ((double) totalPayments) / 100d,
                    ((double) totalInterestPaid) / 100d);

            final int maxNumberOfPayments = input.getInitialTermMonths() + 1;
            while ((balance > 0) && (paymentNumber <= maxNumberOfPayments)) {
                // Calculate H = P x J, this is your current monthly interest
                //long curMonthlyInterest = Math.round(((double) balance) * monthlyInterest);
                long curMonthlyInterest = calculateCurrMonthInterest(input, balance);

                // the amount required to payoff the loan
                long curPayoffAmount = balance + curMonthlyInterest;

                // the amount to payoff the remaining balance may be less than the calculated monthlyPaymentAmount
                long curMonthlyPaymentAmount = calculateCurrMonthPaymentAmount(input, curPayoffAmount);

                // it's possible that the calculated monthlyPaymentAmount is 0,
                // or the monthly payment only covers the interest payment - i.e. no principal
                // so the last payment needs to payoff the loan
                if ((paymentNumber == maxNumberOfPayments) &&
                        ((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) {
                    curMonthlyPaymentAmount = curPayoffAmount;
                }

                // Calculate C = M - H, this is your monthly payment minus your monthly interest,
                // so it is the amount of principal you pay for that month
                long curMonthlyPrincipalPaid = calculateCurrMonthPrincipalPaid(curMonthlyInterest, curMonthlyPaymentAmount);

                // Calculate Q = P - C, this is the new balance of your principal of your loan.
                long curBalance = balance - curMonthlyPrincipalPaid;

                totalPayments += curMonthlyPaymentAmount;
                totalInterestPaid += curMonthlyInterest;

                // output is in dollars
                ConsoleOutputPrinter.printf(printFormatHelper.getLineItemFormatString(), paymentNumber++,
                        ((double) curMonthlyPaymentAmount) / 100d,
                        ((double) curMonthlyInterest) / 100d,
                        ((double) curBalance) / 100d,
                        ((double) totalPayments) / 100d,
                        ((double) totalInterestPaid) / 100d);

                // Set P equal to Q and go back to Step 1: You thusly loop around until the value Q (and hence P) goes to zero.
                balance = curBalance;
            }
        }
    }

    /**
     * Calculates principal paid for current month.
     *
     * @param curMonthlyInterest
     * @param curMonthlyPaymentAmount
     * @return
     */
    @Override
    public long calculateCurrMonthPrincipalPaid(long curMonthlyInterest, long curMonthlyPaymentAmount) {
        return curMonthlyPaymentAmount - curMonthlyInterest;
    }

    /**
     * Calculates amount payed for current month
     *
     * @param input
     * @param curPayoffAmount
     * @return
     */
    @Override
    public long calculateCurrMonthPaymentAmount(AmortizationSchedule input, long curPayoffAmount) {
        return Math.min(input.getMonthlyPaymentAmount(), curPayoffAmount);
    }

    /**
     * Calculates interest for current month
     *
     * @param input
     * @param balance
     * @return
     */
    @Override
    public long calculateCurrMonthInterest(AmortizationSchedule input, double balance) {
        return Math.round(balance * input.getMonthlyInterest());
    }


}
