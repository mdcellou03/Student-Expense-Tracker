package ca.ucalcary.cpsc.groupprojectgui.objects;

/**
 * Represents a tuition expense, including information about scholarships.
 * Extends the Expense class to include specific details relevant to tuition costs.
 *
 */
public class TuitionExpense extends Expense {
    private final double scholarshipAmount; // Scholarship amount that will be deducted from the total tuition cost
    private String term; // Academic term for which the tuition expense applies

    /**
     * Constructs a new TuitionExpense instance with specified amount and scholarship.
     *
     * @param amount             the total cost of tuition before scholarship deduction
     * @param scholarshipAmount  the amount of scholarship received
     */
    public TuitionExpense(double amount,double scholarshipAmount){
        super(amount,ExpenseType.TUITION);
        this.scholarshipAmount = scholarshipAmount;
    }
    // Getter and Setter for term
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Calculates the net amount of tuition expense after scholarship deduction.
     * Ensures that the net amount is not negative.
     *
     * @return the net tuition amount after deducting the scholarship
     *
     */
    @Override
    public double netAmount() {
        return Math.max(getAmount() - scholarshipAmount, 0);
    }
}

