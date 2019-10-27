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
import com.intellij.uiDesigner.core.GridConstraints;
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
import java.sql.SQLOutput;
import java.util.Random;
import java.util.concurrent.CompletionStage;

public class mainGui extends JFrame {
     SegyFlow segyFlow;
     Source<SegyPart, Future<SegyHeaders>> segySource;
    CompletionStage<Done> done;
    ListSelectionListener listSelectionListener;

    //TODO make unactive if settings Change
    static private JSpinner reelsp = new JSpinner();
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
    JSpinner.DefaultEditor rsEditor;

    SpinnerNumberModel model1;
    JSpinner.NumberEditor ne;

    private JLabel seqHelperTF;
    private JLabel seqHelperTFToolTip;
    private JButton muteLawTableBotton;
    private JPanel spinnerPanel;
    static private File directory;
    static private File savePath;
    private File[] choosenFiles;
    private int choosenIndex;
    private ChartExecutor chartExecutor;
    private ChartPanel[] chartPanel;
    static private Settings_singleton settings_singl;
    static private MyDrawingGlassPane myDrawingGlassPane;
    public static BasicArrowButton bUp, bDown; //spinner arrows

    final private float[] scaleKoef = {0.125f, 0.17f, 0.22f, 0.33f, 0.66f, 1f, 1.5f, 3f, 4.5f, 6f, 8f};
    int scaleKoefNumber = 5;

    static public mainController mainController;

    public boolean isPickingMode = false;
    private boolean isNewFolderSelected = false;

    static mainGui mainJFrame;

    ActorSystem system;

    boolean isReading;


    public JButton getPickingButton() {
        return pickingButton;
    }

    public static void main(String[] args) { //TODO make another method of starting (see book)

        settings_singl = new Settings_singleton().getSettings_singleton();
        mainController = new mainController();

        mainJFrame = new mainGui();


//        shooseFileButton.setIc;
//        showFileTxtButton;
//        showFileBinButton;
//        showTraceBinButton;

        mainJFrame.setContentPane(mainJFrame.mainJPanel);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.setTitle("SEGYMpvEditor v0.02 TC");


        mainJFrame.pack();
        mainJFrame.setVisible(true);
        mainJFrame.setResizable(false); // Lock size change
//        JFrame settingsJFrame = new JFrame("settings");
//        Settings settings = new Settings();
//        settingsJFrame.setContentPane(settings.settingsPanel);
//        settingsJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        settingsJFrame.pack();
//        settingsJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 - settingsJFrame.getWidth()/2,
//                mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - settingsJFrame.getHeight()/2);
//        settingsJFrame.setResizable(false);
//        settingsJFrame.setVisible(true);


        initAfterReading();

        //Checking size


//        setupMainController();
//        initializeFrames();

    }

