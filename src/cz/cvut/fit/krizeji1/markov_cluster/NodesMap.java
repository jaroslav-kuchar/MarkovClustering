/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.Soundbank;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;

/**
 *
 * @author Jiri Krizek
 */
class NodesMap {
    // Mapping of nodeId to sequential id
    Map<Integer, Integer> nodeTable = new HashMap<Integer, Integer>();
    // Mapping of sequential id to nodeId
    Map<Integer, Integer> idsTable = new HashMap<Integer, Integer>();
    private final Graph graph;

    public NodesMap(Graph graph) {
        this.graph = graph;
        int i = 0;
	for (Node node : graph.getNodes()) {
	    nodeTable.put(node.getId(), new Integer(i));
	    idsTable.put(i, node.getId());
	    i++;
	}
    }
    
    public int getSequentialIdFor(Node node) {
        return nodeTable.get(node.getId()).intValue();
    }
    
    public Node getNodeForId(int id) {
        return graph.getNode(idsTable.get(id));
    }
    
    
}



        
	
        
