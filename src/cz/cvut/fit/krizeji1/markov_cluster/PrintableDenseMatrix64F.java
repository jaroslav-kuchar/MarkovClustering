/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.ops.CommonOps;

/**
 *
 * @author Jiri Krizek
 */
class PrintableDenseMatrix64F extends DenseMatrix64F implements NodesMatrix {
    private static final Logger logger = Logger.getLogger(PrintableDenseMatrix64F.class.getName());
    private int N;
    private double epsilon;

    public PrintableDenseMatrix64F(int cols, int rows) {
	super(cols, rows);
        if (cols != rows) { throw new IllegalArgumentException("Matrix must be square matrix"); }
        N = cols;
    }

    public PrintableDenseMatrix64F() {
	super();
    }

    public PrintableDenseMatrix64F(DenseMatrix64F dmf) {
	super(dmf);
    }

    public PrintableDenseMatrix64F(double[][] doubles) {
	super(doubles);
        N = getNumCols();
    }

    public PrintableDenseMatrix64F(Matrix64F mf) {
	super(mf);
    }

    public PrintableDenseMatrix64F(int i) {
	super(i);
    }

    public PrintableDenseMatrix64F(int i, int i1, boolean bln, double... doubles) {
	super(i, i1, bln, doubles);
    }
    
    @Override
    public boolean isBinary() {
	for(int i=0  ; i<this.getNumElements(); i++) {
	   double elem = this.get(i);
	   if(!MCClusterer.precisionEqual(elem, 0) && !MCClusterer.precisionEqual(elem, 1)) {
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
    
    @Override
    public double[] getRowNum(int row) {
        if(row < 0 || row >= this.getNumRows()) {
            throw new IllegalArgumentException(this.getClass().getName() +"method getRow("+row+"): Illegal argument "+row);
        }
        
        double[] result = new double[getNumCols()];
        
        for(int col = 0; col < getNumCols(); col++) {
            result[col] = get(row, col);
        }
        return result;
    }
    
    @Override
    public void removeZeroRows() {
        List<double[]> nonZeroRows = new ArrayList<double[]>();
        boolean onlyZeros = true;
        for(int row = 0; row < this.getNumRows(); row++) {
            for(int col = 0; col < this.getNumCols(); col++) {        
                double elem = this.get(row, col);
                if(!precisionEqualZero(elem)) {
                    onlyZeros = false;
                }
            }
            if(onlyZeros) {
                //logger.log(Level.INFO, "Current row {0} has all zero values", row);
            } else {
                //logger.log(Level.INFO, "Current row {0} has some values", row);
                double[] nonZeroRow = this.getRowNum(row);
                nonZeroRows.add(nonZeroRow);
            }
            onlyZeros = true;
        }
                
        // number of nodes (cols)
        int c = nonZeroRows.get(0).length;
        // number of clusters (rows)get
        int r = nonZeroRows.size();
        double[][] result = new double[r][c];
        
        for(int i = 0; i < r; i++) {
            result[i] = nonZeroRows.get(i);
        }
        
        // resize matrix
        this.reshape(r,c);
        // and insert new values without zero lines
        this.set(new PrintableDenseMatrix64F(result));
    }

    @Override
    public void setEpsilon(double EPSILON) {
        epsilon = EPSILON;
    }

    private boolean precisionEqualZero(double val) {
        return Math.abs(val - 0) <= ((Math.abs(val) > 0 ? 0 : Math.abs(val)) * epsilon);
    }

    @Override
    public NodesMatrix copyMatrix() {
        NodesMatrix res = new PrintableDenseMatrix64F(this);
        return res;
    }

    @Override
    public NodesMatrix sumCols() {
        DenseMatrix64F result = CommonOps.sumCols(this, null);
        //System.out.println("Result: " + result.numRows + "; result.numCols:" + result.numCols);
        NodesMatrix res = new PrintableDenseMatrix64F(result);
        return res;
    }

    @Override
    public NodesMatrix multiply(NodesMatrix a, NodesMatrix b) {
        PrintableDenseMatrix64F output = new PrintableDenseMatrix64F(N, N);
        CommonOps.mult((PrintableDenseMatrix64F)a, (PrintableDenseMatrix64F)b, output);
        
        return output;
    }
    
}
