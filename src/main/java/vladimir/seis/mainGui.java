package vladimir.seis;

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import vladimir.seis.segystream.*;
import org.jfree.chart.ChartPanel;
import scala.concurrent.Future;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.CompletionStage;

public class mainGui {


    //TODO make unactive if settings Change

    //    private final ChartPanel chartPanel1;
    public JPanel mainJPanel;
    private JButton shooseFileButton;
    private JPanel filesPanel;
    private JPanel tempPanel;
    private JList filesList;
    private JPanel buttonPanel;
    private JButton showFileTxtButton;
    private JButton showFileBinButton;
    private JButton showTraceBinButton;
    private JButton pickingButton;
    private JButton saveFileButton;
    private JButton settingsBotton;
    private JButton scaleUp;
    private JButton scaleDown;
    private JButton scaleZero;
    private JLabel lawPoint1TL;
    private JLabel lawPoint2TL;
    private JLabel lawPoint3TL;
    private JLabel lawPoint4TL;
    private JLabel lawPoint5TL;
    private JLabel lawPoint6TL;
    private JButton clearButton;
    private JToggleButton balanceToggle;
    private JSpinner reelSpinner;
    private JLabel seqHelperTF;
    private JLabel seqHelperTFToolTip;
    JFileChooser fc;
    static private File directory;
    static private File savePath;
    private File[] choosenFiles;
    private int choosenIndex;
    private ChartExecutor chartExecutor;
    private ChartPanel[] chartPanel;
    static private Settings_singleton settings_singl;
    static private MyDrawingGlassPane myDrawingGlassPane;

    final private float[] scaleKoef = {0.125f, 0.17f, 0.22f, 0.33f, 0.66f, 1f, 1.5f, 3f, 4.5f, 6f, 8f};
    int scaleKoefNumber = 5;

    static public mainController mainController;

    private boolean isPickingMode = false;
    private boolean isNewFolderSelected = false;

    static JFrame mainJFrame;


    public JButton getPickingButton() {
        return pickingButton;
    }

    public static void main(String[] args) { //TODO make another method of starting (see book)

        settings_singl = new Settings_singleton().getSettings_singleton();
        mainJFrame = new JFrame("SEGYMpvEditor v0.02 TC");
        mainController = new mainController();


//        shooseFileButton.setIc;
//        showFileTxtButton;
//        showFileBinButton;
//        showTraceBinButton;

        mainJFrame.setContentPane(new mainGui().mainJPanel);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        mainJFrame.pack();
        mainJFrame.setVisible(true);
        mainJFrame.setResizable(false); // Lock size change
        JFrame settingsJFrame = new JFrame("settings");
        Settings settings = new Settings();
        settingsJFrame.setContentPane(settings.settingsPanel);
        settingsJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        settingsJFrame.pack();
        settingsJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 - settingsJFrame.getWidth()/2,
                mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - settingsJFrame.getHeight()/2);
        settingsJFrame.setResizable(false);
        settingsJFrame.setVisible(true);



        initAfterReading();

