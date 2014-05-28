/*
 * http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/
 */
package cz.cvut.fit.krizeji1.markov_cluster.multicolour;

/**
 *
 * @author Jiri Krizek
 */
public final class Color {

    private float h;
    private float r;
    private float g;
    private float b;
    double golden_ratio_conjugate = 0.618033988749895;

    public Color() {
        h = (float) Math.random();
        this.randomize();
    }

    // Nahodne obarveni clusteru, prevzato z de.uni_leipzig.informatik.asv.gephi.chinesewhispers;
    public Color randomize() {
        h += golden_ratio_conjugate;
        h %= 1;
        hsvToRgb(h, 0.95f, 0.99f);
        return this;
    }

    private void hsvToRgb(float h, float s, float v) {
        int hh = (int) (h * 6);
        float f = (h * 6 - hh);
        float p = (v * (1 - s));
        float q = (v * (1 - f * s));
        float t = (v * (1 - (1 - f) * s));

        switch (hh) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
        }
    }

    /**
     * @return the r
     */
    public float getR() {
        return r;
    }

    /**
     * @return the g
     */
    public float getG() {
        return g;
    }

    /**
     * @return the b
     */
    public float getB() {
        return b;
    }
    
    public int getColorAsInt() {
        int r2 = (int) (r*256);
        int g2 = (int) (g*256) << 8;
        int b2 = (int) (b*256) << 16;
        
        return r2+g2+b2;
    }
}
