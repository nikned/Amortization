
The program prompts the user for:

		the amount he or she is borrowing,
		the annual percentage rate used to repay the loan,
		the term, in years, over which the loan is repaid.

The output includes:

		The first column identifies the payment number.
		The second column contains the amount of the payment.
		The third column shows the amount paid to interest.
		The fourth column has the current balance.  The total payment amount and the interest paid fields.

Amortization Formula:

        This will get you your monthly payment.
        M = P * (J / (1 - (Math.pow(1/(1 + J), N))));

        Where:
             P = Principal
             I = Interest
             J = Monthly Interest in decimal form:  I / (12 * 100)
             N = Number of months of loan
             M = Monthly Payment Amount
