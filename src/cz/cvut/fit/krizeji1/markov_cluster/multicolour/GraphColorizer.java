package cz.cvut.fit.krizeji1.markov_cluster.multicolour;

import org.gephi.clustering.api.Cluster;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.Node;

/**
 *
 * @author Jiri Krizek <krizeji1 at fit.cvut.cz>
 */
public class GraphColorizer {

    private final AttributeTable nodeTable;

    public GraphColorizer(AttributeTable nodeTable) {
        this.nodeTable = nodeTable;
    }

    public void colorizeGraph(Cluster[] result) {
        if(result == null) {
            return;
        }
        AttributeColumn colColours = nodeTable.getColumn(Constants.COLOR_COLUMN);
        // prepare column for color values (replace if needed)
        if (colColours != null) {
            nodeTable.replaceColumn(colColours,
                    Constants.COLOR_COLUMN, Constants.COLOR_COLUMN,
                    AttributeType.STRING, AttributeOrigin.COMPUTED,
                    Constants.COLOR_COL_DEFAULT_VALUE);
        } else {
            nodeTable.addColumn(Constants.COLOR_COLUMN, Constants.COLOR_COLUMN,
                    AttributeType.STRING, AttributeOrigin.COMPUTED,
                    Constants.COLOR_COL_DEFAULT_VALUE);
        }

        Color color = new Color();
        for (Cluster res : result) {
            for (Node n : res.getNodes()) {
                String value = (String) n.getAttributes().getValue(Constants.COLOR_COLUMN);
                
                //shown in Preview window
                // replace default value with first color
                if (value.equals(Constants.COLOR_COL_DEFAULT_VALUE)) {
                    n.getAttributes().setValue(Constants.COLOR_COLUMN, color.getColorAsInt());
                    // append value to previous colours
                } else {
                    n.getAttributes().setValue(Constants.COLOR_COLUMN, value + "," + color.getColorAsInt());
                }
                
                //shown in Overview window
                n.getNodeData().setColor(color.getR(), color.getG(), color.getB());
            }
            // generate new color
            color.randomize();
        }
    }
}
