package main.java.vladimir.seis;

import akka.stream.TLSProtocol;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class Settings {
    private JTextField numberOfSamplesTF;
    private JTextField sizesInBytesTF;
    private JTextField textField3;
    private JPanel preference1;
    private JPanel preference2;
    private JPanel preference3;
    public JPanel settingsPanel;
    private JLabel preference1Txt;
    private JLabel preference2txt;
    private JLabel preference3Txt;
    private JButton confirmButton;
    private int numberOfSamplesInt;
    private int sizesInBytesInt;




    public Settings() {


        numberOfSamplesTF.setText("1024");
        sizesInBytesTF.setText("8196");

        System.out.println(numberOfSamplesInt);


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(""+numberOfSamplesTF.getText().equals(""));
                System.out.println(""+sizesInBytesTF.getText().equals(""));

                if (numberOfSamplesTF.getText().equals("")||sizesInBytesTF.getText().equals("")) {
                    JOptionPane.showMessageDialog(settingsPanel,
                            "Неправильные данные",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE);
                }

                else {

                    numberOfSamplesInt = Integer.parseInt(numberOfSamplesTF.getText());
                    sizesInBytesInt = Integer.parseInt(sizesInBytesTF.getText());

                    mainGui.getSettings_singl().setNumber_of_samples(numberOfSamplesInt);
                    mainGui.getSettings_singl().setSample_sizeInBytes(sizesInBytesInt);


//                    if (numberOfSamplesInt!=0&sizesInBytesInt!=0) {
//                        mainGui.getSettings_singl().setNumber_of_samples(numberOfSamplesInt);
//                        mainGui.getSettings_singl().setSample_sizeInBytes(sizesInBytesInt);
//                    }
//                    else {
//                        mainGui.getSettings_singl().setNumber_of_samples(1024); //TODO temporary solving fot testing Fix
//                        mainGui.getSettings_singl().setSample_sizeInBytes(8169);
//
//                    }
                    System.out.println("" + mainGui.getSettings_singl().getNumber_of_samples());
                    System.out.println("" + mainGui.getSettings_singl().getSample_sizeInBytes());
 //                   settingsPanel.dispatchEvent(new WindowEvent(Settings.class, WindowEvent.WINDOW_CLOSING));
                    JComponent comp = (JComponent) e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                }

            }
        });


    }
}
