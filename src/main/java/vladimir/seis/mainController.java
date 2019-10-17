/*Контроллер в моделе котроллер модель отображение
* */

package vladimir.seis;


import vladimir.seis.segystream.SEGYTempEdit.SegyTempFile;
import vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;
import vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;

import javax.swing.*;
import java.util.ArrayList;

public class mainController {

    //    private  mainGui mainGui;
    private int CURRENT_TRACE_NUMBER;
    private int CURRENT_SAMPLE_NUMBER;
    private int CURRENT_ADD_TRACE_NUMBER;
    private int CURRENT_FILE_SEQ_NUMBER = -1;


    private JPanel mainJPanel;
    private JLabel[] lawJLs = new JLabel[6];
    public SegyTempFile segyTempFile;
    public ArrayList<SegyTempTrace> segyTempTraces;
    public ArrayList<SegyTempTraceData> segyTempTracesDataForDisplaying;
    public ArrayList<SegyTempTraceData> segyTempTracesDataAfterProcessing;
    public ArrayList<SegyTempTraceData> segyTempTracesDataVault;
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
            System.out.println("CURRENT_TRACE_NUMBER --------------------------------------->" +  settings_singleton.getCfgCurrentFileAddTraceNumber());
            System.out.println("CURRENT_TRACE_NUMBER --------------------------------------->" +  settings_singleton.getCfgFilesNumber());
            CURRENT_TRACE_NUMBER = settings_singleton.getCfgTraceNumber() - (settings_singleton.getCfgCurrentFileAddTraceNumber()*settings_singleton.getCfgFilesNumber());
            CURRENT_SAMPLE_NUMBER = settings_singleton.getCfgSamplesNumber();
            System.out.println("CURRENT_TRACE_NUMBER --------------------------------------->" +  CURRENT_TRACE_NUMBER);
            currentBalancingCorrKoef = new float[CURRENT_TRACE_NUMBER];
//            System.out.println("CURRENT_TRACE_NUMBER " + CURRENT_TRACE_NUMBER);
//            System.out.println("CURRENT_SAMPLE_NUMBER " + CURRENT_SAMPLE_NUMBER);

            segyTempTraces = new ArrayList<>();
//            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
//                this.segyTempTraces[i] = new SegyTempTrace();
//            }

            segyTempTracesDataForDisplaying = new ArrayList<>();
//            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
//                this.segyTempTracesDataForDisplaying[i] = new SegyTempTraceData();
//            }

            segyTempTracesDataAfterProcessing = new ArrayList<>();
//            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
//                this.segyTempTracesDataAfterProcessing[i] = new SegyTempTraceData();
//                this.segyTempTracesDataAfterProcessing[i].data = new float[CURRENT_SAMPLE_NUMBER];
//            }

            segyTempTracesDataVault = new ArrayList<>();
//            for (int i = 0; i < CURRENT_TRACE_NUMBER; i++) {
//                this.segyTempTracesDataVault[i] = new SegyTempTraceData();
//                this.segyTempTracesDataVault[i].data = new float[CURRENT_SAMPLE_NUMBER];
//            }
        }

    }

    public SegyTempFile getSegyTempFile() {
        return segyTempFile;
    }

    public ArrayList<SegyTempTrace> getSegyTempTraces() {
        return segyTempTraces;
    }

    public ArrayList<SegyTempTraceData> getSegyTempTracesDataForDisplaying() {
        return segyTempTracesDataForDisplaying;
    }

    public ArrayList<SegyTempTraceData> getSegyTempTracesDataAfterProcessing() {
        return segyTempTracesDataAfterProcessing;
    }

    public ArrayList<SegyTempTraceData> getSegyTempTracesDataFromVault() {
        return segyTempTracesDataVault;
    }


