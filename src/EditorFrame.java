import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditorFrame extends JFrame {
    // Title stored in the top bar
    private final static String TITLE = "Swing Text Editor";
    // The initial square size of the editor window
    private final static int INITIAL_SIZE = 550;

    // # of spaces used for tabs in editor - arbitrary
    private final static int TAB_SIZE = 5;
    // Initial font size
    private final static float FONT_SIZE = 20f;
    // How much do the font size choices increment from min to max
    private final static float TEXT_INCREMENT = 0.5f;
    // Minimum text size that may be use in editor
    private final static int MIN_TEXT_SIZE = 5;
    // Maximum text size that may be use in editor
    private final static int MAX_TEXT_SIZE = 30;

    private final JPanel mainPanel = new JPanel(new GridBagLayout());
    private final JButton openButton = new JButton("Open");
    private final JButton saveButton = new JButton("Save");
    private final JButton printButton = new JButton("Print");
    private final JComboBox<Float> textSizeBox = new JComboBox<>();
    private final JTextArea textArea = new JTextArea();


    public EditorFrame(){
        super(TITLE);
        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(INITIAL_SIZE, INITIAL_SIZE);

        // Sets our action listeners to reference our action methods
        setupListeners();

        // format the panel
        setupComponents();

        // add the formatted panel to our content pane
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void setupComponents(){

        GridBagConstraints c = new GridBagConstraints();

        // set font size using the default font as reference
        textArea.setFont(textArea.getFont().deriveFont(FONT_SIZE));
        // This border will act as a small margin that will increase readability of what is written in the editor
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // set tab size for text field
        textArea.setTabSize(TAB_SIZE);
        // add text field to our scroll pane
        JScrollPane textField = new JScrollPane(textArea);

        addFontSizePanel(c);

        // Set button constraints
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.9;
        c.gridy = 0;
        c.ipady = 50;
        c.ipadx = 60;

        // Add Open button
        c.gridx = 0;
        mainPanel.add(openButton, c);

        // Add Save button
        c.gridx = 1;
        mainPanel.add(saveButton, c);
/*
        // Add Print button
        c.gridx = 2;
        panel.add(printButton, c);
*/

        // for our text field we will add it below the other two
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        // Gridwidth needs to be larger than the number of elements in the x direction to fill the space.
        c.gridwidth = 5;
        c.weighty = 1;
        mainPanel.add(textField, c);
    }

    private  void addFontSizePanel(GridBagConstraints c){
        // This combo box will act as a way to select our text size.
        // I'm going to attempt to put all the various text manipulation items into one panel
        // Then add the panel in afterwards

        // I want it in the last position in our overall panel
        c.gridx = 3;

        // Create the panel that all of our smaller components will nest into
        JPanel textSize = new JPanel(new GridBagLayout());

        // Init all the values that our min and max allow
        for(float i = MIN_TEXT_SIZE; i <= MAX_TEXT_SIZE; i+=TEXT_INCREMENT){
            textSizeBox.addItem(i);

            // This will set our default font size
            if(i == FONT_SIZE){
                textSizeBox.setSelectedIndex((int)((FONT_SIZE-MIN_TEXT_SIZE)/TEXT_INCREMENT));
            }
        }

        // create new constraints for our smaller grid bag
        GridBagConstraints tConstraints = new GridBagConstraints();

        // create our text field that will remind the user what these values are for
        JTextPane textSizeText = new JTextPane();
        textSizeText.setText("Text Size");

        // add the text box to the top
        tConstraints.gridx = 0;
        tConstraints.fill = GridBagConstraints.BOTH;
        textSize.add(textSizeText, tConstraints);

        // add the drop box and resize button
        tConstraints.gridy = 1;
        tConstraints.gridx = 0;
        textSize.add(textSizeBox,tConstraints);
        mainPanel.add(textSize, c);
    }

    private void setupListeners(){
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        // trying out the new lambda notation
        textSizeBox.addActionListener(e -> resize());
    }

    // WORKS
    private void resize(){
        float newFontSize = textSizeBox.getItemAt(textSizeBox.getSelectedIndex());
        textArea.setFont(textArea.getFont().deriveFont(newFontSize));
        textArea.repaint();
        System.out.println(newFontSize);
    }

    // WORKS
    private void save(){
        JFileChooser chooser = new JFileChooser();
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

    // WORKS - currently without extension filters
    private void open(){
        JFileChooser chooser = new JFileChooser();
        //chooser.addChoosableFileFilter(new FileNameExtensionFilter("txt"));
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
                // we know this file exists because chooser said so
            }
        } else {
            System.out.println("Open cancelled by user.");
        }
    }

    // NOT IMPLEMENTED
    private void print(){

    }
}
