import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates a Textfield with a Label in front of it.
 *
 * @author Jelko Jerbic, Eduard Hovinga, Martin Poelman
 *
 */
public class JLabeledTextField extends JPanel{
    private JTextField textField;

	/**
	 * Creates the TextField and Label
	 * @param name The name of the Label
	 * @param value The value that has to be displayed in the textfield (double)
	 */
	public JLabeledTextField(String name, double value)
	{
		this.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel(name);
		textField = new JTextField();
		
		textField.setText("" + value);
		
		this.add(nameLabel);
		this.add(textField);
		
	}
	
	/**
	 * Get the value of the textfield
	 *
	 * @return the value of the textfield converted to double
	 */
	public double getValue()
	{
		return Double.parseDouble(textField.getText());
	}
	
}
