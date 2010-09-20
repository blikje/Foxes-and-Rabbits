import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class NumericInput
 *
 * @author martin poelman
 * @version 0.1, 16-jun-2010 10:57:27
 */
public class NumericInput extends JTextField {
    //Constants to identify which type of number this textField should hold
    public static final int INTEGER_INPUT = 0;
    public static final int PERCENTAGE_INPUT = 1;

    //Regex patterns, used to limit the input of the user
    private static final String[] REGEX_FORMATS = {
            "[0-9]+",
            "^(0*100{1,1}[.,]?((?<=[.,])0{1,2})?$)|(^0*\\d{0,2}[.,]?((?<=[.,])\\d{1,2})?)$"
    };

    //A boolean for each type, used to decide if the value should be exported as a decimal type
    private static final boolean[] DECIMAL_INPUT = {
            false,
            true
    };

    /**
     * Constructor
     *
     * @param inputType The type of value this field is going to hold
     */
    public NumericInput(final int inputType){
        super();
        addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent event) { //Detect which key is pressed and decide if it's allowed

                //Add the new char to the old text
                char c = event.getKeyChar();
                String currentText = getText();
                int cursor = getCaretPosition();
                String[] cursorSplit = {
                        currentText.substring(0, cursor),
                        currentText.substring(cursor)
                    };
                String newText = cursorSplit[0] + c + cursorSplit[1];

                //Check if the new text would match against the regex pattern, if so put it in the textField
                boolean decimal = DECIMAL_INPUT[inputType];
                String regex = REGEX_FORMATS[inputType];
                if(newText.matches(regex)){
                    setText(decimal ? Double.valueOf(newText).toString() : Integer.valueOf(newText).toString());
                    setCaretPosition(cursor + 1 > getText().length() ? cursor : cursor + 1);
                }

                //Always consume the event
                event.consume();
            }
        });
    }

    /**
     * Get the value of this textField as a double
     * @return The double value
     */
    public double getDoubleValue(){
        return Double.valueOf(getText());
    }


    /**
     * Get the value of this textField as an int
     * @return The int value
     */
    public int getIntValue(){
        return Integer.valueOf(getText());
    }
}