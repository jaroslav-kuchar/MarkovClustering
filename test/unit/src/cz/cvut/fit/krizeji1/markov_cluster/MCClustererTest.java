package cz.cvut.fit.krizeji1.markov_cluster;

import cz.cvut.fit.krizeji1.markov_cluster.MCClusterer;
import org.gephi.clustering.api.Cluster;
import org.gephi.graph.api.GraphModel;
import org.gephi.utils.progress.ProgressTicket;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JiriKrizek
 */
public class MCClustererTest {

    MCClusterer c;

    @Before
    public void setUp() {
        c = new MCClusterer();
        c.setProgressTicket(new ProgressTicketMock());
    }

    @Test
    public void executeImplemented() {
        try {
            c.execute(null);
        } catch (UnsupportedOperationException ex) {
            fail("execute() failed with exception " + ex.getClass());
        }
    }

    @Test
    public void executeGetClusters() {
        Cluster[] result = null;
        try {
             result = c.getClusters();
        } catch (UnsupportedOperationException ex) {
            fail("executeGetClusters() failed with exception " + ex.getClass());
        }
    }

    @Test
    public void executecancel() {
        boolean res = false;
        try {
            res = c.cancel();
        } catch (UnsupportedOperationException ex) {
            fail("executeCancel() failed with exception " + ex.getClass());
        }
        assertTrue(res);
    }

    public void executeSetProgressTicket() {
        try {
            c.setProgressTicket(null);
        } catch (UnsupportedOperationException ex) {
            fail("executeSetProgressTicket() failed with exception " + ex.getClass());
        }
    }
}