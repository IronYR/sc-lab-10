package expressivo;

public class Multiplication implements Expression {
	/**
	 * Abstraction function:
	 * 	AF(exp1, exp2) = exp1 * exp2
	 * Rep invariant:
	 * 	exp1 and exp2 keep its rep invariant
	 * Safety from rep exposure:
	 * 	all fields are private and final
	 */
	
	private final Expression exp1, exp2;
	
	public Multiplication(Expression exp1, Expression exp2) {
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	
	@Override public String toString() {
		String left = exp1.isPrimitive() ? exp1.toString() :
			"(" + exp1.toString() + ")";
		String right = exp2.isPrimitive() ? exp2.toString() :
			"(" + exp2.toString() + ")";
		return left + "*" + right;
	}
	
	@Override public boolean equals(Object thatObject) {
		if (!(thatObject instanceof Multiplication)) {
			return false;
		}
		Multiplication that = (Multiplication) thatObject;
		return exp1.equals(that.exp1) && exp2.equals(that.exp2);
	}
	
	@Override public int hashCode() {
		return (exp1.hashCode() * exp2.hashCode()) % 1000;
	}
	
	
	public boolean isPrimitive() {
		return false;
	}
	@Override
    public Expression differentiate(String variable) {
        // Differentiation rule for multiplication: d(u * v)/dx = u'v + uv'
        Expression dLeft = exp1.differentiate(variable);
        Expression dRight = exp2.differentiate(variable);
        return new Addition(
            new Multiplication(dLeft, exp2),
            new Multiplication(exp1, dRight)
        );
    }
}