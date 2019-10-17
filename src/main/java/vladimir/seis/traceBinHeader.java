package vladimir.seis;

import vladimir.seis.segystream.SEGYTempEdit.SegyTempTrace;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class traceBinHeader { //TODO rewrite Segy1 class
    public JPanel traceBinJpanel;
    private JPanel traceSelectorJPanel;
    private JSpinner traceSelector;
    private JPanel binDataJPanel;
    private JPanel infoJPanel1;
    private JPanel dataJPanel1;
    private JTextField traceSequenceNumberWithinLine;
    private JTextField traceSequenceNumberWithinSegyFile;
    private JTextField origFieldRecordNumber;
    private JTextField traceNumberWithinOrigFieldRecord;
    private JTextField energySourcePointNumber;
    private JTextField ensembleNumber;
    private JTextField traceNumberWithinEnsemble;
    private JTextField traceIdCode;
    private JTextField vertSummedTracesYieldingThisTrace;
    private JTextField horizStackedTracesYieldingThisTrace;
    private JTextField dataUse;
    private JTextField distFromSourcePointToReceiverGroup;
    private JTextField receiverGroupElevation;
    private JTextField surfaceElevationAtSource;
    private JTextField sourceDepthBelowSurface;
    private JTextField datumElevationAtReceiverGroup;
    private JTextField datumElevationAtSource;
    private JTextField waterDepthAtSource;
    private JTextField waterDepthAtGroup;
    private JTextField elevationScalar;
    private JTextField coordScalar;
    private JTextField sourceCoordX;
    private JTextField sourceCoordY;
    private JTextField groupCoordX;
    private JTextField groupCoordY;
    private JTextField coordUnits;
    private JTextField weatheringVelocity;
    private JTextField subweatheringVelocity;
    private JTextField upholeTimeAtSource;
    private JTextField upholeTimeAtGroup;
    private JTextField sourceStaticCorrection;
    private JTextField groupStaticCorrectionMs;
    private JTextField totalStaticAppliedMs;
    private JTextField lagTimeAMs;
    private JTextField lagTimeBMs;
    private JTextField delayRecordingTimeMs;
    private JTextField muteTimeStartMs;
    private JTextField muteTimeEndMs;
    private JTextField samplesNumber;
    private JTextField sampleIntervalMs;
    private JTextField gainType;
    private JTextField instrumentGainConstant;
    private JTextField instrumentEarlyOrInitialGain;
    private JTextField correlated;
    private JTextField sweepFrequencyAtStart;
    private JTextField sweepFrequencyAtEnd;
    private JTextField sweepLengthMs;
    private JTextField sweepType;
    private JTextField sweepTraceTaperLengthAtStartMs;
    private JTextField sweepTraceTaperLengthAtEndMs;
    private JTextField taperType;
    private JTextField aliasFilterFreq;
    private JTextField aliasFilterSlope;
    private JTextField notchFilterFreq;
    private JTextField notchFilterSlope;
    private JPanel infoJPanel2;
    private JPanel dataJPanel2;
    private JTextField lowCutFreq;
    private JTextField highCutFreq;
    private JTextField lowCutSlope;
    private JTextField highCutSlope;
    private JTextField year;
    private JTextField dayOfYear;
    private JTextField hourOfDay;
    private JTextField minuteOfHour;
    private JTextField secondOfMinute;
    private JTextField timeBasisCode;
    private JTextField traceWeightFactor;
    private JTextField geophoneGroupNumOfRollSwitchPosOne;
    private JTextField geophoneGroupNumOfTraceOneWithinOrigRecord;
    private JTextField geophoneGroupNumOfLastWithinOrigRecord;
    private JTextField gapSize;
    private JTextField overTravel;
    private JTextField segyHedRev;
    private JTextField shotID;
    private JTextField auxChanSigDesc;
    private JTextField auxChanID;
    private JTextField shotPointLine;
    private JTextField shotPointSta;
    private JTextField recLine;
    private JTextField recSta;
    private JTextField vSMT;
    private JTextField vSMTScaleCode;
    private JTextField vSMTHOA;
    private JTextField vSMTVOA;
    private JTextField sourceType;
    private JTextField sourceTypeSEGD;
    private JTextField auxChanType;
    private JTextField noiseEdType;
    private JTextField noiseEdGateLength;
    private JTextField deviceType;
    private JTextField devSerNum;
    private JTextField devChanNum;
    private JTextField auxChanSourcsetID;
    private JTextField debStatus;
    private JTextField lATTestType;
    private JTextField fixedLowCutFreq;
    private JTextField fixedLowCutSlope;
    private JTextField boxFunction;
    private JTextField notchBCenFreq;
    private JTextField notchBBandwidth;
    private JTextField notchCCenFreq;
    private JTextField notchCBandwidth;
    private JTextField eventType;
    private JTextField sensorTypeID;
    private JTextField vSMTInfo1;
    private JTextField vSMTInfo2;
    private JTextField vSMTInfo3;
    private JTextField vSMTInfo4;
    private JTextField vSMTInfo5;
    private JTextField dataModBit;

    public traceBinHeader() {

        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, mainGui.getSettings_singl().getCfgTraceNumber(), 1);
        traceSelector.setModel(model1);
        JSpinner.DefaultEditor editor = ( JSpinner.DefaultEditor ) traceSelector.getEditor();
        editor.getTextField().setEditable(false);
        traceSelector.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                System.out.println("Source: " + e.getSource());
//                System.out.println("JSPinner: " + traceSelector.getValue());
                setBinHeaderTxtFields(mainGui.mainController.getSegyTempTraces().get((int) traceSelector.getValue() - 1));
            }
        });
    }
    
    public void setBinHeaderTxtFields(SegyTempTrace currentSegyTempTrace
//            Integer traceSequenceNumberWithinLine,
//            Integer traceSequenceNumberWithinSegyFile,
//            Integer origFieldRecordNumber,
//            Integer traceNumberWithinOrigFieldRecord,
//            Integer energySourcePointNumber,
//            Integer ensembleNumber,
//            Integer traceNumberWithinEnsemble,
//            Short traceIdCode,
//            Short vertSummedTracesYieldingThisTrace,
//            Short horizStackedTracesYieldingThisTrace,
//            Short dataUse,
//            Integer distFromSourcePointToReceiverGroup,
//            Integer receiverGroupElevation,
//            Integer surfaceElevationAtSource,
//            Integer sourceDepthBelowSurface,
//            Integer datumElevationAtReceiverGroup,
//            Integer datumElevationAtSource,
//            Integer waterDepthAtSource,
//            Integer waterDepthAtGroup,
//            Short elevationScalar,
//            Short coordScalar,
//            Integer sourceCoordX,
//            Integer sourceCoordY,
//            Integer groupCoordX,
//            Integer groupCoordY,
//            Short coordUnits,
//            Short weatheringVelocity,
//            Short subweatheringVelocity,
//            Short upholeTimeAtSource,
//            Short upholeTimeAtGroup,
//            Short sourceStaticCorrection,
//            Short groupStaticCorrectionMs,
//            Short totalStaticAppliedMs,
//            Short lagTimeAMs,
//            Short lagTimeBMs,
//            Short delayRecordingTimeMs,
//            Short muteTimeStartMs,
//            Short muteTimeEndMs,
//            Short samplesNumber,
//            Short sampleIntervalMs,
//            Short gainType,
//            Short instrumentGainConstant,
//            Short instrumentEarlyOrInitialGain,
//            Short correlated,
//            Short sweepFrequencyAtStart,
//            Short sweepFrequencyAtEnd,
//            Short sweepLengthMs,
//            Short sweepType,
//            Short sweepTraceTaperLengthAtStartMs,
//            Short sweepTraceTaperLengthAtEndMs,
//            Short taperType,
//            Short aliasFilterFreq,
//            Short aliasFilterSlope,
//            Short notchFilterFreq,
//            Short notchFilterSlope,
//            Short lowCutFreq,
//            Short highCutFreq,
//            Short lowCutSlope,
//            Short highCutSlope,
//            Short year,
//            Short dayOfYear,
//            Short hourOfDay,
//            Short minuteOfHour,
//            Short secondOfMinute,
//            Short timeBasisCode,
//            Short traceWeightFactor,
//            Short geophoneGroupNumOfRollSwitchPosOne,
//            Short geophoneGroupNumOfTraceOneWithinOrigRecord,
//            Short geophoneGroupNumOfLastWithinOrigRecord,
//            Short gapSize,
//            Short overTravel,
//            // Specigic I/O FOUR
//            Short segyHedRev,
//            Integer shotID,
//            Byte auxChanSigDesc,
//            Byte auxChanID,
//            Integer shotPointLine,
//            Integer shotPointSta,
//            Short recLine,
//            Short recSta,
//            Byte vSMT,
//            Byte vSMTScaleCode,
//            Short vSMTHOA,
//            Short vSMTVOA,
//            Byte sourceType,
//            Byte sourceTypeSEGD,
//            Byte auxChanType,
//            Byte noiseEdType,
//            Short noiseEdGateLength,
//            Integer deviceType1bdevSerNum3b,
//            Byte devChanNum,
//            Byte auxChanSourcsetID,
//            Byte debStatus,
//            Byte lATTestType,
//            Short fixedLowCutFreq,
//            Byte fixedLowCutSlope,
//            Byte boxFunction,
//            Short notchBCenFreq,
//            Short notchBBandwidth,
//            Short notchCCenFreq,
//            Short notchCBandwidth,
//            Byte eventType,
//            Byte sensorTypeID,
//            Byte vSMTInfo1,
//            Byte vSMTInfo2,
//            Byte vSMTInfo3,
//            Byte vSMTInfo4,
//            Byte vSMTInfo5,
//            Byte dataModBit
    ){
        //Specific for I/O Four deviceType1bdevSerNum3b 4 byte convert deviceType 1 byte  devSerNum 3 byte
        byte[] bytes = ByteBuffer.allocate(4).putInt(currentSegyTempTrace.getDeviceType1bdevSerNum3b()).array();
        byte[] bytesForInt = {0,bytes[1],bytes[2],bytes[3]};
        Byte deviceType = bytes[0];
        Integer devSerNum = java.nio.ByteBuffer.wrap(bytesForInt).order(ByteOrder.BIG_ENDIAN).getInt();


        this.traceSequenceNumberWithinLine.setText(currentSegyTempTrace.getTraceSequenceNumberWithinLine().toString());
        this.traceSequenceNumberWithinLine.setText(currentSegyTempTrace.getTraceSequenceNumberWithinLine().toString());
        this.traceSequenceNumberWithinSegyFile.setText(currentSegyTempTrace.getTraceSequenceNumberWithinSegyFile().toString());
        this.origFieldRecordNumber.setText(currentSegyTempTrace.getOrigFieldRecordNumber().toString());
        this.traceNumberWithinOrigFieldRecord.setText(currentSegyTempTrace.getTraceNumberWithinOrigFieldRecord().toString());
        this.energySourcePointNumber.setText(currentSegyTempTrace.getEnergySourcePointNumber().toString());
        this.ensembleNumber.setText(currentSegyTempTrace.getEnsembleNumber().toString());
        this.traceNumberWithinEnsemble.setText(currentSegyTempTrace.getTraceNumberWithinEnsemble().toString());
        this.traceIdCode.setText(currentSegyTempTrace.getTraceIdCode().toString());
        this.vertSummedTracesYieldingThisTrace.setText(currentSegyTempTrace.getVertSummedTracesYieldingThisTrace().toString());
        this.horizStackedTracesYieldingThisTrace.setText(currentSegyTempTrace.getHorizStackedTracesYieldingThisTrace().toString());
        this.dataUse.setText(currentSegyTempTrace.getDataUse().toString());
        this.distFromSourcePointToReceiverGroup.setText(currentSegyTempTrace.getDistFromSourcePointToReceiverGroup().toString());
        this.receiverGroupElevation.setText(currentSegyTempTrace.getReceiverGroupElevation().toString());
        this.surfaceElevationAtSource.setText(currentSegyTempTrace.getSurfaceElevationAtSource().toString());
        this.sourceDepthBelowSurface.setText(currentSegyTempTrace.getSourceDepthBelowSurface().toString());
        this.datumElevationAtReceiverGroup.setText(currentSegyTempTrace.getDatumElevationAtReceiverGroup().toString());
        this.datumElevationAtSource.setText(currentSegyTempTrace.getDatumElevationAtSource().toString());
        this.waterDepthAtSource.setText(currentSegyTempTrace.getWaterDepthAtSource().toString());
        this.waterDepthAtGroup.setText(currentSegyTempTrace.getWaterDepthAtGroup().toString());
        this.elevationScalar.setText(currentSegyTempTrace.getElevationScalar().toString());
        this.coordScalar.setText(currentSegyTempTrace.getCoordScalar().toString());
        this.sourceCoordX.setText(currentSegyTempTrace.getSourceCoordX().toString());
        this.sourceCoordY.setText(currentSegyTempTrace.getSourceCoordY().toString());
        this.groupCoordX.setText(currentSegyTempTrace.getGroupCoordX().toString());
        this.groupCoordY.setText(currentSegyTempTrace.getGroupCoordY().toString());
        this.coordUnits.setText(currentSegyTempTrace.getCoordUnits().toString());
        this.weatheringVelocity.setText(currentSegyTempTrace.getWeatheringVelocity().toString());
        this.subweatheringVelocity.setText(currentSegyTempTrace.getSubweatheringVelocity().toString());
        this.upholeTimeAtSource.setText(currentSegyTempTrace.getUpholeTimeAtSource().toString());
        this.upholeTimeAtGroup.setText(currentSegyTempTrace.getUpholeTimeAtGroup().toString());
        this.sourceStaticCorrection.setText(currentSegyTempTrace.getSourceStaticCorrection().toString());
        this.groupStaticCorrectionMs.setText(currentSegyTempTrace.getGroupStaticCorrectionMs().toString());
        this.totalStaticAppliedMs.setText(currentSegyTempTrace.getTotalStaticAppliedMs().toString());
        this.lagTimeAMs.setText(currentSegyTempTrace.getLagTimeAMs().toString());
        this.lagTimeBMs.setText(currentSegyTempTrace.getLagTimeBMs().toString());
        this.delayRecordingTimeMs.setText(currentSegyTempTrace.getDelayRecordingTimeMs().toString());
        this.muteTimeStartMs.setText(currentSegyTempTrace.getMuteTimeStartMs().toString());
        this.muteTimeEndMs.setText(currentSegyTempTrace.getMuteTimeEndMs().toString());
        this.samplesNumber.setText(currentSegyTempTrace.getSamplesNumber().toString());
        this.sampleIntervalMs.setText(currentSegyTempTrace.getSampleIntervalMs().toString());
        this.gainType.setText(currentSegyTempTrace.getGainType().toString());
        this.instrumentGainConstant.setText(currentSegyTempTrace.getInstrumentGainConstant().toString());
        this.instrumentEarlyOrInitialGain.setText(currentSegyTempTrace.getInstrumentEarlyOrInitialGain().toString());
        this.correlated.setText(currentSegyTempTrace.getCorrelated().toString());
        this.sweepFrequencyAtStart.setText(currentSegyTempTrace.getSweepFrequencyAtStart().toString());
        this.sweepFrequencyAtEnd.setText(currentSegyTempTrace.getSweepFrequencyAtEnd().toString());
        this.sweepLengthMs.setText(currentSegyTempTrace.getSweepLengthMs().toString());
        this.sweepType.setText(currentSegyTempTrace.getSweepType().toString());
        this.sweepTraceTaperLengthAtStartMs.setText(currentSegyTempTrace.getSweepTraceTaperLengthAtStartMs().toString());
        this.sweepTraceTaperLengthAtEndMs.setText(currentSegyTempTrace.getSweepTraceTaperLengthAtEndMs().toString());
        this.taperType.setText(currentSegyTempTrace.getTaperType().toString());
        this.aliasFilterFreq.setText(currentSegyTempTrace.getAliasFilterFreq().toString());
        this.aliasFilterSlope.setText(currentSegyTempTrace.getAliasFilterSlope().toString());
        this.notchFilterFreq.setText(currentSegyTempTrace.getNotchFilterFreq().toString());
        this.notchFilterSlope.setText(currentSegyTempTrace.getNotchFilterSlope().toString());
        this.lowCutFreq.setText(currentSegyTempTrace.getLowCutFreq().toString());
        this.highCutFreq.setText(currentSegyTempTrace.getHighCutFreq().toString());
        this.lowCutSlope.setText(currentSegyTempTrace.getLowCutSlope().toString());
        this.highCutSlope.setText(currentSegyTempTrace.getHighCutSlope().toString());
        this.year.setText(currentSegyTempTrace.getYear().toString());
        this.dayOfYear.setText(currentSegyTempTrace.getDayOfYear().toString());
        this.hourOfDay.setText(currentSegyTempTrace.getHourOfDay().toString());
        this.minuteOfHour.setText(currentSegyTempTrace.getMinuteOfHour().toString());
        this.secondOfMinute.setText(currentSegyTempTrace.getSecondOfMinute().toString());
        this.timeBasisCode.setText(currentSegyTempTrace.getTimeBasisCode().toString());
        this.traceWeightFactor.setText(currentSegyTempTrace.getTraceWeightFactor().toString());
        this.geophoneGroupNumOfRollSwitchPosOne.setText(currentSegyTempTrace.getGeophoneGroupNumOfRollSwitchPosOne().toString());
        this.geophoneGroupNumOfTraceOneWithinOrigRecord.setText(currentSegyTempTrace.getGeophoneGroupNumOfTraceOneWithinOrigRecord().toString());
        this.geophoneGroupNumOfLastWithinOrigRecord.setText(currentSegyTempTrace.getGeophoneGroupNumOfLastWithinOrigRecord().toString());
        this.gapSize.setText(currentSegyTempTrace.getGapSize().toString());
        this.overTravel.setText(currentSegyTempTrace.getOverTravel().toString());
        this.segyHedRev.setText(currentSegyTempTrace.getSegyHedRev().toString());
        this.shotID.setText(currentSegyTempTrace.getShotID().toString());
        this.auxChanSigDesc.setText(currentSegyTempTrace.getAuxChanSigDesc().toString());
        this.auxChanID.setText(currentSegyTempTrace.getAuxChanID().toString());
        this.shotPointLine.setText(currentSegyTempTrace.getShotPointLine().toString());
        this.shotPointSta.setText(currentSegyTempTrace.getShotPointSta().toString());
        this.recLine.setText(currentSegyTempTrace.getRecLine().toString());
        this.recSta.setText(currentSegyTempTrace.getRecSta().toString());
        this.vSMT.setText(currentSegyTempTrace.getvSMT().toString());
        this.vSMTScaleCode.setText(currentSegyTempTrace.getvSMTScaleCode().toString());
        this.vSMTHOA.setText(currentSegyTempTrace.getvSMTHOA().toString());
        this.vSMTVOA.setText(currentSegyTempTrace.getvSMTVOA().toString());
        this.sourceType.setText(currentSegyTempTrace.getSourceType().toString());
        this.sourceTypeSEGD.setText(currentSegyTempTrace.getSourceTypeSEGD().toString());
        this.auxChanType.setText(currentSegyTempTrace.getAuxChanType().toString());
        this.noiseEdType.setText(currentSegyTempTrace.getNoiseEdType().toString());
        this.noiseEdGateLength.setText(currentSegyTempTrace.getNoiseEdGateLength().toString());
        this.deviceType.setText(deviceType.toString());
        this.devSerNum.setText(devSerNum.toString());
        this.devChanNum.setText(currentSegyTempTrace.getDevChanNum().toString());
        this.auxChanSourcsetID.setText(currentSegyTempTrace.getAuxChanSourcsetID().toString());
        this.debStatus.setText(currentSegyTempTrace.getDebStatus().toString());
        this.lATTestType.setText(currentSegyTempTrace.getlATTestType().toString());
        this.fixedLowCutFreq.setText(currentSegyTempTrace.getFixedLowCutFreq().toString());
        this.fixedLowCutSlope.setText(currentSegyTempTrace.getFixedLowCutSlope().toString());
        this.boxFunction.setText(currentSegyTempTrace.getBoxFunction().toString());
        this.notchBCenFreq.setText(currentSegyTempTrace.getNotchBCenFreq().toString());
        this.notchBBandwidth.setText(currentSegyTempTrace.getNotchBBandwidth().toString());
        this.notchCCenFreq.setText(currentSegyTempTrace.getNotchCCenFreq().toString());
        this.notchCBandwidth.setText(currentSegyTempTrace.getNotchCBandwidth().toString());
        this.eventType.setText(currentSegyTempTrace.getEventType().toString());
        this.sensorTypeID.setText(currentSegyTempTrace.getSensorTypeID().toString());
        this.vSMTInfo1.setText(currentSegyTempTrace.getvSMTInfo1().toString());
        this.vSMTInfo2.setText(currentSegyTempTrace.getvSMTInfo2().toString());
        this.vSMTInfo3.setText(currentSegyTempTrace.getvSMTInfo3().toString());
        this.vSMTInfo4.setText(currentSegyTempTrace.getvSMTInfo4().toString());
        this.vSMTInfo5.setText(currentSegyTempTrace.getvSMTInfo5().toString());
        this.dataModBit.setText(currentSegyTempTrace.getDataModBit().toString());
    }
}
