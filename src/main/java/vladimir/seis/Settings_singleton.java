package main.java.vladimir.seis;

import main.java.vladimir.seis.segystream.SEGYTempEdit.TrimLawSingleValue;

import java.util.ArrayList;

public class Settings_singleton {

    private ArrayList<TrimLawSingleValue> trimLaw = new ArrayList<>();
    private ArrayList<TrimLawSingleValue> fullTrimLaw = new ArrayList<>(48); //MaybeChange to simple array
    private int number_of_samples;
    private int sample_sizeInBytes;


    private Settings_singleton settings_singleton = null;

    public Settings_singleton getSettings_singleton() {
        if (settings_singleton == null)
            settings_singleton = new Settings_singleton();
        return settings_singleton;
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

    //formingFullLengthTrimLaw
    public ArrayList<TrimLawSingleValue> formingFullLengthLaw () {

        ArrayList<TrimLawSingleValue> tempFullTrimLaw = new ArrayList<>(48);



        return  tempFullTrimLaw;
    }

    }


