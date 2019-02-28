import javax.swing.*;
import java.awt.*;

public class EditorFrame extends JFrame {
    private final static String TITLE = "Swing Text Editor";
    private final static int INITIAL_SIZE = 800;

    public EditorFrame(){
        super(TITLE);
        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(INITIAL_SIZE, INITIAL_SIZE);

        Container mainContainer = this.getContentPane();
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setupComponents(mainPanel, constraints);

        mainContainer.add(mainPanel);
        setVisible(true);

    }

    private void setupComponents(JPanel panel, GridBagConstraints c){
        JButton open = new JButton("Open");
        JButton save = new JButton("Save");
        JScrollPane textField = new JScrollPane(new JTextArea());

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(open, c);

        // for save move out gridx index over 1
        c.gridx = 1;
        panel.add(save, c);

        // for our text field we will add it below the other two
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weighty = 1;
        panel.add(textField, c);

    }
}
