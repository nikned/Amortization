package com.amortization; /**
 *
 */
 
 /*****/

import java.io.IOException;

import com.amortization.beans.AmortizationSchedule;
import com.amortization.util.AmortizationHelper;
import com.amortization.util.ConsoleOutputPrinter;

/**
 * Created by nik ned.
 * Class to read Input required for amortization Schedule
 */
public class AmortizationScheduleConsoleInputReader {

    public AmortizationSchedule readUserInputFromConsole() {
        String[] userPrompts = {
                "Please enter the amount you would like to borrow: ",
                "Please enter the annual percentage rate used to repay the loan: ",
                "Please enter the term, in years, over which the loan is repaid: "};

        String line = "";
        AmortizationSchedule input = new AmortizationSchedule();
        double amount = 0;
        double apr = 0;
        int years = 0;

        for (int i = 0; i < userPrompts.length; ) {
            String userPrompt = userPrompts[i];
            try {
                line = ConsoleOutputPrinter.readLine(userPrompt);
            } catch (IOException e) {
                ConsoleOutputPrinter
                        .print("An IOException was encountered. Terminating program.\n");
                return input;
            }

            boolean isValidValue = true;
            try {
                switch (i) {
                    case 0:
                        amount = Double.parseDouble(line);
                        if (!AmortizationHelper.isValidBorrowAmount(amount)) {
                            isValidValue = false;
                            double range[] = AmortizationHelper.getBorrowAmountRange();
                            ConsoleOutputPrinter
                                    .print("Please enter a positive value between "
                                            + range[0] + " and " + range[1] + ". ");
                        } else {
                            input.setAmount(amount);
                            input.setAmountBorrowed(amount);
                        }
                        break;
                    case 1:
                        apr = Double.parseDouble(line);
                        if (!AmortizationHelper.isValidAPRValue(apr)) {
                            isValidValue = false;
                            double range[] = AmortizationHelper.getAPRRange();
                            ConsoleOutputPrinter
                                    .print("Please enter a positive value between "
                                            + range[0] + " and " + range[1] + ". ");
                        } else {
                            input.setApr(apr);
                        }
                        break;
                    case 2:
                        years = Integer.parseInt(line);
                        if (!AmortizationHelper.isValidTerm(years)) {
                            isValidValue = false;
                            int range[] = AmortizationHelper.getTermRange();
                            ConsoleOutputPrinter
                                    .print("Please enter a positive integer value between "
                                            + range[0] + " and " + range[1] + ". ");
                        } else {
                            input.setYears(years);
                            input.setInitialTermMonths(years);
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                isValidValue = false;
            }
            if (isValidValue) {
                i++;
            } else {
                ConsoleOutputPrinter.print("An invalid value was entered.\n");
            }
        }
        return input;

    }
}
