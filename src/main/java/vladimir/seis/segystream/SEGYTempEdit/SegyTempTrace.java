package main.java.vladimir.seis.segystream.SEGYTempEdit;

/*
* Класс который записываютя данные (заголовки трасс) из прочитанного файла
        * для выполнения различных операций, содержит
        * функцию для записи данных */


import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class SegyTempTrace {



    Integer traceSequenceNumberWithinLine;
    Integer traceSequenceNumberWithinSegyFile;
    Integer origFieldRecordNumber;
    Integer traceNumberWithinOrigFieldRecord;
    Integer energySourcePointNumber;
    Integer ensembleNumber;
    Integer traceNumberWithinEnsemble;
    Short traceIdCode;
    Short vertSummedTracesYieldingThisTrace;
    Short horizStackedTracesYieldingThisTrace;
    Short dataUse;
    Integer distFromSourcePointToReceiverGroup;
    Integer receiverGroupElevation;
    Integer surfaceElevationAtSource;
    Integer sourceDepthBelowSurface;
    Integer datumElevationAtReceiverGroup;
    Integer datumElevationAtSource;
    Integer waterDepthAtSource;
    Integer waterDepthAtGroup;
    Short elevationScalar;
    Short coordScalar;
    Integer sourceCoordX;
    Integer sourceCoordY;
    Integer groupCoordX;
    Integer groupCoordY;
    Short coordUnits;
    Short weatheringVelocity;
    Short subweatheringVelocity;
    Short upholeTimeAtSource;
    Short upholeTimeAtGroup;
    Short sourceStaticCorrection;
    Short groupStaticCorrectionMs;
    Short totalStaticAppliedMs;
    Short lagTimeAMs;
    Short lagTimeBMs;
    Short delayRecordingTimeMs;
    Short muteTimeStartMs;
    Short muteTimeEndMs;
    Short samplesNumber;
    Short sampleIntervalMs;
    Short gainType;
    Short instrumentGainConstant;
    Short instrumentEarlyOrInitialGain;
    Short correlated;
    Short sweepFrequencyAtStart;
    Short sweepFrequencyAtEnd;
    Short sweepLengthMs;
    Short sweepType;
    Short sweepTraceTaperLengthAtStartMs;
    Short sweepTraceTaperLengthAtEndMs;
    Short taperType;
    Short aliasFilterFreq;
    Short aliasFilterSlope;
    Short notchFilterFreq;
    Short notchFilterSlope;
    Short lowCutFreq;
    Short highCutFreq;
    Short lowCutSlope;
    Short highCutSlope;
    Short year;
    Short dayOfYear;
    Short hourOfDay;
    Short minuteOfHour;
    Short secondOfMinute;
    Short timeBasisCode;
    Short traceWeightFactor;
    Short geophoneGroupNumOfRollSwitchPosOne;
    Short geophoneGroupNumOfTraceOneWithinOrigRecord;
    Short geophoneGroupNumOfLastWithinOrigRecord;
    Short gapSize;
    Short overTravel;
    // Specigic I/O FOUR
    Short segyHedRev;
    Integer shotID;
    Byte auxChanSigDesc;
    Byte auxChanID;
    Integer shotPointLine;
    Integer shotPointSta;
    Short recLine;
    Short recSta;
    Byte vSMT;
    Byte vSMTScaleCode;
    Short vSMTHOA;
    Short vSMTVOA;
    Byte sourceType;
    Byte sourceTypeSEGD;
    Byte auxChanType;
    Byte noiseEdType;
    Short noiseEdGateLength;
    Integer deviceType1bdevSerNum3b;
    Byte devChanNum;
    Byte auxChanSourcsetID;
    Byte debStatus;
    Byte lATTestType;
    Short fixedLowCutFreq;
    Byte fixedLowCutSlope;
    Byte boxFunction;
    Short notchBCenFreq;
    Short notchBBandwidth;
    Short notchCCenFreq;
    Short notchCBandwidth;
    Byte eventType;
    Byte sensorTypeID;
    Byte vSMTInfo1;
    Byte vSMTInfo2;
    Byte vSMTInfo3;
    Byte vSMTInfo4;
    Byte vSMTInfo5;
    Byte dataModBit;


    //Standard SGY
//    Integer x;
//    Integer y;
//    Integer iLine;
//    Integer xLine;
//    Integer shotPointNum;
//    Integer shotPointNumCoef;
//    Short traceValueMeasUnit;
//    Integer transductionConstantMantissa;
//    Integer transductionConstantExp;
//    Short transductionUnit;
//    Short deviceOrTraceId;
//    Short timeToMsScalar;
//    Short sourceTypeOrOrientation;
//    Integer sourceEnergyDirection0;
//    Short sourceEnergyDirection1;
//    Integer sourceMeasMantissa;
//    Short sourceMeasExp;
//    Short sourceMeasUnit;



    public void setTraceBinHeader(
            Integer traceSequenceNumberWithinLine,
            Integer traceSequenceNumberWithinSegyFile,
            Integer origFieldRecordNumber,
            Integer traceNumberWithinOrigFieldRecord,
            Integer energySourcePointNumber,
            Integer ensembleNumber,
            Integer traceNumberWithinEnsemble,
            Short traceIdCode,
            Short vertSummedTracesYieldingThisTrace,
            Short horizStackedTracesYieldingThisTrace,
            Short dataUse,
            Integer distFromSourcePointToReceiverGroup,
            Integer receiverGroupElevation,
            Integer surfaceElevationAtSource,
            Integer sourceDepthBelowSurface,
            Integer datumElevationAtReceiverGroup,
            Integer datumElevationAtSource,
            Integer waterDepthAtSource,
            Integer waterDepthAtGroup,
            Short elevationScalar,
            Short coordScalar,
            Integer sourceCoordX,
            Integer sourceCoordY,
            Integer groupCoordX,
            Integer groupCoordY,
            Short coordUnits,
            Short weatheringVelocity,
            Short subweatheringVelocity,
            Short upholeTimeAtSource,
            Short upholeTimeAtGroup,
            Short sourceStaticCorrection,
            Short groupStaticCorrectionMs,
            Short totalStaticAppliedMs,
            Short lagTimeAMs,
            Short lagTimeBMs,
            Short delayRecordingTimeMs,
            Short muteTimeStartMs,
            Short muteTimeEndMs,
            Short samplesNumber,
            Short sampleIntervalMs,
            Short gainType,
            Short instrumentGainConstant,
            Short instrumentEarlyOrInitialGain,
            Short correlated,
            Short sweepFrequencyAtStart,
            Short sweepFrequencyAtEnd,
            Short sweepLengthMs,
            Short sweepType,
            Short sweepTraceTaperLengthAtStartMs,
            Short sweepTraceTaperLengthAtEndMs,
            Short taperType,
            Short aliasFilterFreq,
            Short aliasFilterSlope,
            Short notchFilterFreq,
            Short notchFilterSlope,
            Short lowCutFreq,
            Short highCutFreq,
            Short lowCutSlope,
            Short highCutSlope,
            Short year,
            Short dayOfYear,
            Short hourOfDay,
            Short minuteOfHour,
            Short secondOfMinute,
            Short timeBasisCode,
            Short traceWeightFactor,
            Short geophoneGroupNumOfRollSwitchPosOne,
            Short geophoneGroupNumOfTraceOneWithinOrigRecord,
            Short geophoneGroupNumOfLastWithinOrigRecord,
            Short gapSize,
            Short overTravel,
            // Specigic I/O FOUR
            Short segyHedRev,
            Integer shotID,
            Byte auxChanSigDesc,
            Byte auxChanID,
            Integer shotPointLine,
            Integer shotPointSta,
            Short recLine,
            Short recSta,
            Byte vSMT,
            Byte vSMTScaleCode,
            Short vSMTHOA,
            Short vSMTVOA,
            Byte sourceType,
            Byte sourceTypeSEGD,
            Byte auxChanType,
            Byte noiseEdType,
            Short noiseEdGateLength,
            Integer deviceType1bdevSerNum3b,
            Byte devChanNum,
            Byte auxChanSourcsetID,
            Byte debStatus,
            Byte lATTestType,
            Short fixedLowCutFreq,
            Byte fixedLowCutSlope,
            Byte boxFunction,
            Short notchBCenFreq,
            Short notchBBandwidth,
            Short notchCCenFreq,
            Short notchCBandwidth,
            Byte eventType,
            Byte sensorTypeID,
            Byte vSMTInfo1,
            Byte vSMTInfo2,
            Byte vSMTInfo3,
            Byte vSMTInfo4,
            Byte vSMTInfo5,
            Byte dataModBit
            ) {
        this.traceSequenceNumberWithinLine = traceSequenceNumberWithinLine;
        this.traceSequenceNumberWithinSegyFile = traceSequenceNumberWithinSegyFile;
        this.origFieldRecordNumber = origFieldRecordNumber;
        this.traceNumberWithinOrigFieldRecord = traceNumberWithinOrigFieldRecord;
        this.energySourcePointNumber = energySourcePointNumber;
        this.ensembleNumber = ensembleNumber;
        this.traceNumberWithinEnsemble = traceNumberWithinEnsemble;
        this.traceIdCode = traceIdCode;
        this.vertSummedTracesYieldingThisTrace = vertSummedTracesYieldingThisTrace;
        this.horizStackedTracesYieldingThisTrace = horizStackedTracesYieldingThisTrace;
        this.dataUse = dataUse;
        this.distFromSourcePointToReceiverGroup = distFromSourcePointToReceiverGroup;
        this.receiverGroupElevation = receiverGroupElevation;
        this.surfaceElevationAtSource = surfaceElevationAtSource;
        this.sourceDepthBelowSurface = sourceDepthBelowSurface;
        this.datumElevationAtReceiverGroup = datumElevationAtReceiverGroup;
        this.datumElevationAtSource = datumElevationAtSource;
        this.waterDepthAtSource = waterDepthAtSource;
        this.waterDepthAtGroup = waterDepthAtGroup;
        this.elevationScalar = elevationScalar;
        this.coordScalar = coordScalar;
        this.sourceCoordX = sourceCoordX;
        this.sourceCoordY = sourceCoordY;
        this.groupCoordX = groupCoordX;
        this.groupCoordY = groupCoordY;
        this.coordUnits = coordUnits;
        this.weatheringVelocity = weatheringVelocity;
        this.subweatheringVelocity = subweatheringVelocity;
        this.upholeTimeAtSource = upholeTimeAtSource;
        this.upholeTimeAtGroup = upholeTimeAtGroup;
        this.sourceStaticCorrection = sourceStaticCorrection;
        this.groupStaticCorrectionMs = groupStaticCorrectionMs;
        this.totalStaticAppliedMs = totalStaticAppliedMs;
        this.lagTimeAMs = lagTimeAMs;
        this.lagTimeBMs = lagTimeBMs;
        this.delayRecordingTimeMs = delayRecordingTimeMs;
        this.muteTimeStartMs = muteTimeStartMs;
        this.muteTimeEndMs = muteTimeEndMs;
        this.samplesNumber = samplesNumber;
        this.sampleIntervalMs = sampleIntervalMs;
        this.gainType = gainType;
        this.instrumentGainConstant = instrumentGainConstant;
        this.instrumentEarlyOrInitialGain = instrumentEarlyOrInitialGain;
        this.correlated = correlated;
        this.sweepFrequencyAtStart = sweepFrequencyAtStart;
        this.sweepFrequencyAtEnd = sweepFrequencyAtEnd;
        this.sweepLengthMs = sweepLengthMs;
        this.sweepType = sweepType;
        this.sweepTraceTaperLengthAtStartMs = sweepTraceTaperLengthAtStartMs;
        this.sweepTraceTaperLengthAtEndMs = sweepTraceTaperLengthAtEndMs;
        this.taperType = taperType;
        this.aliasFilterFreq = aliasFilterFreq;
        this.aliasFilterSlope = aliasFilterSlope;
        this.notchFilterFreq = notchFilterFreq;
        this.notchFilterSlope = notchFilterSlope;
        this.lowCutFreq = lowCutFreq;
        this.highCutFreq = highCutFreq;
        this.lowCutSlope = lowCutSlope;
        this.highCutSlope = highCutSlope;
        this.year = year;
        this.dayOfYear = dayOfYear;
        this.hourOfDay = hourOfDay;
        this.minuteOfHour = minuteOfHour;
        this.secondOfMinute = secondOfMinute;
        this.timeBasisCode = timeBasisCode;
        this.traceWeightFactor = traceWeightFactor;
        this.geophoneGroupNumOfRollSwitchPosOne = geophoneGroupNumOfRollSwitchPosOne;
        this.geophoneGroupNumOfTraceOneWithinOrigRecord = geophoneGroupNumOfTraceOneWithinOrigRecord;
        this.geophoneGroupNumOfLastWithinOrigRecord = geophoneGroupNumOfLastWithinOrigRecord;
        this.gapSize = gapSize;
        this.overTravel = overTravel;
        // Specigic I/O FOUR
        this.segyHedRev = segyHedRev;
        this.shotID = shotID;
        this.auxChanSigDesc = auxChanSigDesc;
        this.auxChanID = auxChanID;
        this.shotPointLine = shotPointLine;
        this.shotPointSta = shotPointSta;
        this.recLine = recLine;
        this.recSta = recSta;
        this.vSMT = vSMT;
        this.vSMTScaleCode = vSMTScaleCode;
        this.vSMTHOA = vSMTHOA;
        this.vSMTVOA = vSMTVOA;
        this.sourceType = sourceType;
        this.sourceTypeSEGD = sourceTypeSEGD;
        this.auxChanType = auxChanType;
        this.noiseEdType = noiseEdType;
        this.noiseEdGateLength = noiseEdGateLength;
        this.deviceType1bdevSerNum3b = deviceType1bdevSerNum3b;
        this.devChanNum = devChanNum;
        this.auxChanSourcsetID = auxChanSourcsetID;
        this.debStatus = debStatus;
        this.lATTestType = lATTestType;
        this.fixedLowCutFreq = fixedLowCutFreq;
        this.fixedLowCutSlope = fixedLowCutSlope;
        this.boxFunction = boxFunction;
        this.notchBCenFreq = notchBCenFreq;
        this.notchBBandwidth = notchBBandwidth;
        this.notchCCenFreq = notchCCenFreq;
        this.notchCBandwidth = notchCBandwidth;
        this.eventType = eventType;
        this.sensorTypeID = sensorTypeID;
        this.vSMTInfo1 = vSMTInfo1;
        this.vSMTInfo2 = vSMTInfo2;
        this.vSMTInfo3 = vSMTInfo3;
        this.vSMTInfo4 = vSMTInfo4;
        this.vSMTInfo5 = vSMTInfo5;
        this.dataModBit = dataModBit;
    }

    public Integer getTraceSequenceNumberWithinLine() {
        return traceSequenceNumberWithinLine;
    }

    public Integer getTraceSequenceNumberWithinSegyFile() {
        return traceSequenceNumberWithinSegyFile;
    }

    public Integer getOrigFieldRecordNumber() {
        return origFieldRecordNumber;
    }

    public Integer getTraceNumberWithinOrigFieldRecord() {
        return traceNumberWithinOrigFieldRecord;
    }

    public Integer getEnergySourcePointNumber() {
        return energySourcePointNumber;
    }

    public Integer getEnsembleNumber() {
        return ensembleNumber;
    }

    public Integer getTraceNumberWithinEnsemble() {
        return traceNumberWithinEnsemble;
    }

    public Short getTraceIdCode() {
        return traceIdCode;
    }

    public Short getVertSummedTracesYieldingThisTrace() {
        return vertSummedTracesYieldingThisTrace;
    }

    public Short getHorizStackedTracesYieldingThisTrace() {
        return horizStackedTracesYieldingThisTrace;
    }

    public Short getDataUse() {
        return dataUse;
    }

    public Integer getDistFromSourcePointToReceiverGroup() {
        return distFromSourcePointToReceiverGroup;
    }

    public Integer getReceiverGroupElevation() {
        return receiverGroupElevation;
    }

    public Integer getSurfaceElevationAtSource() {
        return surfaceElevationAtSource;
    }

    public Integer getSourceDepthBelowSurface() {
        return sourceDepthBelowSurface;
    }

    public Integer getDatumElevationAtReceiverGroup() {
        return datumElevationAtReceiverGroup;
    }

    public Integer getDatumElevationAtSource() {
        return datumElevationAtSource;
    }

    public Integer getWaterDepthAtSource() {
        return waterDepthAtSource;
    }

    public Integer getWaterDepthAtGroup() {
        return waterDepthAtGroup;
    }

    public Short getElevationScalar() {
        return elevationScalar;
    }

    public Short getCoordScalar() {
        return coordScalar;
    }

    public Integer getSourceCoordX() {
        return sourceCoordX;
    }

    public Integer getSourceCoordY() {
        return sourceCoordY;
    }

    public Integer getGroupCoordX() {
        return groupCoordX;
    }

    public Integer getGroupCoordY() {
        return groupCoordY;
    }

    public Short getCoordUnits() {
        return coordUnits;
    }

    public Short getWeatheringVelocity() {
        return weatheringVelocity;
    }

    public Short getSubweatheringVelocity() {
        return subweatheringVelocity;
    }

    public Short getUpholeTimeAtSource() {
        return upholeTimeAtSource;
    }

    public Short getUpholeTimeAtGroup() {
        return upholeTimeAtGroup;
    }

    public Short getSourceStaticCorrection() {
        return sourceStaticCorrection;
    }

    public Short getGroupStaticCorrectionMs() {
        return groupStaticCorrectionMs;
    }

    public Short getTotalStaticAppliedMs() {
        return totalStaticAppliedMs;
    }

    public Short getLagTimeAMs() {
        return lagTimeAMs;
    }

    public Short getLagTimeBMs() {
        return lagTimeBMs;
    }

    public Short getDelayRecordingTimeMs() {
        return delayRecordingTimeMs;
    }

    public Short getMuteTimeStartMs() {
        return muteTimeStartMs;
    }

    public Short getMuteTimeEndMs() {
        return muteTimeEndMs;
    }

    public Short getSamplesNumber() {
        return samplesNumber;
    }

    public Short getSampleIntervalMs() {
        return sampleIntervalMs;
    }

    public Short getGainType() {
        return gainType;
    }

    public Short getInstrumentGainConstant() {
        return instrumentGainConstant;
    }

    public Short getInstrumentEarlyOrInitialGain() {
        return instrumentEarlyOrInitialGain;
    }

    public Short getCorrelated() {
        return correlated;
    }

    public Short getSweepFrequencyAtStart() {
        return sweepFrequencyAtStart;
    }

    public Short getSweepFrequencyAtEnd() {
        return sweepFrequencyAtEnd;
    }

    public Short getSweepLengthMs() {
        return sweepLengthMs;
    }

    public Short getSweepType() {
        return sweepType;
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

    public Short getAliasFilterFreq() {
        return aliasFilterFreq;
    }

    public Short getAliasFilterSlope() {
        return aliasFilterSlope;
    }

    public Short getNotchFilterFreq() {
        return notchFilterFreq;
    }

    public Short getNotchFilterSlope() {
        return notchFilterSlope;
    }

    public Short getLowCutFreq() {
        return lowCutFreq;
    }

    public Short getHighCutFreq() {
        return highCutFreq;
    }

    public Short getLowCutSlope() {
        return lowCutSlope;
    }

    public Short getHighCutSlope() {
        return highCutSlope;
    }

    public Short getYear() {
        return year;
    }

    public Short getDayOfYear() {
        return dayOfYear;
    }

    public Short getHourOfDay() {
        return hourOfDay;
    }

    public Short getMinuteOfHour() {
        return minuteOfHour;
    }

    public Short getSecondOfMinute() {
        return secondOfMinute;
    }

    public Short getTimeBasisCode() {
        return timeBasisCode;
    }

    public Short getTraceWeightFactor() {
        return traceWeightFactor;
    }

    public Short getGeophoneGroupNumOfRollSwitchPosOne() {
        return geophoneGroupNumOfRollSwitchPosOne;
    }

    public Short getGeophoneGroupNumOfTraceOneWithinOrigRecord() {
        return geophoneGroupNumOfTraceOneWithinOrigRecord;
    }

    public Short getGeophoneGroupNumOfLastWithinOrigRecord() {
        return geophoneGroupNumOfLastWithinOrigRecord;
    }

    public Short getGapSize() {
        return gapSize;
    }

    public Short getOverTravel() {
        return overTravel;
    }

    public Short getSegyHedRev() {
        return segyHedRev;
    }

    public Integer getShotID() {
        return shotID;
    }

    public Byte getAuxChanSigDesc() {
        return auxChanSigDesc;
    }

    public Byte getAuxChanID() {
        return auxChanID;
    }

    public Integer getShotPointLine() {
        return shotPointLine;
    }

    public Integer getShotPointSta() {
        return shotPointSta;
    }

    public Short getRecLine() {
        return recLine;
    }

    public Short getRecSta() {
        return recSta;
    }

    public Byte getvSMT() {
        return vSMT;
    }

    public Byte getvSMTScaleCode() {
        return vSMTScaleCode;
    }

    public Short getvSMTHOA() {
        return vSMTHOA;
    }

    public Short getvSMTVOA() {
        return vSMTVOA;
    }

    public Byte getSourceType() {
        return sourceType;
    }

    public Byte getSourceTypeSEGD() {
        return sourceTypeSEGD;
    }

    public Byte getAuxChanType() {
        return auxChanType;
    }

    public Byte getNoiseEdType() {
        return noiseEdType;
    }

    public Short getNoiseEdGateLength() {
        return noiseEdGateLength;
    }

    public Integer getDeviceType1bdevSerNum3b() {
        return deviceType1bdevSerNum3b;
    }

    public Byte getDevChanNum() {
        return devChanNum;
    }

    public Byte getAuxChanSourcsetID() {
        return auxChanSourcsetID;
    }

    public Byte getDebStatus() {
        return debStatus;
    }

    public Byte getlATTestType() {
        return lATTestType;
    }

    public Short getFixedLowCutFreq() {
        return fixedLowCutFreq;
    }

    public Byte getFixedLowCutSlope() {
        return fixedLowCutSlope;
    }

    public Byte getBoxFunction() {
        return boxFunction;
    }

    public Short getNotchBCenFreq() {
        return notchBCenFreq;
    }

    public Short getNotchBBandwidth() {
        return notchBBandwidth;
    }

    public Short getNotchCCenFreq() {
        return notchCCenFreq;
    }

    public Short getNotchCBandwidth() {
        return notchCBandwidth;
    }

    public Byte getEventType() {
        return eventType;
    }

    public Byte getSensorTypeID() {
        return sensorTypeID;
    }

    public Byte getvSMTInfo1() {
        return vSMTInfo1;
    }

    public Byte getvSMTInfo2() {
        return vSMTInfo2;
    }

    public Byte getvSMTInfo3() {
        return vSMTInfo3;
    }

    public Byte getvSMTInfo4() {
        return vSMTInfo4;
    }

    public Byte getvSMTInfo5() {
        return vSMTInfo5;
    }

    public Byte getDataModBit() {
        return dataModBit;
    }

    public void printHeader() {
//        System.out.println("-------------------------Trace" + traceNumberWithinOrigFieldRecord.toString() + " Bin");
//        System.out.println(traceSequenceNumberWithinLine.toString());
//        System.out.println(traceSequenceNumberWithinSegyFile.toString());
//        System.out.println(origFieldRecordNumber.toString());
//        System.out.println(traceNumberWithinOrigFieldRecord.toString());
//        System.out.println(energySourcePointNumber.toString());
//        System.out.println(ensembleNumber.toString());
//        System.out.println(traceNumberWithinEnsemble.toString());
//        System.out.println(traceIdCode.toString());
//        System.out.println(vertSummedTracesYieldingThisTrace.toString());
//        System.out.println(horizStackedTracesYieldingThisTrace.toString());
//        System.out.println(dataUse.toString());



    }

    public void setAuxChanType(Byte auxChanType) {
        this.auxChanType = auxChanType;
    }

    public void writeToDataOutputStream(DataOutputStream dos) throws IOException {

        ByteBuffer bb = ByteBuffer.allocate(240);
        bb.order(ByteOrder.BIG_ENDIAN);

        bb.putInt(traceSequenceNumberWithinLine);
        bb.putInt(traceSequenceNumberWithinSegyFile);
        bb.putInt(origFieldRecordNumber);
        bb.putInt(traceNumberWithinOrigFieldRecord);
        bb.putInt(energySourcePointNumber);
        bb.putInt(ensembleNumber);
        bb.putInt(traceNumberWithinEnsemble);
        bb.putShort(traceIdCode);
        bb.putShort(vertSummedTracesYieldingThisTrace);
        bb.putShort(horizStackedTracesYieldingThisTrace);
        bb.putShort(dataUse);
        bb.putInt(distFromSourcePointToReceiverGroup);
        bb.putInt(receiverGroupElevation);
        bb.putInt(surfaceElevationAtSource);
        bb.putInt(sourceDepthBelowSurface);
        bb.putInt(datumElevationAtReceiverGroup);
        bb.putInt(datumElevationAtSource);
        bb.putInt(waterDepthAtSource);
        bb.putInt(waterDepthAtGroup);
        bb.putShort(elevationScalar);
        bb.putShort(coordScalar);
        bb.putInt(sourceCoordX);
        bb.putInt(sourceCoordY);
        bb.putInt(groupCoordX);
        bb.putInt(groupCoordY);
        bb.putShort(coordUnits);
        bb.putShort(weatheringVelocity);
        bb.putShort(subweatheringVelocity);
        bb.putShort(upholeTimeAtSource);
        bb.putShort(upholeTimeAtGroup);
        bb.putShort(sourceStaticCorrection);
        bb.putShort(groupStaticCorrectionMs);
        bb.putShort(totalStaticAppliedMs);
        bb.putShort(lagTimeAMs);
        bb.putShort(lagTimeBMs);
        bb.putShort(delayRecordingTimeMs);
        bb.putShort(muteTimeStartMs);
        bb.putShort(muteTimeEndMs);
        bb.putShort(samplesNumber);
        bb.putShort(sampleIntervalMs);
        bb.putShort(gainType);
        bb.putShort(instrumentGainConstant);
        bb.putShort(instrumentEarlyOrInitialGain);
        bb.putShort(correlated);
        bb.putShort(sweepFrequencyAtStart);
        bb.putShort(sweepFrequencyAtEnd);
        bb.putShort(sweepLengthMs);
        bb.putShort(sweepType);
        bb.putShort(sweepTraceTaperLengthAtStartMs);
        bb.putShort(sweepTraceTaperLengthAtEndMs);
        bb.putShort(taperType);
        bb.putShort(aliasFilterFreq);
        bb.putShort(aliasFilterSlope);
        bb.putShort(notchFilterFreq);
        bb.putShort(notchFilterSlope);
        bb.putShort(lowCutFreq);
        bb.putShort(highCutFreq);
        bb.putShort(lowCutSlope);
        bb.putShort(highCutSlope);
        bb.putShort(year);
        bb.putShort(dayOfYear);
        bb.putShort(hourOfDay);
        bb.putShort(minuteOfHour);
        bb.putShort(secondOfMinute);
        bb.putShort(timeBasisCode);
        bb.putShort(traceWeightFactor);
        bb.putShort(geophoneGroupNumOfRollSwitchPosOne);
        bb.putShort(geophoneGroupNumOfTraceOneWithinOrigRecord);
        bb.putShort(geophoneGroupNumOfLastWithinOrigRecord);
        bb.putShort(gapSize);
        bb.putShort(overTravel);
        // Specigic I/O FOUR
        bb.putShort(segyHedRev);
        bb.putInt(shotID);
        bb.put(auxChanSigDesc);
        bb.put(auxChanID);
        bb.putInt(shotPointLine);
        bb.putInt(shotPointSta);
        bb.putShort(recLine);
        bb.putShort(recSta);
        bb.put(vSMT);
        bb.put(vSMTScaleCode);
        bb.putShort(vSMTHOA);
        bb.putShort(vSMTVOA);
        bb.put(sourceType);
        bb.put(sourceTypeSEGD);
        bb.put(auxChanType);
        bb.put(noiseEdType);
        bb.putShort(noiseEdGateLength);
        bb.putInt(deviceType1bdevSerNum3b);
        bb.put(devChanNum);
        bb.put(auxChanSourcsetID);
        bb.put(debStatus);
        bb.put(lATTestType);
        bb.putShort(fixedLowCutFreq);
        bb.put(fixedLowCutSlope);
        bb.put(boxFunction);
        bb.putShort(notchBCenFreq);
        bb.putShort(notchBBandwidth);
        bb.putShort(notchCCenFreq);
        bb.putShort(notchCBandwidth);
        bb.put(eventType);
        bb.put(sensorTypeID);
        bb.put(vSMTInfo1);
        bb.put(vSMTInfo2);
        bb.put(vSMTInfo3);
        bb.put(vSMTInfo4);
        bb.put(vSMTInfo5);
        bb.put(dataModBit);
        dos.write(bb.array());

    }

}
