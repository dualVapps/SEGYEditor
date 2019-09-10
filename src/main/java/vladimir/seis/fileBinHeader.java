package vladimir.seis;

import javax.swing.*;

public class fileBinHeader {
    public JPanel fileBinJPanel;
    private JPanel infoJPanel;
    private JPanel valueJPanel;
    private JPanel infoJPanel2;
    private JTextField jobId;
    private JTextField lineNumber;
    private JTextField reelNumber;
    private JTextField dataTracesPerEnsemble;
    private JTextField auxTracesPerEnsemble;
    private JTextField sampleIntervalMicroSec;
    private JTextField sampleIntervalMicroSecOrig;
    private JTextField samplesPerDataTrace;
    private JTextField samplesPerDataTraceOrig;
    private JTextField dataSampleFormatCode;
    private JTextField ensembleFold;
    private JTextField traceSortingCode;
    private JTextField verticalSumCode;
    private JTextField sweepFrequencyAtStartHz;
    private JTextField sweepFrequencyAtEndHz;
    private JTextField sweepLengthMs;
    private JTextField sweepTypeCode;
    private JTextField traceNumberOfSweepChannel;
    private JTextField sweepTraceTaperLengthAtStartMs;
    private JPanel valueJpanel2;
    private JTextField sweepTraceTaperLengthAtEndMs;
    private JTextField taperType;
    private JTextField correlatedDataTraces;
    private JTextField binaryGainRecovered;
    private JTextField amplitudeRecoveryMethod;
    private JTextField measurementSystem;
    private JTextField impulseSignalPolarity;
    private JTextField vibratoryPolarityCode;
    private JPanel infoAddJPanel;
    private JPanel infoAddJPanel2;
    private JTextField dsuSN;
    private JTextField manufacturer;
    private JTextField formatVersion;
    private JTextField reelHdrRev;
    private JTextField littleIndian;
    private JTextField lineSpacing;
    private JTextField staSpacing;
    private JTextField numOfFiles;
    private JTextField numOfTraces;

    public void setBinHeader(Integer jobId,
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
                             Short vibratoryPolarityCode, //TODO CHECK BIn Trace header
                             Short dsuSN,
                             Short manufacturer,
                             Short formatVersion,
                             Short reelHdrRev,
                             Short littleIndian,
                             Short lineSpacing,
                             Short staSpacing,
                             Integer numOfFiles,
                             Integer numOfTraces
//                             Short fixedLengthTraceFlag,
//                             Short extendedTextHeaders

    ){

        this.jobId.setText(jobId.toString());
        this.lineNumber.setText(lineNumber.toString());
        this.reelNumber.setText(reelNumber.toString());
        this.dataTracesPerEnsemble.setText(dataTracesPerEnsemble.toString());
        this.auxTracesPerEnsemble.setText(auxTracesPerEnsemble.toString());
        this.sampleIntervalMicroSec.setText(sampleIntervalMicroSec.toString());
        this.sampleIntervalMicroSecOrig.setText(sampleIntervalMicroSecOrig.toString());
        this.samplesPerDataTrace.setText(samplesPerDataTrace.toString());
        this.samplesPerDataTraceOrig.setText(samplesPerDataTraceOrig.toString());
        this.dataSampleFormatCode.setText(dataSampleFormatCode.toString());
        this.ensembleFold.setText(ensembleFold.toString());
        this.traceSortingCode.setText(traceSortingCode.toString());
        this.verticalSumCode.setText(verticalSumCode.toString());
        this.sweepFrequencyAtStartHz.setText(sweepFrequencyAtStartHz.toString());
        this.sweepFrequencyAtEndHz.setText(sweepFrequencyAtEndHz.toString());
        this.sweepLengthMs.setText(sweepLengthMs.toString());
        this.sweepTypeCode.setText(sweepTypeCode.toString());
        this.traceNumberOfSweepChannel.setText(traceNumberOfSweepChannel.toString());
        this.sweepTraceTaperLengthAtStartMs.setText(sweepTraceTaperLengthAtStartMs.toString());
        this.sweepTraceTaperLengthAtEndMs.setText(sweepTraceTaperLengthAtEndMs.toString());
        this.taperType.setText(taperType.toString());
        this.correlatedDataTraces.setText(correlatedDataTraces.toString());
        this.binaryGainRecovered.setText(binaryGainRecovered.toString());
        this.amplitudeRecoveryMethod.setText(amplitudeRecoveryMethod.toString());
        this.measurementSystem.setText(measurementSystem.toString());
        this.impulseSignalPolarity.setText(impulseSignalPolarity.toString());
        this.vibratoryPolarityCode.setText(vibratoryPolarityCode.toString());
        this.dsuSN.setText(dsuSN.toString());
        this.manufacturer.setText(manufacturer.toString());
        this.formatVersion.setText(formatVersion.toString());
        this.reelHdrRev.setText(reelHdrRev.toString());
        this.littleIndian.setText(littleIndian.toString());
        this.lineSpacing.setText(lineSpacing.toString());
        this.staSpacing.setText(staSpacing.toString());
        this.numOfFiles.setText(numOfFiles.toString());
        this.numOfTraces.setText(numOfTraces.toString());

//        this.segyRevision segyRevision
//        this.fixedLengthTraceFlag fixedLengthTraceFlag
//        this.extendedTextHeaders extendedTextHeaders

    }
}
