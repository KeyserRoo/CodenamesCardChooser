import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class VisibleWindow extends JFrame {

    JTextField[] jTextFields = new JTextField[5];
    Container c = this.getContentPane();
    JButton computeButton;

    public VisibleWindow() {
        setupWindow();
        setButtons();
        setFields();
        logic();
    }

    public void setupWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CodenamesCardChooser");
        this.setSize(600, 700);
        this.setResizable(false);
        c.isOpaque();
        c.setBackground(new Color(0x333333));
        this.setVisible(true);
    }

    private void setButtons() {
        computeButton = new JButton("Compute");
        computeButton.setBounds((c.getWidth() - 100) / 2, c.getHeight() - (c.getHeight() / 3), 100, 60);
        c.setLayout(null);
        c.add(computeButton);
    }

    private void setFields() {

        //creation of JLabels
        String[] strings = {"Enter width (recommended 5, max 9)",
                "Enter height (recommended 5, max 9)",
                "Enter number of red cards (recommended 8)",
                "Enter number of blue cards (recommended 8)",
                "Enter number of agents (recommended 1)"
        };

        JLabel[] jLabels = new JLabel[5];
        JPanel[] jPanels = new JPanel[5];

        fieldsCreatorLabel(strings, jLabels, jPanels);


        //creation of JTextFields
        JPanel[] jPanels1 = new JPanel[5];

        fieldsCreatorTextField(jTextFields, jPanels1);


        this.setVisible(false);
        this.setVisible(true);
    }

    private void fieldsCreatorLabel(String[] s, JLabel[] jl, JPanel[] jp) {
        for (int i = 0; i < 5; i++) {
            jl[i] = new JLabel(s[i], SwingConstants.CENTER);
            jp[i] = new JPanel();
            jp[i].add(jl[i]);
            jp[i].setBounds(50, 100 + i * 50, 300, 30);
            c.add(jp[i]);
        }
    }

    private void fieldsCreatorTextField(JTextField[] jtf, JPanel[] jp) {
        Dimension nd = new Dimension(80, 20);
        for (int i = 0; i < 5; i++) {
            jtf[i] = new JTextField();
            jtf[i].setPreferredSize(nd);
            jp[i] = new JPanel();
            jp[i].add(jtf[i]);
            jp[i].setBounds(400, 100 + i * 50, 100, 30);
            c.add(jp[i]);
        }
    }

    private void logic() throws WrongInputException {

        //ensures that text input in text fields is indeed numbers
        for (JTextField jTextField : jTextFields) {
            jTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                        e.consume();
                    }
                }
            });
        }

        //makes compute button do the work
        ActionListener listenerCompute = (event) -> {

            //text fields inputs going as parameters for generator
            // params are as following: [0] - width, [1] - height, [2] - red cards, [3] - blue cards, [4] - agents
            int[] params = new int[5];
            for (int i = 0; i < params.length; i++) {
                String text = jTextFields[i].getText();
                if (!(text.isEmpty())) {
                    params[i] = Integer.parseInt(text);
                }else{
                    jTextFields[i].setText("0");
                }
            }

            //handles the case of making only one team
            if (params[2] * params[3] == 0) {
                throw new WrongInputException("Please input at least 1 card for each team");
            }
            //handles the case of drawing the board with at least one of sides equal to 0, or more than 9
            if ((params[0] * params[1] == 0) || (params[0] > 9 || params[1] > 9)) {
                throw new WrongInputException("Each board dimension need to be at least 1 tile, and at most 9 tiles");
            }
            //handles the case of not fitting all cards on the board
            if (params[2] + params[3] + params[4] > params[0] * params[1]) {
                throw new WrongInputException("Too many cards and agents on field of that size!");
            }

            BoardGenerator bg = new BoardGenerator();
            bg.show(params[0], params[1], params[2], params[3], params[4]);
        };
        computeButton.addActionListener(listenerCompute);
    }
}