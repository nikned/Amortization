package com.amortization;

import com.amortization.beans.AmortizationSchedule;
import com.amortization.impl.AmortizationSchedulerImpl;
import com.amortization.interfaces.AmortizationScheduler;
import com.amortization.util.AmortizationHelper;
import com.amortization.util.PrintFormatHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;


/**
 * Created by nikned
 */
public class AmortizationScheduleTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private AmortizationScheduler amortizationScheduler;

    @Before
    public void setUp() {
        amortizationScheduler = new AmortizationSchedulerImpl();
    }


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testMonthlyPaymentAmountGreaterThanAmountBorrowed() {
        try {
            AmortizationSchedule amortizationSchedule = new AmortizationSchedule();
            double monthlyInterestDivisor = 12d * 100d;
            amortizationSchedule.setMonthlyInterest(100);
            amortizationSchedule.setAmountBorrowed(100);
            amortizationScheduler.calculateMonthlyPayment(amortizationSchedule, monthlyInterestDivisor);
            fail("Expect an illegal argument exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }


    }


    @Test
    public void testInvalidInputBorrowAmount() {
        assertFalse(AmortizationHelper.isValidBorrowAmount(-100));

    }

    @Test
    public void testInvalidInputAPR() {
        assertFalse(AmortizationHelper.isValidAPRValue(-100));

    }


    @Test
    public void testInvalidYearTerm() {
        assertFalse(AmortizationHelper.isValidTerm(-100));

    }

    @Test
    public void generateAmortizationScheduleTable() throws Exception {

        AmortizationSchedule amortizationSchedule = new AmortizationSchedule();
        double monthlyInterestDivisor = 12d * 100d;
        amortizationSchedule.setAmount(1000);
        amortizationSchedule.setAmountBorrowed(1000);
        amortizationSchedule.setApr(3);
        PrintFormatHelper printFormatHelper = new PrintFormatHelper();
        amortizationScheduler.generateAmortizationScheduleTable(amortizationSchedule, printFormatHelper);
        assertNotNull(outContent.toString());


    }


}
