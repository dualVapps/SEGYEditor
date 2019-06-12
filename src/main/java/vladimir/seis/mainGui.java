package main.java.vladimir.seis;

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
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import main.java.vladimir.seis.segystream.SegyConfig;
import main.java.vladimir.seis.segystream.SegyFlow;
import main.java.vladimir.seis.segystream.SegyHeaders;
import main.java.vladimir.seis.segystream.SegyPart;
import org.jfree.chart.ChartPanel;
import scala.concurrent.Future;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.CompletionStage;

public class mainGui
{
    //TODO make unactive if settings Change

//    private final ChartPanel chartPanel1;
    public  JPanel mainJPanel;
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
    JFileChooser fc;
    static private File directory;
    static private File savePath;
    private File[] choosenFiles;
    private int choosenIndex;
    private ChartExecutor chartExecutor;
    private ChartPanel[] chartPanel;
    static private Settings_singleton settings_singl;
    static private MyDrawingGlassPane myDrawingGlassPane;

    static public  mainController mainController;

    private boolean isPickingMode = false;

    public static void main(String[] args) { //TODO Delete log and sout tests, add fool protection

        JFrame mainJFrame = new JFrame("mainGui");
//        shooseFileButton.setIc;
//        showFileTxtButton;
//        showFileBinButton;
//        showTraceBinButton;

        mainJFrame.setContentPane(new mainGui().mainJPanel);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myDrawingGlassPane = new MyDrawingGlassPane();
        mainJFrame.setGlassPane(myDrawingGlassPane);
        mainJFrame.pack();
        mainJFrame.setVisible(true);
        mainController = new mainController();
        JFrame settingsJFrame = new JFrame("settings");
        Settings settings = new Settings();
        settingsJFrame.setContentPane(settings.settingsPanel);
        settingsJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        settingsJFrame.pack();
        settingsJFrame.setVisible(true);
        settings_singl = new Settings_singleton().getSettings_singleton();



//        setupMainController();
//        initializeFrames();
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

    public mainGui()
    {

        makeButtonsUnactive();
        //        getMainJPanel().addMouseListener(this);

        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        shooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.

                int returnVal = fc.showOpenDialog(tempPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    directory = fc.getSelectedFile();
                    choosenFiles = directory.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.toLowerCase().endsWith(".sgy");}});

                    if (choosenFiles.length != 0) {

                        for (File file : choosenFiles) {
                            if (file.isFile()) {
                                System.out.println(file.getName());
                            } else
                                System.out.println("not a file......");
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
                                if (!event.getValueIsAdjusting()) {
                                    JList source = (JList) event.getSource();
                                    choosenIndex = source.getSelectedIndex();
                                    startReading();
//                                tempPanel.removeAll();  //TODO Refresh if new file ñhoosen
//                                tempPanel.updateUI();
                                }


                            }
                        });

                        filesList.setListData(choosenFileNames);
                    }

                    else {
                        JOptionPane.showMessageDialog(mainJPanel,
                                "Ôàéëû sgy íå íàéäåíû",
                                "Âíèìàíèå",
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

            private void startReading() {
                final ActorSystem system = ActorSystem.create("111");
                final Materializer materializer = ActorMaterializer.create(system);

                // Construct stream source from file
                Source<ByteString, CompletionStage<IOResult>> fileSource = FileIO.fromFile(choosenFiles[choosenIndex]);

                // Configure and declare the stream    //Previous version
//                SegyConfig config = new SegyConfig(
//                        Charset.forName("CP037"), //textual data charset
//                        8192 //data chunk size, bytes
//                );

                SegyConfig config = new SegyConfig(
                        Charset.forName("CP037"), //textual data charset
                        settings_singl.getSample_sizeInBytes()  //data chunk size, bytes
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


                //TODO Something weard with throwing other threads errors;

//                makeButtonsActive();
//                System.out.println("11111111111111111111111");
//                reDrawChartsWithRenevalData();
//                System.out.println("22222222222222222222222222");


            }
        });
        showFileTxtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame fileTxtJFrame = new JFrame("fileTxtHeader");
                fileTxtHeader fileTxtHeader = new fileTxtHeader();
                fileTxtJFrame.setContentPane(fileTxtHeader.fileTxtJPanel);
                fileTxtJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );
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
                fileBinJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );
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
                traceBinJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE );


//                System.out.println("mainController.getSegyTempTraces(0).toString(): "+mainController.getSegyTempTraces(0).toString());
//                System.out.println("mainController.getSegyTempTraces(0).toString(): "+mainController.getSegyTempTraces(0).getTraceSequenceNumberWithinLine().toString());

                traceBinHeader.setBinHeaderTxtFields(mainController.getSegyTempTraces(0));
                traceBinJFrame.pack();
                traceBinJFrame.setVisible(true);
            }
        });
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                savePath = new File(directory.getAbsolutePath()+"/Edited");
                savePath.mkdir();
