/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.gephi.clustering.spi.Clusterer;
import org.gephi.clustering.spi.ClustererUI;
import org.openide.util.NbBundle;

/**
 *
 * @author JiriKrizek
 */
public class MCClustererUI implements ClustererUI {

    private JPanel panel;
    private MCClusterer clusterer;
    private JTextField inflationTextField;
    private JTextField powerTextField;
    private JTextField pruneTextField;

    ;
    private JCheckBox checkbox;
    private JCheckBox checkboxMultiClust;
    public MCClustererUI() {
        panel = new JPanel();
        initComponents();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setup(Clusterer clstr) {
        this.clusterer = (MCClusterer) clstr;
        this.powerTextField.setText(clusterer.getPower() + "");
        this.inflationTextField.setText(clusterer.getInflation() + "");
        this.pruneTextField.setText(clusterer.getPrune() + "");
        this.checkbox.setSelected(clusterer.isSelfLoop());
        this.checkboxMultiClust.setSelected(clusterer.isExtraClusters());
    }

    @Override
    public void unsetup() {
        try {
            clusterer.setPower(Double.parseDouble(powerTextField.getText()));
        } catch (NumberFormatException nfex) {
            // ignore invalid value
        }

        try {
            clusterer.setInflation(Double.parseDouble(inflationTextField.getText()));
        } catch (NumberFormatException nfex) {
            // ignore invalid value
        }

        try {
            clusterer.setPrune(Double.parseDouble(pruneTextField.getText()));
        } catch (NumberFormatException nfex) {
            // ignore invalid values
        }
        
        clusterer.setSelfLoop(checkbox.isSelected());
        clusterer.setExtraClusters(checkboxMultiClust.isSelected());
    }

    private void initComponents() {
        ParameterContainer container = new ParameterContainer();
        
        String chLabel = NbBundle.getMessage(MCClustererUI.class, "MCClustererUI.selfLoopsLabel");
        this.checkbox = container.addCheckbox(chLabel, true);
        
        String multiClusterLabel = NbBundle.getMessage(MCClustererUI.class, "MCClustererUI.multiClusterLabel");
        this.checkboxMultiClust = container.addCheckbox(multiClusterLabel, true);
        
        String pwrLabel = NbBundle.getMessage(MCClustererUI.class, "MCClustererUI.powerLabel");
        this.powerTextField = container.addInput(pwrLabel);
        
        String inflLabel = NbBundle.getMessage(MCClustererUI.class, "MCClustererUI.inflationLabel");
        this.inflationTextField = container.addInput(inflLabel);
        
        String pruneLabel = NbBundle.getMessage(MCClustererUI.class, "MCClustererUI.pruneLabel");
        this.pruneTextField = container.addInput(pruneLabel);

        panel.add(container);
    }

    private double getDoubleValueFromTextField(JTextField field) {
        try {
            String text = field.getText();
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public double getPrune() {
        return getDoubleValueFromTextField(pruneTextField);
    }

    public double getInflation() {
        return getDoubleValueFromTextField(inflationTextField);
    }

    public double getPower() {
        return getDoubleValueFromTextField(powerTextField);
    }
    
    
}