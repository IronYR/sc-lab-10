/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;
import java.util.Stack;


/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    //   Expression = Add(left:Expression, right:Expression) +
	//		Multiplication(left:Expression, right:Expression) + 
	//		Number(number:double) +
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
	public static Expression parse(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();
        
        ParseTree tree = parser.root();
        ExpressionMaker maker = new ExpressionMaker();
        new ParseTreeWalker().walk(maker, tree);
        return maker.getExpression();
    }
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     * For a Expression is not a primitive, subExpression will be surround by
     * parentheses for grouping, and whitespace will be place before and after operator.
     * Subexpression will be group from right to left. 
     * If Expression is a number, output an equivalent number, accurate to at least 4 decimal places,
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    
    /**
     * @return true is this Expression is a primitive element
     */
    public boolean isPrimitive();
    
    /**
     * @param that Expression to be add
     * @return a new Expression represent add this Expression with other Expression
     */
    public static Expression add(Expression exp1, Expression exp2) {
    	return new Addition(exp1, exp2);
    }
    
    /**
     * @param that Expression to be multiplication
     * @return a new Expression represent multiplication this Expression with other Expression
     */
    public static Expression multiplication(Expression exp1, Expression exp2) {
    	return new Multiplication(exp1, exp2);
    }
   
    /**
     * Differentiate an expression with respect to a variable.
     * @param variable the variable to differentiate by, a case-sensitive nonempty string of letters.
     * @return expression's derivative with respect to variable.
     */
    public Expression differentiate(String variable);
}
