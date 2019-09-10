package vladimir.seis;

import javax.swing.*;

public class fileTxtHeader { //TODO find appropriate size
    public JPanel fileTxtJPanel;
    private JTextArea textArea1;

    public void setTxtHeader(String fileTxtHeader3200) {
        String splittedTxt="";
        for (int i = 0; i < 40; i++) {
            splittedTxt = new String(splittedTxt+fileTxtHeader3200.substring(i*80,(i+1)*80)+"\n");//TODO replace with String Builder

        }
        textArea1.setText(splittedTxt);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