        //Checking size




//        setupMainController();
//        initializeFrames();

    }

    private static void initAfterReading() {

        myDrawingGlassPane = new MyDrawingGlassPane(mainController);
        mainJFrame.setGlassPane(myDrawingGlassPane);
        myDrawingGlassPane.init(); //TODO Rewrite with trasfer GUI elements throw maincontroller
//        mainController.initReadingParameters();
    }


    public static Settings_singleton getSettings_singl() {
        return settings_singl;
    }

    public static mainController getMainController() {
        return mainController;
    }

    public JPanel getMainJPanel() {
        return mainJPanel;
    }

    public static MyDrawingGlassPane getMyDrawingGlassPane() {
        return myDrawingGlassPane;
    }


    public mainGui() {

        final ActorSystem system = ActorSystem.create("111");
        final Materializer materializer = ActorMaterializer.create(system);

        //Set spinner initial parameters
        {
            JSpinner.DefaultEditor rsEditor = ( JSpinner.DefaultEditor ) reelSpinner.getEditor();
            rsEditor.getTextField().setEditable(false);
            reelSpinner.setLayout(null);
            Component c = reelSpinner.getComponent(0);
            if (c instanceof BasicArrowButton) {
//                System.out.println("SetSpinnerButtonSize 1");
                BasicArrowButton b = (BasicArrowButton) c;
                b.setBounds(26, 0, 1, 1);
                b.setSize(25, 25);
            }
            Component c2 = reelSpinner.getComponent(1);
            if (c2 instanceof BasicArrowButton) {
//                System.out.println("SetSpinnerButtonSize 2");
                BasicArrowButton b = (BasicArrowButton) c2;
                b.setBounds(26, 26, 1, 1);
                b.setSize(25, 25);
            }
//            Component c3 = reelSpinner.getComponent(2);
//            if (c3 instanceof JSpinner.NumberEditor || c3 instanceof JSpinner.ListEditor) {
//                JSpinner.DefaultEditor ne = (JSpinner.DefaultEditor) c3;
//                ne.setBounds(1, 1, 10, 10);
//            }
            rsEditor.setBounds(1,1,25,50);
            rsEditor.setAlignmentX(0.5f);
            rsEditor.setAlignmentY(0.5f);
//            rsEditor.setFont(new Font("Arial",Font.PLAIN,16));



            reelSpinner.setVisible(false);
            seqHelperTFToolTip.setVisible(false);
            seqHelperTF.setVisible(false);
        }

        balanceToggle.setSelected(true);

        makeButtonsUnactive();
        //        getMainJPanel().addMouseListener(this);
        mainController.init(pickingButton, lawPoint1TL, lawPoint2TL, lawPoint3TL, lawPoint4TL, lawPoint5TL, lawPoint6TL);
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        shooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                isNewFolderSelected = false;
                directory = null;
                choosenFiles = null;

                int returnVal = fc.showOpenDialog(tempPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    directory = fc.getSelectedFile();
                    choosenFiles = directory.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".sgy");
                        }
                    });

                    if (choosenFiles.length != 0) {


                        for (File file : choosenFiles) {
                            if (file.isFile()) {
//                                System.out.println(file.getName());
                            } else {
//                                System.out.println("not a file......");
                            }
                        }

                        String[] choosenFileNames = new String[choosenFiles.length];

                        for (int i = 0; i < choosenFiles.length; i++) {
                            choosenFileNames[i] = choosenFiles[i].getName();
//                        System.out.println("chooosen " +choosenFileNames[i]);
                        }

                        filesList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
                        filesList.addListSelectionListener(new ListSelectionListener() {

                            @Override
                            public void valueChanged(ListSelectionEvent event) {
                                if (!event.getValueIsAdjusting()&&isNewFolderSelected) {
                                    JList source = (JList) event.getSource();
                                    choosenIndex = source.getSelectedIndex();
                                    makeButtonsUnactive(); //TODO Check
                                    resetValuesWhenNewFileChoosen();
                                    startReading();

//                                tempPanel.removeAll();  //TODO Refresh if new file сhoosen
//                                tempPanel.updateUI();
                                }


                            }
                        });

                        filesList.setListData(choosenFileNames);
                        isNewFolderSelected = true;
                    } else {
                        JOptionPane.showMessageDialog(mainJPanel,
                                "Файлы sgy не найдены",
                                "Внимание",
                                JOptionPane.WARNING_MESSAGE);
                    }


//                        file = fc.getSelectedFile();
                    //This is where a real application would open the file.
//                        log.append("Opening: " + file.getName() + "." + newline);
                } else {
//                        log.append("Open command cancelled by user." + newline);
                }
