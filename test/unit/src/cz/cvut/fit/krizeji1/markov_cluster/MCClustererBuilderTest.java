/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import org.gephi.clustering.spi.Clusterer;
import org.gephi.clustering.spi.ClustererUI;
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
public class MCClustererBuilderTest {

    public MCClustererBuilderTest() {
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
     * Test of getClusterer method, of class MCClustererBuilder.
     */
    @Test
    public void testGetClusterer() {
        System.out.println("getClusterer");
        MCClustererBuilder instance = new MCClustererBuilder();
        Clusterer result = instance.getClusterer();
        assertEquals("MCClusterer", result.getClass().getSimpleName());
    }

    /**
     * Test of getName method, of class MCClustererBuilder.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        MCClustererBuilder instance = new MCClustererBuilder();
        String expResult = MCClusterer.PLUGIN_NAME;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class MCClustererBuilder.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        MCClustererBuilder instance = new MCClustererBuilder();
        String expResult = MCClusterer.PLUGIN_DESCRIPTION;
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getClustererClass method, of class MCClustererBuilder.
     */
    @Test
    public void testGetClustererClass() {
        System.out.println("getClustererClass");
        MCClustererBuilder instance = new MCClustererBuilder();
        Class expResult = MCClusterer.class;
        Class result = instance.getClustererClass();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUI method, of class MCClustererBuilder.
     */
    @Test
    public void testGetUI() {
        System.out.println("getUI");
        MCClustererBuilder instance = new MCClustererBuilder();
        ClustererUI result = instance.getUI();
        assertEquals("MCClustererUI", result.getClass().getSimpleName());
    }
}