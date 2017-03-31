package TextEditor;


import Addition.MenuScroller;
import Listener.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by shund on 27.02.2017.
 */
public class FrameWindow {
    private JFrame frameWindow;
    private FileHandler fileHandler;
    private TextPanel textPanel;
    private JScrollPane scrollPane;
    private JComboBox comboFontSize;
    private JComboBox comboFontType;
    private JMenu fontTypeMenu;
    private JMenu fontSizeMenu;

    public FrameWindow() {
        frameWindow = new JFrame("Text Editor");
        frameWindow.setSize(800, 600);
        frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWindow.setLayout(new BorderLayout());

        Font font = new Font("Verdana", Font.PLAIN, 12);
        frameWindow.setJMenuBar(createMenuBar(font));
        frameWindow.add(createToolBar(), BorderLayout.NORTH);

        createTextPanel();

        createPopUpMenu(font);

        addActionListener();
        frameWindow.setVisible(true);
        frameWindow.toFront();
        frameWindow.requestFocus();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    private JMenuBar createMenuBar(Font font) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu(font));
        menuBar.add(createEditMenu(font));
        menuBar.add(createFormatMenu(font));
        return menuBar;
    }

    private JMenu createFileMenu(Font font) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(font);
        /*------------------------------------------------------------------------------------------------------------*/
        fileMenu.add(createMenuItem("Open", "MenuBar/open.png", null, 'O', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileHandler.openFile();
                unloadFrameWindow();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        fileMenu.add(createMenuItem("Save", "MenuBar/save.png", null, 'S', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileHandler.saveXmlFile();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        fileMenu.addSeparator();
        /*------------------------------------------------------------------------------------------------------------*/
        fileMenu.add(createMenuItem("Quit", "MenuBar/quit.png", null, 'Q', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int answer = JOptionPane.showConfirmDialog(null, "Information will be lost! Save file?", "Quit", JOptionPane.YES_NO_CANCEL_OPTION);
                if (answer == 0) {
                    fileHandler.saveXmlFile();
                }
                if (answer != 2) {
                    getFrameWindow().setVisible(false);
                    System.exit(0);
                }
            }
        }));
        return fileMenu;
    }

    private JMenu createEditMenu(Font font) {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setFont(font);
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        editMenu.add(createMenuItem("Copy", "MenuBar/copy.png", ctrlC, 'C', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.getText().copy();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        editMenu.add(createMenuItem("Paste", "MenuBar/paste.png", ctrlV, 'P', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.getText().cut();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        editMenu.add(createMenuItem("Cut", "MenuBar/cut.png", ctrlX, 'C', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.getText().cut();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        editMenu.addSeparator();
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlA = KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        editMenu.add(createMenuItem("Select All", "MenuBar/selectAll.png", ctrlA, 'S', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textPanel.getText().selectAllText();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        editMenu.add(createMenuItem("Delete All", "MenuBar/clear.png", null, 'D', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().deleteAllText();
                unloadFrameWindow();
            }
        }));
        return editMenu;
    }

    private JMenu createFormatMenu(Font font) {
        ActionListener sliceFontTypeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JRadioButtonMenuItem radioButtonMenuItem = (JRadioButtonMenuItem) actionEvent.getSource();
                String fontType = radioButtonMenuItem.getText();
                JComboBox comboType = getComboFontType();
                comboType.setSelectedItem((Object) fontType);
            }
        };
        ActionListener sliceFontSizeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JRadioButtonMenuItem radioButtonMenuItem = (JRadioButtonMenuItem) actionEvent.getSource();
                String fontSize = radioButtonMenuItem.getText();
                JComboBox comboType = getComboFontSize();
                comboType.setSelectedItem((Object) fontSize);
            }
        };
        /*------------------------------------------------------------------------------------------------------------*/
        JMenu formatMenu = new JMenu("Format");
        formatMenu.setFont(font);
        /*------------------------------------------------------------------------------------------------------------*/
        fontTypeMenu = new JMenu("Font");
        fontTypeMenu.setFont(font);
        String[] type = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        ButtonGroup fontTypeGroup = new ButtonGroup();
        for (int index = 0; index < type.length; index++) {
            JRadioButtonMenuItem fontTypeRB = new JRadioButtonMenuItem(type[index]);
            fontTypeMenu.add(fontTypeRB);
            fontTypeGroup.add(fontTypeRB);
            if (type[index].equals("Times New Roman")) {
                fontTypeRB.setSelected(true);
            }
            fontTypeRB.addActionListener(sliceFontTypeListener);
        }
        MenuScroller typeScroller = new MenuScroller(fontTypeMenu, 5, 50, 3, 1);
        formatMenu.add(fontTypeMenu);
        /*------------------------------------------------------------------------------------------------------------*/
        fontSizeMenu = new JMenu("Size");
        fontSizeMenu.setFont(font);
        String[] size = {"8", "9", "10", "11", "12", "14", "18", "24", "30", "36", "48", "60", "72", "96"};
        ButtonGroup fontSizeGroup = new ButtonGroup();
        for (int index = 0; index < size.length; index++) {
            JRadioButtonMenuItem fontSizeRB = new JRadioButtonMenuItem(size[index]);
            fontSizeMenu.add(fontSizeRB);
            fontSizeGroup.add(fontSizeRB);
            if (type[index].equals("14")) {
                fontSizeRB.setSelected(true);
            }
            fontSizeRB.addActionListener(sliceFontSizeListener);
        }
        MenuScroller sizeScroller = new MenuScroller(fontSizeMenu, 5, 50, 3, 1);
        formatMenu.add(fontSizeMenu);
        return formatMenu;
    }

    private JMenuItem createMenuItem(String name, String path, KeyStroke keyStroke, char mnemonic, Font font, ActionListener action) {
        JMenuItem menuItem = new JMenuItem(name);
        ImageIcon imageIcon = new ImageIcon("Resource/" + path);
        menuItem.setFont(font);
        menuItem.setIcon(imageIcon);
        menuItem.setIconTextGap(5);
        menuItem.setMnemonic(mnemonic);
        menuItem.setAccelerator(keyStroke);
        menuItem.addActionListener(action);
        return menuItem;
    }

    private void unloadMenu(JMenu menu, String data) {
        boolean find = true;
        int index = 0;
        while (find) {
            if (menu.getItem(index).getText().equals(data)) {
                menu.getItem(index).setSelected(true);
                find = false;
            }
            index++;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(200, 200, 200, 108));
        toolBar.add(createButton("ToolBar/open.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileHandler.openFile();
            }
        }));
        toolBar.add(createButton("ToolBar/save.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileHandler.saveXmlFile();
            }
        }));
        toolBar.addSeparator();
        JToggleButton boldButton = createToggleButton("ToolBar/bold.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().changeFontStyle(Font.BOLD);
                JToggleButton button = (JToggleButton) actionEvent.getSource();
                unloadFrameWindow();
            }
        });
        toolBar.add(boldButton);
        JToggleButton italicButton = createToggleButton("ToolBar/italic.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().changeFontStyle(Font.ITALIC);
                unloadFrameWindow();
            }
        });
        toolBar.add(italicButton);
        toolBar.addSeparator();
        String[] fontSize = {"8", "9", "10", "11", "12", "14", "18", "24", "30", "36", "48", "60", "72", "96"};
        comboFontSize = new JComboBox(fontSize);
        comboFontSize.setSelectedItem("14");
        comboFontSize.setMaximumSize(comboFontSize.getPreferredSize());
        comboFontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox comboBox = (JComboBox) actionEvent.getSource();
                String fontSize = (String) comboBox.getSelectedItem();
                unloadMenu(fontSizeMenu, fontSize);
                textPanel.getText().changeFontSize(Integer.parseInt(fontSize));
                unloadFrameWindow();
            }
        });
        toolBar.add(comboFontSize);
        String[] fontType = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        comboFontType = new JComboBox(fontType);
        comboFontType.setSelectedItem("Times New Roman");
        comboFontType.setMaximumSize(comboFontType.getPreferredSize());
        comboFontType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox comboBox = (JComboBox) actionEvent.getSource();
                String fontType = (String) comboBox.getSelectedItem();
                unloadMenu(fontTypeMenu, fontType);
                textPanel.getText().changeFontType(fontType);
                unloadFrameWindow();
            }
        });
        toolBar.add(comboFontType);
        toolBar.addSeparator();
        toolBar.add(createButton("ToolBar/copy.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().copy();
            }
        }));
        toolBar.add(createButton("ToolBar/paste.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().paste();
                unloadFrameWindow();
            }
        }));
        toolBar.add(createButton("ToolBar/cut.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().cut();
                unloadFrameWindow();
            }
        }));
        toolBar.addSeparator();
        toolBar.add(createButton("ToolBar/clear.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().deleteAllText();
                unloadFrameWindow();
            }
        }));
        return toolBar;
    }

    private JToggleButton createToggleButton(String name, ActionListener actionListener) {
        JToggleButton button = new JToggleButton();
        button.addActionListener(actionListener);
        ImageIcon imageIcon = new ImageIcon("Resource/" + name);
        button.setIcon(imageIcon);
        return button;
    }

    private JButton createButton(String name, ActionListener actionListener) {
        JButton button = new JButton();
        button.addActionListener(actionListener);
        ImageIcon imageIcon = new ImageIcon("Resource/" + name);
        button.setIcon(imageIcon);
        return button;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    private void createTextPanel() {
        textPanel = new TextPanel(this);
        scrollPane = new JScrollPane(textPanel);
        scrollPane.getViewport().setBackground(Color.white);
        frameWindow.add(scrollPane, BorderLayout.CENTER);
        textPanel.getText().createInput();
        fileHandler = new FileHandler(this);
    }

    private void createPopUpMenu(Font font) {
        JPopupMenu popupMenu = new JPopupMenu();
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        popupMenu.add(createMenuItem("Copy", "MenuBar/copy.png", ctrlC, 'C', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().copy();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        popupMenu.add(createMenuItem("Paste", "MenuBar/paste.png", ctrlV, 'P', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().paste();
                unloadFrameWindow();
            }
        }));
        /*------------------------------------------------------------------------------------------------------------*/
        KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        popupMenu.add(createMenuItem("Cut", "MenuBar/cut.png", ctrlX, 'C', font, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textPanel.getText().cut();
                unloadFrameWindow();
            }
        }));
        textPanel.setComponentPopupMenu(popupMenu);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public void unloadFrameWindow() {
        scrollPane.revalidate();
        scrollPane.repaint();
        frameWindow.requestFocus();
    }

    public void setViewport(Point point) {
        JViewport viewport = scrollPane.getViewport();
        viewport.setViewPosition(point);
        scrollPane.setViewport(viewport);
    }

    /*----------------------------------------------------------------------------------------------------------------*/
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
        frameWindow.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Information will be lost! Save file?", "Quit", JOptionPane.YES_NO_OPTION);
                if (answer == 0) {
                    fileHandler.saveXmlFile();
                }
                if (answer != 2) {
                    getFrameWindow().setVisible(false);
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

    }

    /*----------------------------------------------------------------------------------------------------------------*/
    public JFrame getFrameWindow() {
        return frameWindow;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public TextPanel getTextPanel() {
        return textPanel;
    }

    public JComboBox getComboFontSize() {
        return comboFontSize;
    }

    public JComboBox getComboFontType() {
        return comboFontType;
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    /*----------------------------------------------------------------------------------------------------------------*/
    public static void main(String[] args) {
        new FrameWindow();
    }
}