//                    log.setCaretPosition(log.getDocument().getLength());


            }

            private void startReading() { // Исспользование класса реактивной (RxJava) акка
//                final ActorSystem system = ActorSystem.create("111");
//                final Materializer materializer = ActorMaterializer.create(system);

                // Construct stream source from file

                Source<ByteString, CompletionStage<IOResult>> fileSource = FileIO.fromFile(choosenFiles[choosenIndex]);

                // Configure and declare the stream    //Previous version
//                SegyConfig config = new SegyConfig(
//                        Charset.forName("CP037"), //textual data charset
//                        8192 //data chunk size, bytes
//                );
//                settings_singl.setSample_sizeInBytes();
//                System.out.println();
                SegyConfig config = new SegyConfig(
                        Charset.forName("CP037"), //textual data charset
                        settings_singl.getCfgEachSampleSizeBytes()  //data chunk size, bytes
                );
                Source<SegyPart, Future<SegyHeaders>> segySource = fileSource.viaMat(new SegyFlow(config), Keep.right());

                // Run the stream
                CompletionStage<Done> done = segySource
                        .map(segy -> {
//
//
//                            System.out.println(segy.info()); // Print debug info
                            return segy;
                        })
                        .toMat(Sink.ignore(), Keep.right()) // wait for the Sink to complete
                        .run(materializer);


                // Wait for stream termination and print the stats
//                done.thenRunAsync(() -> onFinishedreading());
//                done.thenRun(system::terminate);

                done.thenRun(() -> onFinishedreading());


                //TODO Something weard with throwing errors from other threads; Dont execute

//                System.out.println("11111111111111111111111");
//                reDrawChartsWithRenevalData();
////                System.out.println("22222222222222222222222222");
//                makeButtonsActive();



            }
        });

        initChartPanels(); //Метод инициализации

        showFileTxtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fileTxtJFrame = new JFrame("fileTxtHeader");
                fileTxtHeader fileTxtHeader = new fileTxtHeader();
                fileTxtJFrame.setContentPane(fileTxtHeader.fileTxtJPanel);
                fileTxtJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                fileTxtHeader.setTxtHeader(mainController.getSegyTempFile().getFileTextHeader());
                fileTxtJFrame.pack();
                fileTxtJFrame.setVisible(true);
            }
        });
        showFileBinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame fileBinJFrame = new JFrame("fileBinHeader");
                fileBinHeader fileBinHeader = new fileBinHeader();
                fileBinJFrame.setContentPane(fileBinHeader.fileBinJPanel);
                fileBinJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                fileBinHeader.setBinHeader(
                        mainController.getSegyTempFile().getJobId(),
                        mainController.getSegyTempFile().getLineNumber(),
                        mainController.getSegyTempFile().getReelNumber(),
                        mainController.getSegyTempFile().getDataTracesPerEnsemble(),
                        mainController.getSegyTempFile().getAuxTracesPerEnsemble(),
                        mainController.getSegyTempFile().getSampleIntervalMicroSec(),
                        mainController.getSegyTempFile().getSampleIntervalMicroSecOrig(),
                        mainController.getSegyTempFile().getSamplesPerDataTrace(),
                        mainController.getSegyTempFile().getSamplesPerDataTraceOrig(),
                        mainController.getSegyTempFile().getDataSampleFormatCode(),
                        mainController.getSegyTempFile().getEnsembleFold(),
                        mainController.getSegyTempFile().getTraceSortingCode(),
                        mainController.getSegyTempFile().getVerticalSumCode(),
                        mainController.getSegyTempFile().getSweepFrequencyAtStartHz(),
                        mainController.getSegyTempFile().getSweepFrequencyAtEndHz(),
                        mainController.getSegyTempFile().getSweepLengthMs(),
                        mainController.getSegyTempFile().getSweepTypeCode(),
                        mainController.getSegyTempFile().getTraceNumberOfSweepChannel(),
                        mainController.getSegyTempFile().getSweepTraceTaperLengthAtStartMs(),
                        mainController.getSegyTempFile().getSweepTraceTaperLengthAtEndMs(),
                        mainController.getSegyTempFile().getTaperType(),
                        mainController.getSegyTempFile().getCorrelatedDataTraces(),
                        mainController.getSegyTempFile().getBinaryGainRecovered(),
                        mainController.getSegyTempFile().getAmplitudeRecoveryMethod(),
                        mainController.getSegyTempFile().getMeasurementSystem(),
                        mainController.getSegyTempFile().getImpulseSignalPolarity(),
                        mainController.getSegyTempFile().getVibratoryPolarityCode(),
                        mainController.getSegyTempFile().getDsuSN(),
                        mainController.getSegyTempFile().getManufacturer(),
                        mainController.getSegyTempFile().getFormatVersion(),
                        mainController.getSegyTempFile().getReelHdrRev(),
                        mainController.getSegyTempFile().getLittleIndian(),
                        mainController.getSegyTempFile().getLineSpacing(),
                        mainController.getSegyTempFile().getStaSpacing(),
                        mainController.getSegyTempFile().getNumOfFiles(),
                        mainController.getSegyTempFile().getNumOfTraces()

                );
                fileBinJFrame.pack();
                fileBinJFrame.setVisible(true);

            }
        });
        showTraceBinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame traceBinJFrame = new JFrame("traceBinHeader");
                traceBinHeader traceBinHeader = new traceBinHeader();
                traceBinJFrame.setContentPane(traceBinHeader.traceBinJpanel);
                traceBinJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


//                System.out.println("mainController.getSegyTempTraces(0).toString(): "+mainController.getSegyTempTraces(0).toString());
//                System.out.println("mainController.getSegyTempTraces(0).toString(): "+mainController.getSegyTempTraces(0).getTraceSequenceNumberWithinLine().toString());

                traceBinHeader.setBinHeaderTxtFields(mainController.getSegyTempTraces(0));
                traceBinJFrame.pack();
                traceBinJFrame.setVisible(true);
            }
        });

        // Сохранение данных в новую папку /Edited с перезаписью даты и времени создания на исходную
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                savePath = new File(directory.getAbsolutePath() + "/Edited");
                savePath.mkdir();
