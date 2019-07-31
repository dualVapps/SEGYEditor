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
    private JCheckBox isFromNegToPosCB;
    private JPanel preference1;
    private JPanel preference2;
    private JPanel preference3;
    public JPanel settingsPanel;
    private JLabel preference1Txt;
    private JLabel preference2txt;
    private JLabel preference3Txt;
    private JButton confirmButton;
    private JPanel preference4;
    private JTextField AGCWindowTracesTF;
    private JLabel preference4Txt;
    private int numberOfSamplesInt;
    private int sizesInBytesInt;
    private int AGCWindowTraces;




    public Settings() {

        if (mainGui.getSettings_singl().getNumber_of_samples() == -1)
        {

            numberOfSamplesTF.setText("2048");
            sizesInBytesTF.setText("8196");
            isFromNegToPosCB.setSelected(true);
            AGCWindowTracesTF.setText("7");
        }

        else {
            numberOfSamplesTF.setText(Integer.toString(mainGui.getSettings_singl().getNumber_of_samples()));
            sizesInBytesTF.setText(Integer.toString(mainGui.getSettings_singl().getSample_sizeInBytes()));
            isFromNegToPosCB.setSelected(mainGui.getSettings_singl().isFromNegToPos());
            AGCWindowTracesTF.setText(Integer.toString(mainGui.getSettings_singl().getAgcWindowSizeInTraces()));
        }

        System.out.println(numberOfSamplesInt);


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(""+numberOfSamplesTF.getText().equals(""));
                System.out.println(""+sizesInBytesTF.getText().equals(""));

                if (numberOfSamplesTF.getText().equals("")||sizesInBytesTF.getText().equals("")) {
                    JOptionPane.showMessageDialog(settingsPanel,
                            "Неправильные данные размера",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE);
                }

                else if (AGCWindowTracesTF.getText().equals("")||(Integer.parseInt(AGCWindowTracesTF.getText()) % 2 == 0)
                || Integer.parseInt(AGCWindowTracesTF.getText())> 21 || Integer.parseInt(AGCWindowTracesTF.getText()) <= 0){
                    JOptionPane.showMessageDialog(settingsPanel,
                            "Неправильные данные окна",
                            "Внимание",
                            JOptionPane.WARNING_MESSAGE);
                }

                else {

                    numberOfSamplesInt = Integer.parseInt(numberOfSamplesTF.getText());
                    sizesInBytesInt = Integer.parseInt(sizesInBytesTF.getText());
                    AGCWindowTraces = Integer.parseInt(AGCWindowTracesTF.getText());



                    mainGui.getSettings_singl().setNumber_of_samples(numberOfSamplesInt);
                    mainGui.getSettings_singl().setSample_sizeInBytes(sizesInBytesInt);
                    mainGui.getSettings_singl().setFromNegToPos(isFromNegToPosCB.isSelected());
                    mainGui.getSettings_singl().setAgcWindowSizeInTraces(AGCWindowTraces);


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
