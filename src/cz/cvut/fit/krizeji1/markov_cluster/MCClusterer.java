package cz.cvut.fit.krizeji1.markov_cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import multicolour.attribute.GraphColorizer;
import org.gephi.clustering.api.Cluster;
import org.gephi.clustering.spi.Clusterer;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.data.attributes.type.IntegerList;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.Node;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 *
 * @author JiriKrizek
 */
public class MCClusterer implements Clusterer, LongTask {

    static String PLUGIN_NAME = "Markov Clustering";
    static String PLUGIN_DESCRIPTION = "Markov Chain Clustering";
    public static final String COLUMN_DEFAULT_VALUE = "-1";
    private final String COLUMN = "MarkovClusters";
    static final double EPSILON = 0.00000001; 
    boolean isCancelled = false;
    ProgressTicket progress = null;
    private GraphModel graphModel;
    private Graph tempGraph;
    private static final Logger logger = Logger.getLogger(MCClusterer.class.getName());
    // Algorithm settings
    private double power = 2L;
    private double inflation = 2L;
    private double prune = 0.02;
    private boolean selfLoop = true;
    MclAlgorithm mclComputation;
    private NodesMatrix matrix;
    private AttributeModel attrModel;
    private MCClusterImpl[] result;
    private boolean extraClusters = false;

    @Override
    public void execute(org.gephi.graph.api.GraphModel gm) {
        long startAlg = System.currentTimeMillis();
        this.graphModel = gm;
        this.isCancelled = false;
        if (inflation < 0) {
            throw new IllegalStateException("inflation parameter must be >= 0");
        }

        if (progress != null) {
            progress.start();
            progress.progress(NbBundle.getMessage(MCClusterer.class, "MCClusterer.setup"));
        }

        logger.log(Level.INFO, "Algorithm started\nParameters: \n\tpower: {0}\n\tinflation: {1}\n\tprune: {2}", new Object[]{power, inflation, Double.toString(prune)});

        // Preparing column for clusters data
        prepareColumn();

        GraphView view = graphModel.newView();
        tempGraph = graphModel.getUndirectedGraph(view);
        tempGraph.readLock();

        int N = tempGraph.getNodeCount();

        //matrix = new PrintableSparseMatrix(N, N);
        matrix = new PrintableDenseMatrix64F(N, N);
        matrix.setEpsilon(EPSILON);

        NodesMap mapping = new NodesMap(tempGraph);

        // Create an associated matrix
        for (Edge edge : tempGraph.getEdges()) {
            Node sourceNode = edge.getSource();
            Node dstNode = edge.getTarget();

            int src = mapping.getSequentialIdFor(sourceNode);
            int dst = mapping.getSequentialIdFor(dstNode);

            matrix.set(src, dst, 1);
            matrix.set(dst, src, 1);
        }

        MclParameters mclParam = new MclParameters(selfLoop, inflation, prune, power, N);

        mclComputation = new MclAlgorithm(matrix, mapping, mclParam);
        try {
            if (progress != null) {
                this.progress.progress(NbBundle.getMessage(MCClusterer.class, "MCClusterer.computation"));
            }
            long startInnerAlg = System.currentTimeMillis();
            matrix = mclComputation.execute();
            long endInnerAlg = System.currentTimeMillis() - startInnerAlg;
            System.out.println("endInnerAlg: " + endInnerAlg);
        } catch (UserCancelledException e) {
            logger.log(Level.INFO, "Computation cancelled by user");
            return;
        }

        //
        // MCL algorithm beginning
        // 1. Input is an un-directed graph, power parameter e, inflation parameter r and boolean add self loops
        // 2. Create the associated matrix
        // 3. Add self loops to each node (optional)
        // 4. Normalize the matrix
        // for 
        //   5. Expand
        //   6. Inflate by taking inflation of the resulting matrix with parameter r
        // endfor 
        // 7. Repeat steps 5 and 6 until a steady state is reached (convergence)
        // 8. Interpret resulting matrix to discover clusters
        // MCL algorithm end

        // resize matrix to have only rows that contain some non zero value 
        // (number of rows equals number of clusters)
        matrix.removeZeroRows();

        // save to gephi attributes
        saveResults(mapping);

        tempGraph.readUnlockAll();
        if (progress != null) {
            this.progress.finish(NbBundle.getMessage(MCClusterer.class, "MCClusterer.finished"));
        }
        long endAlg = System.currentTimeMillis() - startAlg;
        System.out.println("endAlg: " + endAlg);
    }

    @Override
    public Cluster[] getClusters() {
        if (result == null || result.length == 0) {
            return null;
        } else {
            return result;
        }
    }

    @Override
    public boolean cancel() {
        if (mclComputation != null) {
            this.progress.finish(NbBundle.getMessage(MCClusterer.class, "MCClusterer.cancelled"));
            return mclComputation.cancel();
        }
        return false;
    }

    @Override
    public void setProgressTicket(ProgressTicket pt) {
        this.progress = pt;
    }

