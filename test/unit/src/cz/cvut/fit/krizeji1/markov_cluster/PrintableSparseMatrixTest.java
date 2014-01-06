/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JiriKrizek
 */
public class PrintableSparseMatrixTest {

    private static Random rand;

    public PrintableSparseMatrixTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        rand = new Random();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isBinary method, of class PrintableSparseMatrix.
     */
    @Test
    public void testIsBinary() {
        System.out.println("isBinary");


        double[][] b = new double[][]{
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1},};

        PrintableSparseMatrix instance = new PrintableSparseMatrix(b);
        boolean result = instance.isBinary();
        assertTrue(result);
    }

    @Test
    public void testIsBinary2() {
        System.out.println("isBinary2");


        double[][] b = new double[][]{
            {1, 2, 3, 4, 5, 6, 7, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, -1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 20, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 10}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(b);
        boolean result = instance.isBinary();
        assertFalse(result);
    }

    @Test
    public void testIsBinary3() {
        System.out.println("isBinary3");


        double[][] b = new double[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(b);
        boolean result = instance.isBinary();
        assertTrue(result);
    }

    @Test
    public void testIsBinary4() {
        System.out.println("isBinary4");


        double[][] b = new double[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(b);
        boolean result = instance.isBinary();
        assertTrue(result);
    }

    /**
     * Test of toStringWithoutZeros method, of class PrintableSparseMatrix.
     */
    @Test
    public void testToStringWithoutZeros() {
        System.out.println("toStringWithoutZeros");
        double[][] a = new double[][]{
            {0, 1, 0.000001},
            {0, 1, 0}
        };
        PrintableSparseMatrix instance = new PrintableSparseMatrix(a);
        String expResult = "numRows = 2 , numCols = 3\n"
                + "-------\t1.00000\t0.00000\t\n"
                + "-------\t1.00000\t-------\t\n";
        String result = instance.toStringWithoutZeros();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class PrintableSparseMatrix.
     */
    @Test
    public void testGetRow() {
        System.out.println("getRow");
        int row = 0;

        double[][] a = new double[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
            {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(a);
        double[] expResult = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] result = instance.getRowNum(0);
        assertArrayEquals("Expected result " + Arrays.toString(expResult) + ", "
                + "but got " + Arrays.toString(result),
                expResult, result, 0);

        expResult = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 2};
        result = instance.getRowNum(4);
        assertArrayEquals("Expected result " + Arrays.toString(expResult) + ", "
                + "but got " + Arrays.toString(result),
                expResult, result, 0);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetRowException() {
        System.out.println("getRow");
        int row = 0;

        double[][] a = new double[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
            {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(a);
        double[] result = instance.getRowNum(5);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testGetRowException2() {
        System.out.println("getRow");
        int row = 0;

        double[][] a = new double[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
            {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(a);
        double[] result = instance.getRowNum(-1);
    }

    /**
     * Test of removeZeroRows method, of class PrintableSparseMatrix.
     */
    @Test
    public void testRemoveZeroRows() {
        System.out.println("removeZeroRows");

        double[][] a = new double[][]{
            {1, 2, 3},
            {0, 0, 0},
            {3, 2, 1},
            {2, 2, 0},
            {1, 3, 2}
        };

        PrintableSparseMatrix instance = new PrintableSparseMatrix(a);
        instance.removeZeroRows();

        assertEquals(3, instance.getNumCols());
        assertEquals(4, instance.getNumRows());
        assertEquals(3.0, instance.get(1, 0), 0);
        assertEquals(2.0, instance.get(1, 1), 0);
        assertEquals(1.0, instance.get(1, 2), 0);
    }

    /**
     * Test of copyMatrix method, of class PrintableSparseMatrix.
     */
    @Test
    public void testCopyMatrix() {
        System.out.println("copyMatrix");
        PrintableSparseMatrix instance = new PrintableSparseMatrix(new double[][]{{1, 2, 3}});
        NodesMatrix expResult = instance;
        NodesMatrix result = instance.copyMatrix();

        instance.set(0, 0, 222);

        assertNotSame(expResult, result);
        assertNotSame(instance.get(0, 0), result.get(0, 0));
    }

    /**
     * Test of sumCols method, of class PrintableSparseMatrix.
     */
    @Test
    public void testSumCols() {
        System.out.println("sumCols");

        double[][] a = new double[][]{
            {1, 2, 3, 4, 5},
            {10, 20, 30, 40, 50},
            {11, 22, 33, 44, 55},
            {0, 0, 0, 0, - 1},};

        PrintableSparseMatrix instance = new PrintableSparseMatrix(a);
        NodesMatrix expResult = new PrintableSparseMatrix(new double[][]{{22, 44, 66, 88, 109.00000}});
        NodesMatrix result = instance.sumCols();
        for (int i = 0; i < result.getNumElements(); i++) {
            double res = result.get(i);
            System.out.println("Res["+i+"]: "+res);
            assertEquals(expResult.get(i), res, 0);
        }
        assertEquals(5, instance.getNumCols());
    }

    /**
     * Test of multiply method, of class PrintableSparseMatrix.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        NodesMatrix expResult = new PrintableSparseMatrix(new double[][]{{220, 280, 14}, {490, 640, 32}, {760, 1000, 50}});

        PrintableSparseMatrix a = new PrintableSparseMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        PrintableSparseMatrix b = new PrintableSparseMatrix(new double[][]{{10, 20, 1}, {30, 40, 2}, {50, 60, 3}});
        NodesMatrix result = a.multiply(a, b);

        for (int i = 0; i < result.getNumElements(); i++) {
            assertEquals(expResult.get(i), result.get(i), 0);
        }
    }
}