//                System.out.println(choosenFiles[choosenIndex].getPath());
//                System.out.println(choosenFiles[choosenIndex].getName());
//                System.out.println(choosenFiles[choosenIndex].getAbsolutePath());

                makeProccesing();

                FileOutputStream fos=null;
                try {
                    fos = new FileOutputStream(savePath.getAbsolutePath()+"/"+choosenFiles[choosenIndex].getName());

                    if (fos != null)  {
                        DataOutputStream dos = new DataOutputStream(fos);
                        mainController.segyTempFile.writeToDataOutputStream(dos);

                        for (int i = 0; i < 216; i++) { //TODO change to variable
//                            System.out.println(" *** mainController.segyTempTraces[i].getTraceSequenceNumberWithinLine() " + mainController.segyTempTraces[i].getTraceSequenceNumberWithinLine());
                            mainController.segyTempTraces[i].writeToDataOutputStream(dos);
                            mainController.segyTempTracesData[i].writeToDataOutputStream(dos,settings_singl.getSample_sizeInBytes());
//                            System.out.println("mainController.segyTempTraces.length  " + mainController.segyTempTraces.length);
//                            System.out.println("mainController.segyTempTracesData.length  " + mainController.segyTempTracesData.length);
                        }

                        dos.flush();
                        dos.close();

                    }

                    if (fos != null)  {
                        fos.flush();
                        fos.close();
                    }


                }
                catch (Exception exc1) {
                    exc1.printStackTrace();
                }


                try {
                    BasicFileAttributes attr = Files.readAttributes(Paths.get(choosenFiles[choosenIndex].getPath()), BasicFileAttributes.class);
//                    System.out.println(attr.creationTime().toString());
//                    System.out.println(attr.lastAccessTime().toString());
//                    System.out.println(attr.lastModifiedTime().toString());


                    Path savedPath = Paths.get(savePath.getAbsolutePath()+"/"+choosenFiles[choosenIndex].getName());


                    Files.setAttribute(savedPath,"creationTime",attr.creationTime());
                    Files.setAttribute(savedPath,"lastModifiedTime",attr.lastModifiedTime());

                    BasicFileAttributes attr1 = Files.readAttributes(savedPath, BasicFileAttributes.class);

//                    Files.setLastModifiedTime(savedPath, attr.lastModifiedTime());
//                    Files.set(savedPath, attr.lastModifiedTime());
//                    Files.setLastModifiedTime(savedPath, attr.lastModifiedTime());

//                    System.out.println(attr1.creationTime().toString());
//                    System.out.println(attr1.lastAccessTime().toString());
//                    System.out.println(attr1.lastModifiedTime().toString());


                }
                catch (IOException ioe) {ioe.printStackTrace();}

//                Path file = ...;
//                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
//
//                System.out.println("creationTime: " + attr.creationTime());
//                System.out.println("lastAccessTime: " + attr.lastAccessTime());
//                System.out.println("lastModifiedTime: " + attr.lastModifiedTime());
//



//                    for (int j = 0; j < 6; j++) {
//                        System.out.println("************ Trace# "+ 1 +" ****** Sample# "+(j+1) +"  : " + mainController.segyTempTracesData[0].data[j]);
//                    }
//
//                for (int j = 2020; j < 2048; j++) {
//                    System.out.println("************ Trace# "+ 1 +" ****** Sample# "+(j+1) +"  : " + mainController.segyTempTracesData[0].data[j]);
//                }


            }
        });
