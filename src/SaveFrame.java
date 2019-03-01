import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class SaveFrame extends JFrame {

    private final static String TITLE = "Save";
    private final static int INITIAL_SIZE = 300;
    private final JFileChooser chooser = new JFileChooser();
    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
    private JTextArea textArea;

    public SaveFrame(JTextArea textArea){
        super(TITLE);
        this.textArea = textArea;
        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(INITIAL_SIZE, INITIAL_SIZE);
        JPanel mainpanel = new JPanel(new GridBagLayout());
        formatFrame(mainpanel);
        getContentPane().add(mainpanel);
    }

    private void formatFrame(JPanel panel){
        chooser.setFileFilter(filter);
        panel.add(chooser);
    }
}
