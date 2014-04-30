/**
 *
 */
package com.amortization.beans;

/**
 * Created by nik ned.
 * Bean to store amortization schedule input
 */
public class AmortizationSchedule {

    private double amount = 0;
    private double apr = 0;
    private int years = 0;
    private long amountBorrowed = 0;
    private long monthlyPaymentAmount = 0;
    private double monthlyInterest = 0d;
    private int initialTermMonths = 0;

    public void setAmountBorrowed(long amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public long getMonthlyPaymentAmount() {
        return monthlyPaymentAmount;
    }

    public void setMonthlyPaymentAmount(long monthlyPaymentAmount) {
        this.monthlyPaymentAmount = monthlyPaymentAmount;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public int getInitialTermMonths() {
        return initialTermMonths;
    }

    public void setInitialTermMonths(int years) {
        this.initialTermMonths = years * 12;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public long getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(double amount) {
        this.amountBorrowed = Math.round(amount * 100);
    }


}