//    public static void setMainGui(main.java.vladimir.seis.mainGui mainGui) {
//        mainController.mainGui = mainGui;
//    }

    public void defineJLabelText(String s1, int index) {

        if ((s1 != null) && (index < 6)) {
            lawJLs[index].setText(s1);
        } else if (index < 6) lawJLs[index].setText("  ----------");


    }


    public void clickPickingButtonSuccess() {
//        System.out.println("Picking Button" + mainGui.getPickingButton().toString());
//        mainGui.getPickingButton().doClick();
        pickButton.doClick();
        if (mainGui.getSettings_singl().getCfgTrimLawDescrBegs()[mainGui.getSettings_singl().getCfgCurrentFileSeqNumber()]!=-1) mainGui.getSettings_singl().saveTrimLawEndsDescr();
        mainGui.getSettings_singl().formingFullLengthLaw();
        mainGui.getSettings_singl().formingShiftedFullTrimLaw(segyTempTracesDataForDisplaying);

    }

    public void saveSeismicTraceDataToVault() {
//        System.out.println("length ............ "+ segyTempTracesDataForDisplaying[0].getData().length);
//        System.out.println("length ............ "+ segyTempTracesDataVault[0].getData().length);
//        System.out.println("length ............ ");
        System.out.println(segyTempTracesDataForDisplaying.size());
        for (int i = 0; i < segyTempTracesDataForDisplaying.size(); i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying.get(i).getData().length; j++) {
                segyTempTracesDataVault.get(i).getData()[j] = segyTempTracesDataForDisplaying.get(i).getData()[j];
            }
        }


    }

    public void restoreSeismicTraceDataFromVault() {
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault.length);
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault.toString());
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault[0].getData().toString());
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault[0].getData().length);
        for (int i = 0; i < segyTempTracesDataForDisplaying.size(); i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying.get(i).getData().length; j++) {
                segyTempTracesDataForDisplaying.get(i).getData()[j] = segyTempTracesDataVault.get(i).getData()[j];
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

        System.out.println("segyTempTracesDataForDisplaying ->" + segyTempTracesDataForDisplaying.size());
        for (int i = settings_singleton.getCfgCurrentFileAddTraceNumber()+CURRENT_FILE_SEQ_NUMBER*(48 + settings_singleton.getCfgCurrentFileAddTraceNumber());
             i < 48 +settings_singleton.getCfgCurrentFileAddTraceNumber()+CURRENT_FILE_SEQ_NUMBER*(48 + settings_singleton.getCfgCurrentFileAddTraceNumber());
             i++) { //TODO Fix for zero add trace number
            int  tempSampleNumber = 1000;


            //Calculating summary of array from 0 to trimShifted point and deviding to sample number (average value)
            float regLevel = balancedAmpl; //regulation level
            float sum = 0;
            for (int j = 0; j < tempSampleNumber; j++) {
                sum = sum + Math.abs(segyTempTracesDataForDisplaying.get(i).getData()[j]);
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
        for (int i = settings_singleton.getCfgCurrentFileAddTraceNumber()+CURRENT_FILE_SEQ_NUMBER*(48+ settings_singleton.getCfgCurrentFileAddTraceNumber());
             i < settings_singleton.getCfgCurrentFileAddTraceNumber()+48+CURRENT_FILE_SEQ_NUMBER*(48+settings_singleton.getCfgCurrentFileAddTraceNumber());
             i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying.get(i).getData().length; j++) {
                segyTempTracesDataForDisplaying.get(i).getData()[j] = segyTempTracesDataForDisplaying.get(i).getData()[j] * currentBalancingCorrKoef[i];
            }
        }

    }

    public void resetBalancing() {

        for (int i = settings_singleton.getCfgCurrentFileAddTraceNumber(); i < segyTempTracesDataForDisplaying.size(); i++) {
            for (int j = 0; j < segyTempTracesDataForDisplaying.get(i).getData().length; j++) {

                segyTempTracesDataForDisplaying.get(i).getData()[j] = segyTempTracesDataVault.get(i).getData()[j];
            }
        }

    }

    public void addOneTraceToLists() {
        segyTempTraces.add(new SegyTempTrace());
        segyTempTracesDataForDisplaying.add(new SegyTempTraceData(new float[settings_singleton.getCfgSamplesNumber()]));
        segyTempTracesDataAfterProcessing.add(new SegyTempTraceData(new float[settings_singleton.getCfgSamplesNumber()]));
        segyTempTracesDataVault.add(new SegyTempTraceData(new float[settings_singleton.getCfgSamplesNumber()]));
    }

}
