package TextEditor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * Created by shund on 27.02.2017.
 */
public class FrameWindow  {
    private JFrame frameWindow;
    private FileHandler fileHandler;
    private TextPanel textPanel;
    private JScrollPane scrollPane;

    public FrameWindow(){
        frameWindow = new JFrame("Text Editor");
        frameWindow.setSize(800, 600);
        frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWindow.setLayout(new BorderLayout());
        frameWindow.setJMenuBar(createJMenuBar());
        frameWindow.add(createJToolBar(), BorderLayout.NORTH);

        textPanel = new TextPanel(this);
        scrollPane = new JScrollPane(textPanel);
        scrollPane.getViewport().setBackground(Color.white);

        frameWindow.add(scrollPane, BorderLayout.CENTER);

        fileHandler = new FileHandler(this);

        textPanel.getText().createInput();

        addActionListener();
        frameWindow.setVisible(true);
    }

    private JMenuBar createJMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileJMenu());
        return menuBar;
    }
    private JMenu createFileJMenu(){
        JMenu fileJMenu = new JMenu("File");
        fileJMenu.add(createJMenuItem("Open", "openMenu.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                fileHandler.openFile();
            }
        }));
        fileJMenu.add(createJMenuItem("Save", "saveMenu.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

            }
        }));
        fileJMenu.addSeparator();
        fileJMenu.add(createJMenuItem("Exit", "exitMenu.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }));
        return  fileJMenu;
    }
    private JMenuItem createJMenuItem(String name, String path, ActionListener action){
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setIcon(new ImageIcon("C:\\Users\\shund\\IdeaProjects\\TextEditor\\src\\Resource\\" + path));
        menuItem.addActionListener(action);
        return menuItem;
    }

    private JToolBar createJToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.add(createJButton("openToolBar.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

            }
        }));
        toolBar.add(createJButton("saveToolBar.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

            }
        }));
        toolBar.addSeparator();
        toolBar.add(createJButton("bToolBar.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().changeFontStyle(Font.BOLD);
                unloadFrameWindow();
            }
        }));
        toolBar.add(createJButton("iToolBar.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().changeFontStyle(Font.ITALIC);
                unloadFrameWindow();
            }
        }));
        toolBar.addSeparator();
        String [] size = {"8", "9", "10", "11", "12", "14", "18", "24", "30", "36", "48", "60", "72", "96"};
        toolBar.add(createJComboBox(true, size, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox comboBox = (JComboBox) actionEvent.getSource();
                String fontSize = (String) comboBox.getSelectedItem();
                textPanel.getText().changeFontSize(Integer.parseInt(fontSize));
                unloadFrameWindow();
            }
        }));
        String [] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        toolBar.add(createJComboBox(false, font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().changeFontType(actionEvent);
                unloadFrameWindow();
            }
        }));
        return toolBar;
    }
    private JButton createJButton(String path, ActionListener action){
        JButton button = new JButton();
        button.setFocusable(false);
        button.addActionListener(action);
        button.setIcon(new ImageIcon("C:\\Users\\shund\\IdeaProjects\\TextEditor\\src\\Resource\\" + path));
        return button;
    }
    private JComboBox createJComboBox(Boolean editable, String[] items, ActionListener action){
        JComboBox comboBox = new JComboBox(items);
        comboBox.setEditable(editable);
        comboBox.setMaximumSize(comboBox.getPreferredSize());
        comboBox.addActionListener(action);
        return comboBox;
    }

    public JFrame getFrameWindow(){
        return frameWindow;
    }
    public TextPanel getTextPanel(){
        return textPanel;
    }
    public JScrollPane getScrollPane(){
        return scrollPane;
    }


    public void unloadFrameWindow(){
        scrollPane.revalidate();
        scrollPane.repaint();
        frameWindow.requestFocus();
    }

    private void addActionListener(){
        MouseHandler mouseHandler = new MouseHandler(this);
        textPanel.addMouseListener(mouseHandler);
        textPanel.addMouseMotionListener(mouseHandler);
        scrollPane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                unloadFrameWindow();
            }
        });
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                unloadFrameWindow();
            }
        });
        frameWindow.addKeyListener(new KeyHandler(this));
        frameWindow.addKeyListener(new CaretHandler(this));
        frameWindow.addKeyListener(new DeleteHandler(this));
        frameWindow.addKeyListener(new ShiftHandler(this));
        frameWindow.addKeyListener(new ControlHandler(this));
    }

    public static  void main(String[] args){
        new FrameWindow();
    }
}
