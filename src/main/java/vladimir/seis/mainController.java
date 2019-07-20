/*Контроллер в моделе котроллер модель отображение
* */

package main.java.vladimir.seis;


import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempFile;
import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;
import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;

import javax.swing.*;

public class mainController {

    private  mainGui mainGui;
    private JPanel mainJPanel;
    private JLabel[] lawJLs = new JLabel[6];
    public SegyTempFile segyTempFile;
    public SegyTempTrace[] segyTempTraces;
    public SegyTempTraceData[] segyTempTracesData;
    private JButton pickButton;




    public mainController() {

        // TODO change data limits to variables
        segyTempFile = new SegyTempFile();
        segyTempTraces = new SegyTempTrace[54];
        for (int i = 0; i < 54; i++) {
            this.segyTempTraces[i] = new SegyTempTrace();
        }

        segyTempTracesData = new SegyTempTraceData[54];
        for (int i = 0; i < 54; i++) {
            this.segyTempTracesData[i] = new SegyTempTraceData();
        }



    }

    public void init(JButton pickButton, JLabel label_1, JLabel label_2, JLabel label_3,
                     JLabel label_4, JLabel label_5, JLabel label_6) {
        this.pickButton=pickButton;
        this.lawJLs[0] = label_1;
        this.lawJLs[1] = label_2;
        this.lawJLs[2] = label_3;
        this.lawJLs[3] = label_4;
        this.lawJLs[4] = label_5;
        this.lawJLs[5] = label_6;

    }

    public SegyTempFile getSegyTempFile() {
        return segyTempFile;
    }

    public SegyTempTrace getSegyTempTraces(int traceNumber) {
        return segyTempTraces[traceNumber];
    }

    public SegyTempTraceData[] getSegyTempTracesData() {
        return segyTempTracesData;
    }


//    public static void setMainGui(main.java.vladimir.seis.mainGui mainGui) {
//        mainController.mainGui = mainGui;
//    }

    public void defineJLabelText (String s1, int index) {

        if ((s1 != null)&&(index<7)) { lawJLs[index].setText(s1);} else lawJLs[index].setText("");


    }



    public void clickPickingButton() {
//        System.out.println("Picking Button" + mainGui.getPickingButton().toString());
//        mainGui.getPickingButton().doClick();
        pickButton.doClick();
    }
}
