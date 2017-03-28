package TextEditor;


import Addition.MenuScroller;
import Listener.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by shund on 27.02.2017.
 */
public class FrameWindow {
    private JFrame frameWindow;
    private FileHandler fileHandler;
    private TextPanel textPanel;
    private JScrollPane scrollPane;

    public FrameWindow() {
        frameWindow = new JFrame("Text Editor");
        frameWindow.setSize(800, 600);
        frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWindow.setLayout(new BorderLayout());
        frameWindow.setJMenuBar(createMenuBar());
        frameWindow.add(createToolBar(), BorderLayout.NORTH);

        textPanel = new TextPanel(this);
        scrollPane = new JScrollPane(textPanel);
        scrollPane.getViewport().setBackground(Color.white);

        frameWindow.add(scrollPane, BorderLayout.CENTER);

        fileHandler = new FileHandler(this);

        textPanel.getText().createInput();

        addActionListener();
        frameWindow.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        Font font = new Font("Verdana", Font.PLAIN, 12);
        menuBar.add(createFileMenu(font));
        menuBar.add(createEditMenu(font));
        menuBar.add(createFormatMenu(font));
        return menuBar;
    }

    private JMenu createFileMenu(Font font) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);
        fileMenu.add(createMenuItem("Open", 'O', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileHandler.openFile();
                unloadFrameWindow();
            }
        }));
        fileMenu.add(createMenuItem("Save", 'S', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileHandler.saveXmlFile();
            }
        }));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Quit", 'Q', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int answer = JOptionPane.showConfirmDialog(null, " Are you sure you want to quit? Save file?", "Quit", JOptionPane.YES_NO_OPTION);
                if (answer == 0) {
                   fileHandler.saveXmlFile();
                }
                getFrameWindow().setVisible(false);
                System.exit(0);
            }
        }));
        return fileMenu;
    }

    private JMenu createEditMenu(Font font) {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(font);
        editMenu.add(createMenuItem("Copy", 'C', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().copy();
            }
        }));
        editMenu.add(createMenuItem("Paste", 'P', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().paste();
                unloadFrameWindow();
            }
        }));
        editMenu.add(createMenuItem("Cut", 'C', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().cut();
                unloadFrameWindow();
            }
        }));
        return editMenu;
    }

    private JMenu createFormatMenu(Font font) {
        JMenu formatMenu = new JMenu("Format");
        formatMenu.setFont(font);
        JMenu fontType = new JMenu("Font");
        fontType.setFont(font);
        String[] type = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        for (int index = 0; index < type.length; index++) {
            JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem(type[index]);
            scrollPane.getViewport().add(radioButtonMenuItem);
        }

        fontType.add(scrollPane);
        formatMenu.add(fontType);
        return formatMenu;
    }


    private JMenuItem createMenuItem(String name, char mnemonic, Font font, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setFont(font);
        menuItem.addActionListener(action);
        menuItem.setMnemonic(mnemonic);
        return menuItem;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(200, 200, 200, 108));
        toolBar.add(createButton("open.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileHandler.openFile();
            }
        }));
        toolBar.add(createButton("save.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileHandler.saveXmlFile();
            }
        }));
        toolBar.addSeparator();
        toolBar.add(createButton("bold.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().changeFontStyle(Font.BOLD);
                unloadFrameWindow();
            }
        }));
        toolBar.add(createButton("italic.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.getText().changeFontStyle(Font.ITALIC);
                unloadFrameWindow();
            }
        }));
        toolBar.addSeparator();
        JLabel sizeLabel = new JLabel("Size");
        sizeLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        String[] fontSize = {"8", "9", "10", "11", "12", "14", "18", "24", "30", "36", "48", "60", "72", "96"};
        JComboBox comboSize = new JComboBox(fontSize);
        comboSize.setSelectedItem("14");
        comboSize.setMaximumSize(comboSize.getPreferredSize());
        comboSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox comboBox = (JComboBox) actionEvent.getSource();
                String fontSize = (String) comboBox.getSelectedItem();
                textPanel.getText().changeFontSize(Integer.parseInt(fontSize));
                unloadFrameWindow();
            }
        });
        toolBar.add(comboSize);
        String[] fontType = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox comboFont = new JComboBox(fontType);
        comboFont.setSelectedItem("Times New Roman");
        comboFont.setMaximumSize(comboFont.getPreferredSize());
        comboFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox comboBox = (JComboBox) actionEvent.getSource();
                String fontType = (String) comboFont.getSelectedItem();
                textPanel.getText().changeFontType(fontType);
                unloadFrameWindow();
            }
        });
        toolBar.add(comboFont);
        toolBar.addSeparator();
        toolBar.add(createButton("copy.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().copy();
            }
        }));
        toolBar.add(createButton("paste.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().paste();
                unloadFrameWindow();
            }
        }));
        toolBar.add(createButton("cut.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().cut();
                unloadFrameWindow();
            }
        }));


        return toolBar;
    }

    private JButton createButton(String name, ActionListener actionListener) {
        JButton button = new JButton();
        button.addActionListener(actionListener);
        ImageIcon imageIcon = new ImageIcon("Resource/" + name);
        button.setIcon(imageIcon);
        return button;
    }


    public JFrame getFrameWindow() {
        return frameWindow;
    }

    public TextPanel getTextPanel() {
        return textPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }


    public void unloadFrameWindow() {
        scrollPane.revalidate();
        scrollPane.repaint();
        frameWindow.requestFocus();
    }

    private void addActionListener() {
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

    public static void main(String[] args) {
        new FrameWindow();
    }
}
