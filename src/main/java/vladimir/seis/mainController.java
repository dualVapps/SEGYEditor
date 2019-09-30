/*���������� � ������ ��������� ������ �����������
* */

package vladimir.seis;


import vladimir.seis.segystream.SEGYTempEdit.SegyTempFile;
import vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;
import vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;

import javax.swing.*;

public class mainController {

    //    private  mainGui mainGui;
    private int CURRENT_TRACE_NUMBER;
    private int CURRENT_SAMPLE_NUMBER;
    private int CURRENT_ADD_TRACE_NUMBER;
    private int CURRENT_FILE_SEQ_NUMBER = -1;


    private JPanel mainJPanel;
    private JLabel[] lawJLs = new JLabel[6];
    public SegyTempFile segyTempFile;
    public SegyTempTrace[] segyTempTraces;
    public SegyTempTraceData[] segyTempTracesDataForDisplaying;
    public SegyTempTraceData[] segyTempTracesDataAfterProcessing;
    public SegyTempTraceData[] segyTempTracesDataVault;
    private JButton pickButton;
    private  float[] currentBalancingCorrKoef; // = new float[54];
    Settings_singleton settings_singleton;


    public mainController() {

        // TODO change data limits to variables
        segyTempFile = new SegyTempFile();
        settings_singleton = mainGui.getSettings_singl();
    }

    public void setCURRENT_FILE_SEQ_NUMBER(int CURRENT_FILE_SEQ_NUMBER) {
        this.CURRENT_FILE_SEQ_NUMBER = CURRENT_FILE_SEQ_NUMBER;
    }

    public void init(JButton pickButton, JLabel label_1, JLabel label_2, JLabel label_3,
                     JLabel label_4, JLabel label_5, JLabel label_6) {
        this.pickButton = pickButton;
        this.lawJLs[0] = label_1;
        this.lawJLs[1] = label_2;
        this.lawJLs[2] = label_3;
        this.lawJLs[3] = label_4;
        this.lawJLs[4] = label_5;
        this.lawJLs[5] = label_6;

    }

    public void initReadingParameters() {
        if ((settings_singleton.getCfgTraceNumber() > 1)&&(settings_singleton.getCfgSamplesNumber()>1))
        {
            CURRENT_TRACE_NUMBER = settings_singleton.getCfgTraceNumber();
            CURRENT_SAMPLE_NUMBER = settings_singleton.getCfgSamplesNumber();
            currentBalancingCorrKoef = new float[CURRENT_TRACE_NUMBER];
//            System.out.println("CURRENT_TRACE_NUMBER " + CURRENT_TRACE_NUMBER);
//            System.out.println("CURRENT_SAMPLE_NUMBER " + CURRENT_SAMPLE_NUMBER);

            segyTempTraces = new SegyTempTrace[CURRENT_TRACE_NUMBER];
            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
                this.segyTempTraces[i] = new SegyTempTrace();
            }

            segyTempTracesDataForDisplaying = new SegyTempTraceData[CURRENT_TRACE_NUMBER];
            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
                this.segyTempTracesDataForDisplaying[i] = new SegyTempTraceData();
            }

            segyTempTracesDataAfterProcessing = new SegyTempTraceData[CURRENT_TRACE_NUMBER];
            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
                this.segyTempTracesDataAfterProcessing[i] = new SegyTempTraceData();
                this.segyTempTracesDataAfterProcessing[i].data = new float[CURRENT_SAMPLE_NUMBER];
            }

            segyTempTracesDataVault = new SegyTempTraceData[CURRENT_TRACE_NUMBER];
            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
                this.segyTempTracesDataVault[i] = new SegyTempTraceData();
                this.segyTempTracesDataVault[i].data = new float[CURRENT_SAMPLE_NUMBER];
            }
        }

    }


    public SegyTempFile getSegyTempFile() {
        return segyTempFile;
    }

    public SegyTempTrace getSegyTempTraces(int traceNumber) {
        return segyTempTraces[traceNumber];
    }

    public SegyTempTraceData[] getSegyTempTracesDataForDisplaying() {
        return segyTempTracesDataForDisplaying;
    }

    public SegyTempTraceData[] getSegyTempTracesDataAfterProcessing() {
        return segyTempTracesDataAfterProcessing;
    }

    public SegyTempTraceData[] getSegyTempTracesDataFromVault() {
        return segyTempTracesDataVault;
    }




