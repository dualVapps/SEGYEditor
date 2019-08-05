package main.java.vladimir.seis.segystream.SEGYTempEdit;
/*
* Класс в который записываютя данные из прочитанного файла
* (только текстовый и бинарный заголовки трасс)
* для выполнения различных операций, содержит
* функцию для записи данных
*

*
* */




import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;


public class SegyTempFile {

    String FileTextHeader = new String();

    // binary file header decoding
    Integer jobId;
    Integer lineNumber;
    Integer reelNumber;
    Short dataTracesPerEnsemble;
    Short auxTracesPerEnsemble;
    Short sampleIntervalMicroSec;
    Short sampleIntervalMicroSecOrig;
    Short samplesPerDataTrace;
    Short samplesPerDataTraceOrig;
    Short dataSampleFormatCode;
    Short ensembleFold;
    Short traceSortingCode;
    Short verticalSumCode;
    Short sweepFrequencyAtStartHz;
    Short sweepFrequencyAtEndHz;
    Short sweepLengthMs;
    Short sweepTypeCode;
    Short traceNumberOfSweepChannel;
    Short sweepTraceTaperLengthAtStartMs;
    Short sweepTraceTaperLengthAtEndMs;
    Short taperType;
    Short correlatedDataTraces;
    Short binaryGainRecovered;
    Short amplitudeRecoveryMethod;
    Short measurementSystem;
    Short impulseSignalPolarity;
    Short vibratoryPolarityCode;

    //I/O Specific part

    Short dsuSN;
    Short manufacturer;
    Short formatVersion;
    Short reelHdrRev;
    Short littleIndian;
    Short lineSpacing;
    Short staSpacing;
    Integer numOfFiles;
    Integer numOfTraces;


    // 3261 - 3500 Unassigned (Use: FOR I/O FOUR 3283-3500)
    Short segyRevision; //Rev1 -> 0x100
    Short fixedLengthTraceFlag;
    Short extendedTextHeaders;
    // 3507 - 3600 Unassigned




    public void setFileTextHeader(String fileTextHeader) {
        FileTextHeader = fileTextHeader;
    }

    public void setBinFileHeader(Integer jobId,
            Integer lineNumber,
            Integer reelNumber,
            Short dataTracesPerEnsemble,
            Short auxTracesPerEnsemble,
            Short sampleIntervalMicroSec,
            Short sampleIntervalMicroSecOrig,
            Short samplesPerDataTrace,
            Short samplesPerDataTraceOrig,
            Short dataSampleFormatCode,
            Short ensembleFold,
            Short traceSortingCode,
            Short verticalSumCode,
            Short sweepFrequencyAtStartHz,
            Short sweepFrequencyAtEndHz,
            Short sweepLengthMs,
            Short sweepTypeCode,
            Short traceNumberOfSweepChannel,
            Short sweepTraceTaperLengthAtStartMs,
            Short sweepTraceTaperLengthAtEndMs,
            Short taperType,
            Short correlatedDataTraces,
            Short binaryGainRecovered,
            Short amplitudeRecoveryMethod,
            Short measurementSystem,
            Short impulseSignalPolarity,
            Short vibratoryPolarityCode,
            Short dsuSN,
            Short manufacturer,
            Short formatVersion,
            Short reelHdrRev,
            Short littleIndian,
            Short lineSpacing,
            Short staSpacing,
            Integer numOfFiles,
            Integer numOfTraces,
            Short segyRevision,
            Short fixedLengthTraceFlag,
            Short extendedTextHeaders) {
        this.jobId = jobId;
        this.lineNumber = lineNumber;
        this.reelNumber = reelNumber;
        this.dataTracesPerEnsemble = dataTracesPerEnsemble;
        this.auxTracesPerEnsemble = auxTracesPerEnsemble;
        this.sampleIntervalMicroSec = sampleIntervalMicroSec;
        this.sampleIntervalMicroSecOrig = sampleIntervalMicroSecOrig;
        this.samplesPerDataTrace = samplesPerDataTrace;
        this.samplesPerDataTraceOrig = samplesPerDataTraceOrig;
        this.dataSampleFormatCode = dataSampleFormatCode;
        this.ensembleFold = ensembleFold;
        this.traceSortingCode = traceSortingCode;
        this.verticalSumCode = verticalSumCode;
        this.sweepFrequencyAtStartHz = sweepFrequencyAtStartHz;
        this.sweepFrequencyAtEndHz = sweepFrequencyAtEndHz;
        this.sweepLengthMs = sweepLengthMs;
        this.sweepTypeCode = sweepTypeCode;
        this.traceNumberOfSweepChannel = traceNumberOfSweepChannel;
        this.sweepTraceTaperLengthAtStartMs = sweepTraceTaperLengthAtStartMs;
        this.sweepTraceTaperLengthAtEndMs = sweepTraceTaperLengthAtEndMs;
        this.taperType = taperType;
        this.correlatedDataTraces = correlatedDataTraces;
        this.binaryGainRecovered = binaryGainRecovered;
        this.amplitudeRecoveryMethod = amplitudeRecoveryMethod;
        this.measurementSystem = measurementSystem;
        this.impulseSignalPolarity = impulseSignalPolarity;
        this.vibratoryPolarityCode = vibratoryPolarityCode;
        this.segyRevision = segyRevision;
        this.fixedLengthTraceFlag = fixedLengthTraceFlag;
        this.extendedTextHeaders = extendedTextHeaders;
        this.dsuSN = dsuSN;
        this.manufacturer = manufacturer;
        this.formatVersion = formatVersion;
        this.reelHdrRev = reelHdrRev;
        this.littleIndian = littleIndian;
        this.lineSpacing = lineSpacing;
        this.staSpacing = staSpacing;
        this.numOfFiles = numOfFiles;
        this.numOfTraces = numOfTraces;
    }