//                System.out.println(choosenFiles[choosenIndex].getPath());
//                System.out.println(choosenFiles[choosenIndex].getName());
//                System.out.println(choosenFiles[choosenIndex].getAbsolutePath());

                fillDataToAfterProcessing(); // For Saving also
                makeProccesing();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(savePath.getAbsolutePath() + "/" + choosenFiles[choosenIndex].getName());

                    if (fos != null) {
                        DataOutputStream dos = new DataOutputStream(fos);
                        mainController.segyTempFile.writeToDataOutputStream(dos);

                        for (int i = 0; i < settings_singl.getCfgTraceNumber(); i++) { //TODO change to variable (216)
//                            System.out.println(" *** mainController.segyTempTraces[i].getTraceSequenceNumberWithinLine() " + mainController.segyTempTraces[i].getTraceSequenceNumberWithinLine());
                            mainController.segyTempTraces[i].writeToDataOutputStream(dos);
//                            mainController.segyTempTracesDataForDisplaying[i].writeToDataOutputStream(dos,settings_singl.getSample_sizeInBytes());
                            System.out.println("Saving...getSample_sizeInBytes -->" + settings_singl.getSample_sizeInBytes());
                            System.out.println("Saving...getCfgTraceSizeBytes -->" + settings_singl.getCfgTraceSizeBytes());

                            mainController.segyTempTracesDataAfterProcessing[i].writeToDataOutputStream(dos, settings_singl.getCfgTraceSizeBytes());
//                            System.out.println("mainController.segyTempTraces.length  " + mainController.segyTempTraces.length);
//                            System.out.println("mainController.segyTempTracesDataForDisplaying.length  " + mainController.segyTempTracesDataForDisplaying.length);
                        }

                        dos.flush();
                        dos.close();

                    }

                    if (fos != null) {
                        fos.flush();
                        fos.close();
                    }


                } catch (Exception exc1) {
                    exc1.printStackTrace();
                }


                try {
                    BasicFileAttributes attr = Files.readAttributes(Paths.get(choosenFiles[choosenIndex].getPath()), BasicFileAttributes.class);
//                    System.out.println(attr.creationTime().toString());
//                    System.out.println(attr.lastAccessTime().toString());
//                    System.out.println(attr.lastModifiedTime().toString());


                    Path savedPath = Paths.get(savePath.getAbsolutePath() + "/" + choosenFiles[choosenIndex].getName());


                    Files.setAttribute(savedPath, "creationTime", attr.creationTime());
                    Files.setAttribute(savedPath, "lastModifiedTime", attr.lastModifiedTime());

                    BasicFileAttributes attr1 = Files.readAttributes(savedPath, BasicFileAttributes.class);

//                    Files.setLastModifiedTime(savedPath, attr.lastModifiedTime());
//                    Files.set(savedPath, attr.lastModifiedTime());
//                    Files.setLastModifiedTime(savedPath, attr.lastModifiedTime());

//                    System.out.println(attr1.creationTime().toString());
//                    System.out.println(attr1.lastAccessTime().toString());
//                    System.out.println(attr1.lastModifiedTime().toString());


                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

//                Path file = ...;
//                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
//
//                System.out.println("creationTime: " + attr.creationTime());
//                System.out.println("lastAccessTime: " + attr.lastAccessTime());
//                System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
//


//                    for (int j = 0; j < 6; j++) {
//                        System.out.println("************ Trace# "+ 1 +" ****** Sample# "+(j+1) +"  : " + mainController.segyTempTracesDataForDisplaying[0].data[j]);
//                    }
//
//                for (int j = 2020; j < 2048; j++) {
//                    System.out.println("************ Trace# "+ 1 +" ****** Sample# "+(j+1) +"  : " + mainController.segyTempTracesDataForDisplaying[0].data[j]);
//                }

                mainController.restoreSeismicTraceDataFromVault();
            }
        });
