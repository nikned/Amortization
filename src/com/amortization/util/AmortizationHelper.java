/**
 *
 */
package com.amortization.util;

import com.amortization.beans.AmortizationSchedule;

/**
 * Created by nik ned.
 * Helper to get amortization valid input ranges
 *
 */
public class AmortizationHelper {

	private static final double[] borrowAmountRange = new double[] { 0.01d, 1000000000000d };
	private static final double[] aprRange = new double[] { 0.000001d, 100d };
	private static final int[] termRange = new int[] { 1, 1000000 };

	public static final double[] getBorrowAmountRange() {
		return borrowAmountRange;
	}

	public static final double[] getAPRRange() {
		return aprRange;
	}

	public static final int[] getTermRange() {
		return termRange;
	}

	public static boolean isValidBorrowAmount(double amount) {
		double range[] = getBorrowAmountRange();
		return ((range[0] <= amount) && (amount <= range[1]));
	}

	public static boolean isValidAPRValue(double rate) {
		double range[] = getAPRRange();
		return ((range[0] <= rate) && (rate <= range[1]));
	}

	public static boolean isValidTerm(int years) {
		int range[] = getTermRange();
		return ((range[0] <= years) && (years <= range[1]));
	}

    public static boolean isValidInput(AmortizationSchedule input){

        return (!AmortizationHelper.isValidBorrowAmount(input.getAmount())) ||
                (!AmortizationHelper.isValidAPRValue(input.getApr())) ||
                (!AmortizationHelper.isValidTerm(input.getYears()));
    }


}
