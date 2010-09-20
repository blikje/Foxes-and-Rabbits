import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Creates the OptionPanel
 * 
 * @author Jelko Jerbic, Eduard Hovinga, Martin Poelman
 *
 */
public class OptionPanel extends  JFrame {

    private JPanel optionPanel;

	private JLabeledTextField rabbitField1, rabbitField2, rabbitField3, rabbitField4, rabbitField5;
	private JLabeledTextField foxField1, foxField2, foxField3, foxField4;
	private JLabeledTextField bearField1, bearField2, bearField3, bearField4;
	

    /**
     * Constructor for objects of class OptionPanel
     */
    public OptionPanel()
    {
    	setLocation(100, 50);

    	optionPanel = new JPanel();
    	optionPanel.setLayout(new GridLayout(8,3,6,12));
    	addLabels();
    	add(optionPanel);
    	pack();
    	
    }

    /**
     * Add's the Labels to the OptionPanel
     */
    public void addLabels()
    {
    	//Rabbit
    	rabbitField1 = new JLabeledTextField("Breeding Age",Rabbit.BREEDING_AGE);
    	rabbitField2 = new JLabeledTextField("Maximum Age",Rabbit.MAX_AGE);
    	rabbitField3 = new JLabeledTextField("Breeding %",Rabbit.BREEDING_PROBABILITY);
    	rabbitField4 = new JLabeledTextField("Maximum Litter",Rabbit.MAX_LITTER_SIZE);
    	rabbitField5 = new JLabeledTextField("Myxo Gene %",Rabbit.MYXO_GENE_PERCENTAGE);

    	//Fox
    	foxField1 = new JLabeledTextField("Breeding Age",Fox.BREEDING_AGE);
    	foxField2 = new JLabeledTextField("Maximum Age",Fox.MAX_AGE);
    	foxField3 = new JLabeledTextField("Breeding %",Fox.BREEDING_PROBABILITY);
    	foxField4 = new JLabeledTextField("Maximum Litter",Fox.MAX_LITTER_SIZE);

    	//Bear
    	bearField1 = new JLabeledTextField("Breeding Age",Bear.BREEDING_AGE);
    	bearField2 = new JLabeledTextField("Maximum Age",Bear.MAX_AGE);
    	bearField3 = new JLabeledTextField("Breeding %",Bear.BREEDING_PROBABILITY);
    	bearField4 = new JLabeledTextField("Maximum Litter",Bear.MAX_LITTER_SIZE);
    	
    	//Add Images and Headers
    	optionPanel.add(new JLabel(ImageClass.getImage("rabbit.JPG",50)));
    	optionPanel.add(new JLabel(ImageClass.getImage("fox.JPG",50)));
    	optionPanel.add(new JLabel(ImageClass.getImage("bear.JPG",50)));
    	optionPanel.add(new JLabel("Rabbits"));
    	optionPanel.add(new JLabel("Foxes"));
    	optionPanel.add(new JLabel("Bears"));

    	//Add them to the Panel
    	optionPanel.add(rabbitField1);
    	optionPanel.add(foxField1);
    	optionPanel.add(bearField1);
    	
    	optionPanel.add(rabbitField2);
    	optionPanel.add(foxField2);
    	optionPanel.add(bearField2);
    	
    	optionPanel.add(rabbitField3);
    	optionPanel.add(foxField3);
    	optionPanel.add(bearField3);
    	
    	optionPanel.add(rabbitField4);
    	optionPanel.add(foxField4);
    	optionPanel.add(bearField4);
    	
    	optionPanel.add(rabbitField5);
    	optionPanel.add(new JLabel(""));
    	optionPanel.add(new JLabel(""));
    	
    	//Create Listeners for the Buttons
    	JButton okButton = new JButton("OK");
    	okButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        okPressed();
                    }});
    	JButton cancelButton = new JButton("Cancel");
    	cancelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }});
    	
    	JPanel buttons = new JPanel();
    	buttons.add(okButton);
    	buttons.add(cancelButton);
    	
    	optionPanel.add(buttons);
    }
    
    /**
     * Method for the OK button, Get's the value's from the fields and updates the variable's
     */
    private void okPressed()
    {
    	Rabbit.BREEDING_AGE = (int)rabbitField1.getValue();
    	Rabbit.MAX_AGE = (int)rabbitField2.getValue();
    	Rabbit.BREEDING_PROBABILITY = rabbitField3.getValue();
    	Rabbit.MAX_LITTER_SIZE = (int)rabbitField4.getValue();
    	Rabbit.MYXO_GENE_PERCENTAGE = (int)rabbitField5.getValue();
    	
    	Fox.BREEDING_AGE = (int)foxField1.getValue();
    	Fox.MAX_AGE = (int)foxField2.getValue();
    	Fox.BREEDING_PROBABILITY = foxField3.getValue();
    	Fox.MAX_LITTER_SIZE = (int)foxField4.getValue();
    	
    	Bear.BREEDING_AGE = (int)bearField1.getValue();
    	Bear.MAX_AGE = (int)bearField2.getValue();
    	Bear.BREEDING_PROBABILITY = bearField3.getValue();
    	Bear.MAX_LITTER_SIZE = (int)bearField4.getValue();
    	
    	
    	dispose(); //closes the option panel
    }
   
}
