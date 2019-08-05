package main.java.vladimir.seis;

import main.java.vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;
import main.java.vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;
import org.jfree.data.Range;

import java.util.ArrayList;

public class Settings_singleton {
    final float dYperSample = 0.4706f; //TODO Only for debug

    private ArrayList<TrimLawSingleValue> trimLaw = new ArrayList<>();
    private ArrayList<TrimLawSingleValue> fullTrimLaw = new ArrayList<>(48); //MaybeChange to simple array
    private ArrayList<TrimLawSingleValue> fullTrimShifted = new ArrayList<>(48); //MaybeChange to simple array
    private int number_of_samples = -1;
    private int sample_sizeInBytes;
    private int agcWindowSizeInTraces;
    private float korCoefToAverage;
    private boolean isFromNegToPos;
    private boolean isInPickingMode = false;
    private Range initialFileScaleRange;
    private Range currentFileScaleRange;


    private Settings_singleton settings_singleton = null;

    public Settings_singleton getSettings_singleton() {
        if (settings_singleton == null)
            settings_singleton = new Settings_singleton();
        return settings_singleton;
    }

    public float getKorCoefToAverage() {
        return korCoefToAverage;
    }

    public void setKorCoefToAverage(float korCoefToAverage) {
        this.korCoefToAverage = korCoefToAverage;
    }

    public Range getCurrentFileScaleRange() {
        return currentFileScaleRange;
    }

    public void setCurrentFileScaleRange(Range currentFileScaleRange) {
        this.currentFileScaleRange = currentFileScaleRange;
    }

    public boolean isInPickingMode() {
        return isInPickingMode;
    }

    public void setInPickingMode(boolean inPickingMode) {
        isInPickingMode = inPickingMode;
    }

    public Range getInitialFileScaleRange() {
        return initialFileScaleRange;
    }

    public void setInitialFileScaleRange(Range initialFileScaleRange) {
        this.initialFileScaleRange = initialFileScaleRange;
    }

    public int getNumber_of_samples() {
        return number_of_samples;
    }

    public void setNumber_of_samples(int number_of_samples) {
        this.number_of_samples = number_of_samples;
    }

    public int getSample_sizeInBytes() {
        return sample_sizeInBytes;
    }

    public void setSample_sizeInBytes(int sample_sizeInBytes) {
        this.sample_sizeInBytes = sample_sizeInBytes;
    }


    public void zeroedTrimLaw() {
        trimLaw.clear();
    }

    public void zeroedFullTrimLaw() {fullTrimLaw.clear(); }

    public void zeroedFullTrimLawShofted() {fullTrimShifted.clear(); }


    public void addValueToTrimLaw(TrimLawSingleValue trimLawSingleValue) {
        trimLaw.add(trimLawSingleValue);
    }

    public int getAgcWindowSizeInTraces() {
        return agcWindowSizeInTraces;
    }

    public void setAgcWindowSizeInTraces(int agcWindowSizeInTraces) {
        this.agcWindowSizeInTraces = agcWindowSizeInTraces;
    }

    public boolean isFromNegToPos() {
        return isFromNegToPos;
    }

    public void setFromNegToPos(boolean fromNegToPos) {
        isFromNegToPos = fromNegToPos;
    }

    public ArrayList<TrimLawSingleValue> getTrimLaw() {
        return trimLaw;
    }

    public void setTrimLaw(ArrayList<TrimLawSingleValue> trimLaw) {
        this.trimLaw = trimLaw;
    }

    public ArrayList<TrimLawSingleValue> getFullTrimLaw() {
        return fullTrimLaw;
    }

    public ArrayList<TrimLawSingleValue> getFullTrimShifted() {
        return fullTrimShifted;
    }

    //    public void setFullTrimLaw(ArrayList<TrimLawSingleValue> fullTrimLaw) {
//        this.fullTrimLaw = fullTrimLaw;
//    }

