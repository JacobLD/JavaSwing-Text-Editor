import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class EditorFrame extends JFrame {
    private final static String TITLE = "Swing Text Editor";
    private final static int INITIAL_SIZE = 550;
    private final static float FONT_SIZE = 20f;
    private final static int TAB_SIZE = 5;
    private final JButton openButton = new JButton("Open");
    private final JButton saveButton = new JButton("Save");
    private final JButton printButton = new JButton("Print");
    private final JTextArea textArea = new JTextArea();
    private final JFileChooser chooser = new JFileChooser();

    public EditorFrame(){
        super(TITLE);
        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(INITIAL_SIZE, INITIAL_SIZE);

        // Create our main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        // Sets our action listeners to reference our action methods
        setupButtons();
        // format the panel
        setupComponents(mainPanel);
        // add the formatted panel to our content pane
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void setupComponents(JPanel panel){
        /*// test field
        JComboBox fontComboBox = new JComboBox(new String[]{"1", "2", "3"});
        // end test*/

        GridBagConstraints c = new GridBagConstraints();
        // set font size using the default font
        textArea.setFont(textArea.getFont().deriveFont(FONT_SIZE));
        // set a small border to push the text away from the sides
        //textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 10));
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        // set tab size for text field
        textArea.setTabSize(TAB_SIZE);
        // add text field to our scroll pane
        JScrollPane textField = new JScrollPane(textArea);

        // Set button constraints
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.9;
        c.gridy = 0;
        c.ipady = 50;
        c.ipadx = 60;

        // Add Open button
        c.gridx = 0;
        panel.add(openButton, c);

        // Add Save button
        c.gridx = 1;
        panel.add(saveButton, c);

        // Add Print button
        c.gridx = 2;
        panel.add(printButton, c);

        /*// Test - add combo box
        c.fill = 0;
        c.gridx = 3;
        panel.add(fontComboBox, c);*/

        // for our text field we will add it below the other two
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        c.weighty = 1;
        panel.add(textField, c);
    }

    private void setupButtons(){
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Save.");
                save();
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print();
            }
        });
    }

    // UNDER CONSTRUCTION - WORKS
    private void save(){
        int returnVal = chooser.showSaveDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION){
            File saveFile = chooser.getSelectedFile();
            FileWriter writer;
            try{
                writer = new FileWriter(saveFile);
                String text = textArea.getText();
                writer.write(text);
                System.out.println("Writing - " + text);
                writer.close();
            } catch (IOException e){
                // we know this file exists because we just made it
            }
        } else {
            System.out.println("Save cancelled by user.");
        }
    }

    // UNDER CONSTRUCTION
    private void open(){
        int returnVal = chooser.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION){
            File openFile = chooser.getSelectedFile();
            Scanner scanner;
            textArea.setText("");
            try{
                scanner = new Scanner(openFile);
                while (scanner.hasNextLine()){
                    textArea.append(scanner.nextLine());
                    if(scanner.hasNextLine()){
                        textArea.append("\n");
                    }
                }
                scanner.close();
            } catch (IOException e){
                // we know this file exists because we just made it
            }
        } else {
            System.out.println("Open cancelled by user.");
        }
    }

    // NOT IMPLEMENTED
    private void print(){

    }
}
