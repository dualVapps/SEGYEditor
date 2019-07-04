/*Контроллер в моделе котроллер модель отображение
* */

package main.java.vladimir.seis;


import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempFile;
import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;
import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;

import javax.swing.*;

public class mainController {

    public static mainGui mainGui;
    private String lawSt1, lawSt2, lawSt3, lawSt4, lawSt5, lawSt6;
    public SegyTempFile segyTempFile;
    public SegyTempTrace[] segyTempTraces;
    public SegyTempTraceData[] segyTempTracesData;




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

    public SegyTempFile getSegyTempFile() {
        return segyTempFile;
    }

    public SegyTempTrace getSegyTempTraces(int traceNumber) {
        return segyTempTraces[traceNumber];
    }

    public SegyTempTraceData[] getSegyTempTracesData() {
        return segyTempTracesData;
    }


    public static void setMainGui(main.java.vladimir.seis.mainGui mainGui) {
        mainController.mainGui = mainGui;
    }

    public void defineJLabelText (String s1, String s2, String s3, String s4, String s5, String s6) {

        lawSt1 = s1;
        lawSt2 = s2;
        lawSt3 = s3;
        lawSt4 = s4;
        lawSt5 = s5;
        lawSt6 = s6;
    }

    public void setJLabelMuteLawLabels() {
        mainGui.setJLabelsLaw(lawSt1,lawSt2,lawSt3,lawSt3,lawSt5,lawSt6);
    }
}
