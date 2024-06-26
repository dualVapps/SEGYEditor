package vladimir.seis;

import vladimir.seis.segystream.SEGYTempEdit.SegyTempTraceData;
import vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;
import org.jfree.data.Range;

import java.util.ArrayList;
import java.util.Iterator;

public class Settings_singleton {
    final float dYperSample = 0.4706f; //TODO Only for debug
    //Segy cfg from bin header

    private int cfgCurrentFileSeqNumber = -1;

    private int cfgCurrentFileAddTraceNumber = -1;

    private int cfgSamplesNumber = -1;
    private int cfgEachSampleSizeBytes = -1;
    private int cfgTraceSizeBytes = -1;
    private int cfgTraceNumber = -1;
    private int cfgFilesNumber = -1;

    private int[] cfgTrimLawDescrBegs;
    private int[] cfgTrimLawDescrEnds;

    private ArrayList<TrimLawSingleValue> trimLaw = new ArrayList<>();
    private ArrayList<TrimLawSingleValue> fullTrimLaw = new ArrayList<>(48); //MaybeChange to simple array
    private ArrayList<TrimLawSingleValue> fullTrimShifted = new ArrayList<>(48); //MaybeChange to simple array
//    private int number_of_samples = -1;
    private int sample_sizeInBytes;
    private int agcWindowSizeInTraces;
    private float korCoefToAverage;
    private boolean isFromNegToPos = true;
    private boolean isInPickingMode = false;
    private Range initialFileScaleRange;
    private Range currentFileScaleRange;


    private Settings_singleton settings_singleton = null;

    public Settings_singleton getSettings_singleton() {
        if (settings_singleton == null)
            settings_singleton = new Settings_singleton();
        return settings_singleton;
    }


    public int getCfgCurrentFileAddTraceNumber() {
        return cfgCurrentFileAddTraceNumber;
    }

    public void setCfgCurrentFileAddTraceNumber(int cfgCurrentFileAddTraceNumber) {
        this.cfgCurrentFileAddTraceNumber = cfgCurrentFileAddTraceNumber;
    }

    public int getCfgCurrentFileSeqNumber() {
        return cfgCurrentFileSeqNumber;
    }

    public void setCfgCurrentFileSeqNumber(int cfgCurrentFileSeqNumber) {
        this.cfgCurrentFileSeqNumber = cfgCurrentFileSeqNumber;
    }

    public int getCfgFilesNumber() {
        return cfgFilesNumber;
    }

    public void setCfgFilesNumber(int cfgFilesNumber) {
        this.cfgFilesNumber = cfgFilesNumber;
    }

    public int getCfgTraceNumber() {
        return cfgTraceNumber;
    }

    public void setCfgTraceNumber(int cfgTraceNumber) {
        this.cfgTraceNumber = cfgTraceNumber;
    }

    public int getCfgSamplesNumber() {
        return cfgSamplesNumber;
    }

    public void setCfgSamplesNumber(int cfgSamplesNumber) {
        this.cfgSamplesNumber = cfgSamplesNumber;
    }

    public int getCfgEachSampleSizeBytes() {
        return cfgEachSampleSizeBytes;
    }

    public void setCfgEachSampleSizeBytes(int cfgEachSampleSizeBytes) {
        this.cfgEachSampleSizeBytes = cfgEachSampleSizeBytes;
    }

    public int getCfgTraceSizeBytes() {
        return cfgTraceSizeBytes;
    }