    //formingFullLengthTrimLaw
//    public void formingFullLengthLaw (ArrayList<TrimLawSingleValue> shortMuteLaw) { //TODO Choose after or before AutoPick
    public void formingFullLengthLaw() { //TODO Choose after or before AutoPick

        if (trimLaw.size() > 1) { //ErrorSafe checking

            ArrayList<TrimLawSingleValue> tempFullTrimLaw = new ArrayList<>(48);

            for (int i = 0; i < trimLaw.size() - 1; i++) {
                int fromTrace, toTrace;
                double fromX, toX;
                double fromY, toY;
                int fromSample, toSample;
                float samplePerTrace, dXperTrace, dYperTrace; // dXperTrace, dYperTrace only for visualisation
//            double fromValue, toValue, valuePerTrace;
                fromTrace = trimLaw.get(i).getDatasetValue();
                toTrace = trimLaw.get(i + 1).getDatasetValue();
                fromSample = trimLaw.get(i).getSampleValue();
                toSample = trimLaw.get(i + 1).getSampleValue();
                fromX = trimLaw.get(i).getX();
                toX = trimLaw.get(i + 1).getX();
                fromY = trimLaw.get(i).getY();
                toY = trimLaw.get(i + 1).getY();
//                System.out.println("samplePerTrace -" + (float)(toSample - fromSample) / (toTrace - fromTrace));
                samplePerTrace = (float) (toSample - fromSample) / (toTrace - fromTrace); //float - make result as float (not int)
                dXperTrace = (float) (toX - fromX) / (toTrace - fromTrace); //float - make result as float (not int)
                dYperTrace = (float) (toY - fromY) / (toTrace - fromTrace); //float - make result as float (not int)
//                System.out.println("samplePerTrace -" + samplePerTrace);

                tempFullTrimLaw.add(trimLaw.get(i));
                for (int j = fromTrace + 1; j < toTrace; j++) { //TODO Checking boundary values
                    tempFullTrimLaw.add(new TrimLawSingleValue(
                            trimLaw.get(i).getX() + (j - fromTrace) * dXperTrace,
                            trimLaw.get(i).getY() + (j - fromTrace) * dYperTrace,
                            j,
                            Math.round(trimLaw.get(i).getSampleValue() + (j - fromTrace) * samplePerTrace),
                            -1)
                    );
                }

            }
            tempFullTrimLaw.add(trimLaw.get(trimLaw.size() - 1));


            fullTrimLaw.clear();
            fullTrimLaw.addAll(tempFullTrimLaw);

//            System.out.println("*******************Short Mute Low (<=6)***************************");
            for (int i = 0; i < trimLaw.size(); i++) {
//                System.out.println("" + trimLaw.get(i).getDatasetValue()
//                        + " :: " + trimLaw.get(i).getSampleValue()
//                        + " :: " + trimLaw.get(i).getDataValue()
//                        + " :: " + trimLaw.get(i).getX()
//                        + " :: " + trimLaw.get(i).getY());
            }

//            System.out.println("*******************Full Mute Low (<=48)***************************");
            for (int i = 0; i < fullTrimLaw.size(); i++) {
//                System.out.println("" + fullTrimLaw.get(i).getDatasetValue()
//                        + " :: " + fullTrimLaw.get(i).getSampleValue()
//                        + " :: " + fullTrimLaw.get(i).getDataValue()
//                        + " :: " + fullTrimLaw.get(i).getX()
//                        + " :: " + fullTrimLaw.get(i).getY());
            }

        }
    }

    public void formingShiftedFullTrimLaw(SegyTempTraceData[] segyTempTraceData) {
        if (fullTrimLaw.size() > 1) {
            ArrayList<TrimLawSingleValue> tempShiftedFullTrimLaw = new ArrayList<>(48);

            for (int i = 0; i < fullTrimLaw.size(); i++) {
                boolean isSearchingSuccess = false;
                int shift = 0;
//                System.out.println(isFromNegToPos);
                while (!isSearchingSuccess) {   //100 - maximum searching shift value

                    if (isFromNegToPos) {
//                        System.out.println("Path 1");
                        if (segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue() - shift] < 0 &&
                                segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue() - shift - 1] >= 0)
                        //                      segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue()-shift-2]>0)
                        {
                            isSearchingSuccess = true;
                            tempShiftedFullTrimLaw.add(
                                    i,
                                    new TrimLawSingleValue(
                                            fullTrimLaw.get(i).getX(),
                                            fullTrimLaw.get(i).getY() - (shift+1) * dYperSample,
                                            fullTrimLaw.get(i).getDatasetValue(),
                                            fullTrimLaw.get(i).getSampleValue() - shift - 1,
                                            -1));
                        } else {
                            shift++;
                        }

                        if (shift > 100) tempShiftedFullTrimLaw.add(i, fullTrimLaw.get(i));
                    }

                    else {
//                        System.out.println("Path 2");
                        if (segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue() - shift] > 0 &&
                                segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue() - shift - 1] <= 0)
                        //                      segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue()-shift-2]>0)
                        {
                            isSearchingSuccess = true;
                            tempShiftedFullTrimLaw.add(
                                    i,
                                    new TrimLawSingleValue(
                                            fullTrimLaw.get(i).getX(),
                                            fullTrimLaw.get(i).getY() - (shift + 1) * dYperSample,
                                            fullTrimLaw.get(i).getDatasetValue(),
                                            fullTrimLaw.get(i).getSampleValue() - shift - 1,
                                            -1));
                        } else {
                            shift++;
                        }

                        if (shift > 100) tempShiftedFullTrimLaw.add(i, fullTrimLaw.get(i));

                    }
                }

            }
            fullTrimShifted.clear();
            fullTrimShifted.addAll(tempShiftedFullTrimLaw);

//            System.out.println("*******************Shifted Full Mute Low (<=48)***************************");
            for (int i = 0; i < fullTrimShifted.size(); i++) {
//                System.out.println("" + fullTrimShifted.get(i).getDatasetValue()
//                        + " :: " + fullTrimShifted.get(i).getSampleValue()
//                        + " :: " + fullTrimShifted.get(i).getDataValue()
//                        + " :: " + fullTrimShifted.get(i).getX()
//                        + " :: " + fullTrimShifted.get(i).getY()
//                        + " :: " + (fullTrimLaw.get(i).getSampleValue() - fullTrimShifted.get(i).getSampleValue()));
            }


        }


    }

}




