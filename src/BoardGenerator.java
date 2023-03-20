import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

public class BoardGenerator {
    public enum cardType {red, blue, agent, neutral}

    //generates the grid of desired size containing desired number of entities, and returns result as an array
    private cardType[] generateBoard(int width, int height, int redAmount, int blueAmount, int agentAmount) {
        int temp;
        cardType[] board = new cardType[width * height];
        Arrays.fill(board, cardType.neutral);

        //creates entities on cells marked neutral, in other case rolls again
        //might make 'while' a method on its own later
        while (agentAmount > 0) {
            temp = (int) (Math.random() * board.length);
            if (board[temp] == cardType.neutral) {
                board[temp] = cardType.agent;
                agentAmount--;
            }
        }
        while (redAmount > 0) {
            temp = (int) (Math.random() * board.length);
            if (board[temp] == cardType.neutral) {
                board[temp] = cardType.red;
                redAmount--;
            }
        }
        while (blueAmount > 0) {
            temp = (int) (Math.random() * board.length);
            if (board[temp] == cardType.neutral) {
                board[temp] = cardType.blue;
                blueAmount--;
            }
        }
        return board;
    }

    //takes array and transforms it into its visual representation (the hardware randomizer looks that way)
    public void show(int width, int height, int redAmount, int blueAmount, int agentAmount) {
        cardType[] board = generateBoard(width, height, redAmount, blueAmount, agentAmount);

        JFrame frame = new JFrame();
        // create the panel that holds the grid
        JPanel gridPanel = new JPanel(new GridLayout(height, width));

        for (int i = 0; i < board.length / height; i++) {
            for (int j = 0; j < board.length / width; j++) {
                //picks the color based on the entity on the panel
                ImageIcon imageIcon = switch (board[i * height + j]) {
                    case red -> new ImageIcon(Objects.requireNonNull(getClass().getResource("/blocks/red_block.png")));
                    case blue -> new ImageIcon(Objects.requireNonNull(getClass().getResource("/blocks/blue_block.png")));
                    case agent -> new ImageIcon(Objects.requireNonNull(getClass().getResource("/blocks/black_block.png")));
                    case neutral -> new ImageIcon(Objects.requireNonNull(getClass().getResource("/blocks/white_block.png")));
                };
                JPanel panel = new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),null);
                    }
                };
                //ensures that it looks somewhat presentable
                panel.setPreferredSize(new Dimension(120, 120));
                gridPanel.add(panel);
            }
        }

        // create the panel that holds the bottom panel
        //this is where the info on the player who starts is shown
        JLabel lab = new JLabel("Starting player");
        JPanel bottomPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int a = (int) (Math.random() * 10);
                g.setColor(a % 2 == 0 ? new Color(51, 80, 171) : new Color(170, 51, 56));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        lab.setFont(new Font("Arial",Font.BOLD,40));
        bottomPanel.add(lab);
        bottomPanel.setPreferredSize(new Dimension(width*120, 60));

        // create a panel to hold both the grid panel and the bottom panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(gridPanel);
        mainPanel.add(bottomPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}