package de.fk.notes;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import de.fk.notes.model.Folder;
import de.fk.notes.model.Note;
import de.fk.notes.model.Todo;

public class Notes extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextArea noteTextArea;
    private JTabbedPane tabbedPane;
    private JPanel writePanel;
    private JPanel searchOrganizePanel;
    private JPanel todoPanel;
    private JButton addButton;
    private JButton clearButton;
    private JTable searchResultTable;
    private JTableBuilder<Note> searchResultTableBuilder = JTableBuilders.searchResultTable();
    private JTable todoTable;
    private JTableBuilder<Todo> todoTableBuilder = JTableBuilders.todoTable();
    private JTable treeDetailTable;
    private JTableBuilder<Note> treeDetailTableBuilder = JTableBuilders.treeDetailTable();
    private JTree organizeTree;

    public Notes() {
        setBounds(0, 0, 1024, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Notes");

        getTabbedPane().add("Write", getWritePanel());
        getTabbedPane().add("Search / Organize", getSearchOrganizePanel());
        getTabbedPane().add("Todo", getTodoPanel());
        getTabbedPane().setMnemonicAt(0, KeyEvent.VK_W);
        getTabbedPane().setMnemonicAt(1, KeyEvent.VK_R);
        getTabbedPane().setMnemonicAt(2, KeyEvent.VK_T);
        getTabbedPane().addChangeListener(e -> System.out.println("tab changed"));
        setupTabTraversalKeys(getTabbedPane());
        getContentPane().add(getTabbedPane(), CENTER);

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(getAddButton());
        buttonPanel.add(getClearButton());

        getWritePanel().add(new JScrollPane(getNoteTextArea()), CENTER);
        getWritePanel().add(buttonPanel, SOUTH);

        getSearchOrganizePanel().add(new JTextField(), NORTH);

        var organizeSplitPane = new JSplitPane(HORIZONTAL_SPLIT, new JScrollPane(getOrganizeTree()), new JScrollPane(getTreeDetailTable()));
        var searchSplitPane = new JSplitPane(VERTICAL_SPLIT, new JScrollPane(getSearchResultTable()), organizeSplitPane);
        getSearchOrganizePanel().add(searchSplitPane, CENTER);
        invokeLater(() -> searchSplitPane.setDividerLocation(.3));
        invokeLater(() -> organizeSplitPane.setDividerLocation(.3));
        invokeLater(() -> getNoteTextArea().grabFocus());

        getTodoPanel().add(new JScrollPane(getTodoTable()), CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(getNoteTextArea().getText());
            }
        });

        setVisible(true);
    }

    public JTextArea getNoteTextArea() {
        if (noteTextArea == null) {
            noteTextArea = new JTextArea();
            noteTextArea.setTabSize(5);
            noteTextArea.setFont(new Font("consolas", Font.BOLD, 20));
        }
        return noteTextArea;
    }

    public JTabbedPane getTabbedPane() {
        if (tabbedPane == null) {
            tabbedPane = new JTabbedPane();
        }
        return tabbedPane;
    }

    public JPanel getWritePanel() {
        if (writePanel == null) {
            writePanel = new JPanel(new BorderLayout());
        }
        return writePanel;
    }

    public JPanel getSearchOrganizePanel() {
        if (searchOrganizePanel == null) {
            searchOrganizePanel = new JPanel(new BorderLayout());
        }
        return searchOrganizePanel;
    }

    public JPanel getTodoPanel() {
        if (todoPanel == null) {
            todoPanel = new JPanel(new BorderLayout());
        }
        return todoPanel;
    }

    public JButton getAddButton() {
        if (addButton == null) {
            SaveAction addAction = new SaveAction() {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(getNoteTextArea().getText());
                    getNoteTextArea().setText("");
                    getNoteTextArea().grabFocus();
                }
            };
            addButton = new JButton(addAction);
            String add = "add";
            addButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK),
                    add);
            addButton.getActionMap().put(add, addAction);
        }
        return addButton;
    }

    public JButton getClearButton() {
        if (clearButton == null) {
            clearButton = new JButton(new ClearAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getNoteTextArea().setText("");
                    getNoteTextArea().grabFocus();
                }
            });
        }
        return clearButton;
    }

    private static abstract class ClearAction extends AbstractAction {

        public ClearAction() {
            super("Clear");
            setEnabled(true);
            putValue(MNEMONIC_KEY, KeyEvent.VK_C);
            putValue(SHORT_DESCRIPTION, "Clear Note");
        }
    }

    private static abstract class SaveAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public SaveAction() {
            super("Save");
            setEnabled(true);
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            putValue(SHORT_DESCRIPTION, "Add Note and Clear");
        }
    }

    public JTable getSearchResultTable() {
        if (searchResultTable == null) {
            searchResultTable = searchResultTableBuilder.buildAndLoad();
        }
        return searchResultTable;
    }

    public JTable getTodoTable() {
        if (todoTable == null) {
            todoTable = todoTableBuilder.buildAndLoad();
        }
        return todoTable;
    }

    public JTable getTreeDetailTable() {
        if (treeDetailTable == null) {
            treeDetailTable = treeDetailTableBuilder.buildAndLoad();
        }
        return treeDetailTable;
    }

    public JTree getOrganizeTree() {
        if (organizeTree == null) {
            organizeTree = new JTree();
            organizeTree.setEditable(true);
            organizeTree.setDragEnabled(true);
            organizeTree.setDropMode(DropMode.ON_OR_INSERT);
            organizeTree.setComponentPopupMenu(new TreePopup());
            organizeTree.setTransferHandler(new JTreeTransferHandler());
            Folder root = new Folder("");
            Folder kind1 = new Folder("Notizen");
            root.getChildren().add(kind1);
            Folder kind2 = new Folder("Todos");
            Folder kind22 = new Folder("Todos Sehr wichtig");
            Folder kind221 = new Folder("Todos noch wichtiger");
            root.getChildren().add(kind2);
            kind2.getChildren().add(kind22);
            kind22.getChildren().add(kind221);
            Folder kind3 = new Folder("links");
            root.getChildren().add(kind3);
            Folder kind4 = new Folder("anderes");
            root.getChildren().add(kind4);
            organizeTree.setModel(new DefaultTreeModel(TreeData.getTreeNode(root)) {
                @Override
                public boolean isLeaf(Object node) {
                    return false;
                }
            });
        }
        organizeTree.setRootVisible(false);
        expandNodes(organizeTree, 2);
        return organizeTree;
    }

    private void expandNodes(JTree tree, int level) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        DefaultMutableTreeNode currentNode = root.getNextNode();
        do {
            if (currentNode != null && currentNode.getLevel() <= level) {
                tree.expandPath(new TreePath(currentNode.getPath()));
            }
            currentNode = currentNode != null ? currentNode.getNextNode() : null;
        } while (currentNode != null);
    }

    private void setupTabTraversalKeys(JComponent c) {
        var ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_DOWN_MASK);
        var ctrlShiftTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK);

        // Remove ctrl-tab from normal focus traversal
        Set<AWTKeyStroke> forwardKeys = new HashSet<>(c.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.remove(ctrlTab);
        c.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        // Remove ctrl-shift-tab from normal focus traversal
        Set<AWTKeyStroke> backwardKeys = new HashSet<>(c.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardKeys.remove(ctrlShiftTab);
        c.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        // Add keys to the tab's input map
        InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(ctrlTab, "navigateNext");
        inputMap.put(ctrlShiftTab, "navigatePrevious");
    }

    public static void main(String[] args) {
        // https:stackoverflow.com/questions/100123/application-wide-keyboard-shortcut-java-swing/8485873#8485873
        invokeLater(() -> new Notes());
    }

}
