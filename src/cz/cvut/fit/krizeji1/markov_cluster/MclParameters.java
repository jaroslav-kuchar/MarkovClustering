/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

/**
 *
 * @author Jiri Krizek
 */
public class MclParameters {
    private final boolean selfLoop;
    private final double inflation;
    private final double prune;
    private final double power;
    private final int N;
    
    MclParameters(boolean selfLoop, double inflation, double prune, double power, int N) {
        this.selfLoop = selfLoop;
        this.inflation = inflation;
        this.prune = prune;
        this.power = power;
        this.N = N;
    }

    /**
     * @return the selfLoop
     */
    public boolean isSelfLoop() {
        return selfLoop;
    }

    /**
     * @return the inflation
     */
    public double getInflation() {
        return inflation;
    }

    /**
     * @return the prune
     */
    public double getPrune() {
        return prune;
    }
    
    /**
     * @return the power
     */
    public double getPower() {
        return power;
    }

    /**
     * @return the N
     */
    public int getN() {
        return N;
    }
}
