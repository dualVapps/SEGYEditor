package vladimir.seis.segystream

/* Класс непосредственно считывающий данные из файла
    и записывающий во временные классы для работы
 */


import java.nio.ByteOrder
import java.nio.charset.Charset

import akka.util.ByteString
import vladimir.seis.mainGui

sealed trait PromiseStrategy
case object KEEP extends PromiseStrategy
case object COMPLETE extends PromiseStrategy
case object NOOP extends PromiseStrategy

sealed trait SegyPhase {

  def length: Int
  def matPromise: PromiseStrategy
  def extract(bs: ByteString): (SegyPart, SegyPhase) // (segy, nextPhase)
}

case class TextHeaderPhase(cfg: SegyConfig) extends SegyPhase {
  val length = 3200
  override def matPromise: PromiseStrategy = KEEP
//  mainGui.getMainController
  override def extract(bs: ByteString): (TextHeader, SegyPhase) = {
    val s = bs.decodeString(cfg.charset)
//    System.out.println("--------------"+s.length());
    mainGui.getMainController().getSegyTempFile.setFileTextHeader(s); //fill file text header
    TextHeader(s) -> BinHeaderPhase(cfg)
  }
}

case class BinHeaderPhase(cfg: SegyConfig) extends SegyPhase {
  val length = 400
  override def matPromise: PromiseStrategy = KEEP
  def nextPhase(binHeader: BinHeader): SegyPhase = binHeader.extendedTextHeaders match {
    case extHeaders if extHeaders < 0 =>
      //TODO: Add support for variable number of ext text headers
      throw new UnsupportedOperationException("Variable number of Extended Text Headers not supported yet!")
    case extHeaders if extHeaders == 0 => TraceHeaderPhase(cfg, binHeader) //skip extended text header
    case extHeadersLeft => ExtTextHeaderPhase(new SegyConfig(Charset.forName("CP037"), mainGui.getSettings_singl.getCfgTraceSizeBytes),
      extHeadersLeft, binHeader)
  }
  override def extract(bs: ByteString): (BinHeader, SegyPhase) = {
    var it = bs.iterator // Var!!!
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN

    val segy = BinHeader(
      jobId = it.getInt,
      lineNumber = it.getInt,
      reelNumber = it.getInt,
      dataTracesPerEnsemble = it.getShort,
      auxTracesPerEnsemble = it.getShort,
      sampleIntervalMicroSec = it.getShort,
      sampleIntervalMicroSecOrig = it.getShort,
      samplesPerDataTrace = it.getShort,
      samplesPerDataTraceOrig = it.getShort,
      dataSampleFormatCode = it.getShort,
      ensembleFold = it.getShort,
      traceSortingCode = it.getShort,
      verticalSumCode = it.getShort,
      sweepFrequencyAtStartHz = it.getShort,
      sweepFrequencyAtEndHz = it.getShort,
      sweepLengthMs = it.getShort,
      sweepTypeCode = it.getShort,
      traceNumberOfSweepChannel = it.getShort,
      sweepTraceTaperLengthAtStartMs = it.getShort,
      sweepTraceTaperLengthAtEndMs = it.getShort,
      taperType = it.getShort,
      correlatedDataTraces = it.getShort,
      binaryGainRecovered = it.getShort,
      amplitudeRecoveryMethod = it.getShort,
      measurementSystem = it.getShort,
      impulseSignalPolarity = it.getShort,
      vibratoryPolarityCode = it.getShort,
      dsuSN = it.getShort,
      manufacturer = it.getShort,
      formatVersion = it.getShort,
      reelHdrRev = it.getShort,
      littleIndian = it.getShort,
      lineSpacing = it.getShort,
      staSpacing = it.getShort,
      numOfFiles = it.getInt,
      numOfTraces = it.getInt,
      segyRevision = {
        it = it.drop(3500 - 3283 + 1) // 3261 - 3500 Unassigned (Use: FOR I/O FOUR 3281-3500)
        it.getShort
      },
      fixedLengthTraceFlag = it.getShort,
      extendedTextHeaders = it.getShort
    )
    it = bs.iterator
    mainGui.getMainController().getSegyTempFile.setBinFileHeader(
      it.getInt,
      it.getInt,
      it.getInt,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getShort,
      it.getInt,
      it.getInt,

      {it = it.drop(3500 - 3283 + 1) // 3261 - 3500 Unassigned (FOR I/O skip 3500-3283+1)
        it.getShort
      },
      it.getShort,
      it.getShort
    )
    mainGui.getMainController.getSegyTempFile.printFileHeader()

    val eachSampleSize:Int = mainGui.mainController.segyTempFile.getDataSampleFormatCode.toInt match {
      case 1 => 4
      case 2 => 4
      case 3 => 2
      case 4 => 4
      case 5 => 4
      case _ => 4
    }




    mainGui.getSettings_singl.setCfgEachSampleSizeBytes(eachSampleSize)
    mainGui.getSettings_singl.setCfgSamplesNumber(mainGui.mainController.segyTempFile.getSamplesPerDataTraceOrig.toInt)
    mainGui.getSettings_singl.setCfgTraceSizeBytes(mainGui.getSettings_singl.getCfgEachSampleSizeBytes * mainGui.getSettings_singl.getCfgSamplesNumber)
    mainGui.getSettings_singl.setCfgTraceNumber(mainGui.mainController.segyTempFile.getNumOfTraces)
    mainGui.getSettings_singl.setCfgFilesNumber(mainGui.mainController.segyTempFile.getNumOfFiles)
    mainGui.getSettings_singl.setCfgCurrentFileSeqNumber(0)
    mainGui.getSettings_singl.initTrimLawDescr(mainGui.getSettings_singl.getCfgFilesNumber)
    mainGui.mainController.initReadingParameters()
    segy -> nextPhase(segy)
  }
}

