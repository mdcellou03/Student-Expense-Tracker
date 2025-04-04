package ca.ucalcary.cpsc.groupprojectgui.objects;

/**
 * The GroceryExpense class represents a grocery expense with a seasonal discount.
 * It extends the Expense class, inheriting its basic expense properties and adding
 * a specific functionality to account for a seasonal discount that applies to the grocery
 * expense amount. This class calculates the net amount of the grocery expense considering
 * the applied discount.
 *
 */
public class GroceryExpense extends Expense {
    // The seasonal discount as a decimal value. For example, a 10% discount is represented as 0.1
    private final double seasonalDiscount;

    /**
     * Constructs a new GroceryExpense object with a specified amount and seasonal discount.
     *
     * @param amount the amount of the grocery expense before discount.
     * @param seasonalDiscount the seasonal discount to be applied to the expense amount, represented
     *                         as a decimal.
     *
     */
    public GroceryExpense(double amount,double seasonalDiscount) {
        super(amount,ExpenseType.GROCERY); // Call to the superclass (Expense) constructor
        this.seasonalDiscount= seasonalDiscount; // Initialize the seasonal discount
    }
    @Override
    public double netAmount() {
        // Calculate the net amount by applying the discount to the original amount
        return getAmount() * (1 - seasonalDiscount);
    }

}