//        pickingButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for (int i = 0; i < 2048; i++) {
//
//
////                System.out.println("**************** Trace 0 *** Sample#: "+i+" : " +mainController.segyTempTracesDataForDisplaying[0].getData()[i]);
//                }
//            }
//        });


        pickingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPickingMode) {
                    pickingButton.setBorder(BorderFactory.createLoweredBevelBorder());
                    myDrawingGlassPane.setVisible(true);
                    isPickingMode = true;
                    getSettings_singl().setInPickingMode(true);
                    makeButtonsUnactiveExcPicking();
                } else {
//                    pickingButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    pickingButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    myDrawingGlassPane.setVisible(false);
                    isPickingMode = false;
                    getSettings_singl().setInPickingMode(false);
                    makeButtonsActiveExcPicking();
                }
            }
        });



        settingsBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame settingsJFrame = new JFrame("settings");
                Settings settings = new Settings();
                settingsJFrame.setContentPane(settings.settingsPanel);
                settingsJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                settingsJFrame.pack();
                settingsJFrame.setResizable(false);
                settingsJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 -settingsJFrame.getWidth()/2,
                        mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - settingsJFrame.getHeight()/2);
                settingsJFrame.setVisible(true);


            }
        });

        scaleUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                double temp = chartExecutor.getScaleFactor();
                if (scaleKoefNumber > 0) {
                    scaleKoefNumber--;
                    chartExecutor.setScaleFactor(scaleKoef[scaleKoefNumber]);
                    chartExecutor.setSameScale();
//                    System.out.println("Scale" + chartExecutor.getScaleFactor());
                    tempPanel.revalidate();
                    tempPanel.repaint();
                }

            }
        });

        scaleDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                double temp = chartExecutor.getScaleFactor();
                if (scaleKoefNumber < 10) {
                    scaleKoefNumber++;
                    chartExecutor.setScaleFactor(scaleKoef[scaleKoefNumber]);
                    chartExecutor.setSameScale();
//                    System.out.println("Scale" + chartExecutor.getScaleFactor());
                    tempPanel.revalidate();
                    tempPanel.repaint();
                }

            }
        });

        scaleZero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleKoefNumber = 5; //MiddleValue
                chartExecutor.setScaleFactor(scaleKoef[scaleKoefNumber]);
                chartExecutor.setSameScale();