case class ExtTextHeaderPhase(cfg: SegyConfig, extHeadersLeft: Int, binHeader: BinHeader) extends SegyPhase {
  val length = 3200
  override def matPromise: PromiseStrategy = KEEP
  def nextPhase: SegyPhase = extHeadersLeft match {
    case headers if headers > 0 => ExtTextHeaderPhase(cfg, headers - 1, binHeader)
    case _ => TraceHeaderPhase(new SegyConfig(Charset.forName("CP037"), mainGui.getSettings_singl.getCfgTraceSizeBytes),
      binHeader)
  }
  override def extract(bs: ByteString): (ExtTextHeader, SegyPhase) = {
    val s = bs.decodeString(cfg.charset)
    ExtTextHeader(s) -> nextPhase
  }
}

case class TraceHeaderPhase(cfg: SegyConfig, binHeader: BinHeader) extends SegyPhase {
  val length = 240
  override def matPromise: PromiseStrategy = COMPLETE
//  System.out.println("___***_-----___---- binHeader.fixedLengthTraceFlag  " + binHeader.fixedLengthTraceFlag)
//  System.out.println("___***_-----___---- binHeader.samplesPerDataTrace  " + binHeader.samplesPerDataTrace)
//  System.out.println("___***_-----___---- binHeader.fixedLengthTraceFlag  " + binHeader.fixedLengthTraceFlag)
//  System.out.println("___***_-----___---- binHeader.fixedLengthTraceFlag  " + binHeader.fixedLengthTraceFlag)
  def nextPhase(th: TraceHeader): SegyPhase = {
    val nSamples = binHeader.fixedLengthTraceFlag match {
       case fixed if fixed == 1 => binHeader.samplesPerDataTrace // fixed trace sample number
      case _ => th.samplesNumber match {
        case sNum if sNum == 0 => binHeader.samplesPerDataTrace
        case sNum if sNum > 0 => sNum
      }
    }
    val bytesLeft = nSamples * binHeader.getDataFormat.length
    TraceDataPhase(new SegyConfig(Charset.forName("CP037"), mainGui.getSettings_singl.getCfgTraceSizeBytes), binHeader, th, 0, bytesLeft)
  }
  override def extract(bs: ByteString): (TraceHeader, SegyPhase) = {
    var it = bs.iterator //original val
    implicit val byteOrder: ByteOrder = ByteOrder.BIG_ENDIAN
    val segy = TraceHeader(
      traceSequenceNumberWithinLine = it.getInt,
      traceSequenceNumberWithinSegyFile = it.getInt,
      origFieldRecordNumber = it.getInt,
      traceNumberWithinOrigFieldRecord = it.getInt,
      energySourcePointNumber = it.getInt,
      ensembleNumber = it.getInt,
      traceNumberWithinEnsemble = it.getInt,
      traceIdCode = it.getShort,
      vertSummedTracesYieldingThisTrace = it.getShort,
      horizStackedTracesYieldingThisTrace = it.getShort,
      dataUse = it.getShort,
      distFromSourcePointToReceiverGroup = it.getInt,
      receiverGroupElevation = it.getInt,
      surfaceElevationAtSource = it.getInt,
      sourceDepthBelowSurface = it.getInt,
      datumElevationAtReceiverGroup = it.getInt,
      datumElevationAtSource = it.getInt,
      waterDepthAtSource = it.getInt,
      waterDepthAtGroup = it.getInt,
      elevationScalar = it.getShort,
      coordScalar = it.getShort,
      sourceCoordX = it.getInt,
      sourceCoordY = it.getInt,
      groupCoordX = it.getInt,
      groupCoordY = it.getInt,
      coordUnits = it.getShort,
      weatheringVelocity = it.getShort,
      subweatheringVelocity = it.getShort,
      upholeTimeAtSource = it.getShort,
      upholeTimeAtGroup = it.getShort,
      sourceStaticCorrection = it.getShort,
      groupStaticCorrectionMs = it.getShort,
      totalStaticAppliedMs = it.getShort,
      lagTimeAMs = it.getShort,
      lagTimeBMs = it.getShort,
      delayRecordingTimeMs = it.getShort,
      muteTimeStartMs = it.getShort,
      muteTimeEndMs = it.getShort,
      samplesNumber = it.getShort,
      sampleIntervalMs = it.getShort,
      gainType = it.getShort,
      instrumentGainConstant = it.getShort,
      instrumentEarlyOrInitialGain = it.getShort,
      correlated = it.getShort,
      sweepFrequencyAtStart = it.getShort,
      sweepFrequencyAtEnd = it.getShort,
      sweepLengthMs = it.getShort,
      sweepType = it.getShort,
      sweepTraceTaperLengthAtStartMs = it.getShort,
      sweepTraceTaperLengthAtEndMs = it.getShort,
      taperType = it.getShort,
      aliasFilterFreq = it.getShort,
      aliasFilterSlope = it.getShort,
      notchFilterFreq = it.getShort,
      notchFilterSlope = it.getShort,
      lowCutFreq = it.getShort,
      highCutFreq = it.getShort,
      lowCutSlope = it.getShort,
      highCutSlope = it.getShort,
      year = it.getShort,
      dayOfYear = it.getShort,
      hourOfDay = it.getShort,
      minuteOfHour = it.getShort,
      secondOfMinute = it.getShort,
      timeBasisCode = it.getShort,
      traceWeightFactor = it.getShort,
      geophoneGroupNumOfRollSwitchPosOne = it.getShort,
      geophoneGroupNumOfTraceOneWithinOrigRecord = it.getShort,
      geophoneGroupNumOfLastWithinOrigRecord = it.getShort,
      gapSize = it.getShort,
      overTravel = it.getShort,
      segyHedRev = it.getShort,
      shotID = it.getInt,
      auxChanSigDesc = it.getByte,
      auxChanID = it.getByte,
      shotPointLine = it.getInt,
      shotPointSta = it.getInt,
      recLine = it.getShort,
      recSta = it.getShort,
      vSMT = it.getByte,
      vSMTScaleCode = it.getByte,
      vSMTHOA = it.getShort,
      vSMTVOA = it.getShort,
      sourceType = it.getByte,
      sourceTypeSEGD = it.getByte,
      auxChanType = it.getByte,
      noiseEdType = it.getByte,
      noiseEdGateLength = it.getShort,
      deviceType1bdevSerNum3b = it.getInt,
      devChanNum = it.getByte,
      auxChanSourcsetID = it.getByte,
      debStatus = it.getByte,
      lATTestType = it.getByte,
      fixedLowCutFreq = it.getShort,
      fixedLowCutSlope = it.getByte,
      boxFunction = it.getByte,
      notchBCenFreq = it.getShort,
      notchBBandwidth = it.getShort,
      notchCCenFreq = it.getShort,
      notchCBandwidth = it.getShort,
      eventType = it.getByte,
      sensorTypeID = it.getByte,
      vSMTInfo1 = it.getByte,
      vSMTInfo2 = it.getByte,
      vSMTInfo3 = it.getByte,
      vSMTInfo4 = it.getByte,
      vSMTInfo5 = it.getByte,
      dataModBit = it.getByte


      //For standard segy file
//      x = it.getInt,
//      y = it.getInt,
//      iLine = it.getInt,
//      xLine = it.getInt,
//      shotPointNum = it.getInt,
//      shotPointNumCoef = it.getInt,
//      traceValueMeasUnit = it.getShort,
//      transductionConstantMantissa = it.getInt,
//      transductionConstantExp = it.getInt,
//      transductionUnit = it.getShort,
//      deviceOrTraceId = it.getShort,
//      timeToMsScalar = it.getShort,
//      sourceTypeOrOrientation = it.getShort,
//      sourceEnergyDirection0 = it.getInt,
//      sourceEnergyDirection1 = it.getShort,
//      sourceMeasMantissa = it.getInt,
//      sourceMeasExp = it.getShort,
//      sourceMeasUnit = it.getShort
//      // 233 - 240 Unassigned in v1
    )
//    mainGui.mainController.formingTraceData(segy.samplesNumber)

//    System.out.println(mainGui.mainController.getSegyTempTraces(segy.traceNumberWithinOrigFieldRecord - 1).toString);

//    System.out.println("111111111111--- segy.traceNumberWithinOrigFieldRecord - 1 :" + (segy.traceNumberWithinOrigFieldRecord - 1));

//    mainGui.mainController.getSegyTempTraces(segy.traceNumberWithinOrigFieldRecord - 1).setTraceBinHeader(
    mainGui.mainController.getSegyTempTraces(segy.traceSequenceNumberWithinSegyFile - 1).setTraceBinHeader(
      segy.traceSequenceNumberWithinLine,
      segy.traceSequenceNumberWithinSegyFile,
      segy.origFieldRecordNumber,
      segy.traceNumberWithinOrigFieldRecord,
      segy.energySourcePointNumber,
      segy.ensembleNumber,
      segy.traceNumberWithinEnsemble,
      segy.traceIdCode,
      segy.vertSummedTracesYieldingThisTrace,
      segy.horizStackedTracesYieldingThisTrace,
      segy.dataUse,
      segy.distFromSourcePointToReceiverGroup,
      segy.receiverGroupElevation,
      segy.surfaceElevationAtSource,
      segy.sourceDepthBelowSurface,
      segy.datumElevationAtReceiverGroup,
      segy.datumElevationAtSource,
      segy.waterDepthAtSource,
      segy.waterDepthAtGroup,
      segy.elevationScalar,
      segy.coordScalar,
      segy.sourceCoordX,
      segy.sourceCoordY,
      segy.groupCoordX,
      segy.groupCoordY,
      segy.coordUnits,
      segy.weatheringVelocity,
      segy.subweatheringVelocity,
      segy.upholeTimeAtSource,
      segy.upholeTimeAtGroup,
      segy.sourceStaticCorrection,
      segy.groupStaticCorrectionMs,
      segy.totalStaticAppliedMs,
      segy.lagTimeAMs,
      segy.lagTimeBMs,
      segy.delayRecordingTimeMs,
      segy.muteTimeStartMs,
      segy.muteTimeEndMs,
      segy.samplesNumber,
      segy.sampleIntervalMs,
      segy.gainType,
      segy.instrumentGainConstant,
      segy.instrumentEarlyOrInitialGain,
      segy.correlated,
      segy.sweepFrequencyAtStart,
      segy.sweepFrequencyAtEnd,
      segy.sweepLengthMs,
      segy.sweepType,
      segy.sweepTraceTaperLengthAtStartMs,
      segy.sweepTraceTaperLengthAtEndMs,
      segy.taperType,
      segy.aliasFilterFreq,
      segy.aliasFilterSlope,
      segy.notchFilterFreq,
      segy.notchFilterSlope,
      segy.lowCutFreq,
      segy.highCutFreq,
      segy.lowCutSlope,
      segy.highCutSlope,
      segy.year,
      segy.dayOfYear,
      segy.hourOfDay,
      segy.minuteOfHour,
      segy.secondOfMinute,
      segy.timeBasisCode,
      segy.traceWeightFactor,
      segy.geophoneGroupNumOfRollSwitchPosOne,
      segy.geophoneGroupNumOfTraceOneWithinOrigRecord,
      segy.geophoneGroupNumOfLastWithinOrigRecord,
      segy.gapSize,
      segy.overTravel,
      segy.segyHedRev,
      segy.shotID,
      segy.auxChanSigDesc,
      segy.auxChanID,
      segy.shotPointLine,
      segy.shotPointSta,
      segy.recLine,
      segy.recSta,
      segy.vSMT,
      segy.vSMTScaleCode,
      segy.vSMTHOA,
      segy.vSMTVOA,
      segy.sourceType,
      segy.sourceTypeSEGD,
      segy.auxChanType,
      segy.noiseEdType,
      segy.noiseEdGateLength,
      segy.deviceType1bdevSerNum3b,
      segy.devChanNum,
      segy.auxChanSourcsetID,
      segy.debStatus,
      segy.lATTestType ,
      segy.fixedLowCutFreq,
      segy.fixedLowCutSlope,
      segy.boxFunction,
      segy.notchBCenFreq,
      segy.notchBBandwidth,
      segy.notchCCenFreq,
      segy.notchCBandwidth,
      segy.eventType,
      segy.sensorTypeID,
      segy.vSMTInfo1,
      segy.vSMTInfo2,
      segy.vSMTInfo3,
      segy.vSMTInfo4,
      segy.vSMTInfo5,
      segy.dataModBit
    )

//    mainGui.getMainController.getSegyTempTraces(segy.traceNumberWithinOrigFieldRecord - 1) .printHeader()
    mainGui.getMainController.getSegyTempTraces(segy.traceSequenceNumberWithinSegyFile - 1) .printHeader()



    segy -> nextPhase(segy)
  }
}

