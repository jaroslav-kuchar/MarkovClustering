package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.gephi.clustering.api.Cluster;
import org.gephi.graph.api.Node;

/**
 *
 * @author JiriKrizek
 */
public class MCClusterImpl implements Cluster {

    private List<MCNode> nodes = new ArrayList<MCNode>();
    private String clusterName = "untitled";
    private Node metaNode = null;
    private int id;

    public MCClusterImpl(int id) {
        this.id = id;
    }

    public MCClusterImpl(List<MCNode> nodeList, int id) {
        this.nodes = nodeList;
        this.id = id;
    }
    
    public void addNode(MCNode node) {
        if(!this.nodes.contains(node)) {
            this.nodes.add(node);
        }
    }
    
    public void setName(String clusterName) {
        this.clusterName = clusterName;
    }
    
    @Override
    public Node[] getNodes() {
        Node[] res = new Node[this.nodes.size()];
        int pos = 0;
        
        Iterator<MCNode> i = this.nodes.iterator();
        while(i.hasNext()) {
            res[pos++] = i.next().getNode();
        }
        return res;
    }

    @Override
    public int getNodesCount() {
        return this.nodes.size();
    }

    @Override
    public String getName() {
        return clusterName;
    }

    @Override
    public Node getMetaNode() {
        return this.metaNode;
    }

    @Override
    public void setMetaNode(Node node) {
        this.metaNode = node;
    }
    
    public int getId() {
        return this.id;
    }
}
