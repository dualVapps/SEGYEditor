package vladimir.seis;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class muteLawTable {
    public JPanel muteLawTableMainPanel;
    private JScrollPane muteLawTableScrollPanel;
    private JTable mainJtable;

    public muteLawTable() {



        TableModel tableModel = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return mainGui.getSettings_singl().getTrimLaw().size();

            }

            @Override
            public int getColumnCount() {
                return 5;
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "¹";

                    case 1:
                        return "Reel";

                    case 2:
                        return "Trace";

                    case 3:
                        return "Sample";

                    case 4:
                        return "Time";

                    default:
                        return "Add+";

                }
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return rowIndex+1;
                    case 1:
                        return mainGui.getSettings_singl().getTrimLaw().get(rowIndex).getReelNumber()+1; //Change to reel? if there sre few
                    case 2:
                        return mainGui.getSettings_singl().getTrimLaw().get(rowIndex).getDatasetValue();
                    case 3:
                        return mainGui.getSettings_singl().getTrimLaw().get(rowIndex).getSampleValue();
                    case 4:
                        float tempTime = mainGui.getSettings_singl().getTrimLaw().get(rowIndex).getSampleValue()*mainGui.getMainController().getSegyTempFile().getSampleIntervalMicroSecOrig();
                        tempTime = tempTime/1000000;
                        return tempTime; //in sec
                    default:
                        return "error";
                }

            }
        };


        mainJtable = new JTable(tableModel);
//        System.out.println("main table created" + mainJtable.getClass().getSimpleName());
//        muteLawTableScrollPanel.setLayout(new ScrollPaneLayout());

        muteLawTableScrollPanel.getViewport().add(mainJtable);
//        muteLawTableScrollPanel.add(mainJtable);
//        System.out.println("main table add. Rows - " + tableModel.getRowCount());

//        System.out.println("Begs:");
//        for (int i = 0; i < mainGui.getSettings_singl().getCfgTrimLawDescrBegs().length; i++) {
//
//            System.out.print("" + mainGui.getSettings_singl().getCfgTrimLawDescrBegs()[i] + " ");
//        }
//        System.out.println("ENDs:");
//        for (int i = 0; i < mainGui.getSettings_singl().getCfgTrimLawDescrEnds().length; i++) {
//
//            System.out.print("" + mainGui.getSettings_singl().getCfgTrimLawDescrEnds()[i] + " ");
//        }



    }
}
