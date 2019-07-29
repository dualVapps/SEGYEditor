package main.java.vladimir.seis;

import main.java.vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;
import org.jfree.data.Range;

import java.util.ArrayList;

public class Settings_singleton {

    private ArrayList<TrimLawSingleValue> trimLaw = new ArrayList<>();
    private ArrayList<TrimLawSingleValue> fullTrimLaw = new ArrayList<>(48); //MaybeChange to simple array
    private int number_of_samples;
    private int sample_sizeInBytes;
    private Range initialFileScaleRange;


    private Settings_singleton settings_singleton = null;

    public Settings_singleton getSettings_singleton() {
        if (settings_singleton == null)
            settings_singleton = new Settings_singleton();
        return settings_singleton;
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

    public void zerodTrimLaw () {
            trimLaw.clear();
        }

    public void addValueToTrimLaw(TrimLawSingleValue trimLawSingleValue)
    {
        trimLaw.add(trimLawSingleValue);
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

//    public void setFullTrimLaw(ArrayList<TrimLawSingleValue> fullTrimLaw) {
//        this.fullTrimLaw = fullTrimLaw;
//    }

    //formingFullLengthTrimLaw
    public ArrayList<TrimLawSingleValue> formingFullLengthLaw (ArrayList<TrimLawSingleValue> shortMuteLaw) { //TODO Choose after or before AutoPick

        ArrayList<TrimLawSingleValue> tempFullTrimLaw = new ArrayList<>(48);

        for (int i = 0; i < shortMuteLaw.size()-1; i++) {
            int fromTrace, toTrace;
            int fromSample, toSample, samplePerTrace;
//            double fromValue, toValue, valuePerTrace;
            fromTrace = shortMuteLaw.get(i).getDatasetValue();
            toTrace = shortMuteLaw.get(i+1).getDatasetValue();
            fromSample = shortMuteLaw.get(i).getSampleValue();
            toSample = shortMuteLaw.get(i+1).getSampleValue();
            samplePerTrace = Math.round((toSample-fromSample)/(toTrace-fromTrace));
            tempFullTrimLaw.add(shortMuteLaw.get(i));
            for (int j = fromTrace+1; j < toTrace; j++) { //TODO Checking boundary values
                tempFullTrimLaw.add(new TrimLawSingleValue(
                        j,
       shortMuteLaw.get(i).getSampleValue()+j*samplePerTrace
                ));
            }

        }




        return  tempFullTrimLaw;
    }

    }