/**
  * @param pos index of starting sample of the DataChunk in the Trace
  * @param bytesLeft bytes left to fetch for the current Trace
  */
case class TraceDataPhase(cfg: SegyConfig, binHeader: BinHeader, th: TraceHeader, pos: Int, bytesLeft: Int)
  extends SegyPhase
{
  override def length: Int = Math.min(cfg.dataChunkSize, bytesLeft)
  override def matPromise: PromiseStrategy = NOOP
  def nextPhase(td: TraceDataChunk): SegyPhase = {
    val bytesUsed = td.bs.length
    val curPos = pos + bytesUsed / binHeader.getDataFormat.length
    bytesLeft - bytesUsed match {
      case left if left > 0 => TraceDataPhase(new SegyConfig(Charset.forName("CP037"), mainGui.getSettings_singl.getCfgTraceSizeBytes), binHeader, th, curPos, left)
      case left if left == 0 => TraceHeaderPhase(new SegyConfig(Charset.forName("CP037"), mainGui.getSettings_singl.getCfgTraceSizeBytes), binHeader)
      case left if left < 0 => throw new SegyException(s"Something went wrong, negative offset reading SegY Data: $left")
    }
  }
  override def extract(bs: ByteString): (TraceDataChunk, SegyPhase) = {
    var a: Array[Float] = new Array[Float](2048)
    val segy = TraceDataChunk(bs, pos, th.segyHedRev, th.traceIdCode, a)(binHeader.getDataFormat)// , shotPointLine, shotPointSta


//    mainGui.mainController.segyTempTracesDataForDisplaying(th.traceNumberWithinOrigFieldRecord-1).data=segy.floatData;
    mainGui.mainController.segyTempTracesDataForDisplaying(th.traceSequenceNumberWithinSegyFile -1).data=segy.floatData;
//    System.out.println("-segy.floatData----------------------- data length" + segy.floatData.length)
//    System.out.println("-segy.floatData----------------------- data " + segy.floatData.toString)
//    System.out.println("-segy.floatData----------------------- data " + segy.floatData.toString)
//    System.out.println("-a----------------------- data " + a(0).toString)
//    System.out.println("-a----------------------- data " + a(1).toString)

    segy -> nextPhase(segy)
  }

//  System.out.println("------------------------- data length" + mainGui.mainController.segyTempTracesDataForDisplaying.length)

}

object SegyHeaders {
  def of(segyParts: Seq[SegyPart]): SegyHeaders = segyParts match {
    case Seq(th: TextHeader, bh: BinHeader) =>
      SegyHeaders(th, bh, Vector.empty)
    case Seq(th: TextHeader, bh: BinHeader, ext @ _*) =>
      SegyHeaders(th, bh, ext.map(_.asInstanceOf[ExtTextHeader]).toVector)
    case _ => throw new SegyException(s"Wrong SegY headers: $segyParts")
  }
}
case class SegyHeaders(
  textHeader: TextHeader,
  binHeader: BinHeader,
  extTextHeaders: Vector[ExtTextHeader]
)
