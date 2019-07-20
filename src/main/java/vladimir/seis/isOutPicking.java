package main.java.vladimir.seis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class isOutPicking extends JDialog {
    public JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel isOutPickingText;

    public isOutPicking() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(e);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(e);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Window w = e.getWindow();
                w.dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(e);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(ActionEvent e) {
        // add your code here
        System.out.println("main controller -- "+mainGui.getMainController().toString());
        mainGui.getMainController().clickPickingButton();
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();

        //        dispose();
    }

    private void onCancel(ActionEvent e) {
        // add your code here if necessary

        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
//        dispose();
    }

    public static void main(String[] args) {
        isOutPicking dialog = new isOutPicking();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
