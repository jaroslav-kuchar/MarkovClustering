/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.ArrayList;
import java.util.List;
import org.gephi.data.attributes.type.IntegerList;
import org.gephi.graph.api.Node;

/**
 *
 * @author Jiri Krizek
 */
public class MCNode {

    private final Node node;
    private MCClusterImpl cluster;
    private List<MCClusterImpl> clusterList;
    private final String COLUMN = "MarkovClusters";

    MCNode(Node node) {
        this.node = node;
        this.clusterList = new ArrayList<MCClusterImpl>();
    }

    public MCClusterImpl addToCluster(MCClusterImpl cluster) {
        this.cluster = cluster;
        this.cluster.addNode(this);
        clusterList.add(cluster);
        this.getNode().getAttributes().setValue(COLUMN, this.getClustersIntList());
        return cluster;
    }

    public boolean isMultiCluster() {
        return getNumberOfClusters() > 1;
    }

    public Node getNode() {
        return node;
    }

    private int getNumberOfClusters() {
        return clusterList.size();
    }

    public List<MCClusterImpl> getClusters() {
        return clusterList;
    }

    private IntegerList getClustersIntList() {
        int[] res = new int[clusterList.size()];
        
        for (int i=0; i<clusterList.size(); i++) {
            res[i] = clusterList.get(i).getId();
        }
        
        return new IntegerList(res);
    }
}