//    public static void setMainGui(main.java.vladimir.seis.mainGui mainGui) {
//        mainController.mainGui = mainGui;
//    }

    public void defineJLabelText(String s1, int index) {

        if ((s1 != null) && (index < 6)) {
            lawJLs[index].setText(s1);
        } else lawJLs[index].setText("  ----------");


    }


    public void clickPickingButtonSuccess() {
//        System.out.println("Picking Button" + mainGui.getPickingButton().toString());
//        mainGui.getPickingButton().doClick();
        pickButton.doClick();
        mainGui.getSettings_singl().formingFullLengthLaw();
        mainGui.getSettings_singl().formingShiftedFullTrimLaw(segyTempTracesDataForDisplaying);

    }

    public void saveSeismicTraceDataToVault() {
//        System.out.println("length ............ "+ segyTempTracesDataForDisplaying[0].getData().length);
//        System.out.println("length ............ "+ segyTempTracesDataVault[0].getData().length);
//        System.out.println("length ............ ");
        for (int i = 0; i < segyTempTracesDataForDisplaying.length; i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying[i].getData().length; j++) {
                segyTempTracesDataVault[i].getData()[j] = segyTempTracesDataForDisplaying[i].getData()[j];
            }
        }


    }

    public void restoreSeismicTraceDataFromVault() {
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault.length);
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault.toString());
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault[0].getData().toString());
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault[0].getData().length);
        for (int i = 0; i < segyTempTracesDataForDisplaying.length; i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying[i].getData().length; j++) {
                segyTempTracesDataForDisplaying[i].getData()[j] = segyTempTracesDataVault[i].getData()[j];
            }
        }

    }

    public void balancingTempData() { //TODO rewrite AGN to balancing
        CURRENT_FILE_SEQ_NUMBER = settings_singleton.getCfgCurrentFileSeqNumber();
        System.out.println("CURRENT_FILE_SEQ_NUMBER "+ CURRENT_FILE_SEQ_NUMBER);
        System.out.println("StartBalancing");
        calcBalancingKoef();
        System.out.println("calcBalancingKoef");
        applyBalancingToTempData();
        System.out.println("applyBalancingToTempData");


    }

    public void calcBalancingKoef () {

        System.out.println("CalcBalancingKoef ->> Start");

        final int balancedAmpl = 1;

        System.out.println("segyTempTracesDataForDisplaying ->" + segyTempTracesDataForDisplaying.length);
        for (int i = 6+CURRENT_FILE_SEQ_NUMBER*54; i < 54+CURRENT_FILE_SEQ_NUMBER*54; i++) { //TODO Fix for zero add trace number
            int  tempSampleNumber = 1000;


            //Calculating summary of array from 0 to trimShifted point and deviding to sample number (average value)
            float regLevel = balancedAmpl; //regulation level
            float sum = 0;
            for (int j = 0; j < tempSampleNumber; j++) {
                sum = sum + Math.abs(segyTempTracesDataForDisplaying[i].getData()[j]);
            }

//            System.out.println("sum ->>> "+ i+ " ---- " +sum);

            float averageSum = sum / tempSampleNumber;

//            System.out.println("averageSum ->>> "+ i+ " ---- " +averageSum);

            currentBalancingCorrKoef[i] = regLevel/averageSum;

//            System.out.println("averageSum ->>> "+ i+ " ---- " +averageSum);


        }
//        System.out.println("CalcBalancingKoef ->> Finish");
    }

    public void applyBalancingToTempData() {
//        System.out.println("applyBalancingToTempData ->> start");
        for (int i = 6+CURRENT_FILE_SEQ_NUMBER*54; i < 54+CURRENT_FILE_SEQ_NUMBER*54; i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying[i].getData().length; j++) {
                segyTempTracesDataForDisplaying[i].getData()[j] = segyTempTracesDataForDisplaying[i].getData()[j] * currentBalancingCorrKoef[i];
            }
        }

    }

    public void resetBalancing() {

        for (int i = 6; i < segyTempTracesDataForDisplaying.length; i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying[i].getData().length; j++) {

                segyTempTracesDataForDisplaying[i].getData()[j] = segyTempTracesDataVault[i].getData()[j];
            }
        }

    }
}
