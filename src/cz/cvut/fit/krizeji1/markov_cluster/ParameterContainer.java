/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.krizeji1.markov_cluster;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author JiriKrizek
 */
class ParameterContainer extends Container {

    private final int TEXT_WIDTH = 15;
    private GridBagLayout gridbag;
    private GridBagConstraints constraints;

    public ParameterContainer() {

        initComponents();
    }

    private void initComponents() {
        this.gridbag = new GridBagLayout();
        constraints = new GridBagConstraints();
        this.setLayout(gridbag);
    }
    
    public JCheckBox addCheckbox(String text, boolean checked) {
        JCheckBox checkbox = new JCheckBox(text, checked);
        constraints.fill = GridBagConstraints.REMAINDER;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(checkbox, constraints);
        this.add(checkbox);
        return checkbox;
    }
    
    public JTextField addInput(String text){
        JLabel label = new JLabel(text+":", JLabel.RIGHT);
        JTextField textField = new JTextField();
        textField.setColumns(TEXT_WIDTH);
        
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.ipadx = 10;
        constraints.ipady = 10;
        constraints.insets = new Insets(5, 20, 5, 20);

        this.add(label);

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(textField, constraints);
        this.add(textField);
        return textField;
    }
}
