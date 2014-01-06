/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import org.gephi.graph.api.Node;
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
public class MCClusterImplTest {

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
     * Test of getNodesCount method, of class MCClusterImpl.
     */
    @Test
    public void testGetNodesCount() {
        System.out.println("getNodesCount");
        MCClusterImpl instance = new MCClusterImpl(1);
        int result = instance.getNodesCount();
        assertTrue(result >= 0);
    }

    /**
     * Test of getName method, of class MCClusterImpl.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        MCClusterImpl instance = new MCClusterImpl(2);
        String result = instance.getName();
        assertNotNull(result);
    }
}