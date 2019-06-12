package main.java.vladimir.seis;

import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempFile;
import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;
import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;

public class mainController {

    public static mainGui mainGui;
    public SegyTempFile segyTempFile;
    public SegyTempTrace[] segyTempTraces;
    public SegyTempTraceData[] segyTempTracesData;

    public mainController() {           // TODO change data limits to variables
        segyTempFile = new SegyTempFile();
        segyTempTraces = new SegyTempTrace[216];
        for (int i = 0; i < 216; i++) {
            this.segyTempTraces[i] = new SegyTempTrace();
        }

        segyTempTracesData = new SegyTempTraceData[216];
        for (int i = 0; i < 216; i++) {
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
}