    public void setCfgTraceSizeBytes(int cfgTraceSizeBytes) {
        this.cfgTraceSizeBytes = cfgTraceSizeBytes;
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

//    public int getNumber_of_samples() {
//        return number_of_samples;
//    }
//
//    public void setNumber_of_samples(int number_of_samples) {
//        this.number_of_samples = number_of_samples;
//    }

    public int getSample_sizeInBytes() {
        return sample_sizeInBytes;
    }

    public void setSample_sizeInBytes(int sample_sizeInBytes) {
        this.sample_sizeInBytes = sample_sizeInBytes;
    }


    public void zeroedTrimLaw() {
        trimLaw.clear();
    }

    public void zeroedFullTrimLaw() {
        fullTrimLaw.clear();
    }

    public void zeroedFullTrimLawShifted() {
        fullTrimShifted.clear();
    }


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

    public int[] getCfgTrimLawDescrBegs() {
        return cfgTrimLawDescrBegs;
    }

    public void setCfgTrimLawDescrBegs(int[] cfgTrimLawDescrBegs) {
        this.cfgTrimLawDescrBegs = cfgTrimLawDescrBegs;
    }

    public int[] getCfgTrimLawDescrEnds() {
        return cfgTrimLawDescrEnds;
    }

    public void setCfgTrimLawDescrEnds(int[] cfgTrimLawDescrEnds) {
        this.cfgTrimLawDescrEnds = cfgTrimLawDescrEnds;
    }

    public void resetFileScales() {
        initialFileScaleRange = null;
        currentFileScaleRange = null;
    }

    //    public void setFullTrimLaw(ArrayList<TrimLawSingleValue> fullTrimLaw) {
//        this.fullTrimLaw = fullTrimLaw;
//    }

    //formingFullLengthTrimLaw
//    public void formingFullLengthLaw (ArrayList<TrimLawSingleValue> shortMuteLaw) { //TODO Choose after or before AutoPick
    public void formingFullLengthLaw() { //TODO Choose after or before AutoPick

        fullTrimLaw.clear();
        ArrayList<ArrayList<TrimLawSingleValue>> tempLists = new ArrayList<>();
        for (int i = 0; i < cfgFilesNumber; i++) {
            tempLists.add(new ArrayList<TrimLawSingleValue>(48));
            for (int j = 0; j < trimLaw.size(); j++) {
                if (trimLaw.get(j).getReelNumber() == i) {
                    tempLists.get(i).add(trimLaw.get(j));
                }
            }

        }

        for (int i = 0; i < tempLists.size(); i++) {
            fullTrimLaw.addAll(formingFullLengthLaw(tempLists.get(i)));
        }
//        System.out.println("lengths");
//        System.out.println(trimLaw.size());
//        System.out.println("---");
        for (int i = 0; i < tempLists.size(); i++) {
//            System.out.println(tempLists.get(i).size());
        }
//        System.out.println("---");
//        System.out.println(fullTrimLaw.size());

    }

    public void formingShiftedFullTrimLaw(ArrayList<SegyTempTraceData> segyTempTraceData) {
        if (fullTrimLaw.size() > 1) {
            ArrayList<TrimLawSingleValue> tempShiftedFullTrimLaw = new ArrayList<>(48);

            for (int i = 0; i < fullTrimLaw.size(); i++) {
                boolean isSearchingSuccess = false;
                int shift = 0;
//                System.out.println(isFromNegToPos);
                int tempTraceNumber = fullTrimLaw.get(i).getDatasetValue()+(fullTrimLaw.get(i).getReelNumber()* (48 + getCfgCurrentFileAddTraceNumber()));
//                System.out.println("tempTraceNumber " + tempTraceNumber);
                while (!isSearchingSuccess) {   //100 - maximum searching shift value

                    if (isFromNegToPos) {
//                        System.out.println("Path 1");
                        if (shift < fullTrimLaw.get(i).getSampleValue() &&
                                segyTempTraceData.get(tempTraceNumber).getData()[fullTrimLaw.get(i).getSampleValue() - shift] < 0 &&
                                segyTempTraceData.get(tempTraceNumber).getData()[fullTrimLaw.get(i).getSampleValue() - shift - 1] >= 0)
                        //                      segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue()-shift-2]>0)
                        {
                            isSearchingSuccess = true;
                            tempShiftedFullTrimLaw.add(
                                    i,
                                    new TrimLawSingleValue(
                                            fullTrimLaw.get(i).getX(),
                                            fullTrimLaw.get(i).getY() - shift * dYperSample,
                                            fullTrimLaw.get(i).getReelNumber(),
                                            fullTrimLaw.get(i).getDatasetValue(),
                                            fullTrimLaw.get(i).getSampleValue() - shift,
                                            -1));
                        } else {
                            shift++;
                        }

                        if (shift > 100) {
                            isSearchingSuccess = true;
                            tempShiftedFullTrimLaw.add(i, fullTrimLaw.get(i));
                        }
                    }
                    else {
//                        System.out.println("Path 2");


                        if (
                                shift < fullTrimLaw.get(i).getSampleValue() &&
                                        segyTempTraceData.get(tempTraceNumber).getData()[fullTrimLaw.get(i).getSampleValue() - shift] > 0 &&
                                        segyTempTraceData.get(tempTraceNumber).getData()[fullTrimLaw.get(i).getSampleValue() - shift - 1] <= 0)
                        //                      segyTempTraceData[fullTrimLaw.get(i).getDatasetValue()].getData()[fullTrimLaw.get(i).getSampleValue()-shift-2]>0)
                        {
                            isSearchingSuccess = true;
                            tempShiftedFullTrimLaw.add(
                                    i,
                                    new TrimLawSingleValue(
                                            fullTrimLaw.get(i).getX(),
                                            fullTrimLaw.get(i).getY() - (shift + 1) * dYperSample,
                                            fullTrimLaw.get(i).getReelNumber(),
                                            fullTrimLaw.get(i).getDatasetValue(),
                                            fullTrimLaw.get(i).getSampleValue(),
                                            -1));
                        } else {
                            shift++;
                        }

                        if (shift > 100) {
                            isSearchingSuccess = true;
                            tempShiftedFullTrimLaw.add(i, fullTrimLaw.get(i));
                        }

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

    public void initTrimLawDescr(int size) { //Used in scala
        cfgTrimLawDescrBegs = new int[size];
        cfgTrimLawDescrEnds = new int[size];

        for (int i = 0; i < cfgTrimLawDescrBegs.length; i++) {
            cfgTrimLawDescrBegs[i] = -1;
            cfgTrimLawDescrEnds[i] = -1;
        }
    }

    public void saveTrimLawEndsDescr() {
        cfgTrimLawDescrEnds[cfgCurrentFileSeqNumber] = getTrimLaw().get(getTrimLaw().size() - 1).getDatasetValue();
    }

    public void deleteTrimLawSpec(int reel) {
        ArrayList<TrimLawSingleValue> tempArraylist = new ArrayList(getTrimLaw());

        Iterator<TrimLawSingleValue> itr = getTrimLaw().iterator();


        while (itr.hasNext()) {
            TrimLawSingleValue tValue = itr.next();

            if (tValue.getReelNumber() == reel) {
                itr.remove();
            }

        }


    }

    private ArrayList<TrimLawSingleValue> formingFullLengthLaw(ArrayList<TrimLawSingleValue> trimLaw) {
        ArrayList<TrimLawSingleValue> tempFullTrimLaw = new ArrayList<>();
        if (trimLaw.size() > 1) { //ErrorSafe checking



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
                            trimLaw.get(i).getReelNumber(),
                            j,
                            Math.round(trimLaw.get(i).getSampleValue() + (j - fromTrace) * samplePerTrace),
                            -1)
                    );
                }

            }
            tempFullTrimLaw.add(trimLaw.get(trimLaw.size() - 1));




////            System.out.println("*******************Short Mute Low (<=6)***************************");
//            for (int i = 0; i < trimLaw.size(); i++) {
////                System.out.println("" + trimLaw.get(i).getDatasetValue()
////                        + " :: " + trimLaw.get(i).getSampleValue()
////                        + " :: " + trimLaw.get(i).getDataValue()
////                        + " :: " + trimLaw.get(i).getX()
////                        + " :: " + trimLaw.get(i).getY());
//            }
//
////            System.out.println("*******************Full Mute Low (<=48)***************************");
//            for (int i = 0; i < fullTrimLaw.size(); i++) {
////                System.out.println("" + fullTrimLaw.get(i).getDatasetValue()
////                        + " :: " + fullTrimLaw.get(i).getSampleValue()
////                        + " :: " + fullTrimLaw.get(i).getDataValue()
////                        + " :: " + fullTrimLaw.get(i).getX()
////                        + " :: " + fullTrimLaw.get(i).getY());
//            }


        }

        return tempFullTrimLaw;
    }

    public void resetCfgValues() {

        cfgCurrentFileSeqNumber = -1;
        cfgCurrentFileAddTraceNumber = 0; // Initial count
        cfgSamplesNumber = -1;
        cfgEachSampleSizeBytes = -1;
        cfgTraceSizeBytes = -1;
        cfgTraceNumber = -1;
        cfgFilesNumber = -1;
    }
}




