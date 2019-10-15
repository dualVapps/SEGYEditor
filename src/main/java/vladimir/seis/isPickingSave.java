package vladimir.seis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class isPickingSave extends JDialog {
    public JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel isPickingSaveText;


    public isPickingSave(Frame owner) {
        super(owner);
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
                onCancel(e);
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
        System.out.println("isPicking - ");
        System.out.println(((mainGui) getOwner()).getClass().getSimpleName());

        ((mainGui) getOwner()).pickingModeSpinActionOk();
//        mainGui.pickingModeSpinAction();
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }

    private void onCancel(AWTEvent e) {
        // add your code here if necessary

        if (e instanceof ActionEvent) {

            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
        }
        if (e instanceof WindowEvent) {
            Window win = ((WindowEvent) e).getWindow();
            win.dispose();
        }

        ((mainGui) getOwner()).pickingModeSpinActionCancel();
    }

//    public static void main(String[] args) {
//        isPickingSave dialog = new isPickingSave();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
