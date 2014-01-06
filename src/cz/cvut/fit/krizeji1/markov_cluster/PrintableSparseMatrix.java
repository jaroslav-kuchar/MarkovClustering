/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.vector.Vector;

/**
 *
 * @author Jiri Krizek
 */
public class PrintableSparseMatrix extends CRSMatrix implements NodesMatrix {

    private static final Logger logger = Logger.getLogger(PrintableSparseMatrix.class.getName());
    private int N;
    private double epsilon;

    public PrintableSparseMatrix(int cols, int rows) {
        super(cols, rows);
        N = cols;
    }

    public PrintableSparseMatrix() {
        super();
    }

    public PrintableSparseMatrix(CRSMatrix crsMatrix) {
        super(crsMatrix);
    }

    public PrintableSparseMatrix(PrintableSparseMatrix matrix) {
        super(matrix);
    }

    public PrintableSparseMatrix(double[][] doubles) {
        super(doubles);
        N = getNumCols();
    }

    @Override
    public void setEpsilon(double EPSILON) {
        this.epsilon = EPSILON;
    }

    @Override
    public boolean isBinary() {
        for (int i = 0; i < (this.columns * this.rows); i++) {
            double elem = this.get(i);
            if (!MCClusterer.precisionEqual(elem, 0) && !MCClusterer.precisionEqual(elem, 1)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toStringWithoutZeros() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("numRows = %d , numCols = %d\n", this.getNumRows(), this.getNumCols()));
        for (int row = 0; row < this.getNumRows(); row++) {
            for (int col = 0; col < this.getNumCols(); col++) {
                double val = this.get(row, col);
                if (val == 0) {
                    result.append("-------\t");
                } else {
                    String valStr = String.format("%.5f\t", val);
                    result.append(valStr);
                }
            }
            result.append("\n");
        }

        return result.toString();
    }

    public Vector getRowNumVector(int row) {
        return this.getRow(row);
    }

    @Override
    public double[] getRowNum(int row) {
        if (row < 0 || row >= this.getNumRows()) {
            System.out.println("exception thrown");
            throw new IllegalArgumentException(this.getClass().getName() + "method getRow(" + row + "): Illegal argument " + row);
        }

        Vector v = this.getRowNumVector(row);
        double[] result = new double[v.length()];

        for (int i = 0; i < v.length(); i++) {
            result[i] = v.get(i);
        }
        return result;
    }

    @Override
    public double set(int index, double value) {
        super.set((index / super.columns), (index % super.columns), value);
        return value;
    }

    @Override
    public NodesMatrix copyMatrix() {
        NodesMatrix res = new PrintableSparseMatrix(this);
        return res;
    }

    @Override
    public int getNumCols() {
        return super.columns;
    }

    @Override
    public int getNumRows() {
        return super.rows;
    }

    @Override
    public int getNumElements() {
        return super.rows * super.columns;
    }

    @Override
    public double get(int index) {
        return super.get((index / this.columns), (index % this.columns));
    }

    @Override
    public NodesMatrix multiply(NodesMatrix a, NodesMatrix b) {
        Matrix aa = nodesMatrixAsMatrix(a, N);
        Matrix bb = nodesMatrixAsMatrix(b, N);

        Matrix res = aa.multiply(bb);
        return new PrintableSparseMatrix((CRSMatrix) res);
    }

    @Override
    public NodesMatrix sumCols() {
        PrintableSparseMatrix matrix = new PrintableSparseMatrix(1, getNumCols());

        for (int i = 0; i < this.getNumCols(); i++) {
            //System.out.println(super.getColumn(i));
            double sum = super.getColumn(i).sum();
            matrix.set(0, i, sum);
        }
        return matrix;
    }

    @Override
    public void removeZeroRows() {
        // Don't do anything if matrix is empty
        if (this.getNumElements() == 0) {
            return;
        }

        List<Vector> nonZeroRows = new ArrayList<Vector>();
        boolean onlyZeros = true;
        for (int row = 0; row < this.getNumRows(); row++) {
            for (int col = 0; col < this.getNumCols(); col++) {
                double elem = this.get(row, col);
                if (!precisionEqualZero(elem)) {
                    onlyZeros = false;
                }
            }
            if (!onlyZeros) {
                Vector nonZeroRow = this.getRowNumVector(row);
                nonZeroRows.add(nonZeroRow);
            }
            onlyZeros = true;
        }

        // number of nodes (cols)
        int c = nonZeroRows.get(0).length();
        // number of clusters (rows)get
        int r = nonZeroRows.size();

        // resize matrix
        this.columns = c;
        this.rows = r;
        this.resize(r, c);
        //super.resize(r, c);
        //this.resize(r,c);
        System.out.println("cols: "+this.getNumCols()+" rows: "+this.getNumRows());
        this.getNumCols();
        this.getNumRows();
        System.out.println("cols: " + this.columns + " rows: " + this.rows);
        System.out.println("non zero rows: "+ nonZeroRows.size());
        for (int i = 0; i < nonZeroRows.size(); i++) {
            Vector get = nonZeroRows.get(i);
            this.setRow(i, get);
        }
        //System.out.println(this.toStringWithoutZeros());
    }

    private boolean precisionEqualZero(double val) {
        return Math.abs(val - 0) <= ((Math.abs(val) > 0 ? 0 : Math.abs(val)) * epsilon);
    }

    private Matrix nodesMatrixAsMatrix(NodesMatrix a, int num) {
        Matrix bb = new CRSMatrix(a.getNumRows(), a.getNumCols());

        for (int row = 0; row < a.getNumRows(); row++) {
            for (int col = 0; col < a.getNumCols(); col++) {
                double val = a.get(row, col);
                bb.set(row, col, a.get(row, col));
                double settedVal = bb.get(row, col);
            }
        }
        return bb;
    }
}
