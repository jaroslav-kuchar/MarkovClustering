/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import org.gephi.clustering.spi.Clusterer;
import org.gephi.clustering.spi.ClustererBuilder;
import org.gephi.clustering.spi.ClustererUI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author JiriKrizek
 */
@ServiceProvider(service = ClustererBuilder.class)
public class MCClustererBuilder<T> implements ClustererBuilder {

    @Override
    public Clusterer getClusterer() {
        return new MCClusterer();
    }

    @Override
    public String getName() {
        return MCClusterer.PLUGIN_NAME;
    }

    @Override
    public String getDescription() {
        return MCClusterer.PLUGIN_DESCRIPTION;
    }

    @Override
    public Class getClustererClass() {
        return MCClusterer.class;
    }

    @Override
    public ClustererUI getUI() {
        return new MCClustererUI();
    }
    
}
