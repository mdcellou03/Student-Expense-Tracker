package ca.ucalcary.cpsc.groupprojectgui.objects;

/**
 * The RentExpense class represents an expense record for rent,
 * including utilities expenses. It extends the Expense class to
 * include additional information specific to rent and utilities expenses.
 *
 */
public class RentExpense extends Expense {
    private double utilitiesExpenses; // Stores the total utilities expenses associated with the rent expense
    /**
     * Constructs a new RentExpense instance with specified amount for rent and utilities expenses.
     *
     * @param amount The total amount of the rent expense.
     * @param utilitiesExpenses The total utilities expenses associated with the rent.
     */
    public RentExpense(double amount,double utilitiesExpenses){
        super(amount,ExpenseType.RENT); // Initialize the parent class with the amount and type of expense
        this.utilitiesExpenses=utilitiesExpenses; // Set the utilities expenses
    }

    /**
     * Calculates the net amount of the rent expense by adding the base rent to the utilities expenses.
     *
     * @return The net amount of the rent expense.
     */
    @Override
    public double netAmount() {
        return getAmount() + utilitiesExpenses; // Return the sum of rent amount and utilities expenses
    }

}