    private static void initAfterReading() {

        myDrawingGlassPane = new MyDrawingGlassPane(mainController, bUp, bDown, reelsp);
        mainJFrame.setGlassPane(myDrawingGlassPane);
        myDrawingGlassPane.init(); //TODO Rewrite with trasfer GUI elements throug maincontroller
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

        system = ActorSystem.create("111");
        final Materializer materializer = ActorMaterializer.create(system);

        //Set spinner initial parameters

//        for (int i = 0; i < reelsp.getComponents().length; i++) {
//            System.out.println(reelsp.getComponent(i));
//        }


        rsEditor = ( JSpinner.DefaultEditor ) reelsp.getEditor();
        rsEditor.getTextField().setEditable(false);

//        reelsp.setLayout(null);
//

        Component c = reelsp.getComponent(0);
        if (c instanceof BasicArrowButton) {
//                System.out.println("SetSpinnerButtonSize 1");
            bUp = (BasicArrowButton) c;
            bUp.setBounds(26, 0, 25, 25);


        }
        Component c2 = reelsp.getComponent(1);
        if (c2 instanceof BasicArrowButton) {
            bDown = (BasicArrowButton) c2;
            bDown.setBounds(26, 26, 25, 25);

        }

        rsEditor.setBounds(1,1,25,50);
        rsEditor.setAlignmentX(0.5f);
        rsEditor.setAlignmentY(0.5f);








        try {
            spinnerPanel.add(reelsp, new GridConstraints(0,
                    0,
                    1,
                    1,
                    0,
                    0,
                    GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_WANT_GROW,
                    new Dimension(53, 53),
                    new Dimension(53, 53),
                    new Dimension(53, 53)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        reelsp.setVisible(false);
        seqHelperTFToolTip.setVisible(false);
        seqHelperTF.setVisible(false);


//            buttonPanel.add(reelSpinner,  new GridConstraints());
//            reelSpinner.setVisible(true);


        balanceToggle.setSelected(true);

        makeButtonsUnactive();
        //        getMainJPanel().addMouseListener(this);
        mainController.init(pickingButton, lawPoint1TL, lawPoint2TL, lawPoint3TL, lawPoint4TL, lawPoint5TL, lawPoint6TL);
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        shooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("shooseFileButton");
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

                        if (filesList.getListSelectionListeners().length==0) {
                            listSelectionListener = new ListSelectionListener() {  // TODO memory leak fix unlimited listeter creation on folder choosing

                                @Override
                                public void valueChanged(ListSelectionEvent event) {

//                                    System.out.println("valueChanged1  " + this.getClass().getName());
//                                    System.out.println("valueChanged2 " + event.toString());
//                                    System.out.println("valueChanged3 " + event.getValueIsAdjusting());
//                                    System.out.println("valueChanged4 " + event.getSource().getClass().getName());
//                                    System.out.println("valueChanged5 " + event.getClass().getName());

                                    if (!event.getValueIsAdjusting() && isNewFolderSelected && !isReading) {
                                        JList source = (JList) event.getSource();
                                        choosenIndex = source.getSelectedIndex();
                                        makeButtonsUnactive(); //TODO Check
                                        resetValuesWhenNewFileChoosen();
                                        startReading();
                                        isReading = true;

                                        //                                tempPanel.removeAll();  //TODO Refresh if new file сhoosen
                                        //                                tempPanel.updateUI();
                                    }


                                }
                            };

                        }

                        if (filesList.getListSelectionListeners().length==0) filesList.addListSelectionListener(listSelectionListener);

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
                system = ActorSystem.create("111");
                Materializer materializer = ActorMaterializer.create(system);

                CompletionStage<Done> done = segySource
                        .map(segy -> {
//
//
//                            System.out.println(segy.info()); // Print debug info
                            return segy;
                        })
                        .toMat(Sink.ignore(), Keep.right()) // wait for the Sink to complete
                        .run(materializer);

//                System.out.println("2 done.getClass().getName()" + done.getClass().getName());
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

                traceBinHeader.setBinHeaderTxtFields(mainController.getSegyTempTraces().get(0));
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

                fillDataToAfterProcessing(); // For Saving only
                makeProccesing();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(savePath.getAbsolutePath() + "/" + choosenFiles[choosenIndex].getName());

                    if (fos != null) {
                        DataOutputStream dos = new DataOutputStream(fos);
                        mainController.segyTempFile.writeToDataOutputStream(dos);

                        for (int i = 0; i < mainController.getSegyTempTracesDataAfterProcessing().size(); i++) { //TODO change to variable (216)
//                            System.out.println(" *** mainController.segyTempTraces[i].getTraceSequenceNumberWithinLine() " + mainController.segyTempTraces[i].getTraceSequenceNumberWithinLine());
                            mainController.segyTempTraces.get(i).writeToDataOutputStream(dos);
//                            mainController.segyTempTracesDataForDisplaying[i].writeToDataOutputStream(dos,settings_singl.getSample_sizeInBytes());
//                            System.out.println("Saving...getSample_sizeInBytes -->" + settings_singl.getSample_sizeInBytes());
//                            System.out.println("Saving...getCfgTraceSizeBytes -->" + settings_singl.getCfgTraceSizeBytes());

                            mainController.segyTempTracesDataAfterProcessing.get(i).writeToDataOutputStream(dos, settings_singl.getCfgTraceSizeBytes());
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
                    myDrawingGlassPane.checkPickingStatus();
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


        muteLawTableBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                JFrame settingsJFrame = new JFrame("Mute Law");
                muteLawTable muteLawTable = new muteLawTable();
                settingsJFrame.setContentPane(muteLawTable.muteLawTableMainPanel);
                settingsJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                settingsJFrame.pack();
                settingsJFrame.setResizable(false);
//                settingsJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 -settingsJFrame.getWidth()/2,
//                        mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - settingsJFrame.getHeight()/2);
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
            if (scaleZero.isEnabled()) {
                if (balanceToggle.isSelected()) {
                    getMainController().balancingTempData();
//                    System.out.println("Balanced");
                } else {
                    getMainController().resetBalancing();  //TODO Fix: need different behavior on first open and button click
                    chartExecutor.resetPlotsRange();
//                    System.out.println("Notbalanced");
                }

                redrawCharts();
            }
            }
        });


    }

    private void makeProccesing() { //TODO Changing in SEGY FIle

//        for (int i = 0; i < 54; i++) {
//            mainController.segyTempTraces[i].setAuxChanType((byte)0x01);
//        }
//        processingTraceHeader(); //No need for TC version
//        processingDataUpperOfPickingAGC();
        processingDataUpperOfPickingRandom();


    }

    private void fillDataToAfterProcessing() {
        //PrepareData for processing and saving


        for (int i = 0; i < getMainController().getSegyTempTracesDataAfterProcessing().size(); i++) {
            for (int j = 0; j < getSettings_singl().getCfgSamplesNumber(); j++) {
//                System.out.println("i = " + i + " j = " + j);
                getMainController().getSegyTempTracesDataAfterProcessing().get(i).getData()[j] = getMainController().getSegyTempTracesDataFromVault().get(i).getData()[j];
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

    void reDrawChartsWithRenevalData() { //TODO Check why execute twice
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
//        System.out.println("reDrawChartsWithRenevalData CURRENT_FILE_SEQ_NUMBER ->>" + CURRENT_FILE_SEQ_NUMBER);
//        System.out.println("reDrawChartsWithRenevalData single singl ->>" + getSettings_singl().getCfgFilesNumber());

        for (int j = 0; j < 54; j++) {
            try {
                chartExecutor.updateWithDataset(j, CURRENT_FILE_SEQ_NUMBER);
            } catch (IllegalArgumentException e) {
            }

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
        try {


//        system::terminate;
//        System.out.println("mainController.onFinishedReading()");

//            System.out.println("onFinish AddTrace" + getSettings_singl().getCfgCurrentFileAddTraceNumber());
//            System.out.println("onFinish " + this.getClass().getName());
            mainController.saveSeismicTraceDataToVault();// TODO replace out of on finish
//            System.out.println("saveSeismicTraceDataToVault");

//            System.out.println("balancingTempData");

            redrawCharts();
//            System.out.println("redrawCharts");

            if (getSettings_singl().getCfgTraceNumber() > 48 + getSettings_singl().getCfgCurrentFileAddTraceNumber()) {
//                System.out.println("mainGui.getSettings_singl().getCfgFilesNumber()" + mainGui.getSettings_singl().getCfgFilesNumber());
                try {
                    reelsp.setValue(1);
                    activateReelSpinner();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else deactivateReelSpinner();

            makeButtonsActive();
//            System.out.println("makeButtonsActive");
//            System.out.println("Add trace number = " + getSettings_singl().getCfgCurrentFileAddTraceNumber());


//        done.thenRunAsync(() -> onFinishedreading()
//        System.out.println("mainController.saveSeismicTraceDataToVault()");

//        System.out.println("mainController.saveSeismicTraceDataToVault()");
            system.terminate();
            isReading = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Ошибка чтения SGY файла. /n Перезапустите программу");
        }
    }

    private void redrawCharts() {

        if (balanceToggle.isSelected()) {
            getMainController().balancingTempData();
//            System.out.println("Balanced");
        }
        reDrawChartsWithRenevalData();
        chartExecutor.setInitialSameScale(); //Set initial scale separate for each file
        chartExecutor.setSameScale();   // TODO Write javadoc
        myDrawingGlassPane.reloadTrimLaw();
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
          muteLawTableBotton.setEnabled(false);
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
        muteLawTableBotton.setEnabled(true);
        scaleUp.setEnabled(true);
        scaleDown.setEnabled(true);
        scaleZero.setEnabled(true);
    }

    private void makeButtonsActiveExcPicking() {
//        shooseFileButton.setEnabled(true);
        showFileTxtButton.setEnabled(true);
        showFileBinButton.setEnabled(true);
        showTraceBinButton.setEnabled(true);
//        pickingButton.setEnabled(true);
        saveFileButton.setEnabled(true);
        muteLawTableBotton.setEnabled(true);
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
        muteLawTableBotton.setEnabled(false);
        scaleUp.setEnabled(true);
        scaleDown.setEnabled(true);
        scaleZero.setEnabled(true);
    }

    public static void pickingDisablerGui() {
        JFrame pickingGUIJFrame = new JFrame("Quit picking?");
        isOutPicking isOutPicking = new isOutPicking();
        pickingGUIJFrame.setContentPane(isOutPicking.contentPane);
        pickingGUIJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pickingGUIJFrame.pack();
        pickingGUIJFrame.setResizable(false);
        pickingGUIJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 - pickingGUIJFrame.getWidth() / 2,
                mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - pickingGUIJFrame.getHeight() / 2);
        pickingGUIJFrame.setVisible(true);


    }

    private void resetValuesWhenNewFileChoosen() {
//        System.out.println("resetValuesWhenNewFileChoosen executed");
        for (int i = 0; i < 6; i++) {           //numbers of labels
            mainController.defineJLabelText(null, i);
        }
        mainController.resetForReading();
        settings_singl.zeroedTrimLaw();
        settings_singl.zeroedFullTrimLaw();
        settings_singl.zeroedFullTrimLawShifted();
        settings_singl.resetCfgValues();

        myDrawingGlassPane.zeroedMuteLaw();

        scaleKoefNumber = 5;
        chartExecutor.setScaleFactor(1.0);
        if (mainGui.getSettings_singl().getInitialFileScaleRange() != null) chartExecutor.resetPlotsRange();

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

//    private void processingTraceHeader() { //TODO CHECK if 54*4 (216) traces
//        int reelNumber = settings_singl.getCfgFilesNumber();
//        for (int j = 0; j < reelNumber; j++) {
//
//
//            for (int i = 0; i < mainController.getSegyTempTracesDataFromVault()[2 + j * 54].getData().length; i++) {
//                mainController.getSegyTempTracesDataAfterProcessing()[2 + j * 54].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[3].getData()[i] +
//                        mainController.getSegyTempTracesDataForDisplaying()[4 + j * 54].getData()[i]) / 2;
//
//            }
//        }
//
//        //TODO for future release in 216
//
////        for (int i = 0; i < mainController.getSegyTempTracesDataForDisplaying()[56].getData().length; i++) {
////            mainController.getSegyTempTracesDataForDisplaying()[56].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[57].getData()[i] +
////                    mainController.getSegyTempTracesDataForDisplaying()[58].getData()[i])/2;
////        }
////
////        for (int i = 0; i < mainController.getSegyTempTracesDataForDisplaying()[110].getData().length; i++) {
////            mainController.getSegyTempTracesDataForDisplaying()[110].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[111].getData()[i] +
////                    mainController.getSegyTempTracesDataForDisplaying()[112].getData()[i])/2;
////        }
////
////        for (int i = 0; i < mainController.getSegyTempTracesDataForDisplaying()[164].getData().length; i++) {
////            mainController.getSegyTempTracesDataForDisplaying()[164].getData()[i] = (mainController.getSegyTempTracesDataForDisplaying()[165].getData()[i] +
////                    mainController.getSegyTempTracesDataForDisplaying()[166].getData()[i])/2;
////
////
////        }
//    }

    private void processingDataUpperOfPickingAGC() { //TODO need applying something better than average (or some transformation)

        //Applying AGC with c = arithmetic average for samples above  shifted trim law
        try {
//            System.out.println("Saving");
            for (int i = 0; i < mainGui.getSettings_singl().getFullTrimShifted().size();
                 i++) {
                int tempTraceNumber, tempSampleNumber;
                tempTraceNumber = getSettings_singl().getFullTrimShifted().get(i).getDatasetValue();
                tempSampleNumber = getSettings_singl().getFullTrimShifted().get(i).getSampleValue();

                //Copying array above trimShiftedLaw
                float[] tempTrimDataArray = new float[tempSampleNumber];
                for (int j = 0; j < tempTrimDataArray.length; j++) {
                    tempTrimDataArray[j] = getMainController().getSegyTempTracesDataAfterProcessing().get(tempTraceNumber).getData()[j];
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

                    tempAvarage[j] = sum / 21; //AGC window in samples
                }

                for (int j = 0; j < shift; j++) {                       //Execution on AGC boundaries
                    tempAvarage[j] = tempAvarage[shift];
                }

                for (int j = tempTrimDataArray.length - shift; j < tempTrimDataArray.length; j++) {
                    tempAvarage[j] = tempAvarage[tempTrimDataArray.length - shift - 1];
                }


                //Calculating AGC coefficients
                float[] tempKoef = new float[tempAvarage.length];

                for (int j = 0; j < tempKoef.length; j++) {
//                    System.out.print("000 " + regLevel + " | " + tempAvarage[j]);
                    tempKoef[j] = regLevel / tempAvarage[j];

                }

                // Rewrite seismic data

                for (int j = 0; j < tempKoef.length; j++) {

//                    System.out.print(tempKoef[j] + " | " + tempTrimDataArray[j]);
                    getMainController().getSegyTempTracesDataAfterProcessing().get(tempTraceNumber).getData()[j] = tempTrimDataArray[j] * tempKoef[j];
                }


//                Output in console for debug
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

        } catch (Exception e) {e.printStackTrace();}
    }

    private void processingDataUpperOfPickingRandom() {
//        System.out.println("Saving");
        for (int i = 0; i < mainGui.getSettings_singl().getFullTrimShifted().size();
             i++) {


            int tempDatasetValue = mainGui.getSettings_singl().getFullTrimShifted().get(i).getDatasetValue();
            int tempReelValue = mainGui.getSettings_singl().getFullTrimShifted().get(i).getReelNumber();
            int currentTraceNumberFromLaw = tempDatasetValue + tempReelValue *(48+mainGui.getSettings_singl().getCfgCurrentFileAddTraceNumber());

//            System.out.println("Trace number comparison");
//            System.out.println(currentTraceNumberFromLaw); // Begins from 0
//            System.out.println(getMainController().getSegyTempTraces().get(currentTraceNumberFromLaw).getTraceSequenceNumberWithinSegyFile()); //Begins grom 1

            float sum = 0;
            for (int j = 0; j < 1000; j++) { //Calculate average for first 1000 samples
                sum = sum + Math.abs(getMainController().getSegyTempTracesDataAfterProcessing().get(currentTraceNumberFromLaw).getData()[j]);
            }

            float traceCorrKoef;
            traceCorrKoef = sum/1000; //Average

            traceCorrKoef = traceCorrKoef/100; //Divide average by 100 - level to random

            Random generator = new Random();

            for (int j = 0; j < mainGui.getSettings_singl().getFullTrimShifted().get(i).getSampleValue() ; j++) {

                float fTemp = 0;
                int sign = generator.nextInt(2);


                    fTemp = traceCorrKoef * generator.nextFloat();


                if (sign ==0 ) {
                    getMainController().getSegyTempTracesDataAfterProcessing().get(currentTraceNumberFromLaw).getData()[j] = fTemp;
                }
                else if (sign == 1) getMainController().getSegyTempTracesDataAfterProcessing().get(currentTraceNumberFromLaw).getData()[j] = fTemp* -1;

            }

//            System.out.println("Processing info");
//            System.out.println(currentTraceNumberFromLaw);
//            System.out.println(traceCorrKoef);
            int sign = generator.nextInt(2);
//            System.out.println(sign);
//            System.out.println("Processing info /End");


//            final int balancedAmpl = 1;
//
//            System.out.println("segyTempTracesDataForDisplaying ->" + segyTempTracesDataForDisplaying.length);
//
//                int  tempSampleNumber = 1000;
//
//
//                //Calculating summary of array from 0 to trimShifted point and deviding to sample number (average value)
//                float regLevel = balancedAmpl; //regulation level
//                float sum = 0;
//                for (int j = 0; j < tempSampleNumber; j++) {
//                    sum = sum + Math.abs(segyTempTracesDataForDisplaying[i].getData()[j]);
//                }
//
////            System.out.println("sum ->>> "+ i+ " ---- " +sum);
//
//                float averageSum = sum / tempSampleNumber;
//
////            System.out.println("averageSum ->>> "+ i+ " ---- " +averageSum);
//
//                currentBalancingCorrKoef[i] = regLevel/averageSum;
//
//            System.out.println("averageSum ->>> "+ i+ " ---- " +averageSum);
//





        }
    }

    public void activateReelSpinner() {
        {//Add spinner programmatically
//            System.out.println("spinner");
        }
        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, mainGui.getSettings_singl().getCfgFilesNumber(), 1);
        reelsp.setModel(model1);
        rsEditor = ( JSpinner.DefaultEditor ) reelsp.getEditor();
        rsEditor.getTextField().setEditable(false);


        reelsp.setVisible(true);
        seqHelperTFToolTip.setVisible(true);
        seqHelperTF.setVisible(true);
        if (reelsp.getChangeListeners().length <2) reelsp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) { //Logic for mute law with multireel tape

//                System.out.println("reelsp.getChangeListeners().length ->" + reelsp.getChangeListeners().length);

                if (isPickingMode) {
//                    System.out.println("getCfgTrimLawDescrBegs value " + (int) reelsp.getValue() +" : "+settings_singl.getCfgTrimLawDescrBegs()[(int) reelsp.getValue()-1]);
//                    System.out.println("getCfgTrimLawDescrBegs ss " + settings_singl.getCfgCurrentFileSeqNumber() +" : "  +settings_singl.getCfgTrimLawDescrBegs()[settings_singl.getCfgCurrentFileSeqNumber()]);
//                    System.out.println("getCfgTrimLawDescrEnds valur " + (int) reelsp.getValue() +" : " +settings_singl.getCfgTrimLawDescrEnds()[(int) reelsp.getValue()-1]);
//                    System.out.println("getCfgTrimLawDescrEnds ss " + settings_singl.getCfgCurrentFileSeqNumber() +" : " +settings_singl.getCfgTrimLawDescrEnds()[settings_singl.getCfgCurrentFileSeqNumber()]);

                    if (settings_singl.getCfgTrimLawDescrBegs()[(int)  settings_singl.getCfgCurrentFileSeqNumber()] != -1 &&
                        settings_singl.getCfgTrimLawDescrEnds()[(int)  settings_singl.getCfgCurrentFileSeqNumber()] == -1 &&
                        settings_singl.getCfgCurrentFileSeqNumber()+1 != (int) reelsp.getValue()) {

                        JFrame pickingGUIJFrame = new JFrame("Save picking?");
                        isPickingSave isPickingSave = new isPickingSave(mainJFrame); //Test
                        pickingGUIJFrame.setContentPane(isPickingSave.contentPane);
                        pickingGUIJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        pickingGUIJFrame.pack();
                        pickingGUIJFrame.setResizable(false);
                        pickingGUIJFrame.setLocation(mainJFrame.getLocationOnScreen().x + mainJFrame.getWidth() / 2 - pickingGUIJFrame.getWidth() / 2,
                                mainJFrame.getLocationOnScreen().y + mainJFrame.getHeight() / 2 - pickingGUIJFrame.getHeight() / 2);
                        pickingGUIJFrame.setVisible(true);
//                        System.out.println("Spinner value -" + reelsp.getValue());
//                        System.out.println("Spinner value -" + reelsp);
//                        System.out.println("Spinner value -" + reelsp.getModel().toString());
                        myDrawingGlassPane.reloadTrimLaw();
                    }
                    else {
                        settings_singl.setCfgCurrentFileSeqNumber((int) reelsp.getValue() - 1);
//                        System.out.println("CURRENT_FILE_SEQ_NUMBER -> " + settings_singl.getCfgCurrentFileSeqNumber());
                        seqHelperTF.setText(reelsp.getValue().toString() + " of " + Integer.toString(settings_singl.getCfgFilesNumber()));
                        redrawCharts();
                        myDrawingGlassPane.checkPickingStatus();}
                } else {
                    myDrawingGlassPane.reloadTrimLaw();
                    settings_singl.setCfgCurrentFileSeqNumber((int) reelsp.getValue() - 1);
//                    System.out.println("CURRENT_FILE_SEQ_NUMBER -> " + settings_singl.getCfgCurrentFileSeqNumber());
                    seqHelperTF.setText(reelsp.getValue().toString() + " of " + Integer.toString(settings_singl.getCfgFilesNumber()));
                    myDrawingGlassPane.checkAndAddLawEnd();
                    redrawCharts();
                }


            }
        });
//        System.out.println("spinner 2 --> " +reelsp.getSize());
//        System.out.println(bUp.getSize());
//        System.out.println(bDown.getSize());
//        System.out.println(spinnerPanel.getSize());
//        System.out.println(rsEditor.getSize());


//        try {
//            spinnerPanel.add(reelsp, new GridConstraints(0,
//                    0,
//                    1,
//                    1,
//                    0,
//                    0,
//                    GridConstraints.SIZEPOLICY_WANT_GROW,
//                    GridConstraints.SIZEPOLICY_WANT_GROW,
//                    new Dimension(53, 53),
//                    new Dimension(53, 53),
//                    new Dimension(53, 53)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println("spinner --> " +reelsp.getSize());


    }

    public void deactivateReelSpinner() {
        reelsp.setVisible(false);
        seqHelperTFToolTip.setVisible(false);
        seqHelperTF.setVisible(false);
    }

    public int calculateTraceLengthInBytes() {


        return 0;
    }

    public void pickingModeSpinActionOk() {
        myDrawingGlassPane.checkAndAddLawEnd();
        settings_singl.setCfgCurrentFileSeqNumber((int) reelsp.getValue() - 1);
        myDrawingGlassPane.reloadTrimLaw();
        getMyDrawingGlassPane().checkPickingStatus();
        seqHelperTF.setText(reelsp.getValue().toString() + " of " + Integer.toString(settings_singl.getCfgFilesNumber()));
        redrawCharts();

//        System.out.println();
//        for (int i = 0; i < getSettings_singl().getCfgTrimLawDescrBegs().length; i++) {
//            System.out.print(getSettings_singl().getCfgTrimLawDescrBegs()[i]+ " ");
//        }
//        System.out.println();
//        for (int i = 0; i < getSettings_singl().getCfgTrimLawDescrEnds().length; i++) {
//            System.out.print(getSettings_singl().getCfgTrimLawDescrEnds()[i]+ " ");
//        }
//        System.out.println();
    }

    public void pickingModeSpinActionCancel() {
        myDrawingGlassPane.reloadTrimLaw();
        getSettings_singl().deleteTrimLawSpec(getSettings_singl().getCfgCurrentFileSeqNumber());
        getMyDrawingGlassPane().zeroedMuteLaw();
        getMyDrawingGlassPane().checkPickingStatus();
        reelsp.setValue(settings_singl.getCfgCurrentFileSeqNumber()+1);
        seqHelperTF.setText(reelsp.getValue().toString() + " of " + Integer.toString(settings_singl.getCfgFilesNumber()));
        getSettings_singl().getCfgTrimLawDescrBegs()[getSettings_singl().getCfgCurrentFileSeqNumber()] = -1;
        getSettings_singl().getCfgTrimLawDescrEnds()[getSettings_singl().getCfgCurrentFileSeqNumber()] = -1;
        redrawCharts(); //TODO No need change, redraw glass pane
    }


}

