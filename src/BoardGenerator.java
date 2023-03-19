import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

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
                Color color = switch (board[i * height + j]) {
                    case red -> new Color(232, 72, 72);
                    case blue -> new Color(0, 98, 168);
                    case agent -> Color.black;
                    case neutral -> Color.white;
                };
                JPanel panel = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(color);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
                //ensures that it looks somewhat presentable
                panel.setPreferredSize(new Dimension(100, 100));
                gridPanel.add(panel);
            }
        }

        // create the panel that holds the bottom panel
        //this is where the info on the player who starts is shown
        JPanel bottomPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int a = (int) (Math.random() * 10);
                g.setColor(a % 2 == 0 ? Color.blue : Color.red);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bottomPanel.setPreferredSize(new Dimension(500, 50));

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