//                System.out.println("Scale zeroed" + chartExecutor.getScaleFactor());
                tempPanel.revalidate();
                tempPanel.repaint();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        balanceToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (balanceToggle.isSelected()) {
                    getMainController().balancingTempData();
//                    System.out.println("Balanced");
                }
                else {
                    getMainController().resetBalancing();  //TODO Fix: need different behavior on first open and button click
                    chartExecutor.resetPlotsRange();
//                    System.out.println("Notbalanced");
                }

                redrawCharts();
            }
        });
    }

    private void makeProccesing() { //TODO Changing in SEGY FIle

//        for (int i = 0; i < 54; i++) {
//            mainController.segyTempTraces[i].setAuxChanType((byte)0x01);
//        }
        processingTraceHeader();
        processingDataUpperOfPicking();


    }

    private void fillDataToAfterProcessing(){
        //PrepareData for processing and saving


        for (int i = 0; i < getSettings_singl().getCfgTraceNumber(); i++) {
            for (int j = 0; j < getSettings_singl().getCfgSamplesNumber(); j++) {
//                System.out.println("i = " + i + " j = " + j);
                getMainController().getSegyTempTracesDataAfterProcessing()[i].getData()[j] = getMainController().getSegyTempTracesDataFromVault()[i].getData()[j];
            }

        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void initChartPanels() {

        chartExecutor = new ChartExecutor("Line Chart Demo");
        chartPanel = chartExecutor.getChartPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));
        tempPanel.add(chartPanel[0]);
        tempPanel.add(chartPanel[1]);

////        myDrawingGlassPane.setButtonJPanel(buttonPanel);
//        myDrawingGlassPane.setTempJPanel(tempPanel);

//        pickingJLF.setPosition(tempPanel, JLayeredPane.DEFAULT_LAYER);
//        for (int j = 0; j < chartPanel.length; j++) {
////                    tempPanel.add(chartPanel[j], BorderLayout.LINE_END);
//
////             tempPanel.add(chartPanel[j]);


        chartExecutor.setSettings_singleton(settings_singl);
//        System.out.println("Add charts");
//        System.out.println(tempPanel.toString());
//        tempPanel.setVisible(false);

//        pickingJLF.revalidate();
//        pickingJLF.repaint();

//        pickingJLF.
        //Code for adding crosshair
//            CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
//            chartPanel[1].addOverlay(crosshairOverlay.addDomainCrosshair(new Crosshair()));


//        }


    }


    private static void initializeFrames() {
        //


    }

    void reDrawChartsWithRenevalData() {
        int CURRENT_FILE_SEQ_NUMBER = settings_singl.getCfgCurrentFileSeqNumber();

        //                chartExecutor = new ChartExecutor("Line Chart Demo");  //Previous version of code
//                ChartPanel[] chartPanel = chartExecutor.getChartPanel();
//
//                //TODO Add charts here
//                System.out.println("ChartObject"+chartExecutor.toString());
//                System.out.println("ChartObject"+chartExecutor.getChartPanel().toString());
//                System.out.println("ChartObject"+chartPanel.toString());
//                tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.X_AXIS));
//                for (int j = 0; j < chartPanel.length; j++) {
////                    tempPanel.add(chartPanel[j], BorderLayout.LINE_END);
//                    tempPanel.add(chartPanel[j]);
//                    System.out.println("Add chart: " + j);
//                }
//        System.out.println("******************************************************");
        System.out.println("reDrawChartsWithRenevalData CURRENT_FILE_SEQ_NUMBER ->>" + CURRENT_FILE_SEQ_NUMBER);
        System.out.println("reDrawChartsWithRenevalData single singl ->>" + getSettings_singl().getCfgFilesNumber());

        for (int j = 0; j < 54; j++) {
            try {chartExecutor.updateWithDataset(j,CURRENT_FILE_SEQ_NUMBER);}
            catch (IllegalArgumentException e) {}

        }

        chartExecutor.redefineDatasets(CURRENT_FILE_SEQ_NUMBER);

        tempPanel.revalidate();
        tempPanel.repaint();

//        System.out.println("******************************************************");


//                  for (int j = 0; j < chartPanel.length; j++) {
////                    tempPanel.add(chartPanel[j], BorderLayout.LINE_END);
//                    tempPanel.add(chartPanel[j]);
//                    System.out.println("Add chart: " + j);
//                }

    }

    void onFinishedreading() {
//        system::terminate;
//        System.out.println("mainController.onFinishedReading()");

        mainController.saveSeismicTraceDataToVault();// TODO replace out of on finish
        System.out.println("saveSeismicTraceDataToVault");

        System.out.println("balancingTempData");

        redrawCharts();
        System.out.println("redrawCharts");

        if (getSettings_singl().getCfgTraceNumber()>54) {
            activateReelSpinner();
        }
        else deactivateReelSpinner();

        makeButtonsActive();
        System.out.println("makeButtonsActive");



//        done.thenRunAsync(() -> onFinishedreading()
//        System.out.println("mainController.saveSeismicTraceDataToVault()");

//        System.out.println("mainController.saveSeismicTraceDataToVault()");
    }

    private void redrawCharts() {

        if (balanceToggle.isSelected()) {
           getMainController().balancingTempData();
//            System.out.println("Balanced");
        }
        reDrawChartsWithRenevalData();
        chartExecutor.setInitialSameScale(); //Set initial scale separate for each file
        chartExecutor.setSameScale();   // TODO Write javadoc
    }

    void setJLabelsLaw(String s1, String s2, String s3, String s4, String s5, String s6) {

        if (s1 != null) lawPoint1TL.setText(s1);
        else lawPoint1TL.setText("");
        if (s2 != null) lawPoint2TL.setText(s1);
        else lawPoint2TL.setText("");
        if (s3 != null) lawPoint3TL.setText(s1);
        else lawPoint3TL.setText("");
        if (s4 != null) lawPoint4TL.setText(s1);
        else lawPoint4TL.setText("");
        if (s5 != null) lawPoint5TL.setText(s1);
        else lawPoint5TL.setText("");
        if (s6 != null) lawPoint6TL.setText(s1);
        else lawPoint6TL.setText("");
        filesPanel.revalidate();
        filesPanel.repaint();

    }

    private void makeButtonsUnactive() {
        showFileTxtButton.setEnabled(false);
        showFileBinButton.setEnabled(false);
        showTraceBinButton.setEnabled(false);
        pickingButton.setEnabled(false);
        saveFileButton.setEnabled(false);
        clearButton.setEnabled(false);
//          settingsBotton.setEnabled(false);
        scaleUp.setEnabled(false);
        scaleDown.setEnabled(false);
        scaleZero.setEnabled(false);
    }

    private void makeButtonsActive() {
        showFileTxtButton.setEnabled(true);
        showFileBinButton.setEnabled(true);
        showTraceBinButton.setEnabled(true);
        pickingButton.setEnabled(true);
        saveFileButton.setEnabled(true);
//          settingsBotton.setEnabled(false);
        scaleUp.setEnabled(true);
        scaleDown.setEnabled(true);
        scaleZero.setEnabled(true);
    }

    private void makeButtonsActiveExcPicking() {
//        shooseFileButton.setEnabled(true);
        showFileTxtButton.setEnabled(true);
        showFileBinButton.setEnabled(true);
        showTraceBinButton.setEnabled(true);
        pickingButton.setEnabled(true);
        saveFileButton.setEnabled(true);
        settingsBotton.setEnabled(true);
        scaleUp.setEnabled(true);
        scaleDown.setEnabled(true);
        scaleZero.setEnabled(true);
        clearButton.setEnabled(false);

    }

    private void makeButtonsUnactiveExcPicking() {
//        shooseFileButton.setEnabled(false);
        showFileTxtButton.setEnabled(false);
        showFileBinButton.setEnabled(false);
        showTraceBinButton.setEnabled(false);
        clearButton.setEnabled(true);
//          pickingButton.setEnabled(false);
        saveFileButton.setEnabled(false);
        settingsBotton.setEnabled(false);
        scaleUp.setEnabled(false);
        scaleDown.setEnabled(false);
        scaleZero.setEnabled(false);
    }

    public static void pickingDisablerGui() {
        JFrame pickingGUIJFrame = new JFrame("Quit picking?");
        isOutPicking isOutPicking = new isOutPicking();
        pickingGUIJFrame.setContentPane(isOutPicking.contentPane);
        pickingGUIJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pickingGUIJFrame.pack();
        pickingGUIJFrame.setResizable(false);
        pickingGUIJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 -pickingGUIJFrame.getWidth()/2,
                mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - pickingGUIJFrame.getHeight()/2);
        pickingGUIJFrame.setVisible(true);


    }

    private void resetValuesWhenNewFileChoosen() {
//        System.out.println("resetValuesWhenNewFileChoosen executed");
        for (int i = 0; i < 6; i++) {           //numbers of labels
            mainController.defineJLabelText(null, i);
        }
        settings_singl.zeroedTrimLaw();
        settings_singl.zeroedFullTrimLaw();
        settings_singl.zeroedFullTrimLawShofted();

        myDrawingGlassPane.zeroedMuteLaw();

        scaleKoefNumber = 5;
        chartExecutor.setScaleFactor(1.0);
        if (mainGui.getSettings_singl().getInitialFileScaleRange()!=null) chartExecutor.resetPlotsRange();

//        chartExecutor.setInitialSameScale();
//        chartExecutor.setSameScale();

//        ((MyDrawingGlassPane) mainJFrame.getGlassPane()).zeroedMuteLaw();


    }

