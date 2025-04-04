package ca.ucalcary.cpsc.groupprojectgui.objects;

/**
 * Represents an abstract Expense class. This class serves as a base for various types of expenses,
 * encapsulating common properties like amount and type of expense. It requires subclassing to implement
 * the calculation of the net amount specific to the expense's nature.
 *
 */
public abstract class Expense {
    private double amount;
    private final ExpenseType type; // Type of the expense, represented by the ExpenseType enum

    // Constructor
    /**
     * Constructs a new Expense with specified amount and type.
     *
     * @param amount The monetary amount of the expense.
     * @param type   The type of the expense, as defined by the ExpenseType enum.
     *
     */
    public Expense(double amount, ExpenseType type) {
        this.amount = amount;
        this.type = type;
    }

    // Here are my Getters
    public double getAmount() {
        return amount;
    }

    // Here are my Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // This is the  method which will return the type
    public ExpenseType getExpenseType() {
        return this.type;
    }

    /**
     * Calculates and returns the net amount of the expense.
     * This method must be implemented by subclasses to account for specific expense calculations.
     *
     * @return The net amount of the expense after applying necessary adjustments or calculations.
     *
     */
    public abstract double netAmount();
    //public abstract double expenseCategoryTotal();
}
