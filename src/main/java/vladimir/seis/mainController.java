/*Контроллер в моделе котроллер модель отображение
* */

package vladimir.seis;


import vladimir.seis.segystream.SEGYTempEdit.SegyTempFile;
import vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;
import vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;

import javax.swing.*;

public class mainController {

    //    private  mainGui mainGui;
    private JPanel mainJPanel;
    private JLabel[] lawJLs = new JLabel[6];
    public SegyTempFile segyTempFile;
    public SegyTempTrace[] segyTempTraces;
    public SegyTempTraceData[] segyTempTracesData;
    public SegyTempTraceData[] segyTempTracesDataVault;
    private JButton pickButton;
    private  float[] currentBalancingCorrKoef = new float[54];


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

        segyTempTracesDataVault = new SegyTempTraceData[54];
        for (int i = 0; i < 54; i++) {
            this.segyTempTracesDataVault[i] = new SegyTempTraceData();
            this.segyTempTracesDataVault[i].data = new float[2048];
        }


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
        mainGui.getSettings_singl().formingShiftedFullTrimLaw(segyTempTracesData);

    }

    public void saveSeismicTraceDataToVault() {
//        System.out.println("length ............ "+ segyTempTracesData[0].getData().length);
//        System.out.println("length ............ "+ segyTempTracesDataVault[0].getData().length);
//        System.out.println("length ............ ");
        for (int i = 0; i < segyTempTracesData.length; i++) {
            for (int j = 0; j < segyTempTracesData[i].getData().length; j++) {
                segyTempTracesDataVault[i].getData()[j] = segyTempTracesData[i].getData()[j];
            }
        }


    }

    public void restoreSeismicTraceDataToVault() {
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault.length);
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault.toString());
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault[0].getData().toString());
//        System.out.println("restoreSeismicTraceDataToVault" + segyTempTracesDataVault[0].getData().length);
        for (int i = 0; i < segyTempTracesData.length; i++) {
            for (int j = 0; j < segyTempTracesData[i].getData().length; j++) {
                segyTempTracesData[i].getData()[j] = segyTempTracesDataVault[i].getData()[j];
            }
        }

    }

    public void balancingTempData() { //TODO rewrite AGN to balancing
        calcBalancingKoef();
        applyBalancingToTempData();


    }

    public void calcBalancingKoef () {
//        System.out.println("CalcBalancingKoef ->> Start");

        final int balancedAmpl = 1;

        for (int i = 6; i < segyTempTracesData.length; i++) {
            int  tempSampleNumber = 1000;


            //Calculating summary of array from 0 to trimShifted point and deviding to sample number (average value)
            float regLevel = balancedAmpl; //regulation level
            float sum = 0;
            for (int j = 0; j < tempSampleNumber; j++) {
                sum = sum + Math.abs(segyTempTracesData[i].getData()[j]);
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
        for (int i = 6; i < segyTempTracesData.length; i++) {
            for (int j = 0; j < segyTempTracesData[i].getData().length; j++) {
                segyTempTracesData[i].getData()[j] = segyTempTracesData[i].getData()[j] * currentBalancingCorrKoef[i];
            }
        }

    }

    public void resetBalancing() {

        for (int i = 6; i < segyTempTracesData.length; i++) {
            for (int j = 0; j < segyTempTracesData[i].getData().length; j++) {

                segyTempTracesData[i].getData()[j] = segyTempTracesDataVault[i].getData()[j];
            }
        }

    }
}
