package com.amortization.interfaces;

import com.amortization.beans.AmortizationSchedule;
import com.amortization.util.PrintFormatHelper;

/**
 * Created by nik ned.
 */
public interface AmortizationScheduler {


    public long calculateMonthlyPayment(AmortizationSchedule input, double monthlyInterestDivisor);

    public void outputAmortizationSchedule(AmortizationSchedule input) throws Exception;

    public double calculateMonthlyInterest(double apr, double monthlyInterestDivisor);

    public void generateAmortizationScheduleTable(AmortizationSchedule input, PrintFormatHelper printFormatHelper) throws Exception;

    public long calculateCurrMonthPrincipalPaid(long curMonthlyInterest, long curMonthlyPaymentAmount);

    public long calculateCurrMonthPaymentAmount(AmortizationSchedule input, long curPayoffAmount);

    public long calculateCurrMonthInterest(AmortizationSchedule input, double balance);


}