    public void printFileHeader(){

//        System.out.print( lineNumber.toString()+":  ");
//        System.out.println( lineNumber);
//        System.out.print( reelNumber.toString()+":  ");
//        System.out.println( reelNumber);
//        System.out.print( dataTracesPerEnsemble.toString()+":  ");
//        System.out.println( dataTracesPerEnsemble);
//        System.out.print( auxTracesPerEnsemble.toString()+":  ");
//        System.out.println( auxTracesPerEnsemble);
//        System.out.print( sampleIntervalMicroSec.toString()+":  ");
//        System.out.println( sampleIntervalMicroSec);
//        System.out.print( sampleIntervalMicroSecOrig.toString()+":  ");
//        System.out.println( sampleIntervalMicroSecOrig);
//        System.out.print( samplesPerDataTrace.toString()+":  ");
//        System.out.println( samplesPerDataTrace);
//        System.out.print( samplesPerDataTraceOrig.toString()+":  ");
//        System.out.println( samplesPerDataTraceOrig);
//        System.out.print( dataSampleFormatCode.toString()+":  ");
//        System.out.println( dataSampleFormatCode);
//        System.out.print( ensembleFold.toString()+":  ");
//        System.out.println( ensembleFold);
//        System.out.print( traceSortingCode.toString()+":  ");
//        System.out.println( traceSortingCode);
//        System.out.print( verticalSumCode.toString()+":  ");
//        System.out.println( verticalSumCode);
//        System.out.print( sweepFrequencyAtStartHz.toString()+":  ");
//        System.out.println( sweepFrequencyAtStartHz);
//        System.out.print( sweepFrequencyAtEndHz.toString()+":  ");
//        System.out.println( sweepFrequencyAtEndHz);
//        System.out.print( sweepLengthMs.toString()+":  ");
//        System.out.println( sweepLengthMs);
//        System.out.print( sweepTypeCode.toString()+":  ");
//        System.out.println( sweepTypeCode);
//        System.out.print( traceNumberOfSweepChannel.toString()+":  ");
//        System.out.println( traceNumberOfSweepChannel);
//        System.out.print( sweepTraceTaperLengthAtStartMs.toString()+":  ");
//        System.out.println( sweepTraceTaperLengthAtStartMs);
//        System.out.print( sweepTraceTaperLengthAtEndMs.toString()+":  ");
//        System.out.println( sweepTraceTaperLengthAtEndMs);
//        System.out.print( taperType.toString()+":  ");
//        System.out.println( taperType);
//        System.out.print( correlatedDataTraces.toString()+":  ");
//        System.out.println( correlatedDataTraces);
//        System.out.print( binaryGainRecovered.toString()+":  ");
//        System.out.println( binaryGainRecovered);
//        System.out.print( amplitudeRecoveryMethod.toString()+":  ");
//        System.out.println( amplitudeRecoveryMethod);
//        System.out.print( measurementSystem.toString()+":  ");
//        System.out.println( measurementSystem);
//        System.out.print( impulseSignalPolarity.toString()+":  ");
//        System.out.println( impulseSignalPolarity);
//        System.out.print( vibratoryPolarityCode.toString()+":  ");
//        System.out.println( vibratoryPolarityCode);
//        System.out.print( segyRevision.toString()+":  ");
//        System.out.println( segyRevision);
//        System.out.print( fixedLengthTraceFlag.toString()+":  ");
//        System.out.println( fixedLengthTraceFlag);
//        System.out.print( extendedTextHeaders.toString()+":  ");
//        System.out.println( extendedTextHeaders);
    }

    public String getFileTextHeader() {
        return FileTextHeader;
    }