    /**
     * @return the power
     */
    public double getPower() {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(double power) {
        this.power = power;
    }

    /**
     * @return the inflation
     */
    public double getInflation() {
        return inflation;
    }

    /**
     * @param inflation the inflation to set
     */
    public void setInflation(double inflation) {
        this.inflation = inflation;
    }

    /**
     * @return the prune
     */
    public double getPrune() {
        return prune;
    }

    /**
     * @param prune the prune to set
     */
    public void setPrune(double prune) {
        this.prune = prune;
    }

    /**
     * @return the isSelfLoop
     */
    public boolean isSelfLoop() {
        return selfLoop;
    }

    /**
     * @param isSelfLoop the isSelfLoop to set
     */
    public void setSelfLoop(boolean selfLoop) {
        this.selfLoop = selfLoop;
    }

    public static boolean precisionEqualZero(double a) {
        return MCClusterer.precisionEqual(a, 0);
    }

    public static boolean precisionEqual(double a, double b) {
        return Math.abs(a - b) <= ((Math.abs(a) > Math.abs(b) ? Math.abs(b) : Math.abs(a)) * EPSILON);
    }

    private void prepareColumn() {
        if (this.attrModel == null) {
            this.attrModel = getAttrModel();
        }
        AttributeTable nodeTable = attrModel.getNodeTable();
        AttributeColumn col = nodeTable.getColumn(COLUMN);
        if (col != null) {
            nodeTable.replaceColumn(col, COLUMN, COLUMN, AttributeType.LIST_INTEGER, AttributeOrigin.COMPUTED, COLUMN_DEFAULT_VALUE);
        } else {
            nodeTable.addColumn(COLUMN, COLUMN, AttributeType.LIST_INTEGER, AttributeOrigin.COMPUTED, COLUMN_DEFAULT_VALUE);
        }
    }

    private void saveResults(NodesMap mapping) {
        int numberOfClusters = matrix.getNumRows();
        Map<Integer, MCClusterImpl> resMap = new HashMap<Integer, MCClusterImpl>(numberOfClusters);

        logger.log(Level.INFO, "Number of clusters is {0}", numberOfClusters);
        if (this.attrModel == null) {
            this.attrModel = getAttrModel();
        }

        // read by columns down then right
        for (int nodeId = 0; nodeId < matrix.getNumCols(); nodeId++) {
            // for every column, which clusters node belongs to
            //Node currentNode = mapping.getNodeForId(nodeId);
            MCNode mcCurrentNode = new MCNode(mapping.getNodeForId(nodeId));

            for (int cluster = 0; cluster < matrix.getNumRows(); cluster++) {
                // Initialize cluster
                if ((!resMap.containsKey(cluster))) {
                    MCClusterImpl cl = new MCClusterImpl(cluster);

                    int clusterHumanReadableNum = cluster + 1;
                    cl.setName("Cluster " + clusterHumanReadableNum);

                    resMap.put(cluster, cl);
                }

                double clusterClassification = matrix.get(cluster, nodeId);


                // clusterClassification not zero or really close to zero - has some cluster classification
                if (!precisionEqual(clusterClassification, 0)) {
                    //result[cluster].addNode(currentNode);
                    MCClusterImpl clust = resMap.get(cluster);
                    clust.addNode(mcCurrentNode);
                    mcCurrentNode.addToCluster(clust);
                    //node {0} belongs to cluster {1}", new Object[]{cluster, node}
                }
            }

            result = new MCClusterImpl[resMap.size()];

            for (Entry<Integer, MCClusterImpl> entry : resMap.entrySet()) {
                Integer key = entry.getKey();
                MCClusterImpl value = entry.getValue();

                result[key] = value;
            }

            Map<List<Integer>, MCClusterImpl> multipleClustMap = new HashMap<List<Integer>, MCClusterImpl>();

            int multipClId = matrix.getNumRows();
            if (extraClusters && mcCurrentNode.isMultiCluster()) {

                List<Integer> list = new ArrayList<Integer>();
                for (MCClusterImpl c : mcCurrentNode.getClusters()) {
                    list.add(c.getId());
                }
                MCClusterImpl clust;
                if (multipleClustMap.containsKey(list)) {
                    clust = multipleClustMap.get(list);
                } else {
                    clust = new MCClusterImpl(multipClId);
                    clust.addNode(mcCurrentNode);
                    multipleClustMap.put(list, clust);
                    resMap.put(multipClId++, clust);
                }
                StringBuilder humanReadableName = new StringBuilder("Clusters: [");
                for (int i = 0; i < list.size(); i++) {
                    int humanNum = list.get(i) + 1;
                    humanReadableName.append(humanNum);
                    if ((i + 1) < list.size()) {
                        humanReadableName.append(", ");
                    }
                }
                humanReadableName.append("]");
                clust.setName(humanReadableName.toString());
            }
        }

        AttributeTable nodeTable = Lookup.getDefault().lookup(AttributeController.class).getModel().getNodeTable();
        GraphColorizer c = new GraphColorizer(nodeTable);
        c.colorizeGraph(result);

        for (Node n : tempGraph.getNodes()) {
            int size = ((IntegerList) n.getAttributes().getValue(COLUMN)).size();
        }
    }

    private AttributeModel getAttrModel() {
        return Lookup.getDefault().lookup(AttributeController.class).getModel();
    }

    /**
     * @return the extraClusters
     */
    public boolean isExtraClusters() {
        return extraClusters;
    }

    /**
     * @param extraClusters the extraClusters to set
     */
    public void setExtraClusters(boolean extraClusters) {
        this.extraClusters = extraClusters;
    }
}
