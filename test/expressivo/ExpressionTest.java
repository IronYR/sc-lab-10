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
    
}