//        pickingButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                for (int i = 0; i < 2048; i++) {
//
//
////                System.out.println("**************** Trace 0 *** Sample#: "+i+" : " +mainController.segyTempTracesData[0].getData()[i]);
//                }
//            }
//        });
        pickingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPickingMode) {
                    pickingButton.setBorder(BorderFactory.createLoweredBevelBorder());
                    myDrawingGlassPane.setVisible(true);
                }

                else
                {
                    pickingButton.setBorder(null);
                    myDrawingGlassPane.setVisible(false);
                }
            }
        });

        initChartPanels();

        settingsBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame settingsJFrame = new JFrame("settings");
                Settings settings = new Settings();
                settingsJFrame.setContentPane(settings.settingsPanel);
                settingsJFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                settingsJFrame.pack();
                settingsJFrame.setVisible(true);



            }
        });

        scaleUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double temp = chartExecutor.getScaleFactor();
                chartExecutor.setScaleFactor(0.75*temp);
                chartExecutor.setSameScale();
                System.out.println("Scale" + temp);
                tempPanel.revalidate();
                tempPanel.repaint();

            }
        });

        scaleDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double temp = chartExecutor.getScaleFactor();
                chartExecutor.setScaleFactor(1.5*temp);
                chartExecutor.setSameScale();
                tempPanel.revalidate();
                tempPanel.repaint();

            }
        });

        scaleZero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chartExecutor.setScaleFactor(0.5);
                chartExecutor.setSameScale();
                tempPanel.revalidate();
                tempPanel.repaint();
            }
        });
    }
    private void makeProccesing() { //TODO Changing in SEGY FIle

//        for (int i = 0; i < 54; i++) {
//            mainController.segyTempTraces[i].setAuxChanType((byte)0x01);
//        }

        processingTraceHeader();




    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void initChartPanels() {

        chartExecutor = new ChartExecutor("Line Chart Demo");
        chartPanel = chartExecutor.getChartPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.X_AXIS));
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
        System.out.println("Add charts");
        System.out.println(tempPanel.toString());
//        tempPanel.setVisible(false);

//        pickingJLF.revalidate();
//        pickingJLF.repaint();

//        pickingJLF.
            //Code for adding crosshair
//            CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
//            chartPanel[1].addOverlay(crosshairOverlay.addDomainCrosshair(new Crosshair()));


//        }



    }

    // Changing traces with 4 secmic records
    private void processingTraceHeader() {
        for (int i = 0; i < mainController.getSegyTempTracesData()[2].getData().length; i++) {
            mainController.getSegyTempTracesData()[2].getData()[i] = (mainController.getSegyTempTracesData()[3].getData()[i] +
                    mainController.getSegyTempTracesData()[4].getData()[i])/2;

        }

        for (int i = 0; i < mainController.getSegyTempTracesData()[56].getData().length; i++) {
            mainController.getSegyTempTracesData()[56].getData()[i] = (mainController.getSegyTempTracesData()[57].getData()[i] +
                    mainController.getSegyTempTracesData()[58].getData()[i])/2;
        }

        for (int i = 0; i < mainController.getSegyTempTracesData()[110].getData().length; i++) {
            mainController.getSegyTempTracesData()[110].getData()[i] = (mainController.getSegyTempTracesData()[111].getData()[i] +
                    mainController.getSegyTempTracesData()[112].getData()[i])/2;
        }

        for (int i = 0; i < mainController.getSegyTempTracesData()[164].getData().length; i++) {
            mainController.getSegyTempTracesData()[164].getData()[i] = (mainController.getSegyTempTracesData()[165].getData()[i] +
                    mainController.getSegyTempTracesData()[166].getData()[i])/2;


        }
    }

    private static void initializeFrames()
    {
        //


    }

     void  reDrawChartsWithRenevalData(){

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
        System.out.println("******************************************************");
        System.out.println("******************************************************" + chartExecutor.toString());
        for (int j = 0; j < 54; j++) {
            chartExecutor.updateWithDataset(j);

        }

        chartExecutor.setSameScale();   // TODO Write javadoc

//                  for (int j = 0; j < chartPanel.length; j++) {
////                    tempPanel.add(chartPanel[j], BorderLayout.LINE_END);
//                    tempPanel.add(chartPanel[j]);
//                    System.out.println("Add chart: " + j);
//                }
        tempPanel.revalidate();
        tempPanel.repaint();
    }

    void onFinishedreading(){
//        system::terminate;
        reDrawChartsWithRenevalData();
        makeButtonsActive();
//        done.thenRunAsync(() -> onFinishedreading()
    }


    private void makeButtonsUnactive() {
          showFileTxtButton.setEnabled(false);
          showFileBinButton.setEnabled(false);
          showTraceBinButton.setEnabled(false);
          pickingButton.setEnabled(false);
          saveFileButton.setEnabled(false);
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
        showFileTxtButton.setEnabled(true);
        showFileBinButton.setEnabled(true);
        showTraceBinButton.setEnabled(true);
        pickingButton.setEnabled(true);
        saveFileButton.setEnabled(true);
        settingsBotton.setEnabled(true);
        scaleUp.setEnabled(true);
        scaleDown.setEnabled(true);
        scaleZero.setEnabled(true);

    }
    private void makeButtonsUnactiveExcPicking() {
          showFileTxtButton.setEnabled(false);
          showFileBinButton.setEnabled(false);
          showTraceBinButton.setEnabled(true);
//          pickingButton.setEnabled(false);
          saveFileButton.setEnabled(false);
          settingsBotton.setEnabled(false);
          scaleUp.setEnabled(false);
          scaleDown.setEnabled(false);
          scaleZero.setEnabled(false);
    }

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
}
