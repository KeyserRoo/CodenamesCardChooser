import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class VisibleWindow extends JFrame {
    Container c = this.getContentPane();
    JButton computeButton;
    JTextField widthtf = new JTextField();
    JTextField heighttf = new JTextField();
    JTextField redAmounttf = new JTextField();
    JTextField blueAmounttf = new JTextField();
    JTextField agentAmounttf = new JTextField();

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
        Dimension nd = new Dimension(80, 20);


        JLabel width = new JLabel("Enter width (recommended 5, max 9)", SwingConstants.CENTER);
        JPanel widthpan = new JPanel();
        widthpan.add(width);
        widthpan.setBounds(50, 100, 300, 30);
        c.add(widthpan);

        JLabel height = new JLabel("Enter height (recommended 5, max 9)", SwingConstants.CENTER);
        JPanel heightpan = new JPanel();
        heightpan.add(height);
        heightpan.setBounds(50, 150, 300, 30);
        c.add(heightpan);

        JLabel redAmount = new JLabel("Enter number of red cards (recommended 8)", SwingConstants.CENTER);
        JPanel redAmountpan = new JPanel();
        redAmountpan.add(redAmount);
        redAmountpan.setBounds(50, 200, 300, 30);
        c.add(redAmountpan);

        JLabel blueAmount = new JLabel("Enter number of blue cards (recommended 8)", SwingConstants.CENTER);
        JPanel blueAmountpan = new JPanel();
        blueAmountpan.add(blueAmount);
        blueAmountpan.setBounds(50, 250, 300, 30);
        c.add(blueAmountpan);

        JLabel agentAmount = new JLabel("Enter number of agents (recommended 1)", SwingConstants.CENTER);
        JPanel agentAmountpan = new JPanel();
        agentAmountpan.add(agentAmount);
        agentAmountpan.setBounds(50, 300, 300, 30);
        c.add(agentAmountpan);

        widthtf.setPreferredSize(nd);
        JPanel widthtfpan = new JPanel();
        widthtfpan.add(widthtf);
        widthtfpan.setBounds(400, 100, 100, 30);
        c.add(widthtfpan);

        heighttf.setPreferredSize(nd);
        JPanel heighttfpan = new JPanel();
        heighttfpan.add(heighttf);
        heighttfpan.setBounds(400, 150, 100, 30);
        c.add(heighttfpan);

        redAmounttf.setPreferredSize(nd);
        JPanel redAmounttfpan = new JPanel();
        redAmounttfpan.add(redAmounttf);
        redAmounttfpan.setBounds(400, 200, 100, 30);
        c.add(redAmounttfpan);

        blueAmounttf.setPreferredSize(nd);
        JPanel blueAmounttfpan = new JPanel();
        blueAmounttfpan.add(blueAmounttf);
        blueAmounttfpan.setBounds(400, 250, 100, 30);
        c.add(blueAmounttfpan);

        agentAmounttf.setPreferredSize(nd);
        JPanel agentAmounttfpan = new JPanel();
        agentAmounttfpan.add(agentAmounttf);
        agentAmounttfpan.setBounds(400, 300, 100, 30);
        c.add(agentAmounttfpan);

        this.setVisible(false);
        this.setVisible(true);
    }

    private void logic() throws WrongInputException {

        //ensures that text input in text fields is indeed numbers
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        };
        widthtf.addKeyListener(kl);
        heighttf.addKeyListener(kl);
        redAmounttf.addKeyListener(kl);
        blueAmounttf.addKeyListener(kl);
        agentAmounttf.addKeyListener(kl);

        //let's compute button do the work
        ActionListener listenerCompute = (event) -> {

            //text fields inputs going as parameters for generator
            int w = Integer.parseInt(widthtf.getText());
            int h = Integer.parseInt(heighttf.getText());
            int r = Integer.parseInt(redAmounttf.getText());
            int b = Integer.parseInt(blueAmounttf.getText());
            int a = Integer.parseInt(agentAmounttf.getText());

            //handles the case of making only one team
            if(r*b==0){
                throw new WrongInputException("Are you sure you want to play 2 team game with only one team?");
            }
            //handles the case of drawing the board with one of sides equal to 0
            if(w*h==0){
                throw new WrongInputException("You can't have a board with one of dimensions equal to 0!");
            }
            //handles the case of making unreasonably big board
            if(w>9||h>9){
                throw new WrongInputException("Please choose smaller board");
            }
            //handles the case of not fitting all cards on the board
            if (r + b + a > w * h) {
                throw new WrongInputException("Too many cards and agents on field of that size!");
            }

            BoardGenerator bg = new BoardGenerator();
            bg.show(w, h, r, b, a);
        };
        computeButton.addActionListener(listenerCompute);
    }
}