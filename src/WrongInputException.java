import javax.swing.*;
import java.awt.*;

public class WrongInputException extends RuntimeException{
    public WrongInputException(String message) {
        JFrame frame = new JFrame("error");
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial",Font.PLAIN,70));
        frame.setResizable(false);
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