    public Integer getJobId() {
        return jobId;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Integer getReelNumber() {
        return reelNumber;
    }

    public Short getDataTracesPerEnsemble() {
        return dataTracesPerEnsemble;
    }

    public Short getAuxTracesPerEnsemble() {
        return auxTracesPerEnsemble;
    }

    public Short getSampleIntervalMicroSec() {
        return sampleIntervalMicroSec;
    }

    public Short getSampleIntervalMicroSecOrig() {
        return sampleIntervalMicroSecOrig;
    }

    public Short getSamplesPerDataTrace() {
        return samplesPerDataTrace;
    }

    public Short getSamplesPerDataTraceOrig() {
        return samplesPerDataTraceOrig;
    }

    public Short getDataSampleFormatCode() {
        return dataSampleFormatCode;
    }

    public Short getEnsembleFold() {
        return ensembleFold;
    }

    public Short getTraceSortingCode() {
        return traceSortingCode;
    }

    public Short getVerticalSumCode() {
        return verticalSumCode;
    }

    public Short getSweepFrequencyAtStartHz() {
        return sweepFrequencyAtStartHz;
    }

    public Short getSweepFrequencyAtEndHz() {
        return sweepFrequencyAtEndHz;
    }

    public Short getSweepLengthMs() {
        return sweepLengthMs;
    }

    public Short getSweepTypeCode() {
        return sweepTypeCode;
    }

    public Short getTraceNumberOfSweepChannel() {
        return traceNumberOfSweepChannel;
    }

    public Short getSweepTraceTaperLengthAtStartMs() {
        return sweepTraceTaperLengthAtStartMs;
    }

    public Short getSweepTraceTaperLengthAtEndMs() {
        return sweepTraceTaperLengthAtEndMs;
    }

    public Short getTaperType() {
        return taperType;
    }

    public Short getCorrelatedDataTraces() {
        return correlatedDataTraces;
    }

    public Short getBinaryGainRecovered() {
        return binaryGainRecovered;
    }

    public Short getAmplitudeRecoveryMethod() {
        return amplitudeRecoveryMethod;
    }

    public Short getMeasurementSystem() {
        return measurementSystem;
    }

    public Short getImpulseSignalPolarity() {
        return impulseSignalPolarity;
    }

    public Short getVibratoryPolarityCode() {
        return vibratoryPolarityCode;
    }


    public Short getDsuSN() {
        return dsuSN;
    }

    public Short getManufacturer() {
        return manufacturer;
    }

    public Short getFormatVersion() {
        return formatVersion;
    }

    public Short getReelHdrRev() {
        return reelHdrRev;
    }

    public Short getLittleIndian() {
        return littleIndian;
    }

    public Short getLineSpacing() {
        return lineSpacing;
    }

    public Short getStaSpacing() {
        return staSpacing;
    }

    public Integer getNumOfFiles() {
        return numOfFiles;
    }

    public Integer getNumOfTraces() {
        return numOfTraces;
    }

    public Short getSegyRevision() {
        return segyRevision;
    }


    public void writeToDataOutputStream(DataOutputStream dos) throws IOException {
        dos.write(FileTextHeader.getBytes(Charset.forName("CP037")));

        ByteBuffer bb = ByteBuffer.allocate(400);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(jobId);
        bb.putInt(lineNumber);
        bb.putInt(reelNumber);
        bb.putShort(dataTracesPerEnsemble);
        bb.putShort(auxTracesPerEnsemble);
        bb.putShort(sampleIntervalMicroSec);
        bb.putShort(sampleIntervalMicroSecOrig);
        bb.putShort(samplesPerDataTrace);
        bb.putShort(samplesPerDataTraceOrig);
        bb.putShort(dataSampleFormatCode);
        bb.putShort(ensembleFold);
        bb.putShort(traceSortingCode);
        bb.putShort(verticalSumCode);
        bb.putShort(sweepFrequencyAtStartHz);
        bb.putShort(sweepFrequencyAtEndHz);
        bb.putShort(sweepLengthMs);
        bb.putShort(sweepTypeCode);
        bb.putShort(traceNumberOfSweepChannel);
        bb.putShort(sweepTraceTaperLengthAtStartMs);
        bb.putShort(sweepTraceTaperLengthAtEndMs);
        bb.putShort(taperType);
        bb.putShort(correlatedDataTraces);
        bb.putShort(binaryGainRecovered);
        bb.putShort(amplitudeRecoveryMethod);
        bb.putShort(measurementSystem);
        bb.putShort(impulseSignalPolarity);
        bb.putShort(vibratoryPolarityCode);
        bb.putShort(dsuSN);
        bb.putShort(manufacturer);
        bb.putShort(formatVersion);
        bb.putShort(reelHdrRev);
        bb.putShort(littleIndian);
        bb.putShort(lineSpacing);
        bb.putShort(staSpacing);
        bb.putInt(numOfFiles);
        bb.putInt(numOfTraces);
        for (int i = 0; i < 226; i++) { //228 = 3500 - 3283 + 1 Gap in read order

            bb.put((byte)0x00);
        }
        bb.putShort(segyRevision);
        bb.putShort(fixedLengthTraceFlag);
        bb.putShort(extendedTextHeaders);

        dos.write(bb.array());


//        numOfTraces = it.getInt,
//                segyRevision = {
//                        it = it.drop(3500 - 3283 + 1) // 3261 - 3500 Unassigned (Use: FOR I/O FOUR 3281-3500)
//                        it.getShort
//                },
//                fixedLengthTraceFlag = it.getShort,

    }


}
