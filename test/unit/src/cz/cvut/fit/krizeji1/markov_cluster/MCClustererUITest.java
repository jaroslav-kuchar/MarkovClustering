/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import javax.swing.JPanel;
import org.gephi.clustering.spi.Clusterer;
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
public class MCClustererUITest {
    
    public MCClustererUITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of getPanel method, of class MCClustererUI.
     */
    @Test
    public void testGetPanel() {
        System.out.println("getPanel");
        MCClustererUI instance = new MCClustererUI();
        JPanel result = instance.getPanel();
        assertNotNull(result);
    }
}