//    public static void pickingDisabler() {
//        pickingButton.doClick();
//    }

//    public static void setupMainController() {
//        mainController.setMainGui(this);
//    }


//    @Override
//    public void mouseClicked(MouseEvent e) {
//        System.out.println("Window X  :" + e.getX());
//        System.out.println("Window Y  :" + e.getY());
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }

    // Changing traces with 4 seismic records
    private void processingTraceHeader() { //TODO CHECK if 54*4 (216) traces
        int reelNumber = settings_singl.getCfgFilesNumber();
        for (int j = 0; j < reelNumber; j++) {


            for (int i = 0; i < mainController.getSegyTempTracesDataFromVault()[2+j*54].getData().length; i++) {
                mainController.getSegyTempTracesDataAfterProcessing()[2+j*54].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[3].getData()[i] +
                        mainController.getSegyTempTracesDataForDisplaying()[4+j*54].getData()[i]) / 2;

            }
        }

        //TODO for future release in 216

//        for (int i = 0; i < mainController.getSegyTempTracesDataForDisplaying()[56].getData().length; i++) {
//            mainController.getSegyTempTracesDataForDisplaying()[56].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[57].getData()[i] +
//                    mainController.getSegyTempTracesDataForDisplaying()[58].getData()[i])/2;
//        }
//
//        for (int i = 0; i < mainController.getSegyTempTracesDataForDisplaying()[110].getData().length; i++) {
//            mainController.getSegyTempTracesDataForDisplaying()[110].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[111].getData()[i] +
//                    mainController.getSegyTempTracesDataForDisplaying()[112].getData()[i])/2;
//        }
//
//        for (int i = 0; i < mainController.getSegyTempTracesDataForDisplaying()[164].getData().length; i++) {
//            mainController.getSegyTempTracesDataForDisplaying()[164].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[165].getData()[i] +
//                    mainController.getSegyTempTracesDataForDisplaying()[166].getData()[i])/2;
//
//
//        }
    }

    private void processingDataUpperOfPicking() { //TODO need applying something better than average (or some transformation)

        //Applying AGC with c = arithmetic average for samples above  shifted trim law



        for (int i = 0; i < mainGui.getSettings_singl().getFullTrimShifted().size();
             i++) {
            int tempTraceNumber, tempSampleNumber;
            tempTraceNumber = getSettings_singl().getFullTrimShifted().get(i).getDatasetValue();
            tempSampleNumber = getSettings_singl().getFullTrimShifted().get(i).getSampleValue();

            //Copying array above trimShiftedLaw
            float[] tempTrimDataArray = new float[tempSampleNumber];
            for (int j = 0; j < tempTrimDataArray.length; j++) {
                tempTrimDataArray[j] = getMainController().getSegyTempTracesDataAfterProcessing()[tempTraceNumber].getData()[j];
            }

            //Calculating summary of array from 0 to trimShifted point and deviding to sample number (average value)
            float regLevel; //regulation level
            float sum = 0;
            for (int j = 0; j < tempTrimDataArray.length; j++) {
                sum = sum + tempTrimDataArray[j];
            }

            regLevel = sum / tempTrimDataArray.length;
            regLevel = regLevel * getSettings_singl().getKorCoefToAverage(); // Applying correction coefficient

//            System.out.printf(":" + i + ":");
//            System.out.print(" -1- ");
//            System.out.printf("%4f", sum);
//            System.out.print(" -2- " + tempTrimDataArray.length);
//            System.out.print(" -3- " + regLevel);
//
//            System.out.println();

            int shift = (int) getSettings_singl().getAgcWindowSizeInTraces() / 2;
//            System.out.println("Shift: " + shift);

            //First stem of AGC - calculating array of summary value in window from settings of input array[window/2; size-window/2]
            float[] tempAvarage = new float[tempTrimDataArray.length];

            for (int j = shift; j < tempTrimDataArray.length - shift; j++) { //
                sum = Math.abs(tempTrimDataArray[j]);
                for (int k = 1; k <= shift; k++) {
                    sum = sum + Math.abs(tempTrimDataArray[j + k]);
                    sum = sum + Math.abs(tempTrimDataArray[j - k]);
                }

                tempAvarage[j] = sum / getSettings_singl().getAgcWindowSizeInTraces();
            }

            for (int j = 0; j < shift; j++) {                       //Execution on AGC boundaries
                tempAvarage[j] = tempAvarage[shift];
            }

            for (int j = tempTrimDataArray.length - shift; j < tempTrimDataArray.length; j++) {
                tempAvarage[j] = tempAvarage[tempTrimDataArray.length-shift-1];
            }



            //Calculating AGC coefficients
            float[] tempKoef = new float[tempAvarage.length];

            for (int j = 0; j < tempKoef.length; j++) {
                tempKoef[j] = regLevel / tempAvarage[j];

            }

            // Rewrite seismic data

            for (int j = 0; j < tempKoef.length; j++) {
                getMainController().getSegyTempTracesDataAfterProcessing()[tempTraceNumber].getData()[j] = tempTrimDataArray[j] * tempKoef[j];
            }


            //Output in console for debug
//            System.out.println("********Result og AGC*********");
//            for (int j = 0; j < tempTrimDataArray.length; j++) {
//                System.out.printf(":" + j + ":");
//                System.out.print(" -1- ");
//                System.out.printf("%4f", tempTrimDataArray[j]);
//                System.out.print(" -2- " + shift);
//                System.out.print(" -3- ");
//                System.out.printf("%4f", tempAvarage[j]);
//                System.out.print(" -4- " + regLevel);
//                System.out.print(" -5- ");
//                System.out.printf("%4f", tempKoef[j]);
//                System.out.print(" -6- ");
//                System.out.printf("%4f", getMainController().getSegyTempTracesDataForDisplaying()[tempTraceNumber].getData()[j]);
//                System.out.println();
//            }


        }


    }

    public void activateReelSpinner() {
        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, mainGui.getSettings_singl().getCfgFilesNumber(), 1);
        reelSpinner.setModel(model1);

        reelSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                settings_singl.setCfgCurrentFileSeqNumber((int) reelSpinner.getValue() - 1);
                System.out.println("CURRENT_FILE_SEQ_NUMBER -> "+ settings_singl.getCfgCurrentFileSeqNumber());
                seqHelperTF.setText(reelSpinner.getValue().toString() + " of " + Integer.toString(settings_singl.getCfgFilesNumber()));
                redrawCharts();

            }
        });

        JSpinner.DefaultEditor rsEditor = ( JSpinner.DefaultEditor ) reelSpinner.getEditor();
        rsEditor.getTextField().setEditable(false);
        rsEditor.setBounds(1,1,25,50);
        reelSpinner.setVisible(true);
        seqHelperTFToolTip.setVisible(true);
        seqHelperTF.setVisible(true);
    }
    public void deactivateReelSpinner() {
        reelSpinner.setVisible(false);
        seqHelperTFToolTip.setVisible(false);
        seqHelperTF.setVisible(false);
    }

    public int calculateTraceLengthInBytes() {


        return 0;
    }



}

