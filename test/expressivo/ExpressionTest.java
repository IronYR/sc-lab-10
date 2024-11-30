/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    // toString():
    //   - test primitive numbers
    //   - test simple addition
    //   - test simple multiplication
    //   - test nested expressions with addition and multiplication
    //   - test complex expressions with multiple operations
    //
    // equals():
    //   - test same numbers are equal
    //   - test different numbers are not equal
    //   - test same expressions with same structure are equal
    //   - test same expressions with different structure are not equal
    //   - test with null and different types
    //
    // hashCode():
    //   - test same expressions have same hashcode
    //   - test different numbers have different hashcodes
    //   - test different expressions have different hashcodes
    //   - test complex expressions maintain consistency
	//
    // Variables:
    //   - Variables are correctly represented as strings
    //   - Variable equality works as expected
    //   - Variables can be combined with numbers in expressions
    //   - Complex expressions with variables are formatted correctly
    //   - HashCode is consistent for equal variables and expressions containing variables
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // toString() tests
    @Test
    public void testToStringNumber() {
        Expression num = new Number(42.0);
        assertEquals("42.0", num.toString());
    }
    
    @Test
    public void testToStringSimpleAddition() {
        Expression sum = Expression.add(new Number(1.0), new Number(2.0));
        assertEquals("1.0+2.0", sum.toString());
    }
    
    @Test
    public void testToStringSimpleMultiplication() {
        Expression product = Expression.multiplication(new Number(2.0), new Number(3.0));
        assertEquals("2.0*3.0", product.toString());
    }
    
    @Test
    public void testToStringNestedExpression() {
        Expression inner = Expression.multiplication(new Number(2.0), new Number(3.0));
        Expression outer = Expression.add(new Number(1.0), inner);
        assertEquals("1.0+(2.0*3.0)", outer.toString());
    }
    
    @Test
    public void testToStringComplexExpression() {
        Expression e1 = Expression.multiplication(new Number(2.0), new Number(3.0));
        Expression e2 = Expression.multiplication(new Number(4.0), new Number(5.0));
        Expression sum = Expression.add(e1, e2);
        assertEquals("(2.0*3.0)+(4.0*5.0)", sum.toString());
    }
    
    // equals() tests
    @Test
    public void testEqualsSameNumbers() {
        Expression num1 = new Number(42.0);
        Expression num2 = new Number(42.0);
        assertTrue(num1.equals(num2));
    }
    
    @Test
    public void testEqualsDifferentNumbers() {
        Expression num1 = new Number(42.0);
        Expression num2 = new Number(24.0);
        assertFalse(num1.equals(num2));
    }
    
    @Test
    public void testEqualsSameStructure() {
        Expression e1 = Expression.add(new Number(1.0), new Number(2.0));
        Expression e2 = Expression.add(new Number(1.0), new Number(2.0));
        assertTrue(e1.equals(e2));
    }
    
    @Test
    public void testEqualsDifferentStructure() {
        Expression e1 = Expression.add(new Number(1.0), new Number(2.0));
        Expression e2 = Expression.multiplication(new Number(1.0), new Number(2.0));
        assertFalse(e1.equals(e2));
    }
    
    @Test
    public void testEqualsNull() {
        Expression num = new Number(42.0);
        assertFalse(num.equals(null));
        assertFalse(num.equals("42.0"));
    }
    
    // hashCode() tests
    @Test
    public void testHashCodeConsistency() {
        Expression e1 = Expression.add(new Number(1.0), new Number(2.0));
        Expression e2 = Expression.add(new Number(1.0), new Number(2.0));
        assertEquals(e1.hashCode(), e2.hashCode());
    }
    
    @Test
    public void testHashCodeDifferentNumbers() {
        Expression num1 = new Number(42.0);
        Expression num2 = new Number(24.0);
        assertNotEquals(num1.hashCode(), num2.hashCode());
    }
    
    @Test
    public void testHashCodeDifferentExpressions() {
        Expression e1 = Expression.add(new Number(1.0), new Number(2.0));
        Expression e2 = Expression.multiplication(new Number(1.0), new Number(2.0));
        assertNotEquals(e1.hashCode(), e2.hashCode());
    }
    
    @Test
    public void testHashCodeComplexExpression() {
        Expression e1 = Expression.add(
            Expression.multiplication(new Number(2.0), new Number(3.0)),
            Expression.multiplication(new Number(4.0), new Number(5.0))
        );
        Expression e2 = Expression.add(
            Expression.multiplication(new Number(2.0), new Number(3.0)),
            Expression.multiplication(new Number(4.0), new Number(5.0))
        );
        assertEquals(e1.hashCode(), e2.hashCode());
    }
    
    // Variable tests
    @Test
    public void testVariableToString() {
        Expression var = new Variable("x");
        assertEquals("x", var.toString());
    }
    
    @Test
    public void testVariableEquals() {
        Expression var1 = new Variable("x");
        Expression var2 = new Variable("x");
        Expression var3 = new Variable("y");
        
        assertTrue(var1.equals(var2));
        assertFalse(var1.equals(var3));
    }
    
    @Test
    public void testVariableInExpression() {
        Expression var = new Variable("x");
        Expression num = new Number(2.0);
        Expression product = Expression.multiplication(var, num);
        assertEquals("x*2.0", product.toString());
    }
    
    @Test
    public void testComplexVariableExpression() {
        Expression x = new Variable("x");
        Expression y = new Variable("y");
        Expression sum = Expression.add(
            Expression.multiplication(x, new Number(2.0)),
            Expression.multiplication(y, new Number(3.0))
        );
        assertEquals("(x*2.0)+(y*3.0)", sum.toString());
    }
    
    @Test
    public void testVariableHashCodeConsistency() {
        Expression var1 = new Variable("x");
        Expression var2 = new Variable("x");
        Expression var3 = new Variable("y");
        
        assertEquals(var1.hashCode(), var2.hashCode());
        assertNotEquals(var1.hashCode(), var3.hashCode());
        
        // Test hashCode consistency in complex expressions
        Expression exp1 = Expression.add(var1, new Number(1.0));
        Expression exp2 = Expression.add(var2, new Number(1.0));
        assertEquals(exp1.hashCode(), exp2.hashCode());
    }
    
}
