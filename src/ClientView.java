import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by rlukas on 26.08.2015.
 */
public class ClientView extends JFrame implements ActionListener {
    private JPanel rootPanel;
    private JPanel serverAddressPanel;
    private JButton connectButton;
    private JTextField textFieldServer;
    private JButton matrix1Button;
    private JButton matrix2Button;
    private JButton matrix3Button;
    private JButton matrix4Button;
    private JButton matrix5Button;
    private JPanel matrixButtonPanel;
    private JPanel matrixOutputPanel;
    private JPanel panelStatus;
    private JLabel statusTextField;
    private JLabel index11;
    private JLabel index12;
    private JLabel index13;
    private JLabel index14;
    private JLabel index15;
    private JLabel index21;
    private JLabel index22;
    private JLabel index23;
    private JLabel index24;
    private JLabel index25;
    private JLabel index31;
    private JLabel index32;
    private JLabel index33;
    private JLabel index34;
    private JLabel index35;
    private JLabel index41;
    private JLabel index42;
    private JLabel index43;
    private JLabel index44;
    private JLabel index45;
    private JLabel index51;
    private JLabel index52;
    private JLabel index53;
    private JLabel index54;
    private JLabel index55;

    private String matrixChoice = null;
    public ClientConnect clientConnect;
    public List<String> matrixResult = new ArrayList<>();

    public JLabel[] labelIndexArray = {index11, index12, index13, index14, index15,
            index21, index22, index23, index24, index25,
            index31, index32, index33, index34, index35,
            index41, index42, index43, index44, index45,
            index51, index52, index53, index54, index55};

    public ClientView() {
        super("MatrixClient");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addAllActionButtonToListener();
        setVisible(true);
    }

    public void addAllActionButtonToListener(){
        connectButton.addActionListener(this);
        matrix1Button.addActionListener(this);
        matrix2Button.addActionListener(this);
        matrix3Button.addActionListener(this);
        matrix4Button.addActionListener(this);
        matrix5Button.addActionListener(this);
    }

    public void actionPerformed (ActionEvent ae) {
        if(ae.getSource() == this.connectButton) {
            atConnect();
            clientConnect = new ClientConnect();
            clientConnect.ipAddress = textFieldServer.getText();
            if (clientConnect.isIpAddressUsable()){
                statusConnected();
            } else {
                statusIPAddressError();
            }

            if (ae.getSource() == this.matrix1Button) {
                matrixChoice = "matrix1";
                setClientConnect();
                matrixFromServer();
            } else if (ae.getSource() == this.matrix2Button) {
                matrixChoice = "matrix2";
                setClientConnect();
                matrixFromServer();
            } else if (ae.getSource() == this.matrix3Button) {
                matrixChoice = "matrix3";
                setClientConnect();
                matrixFromServer();
            } else if (ae.getSource() == this.matrix4Button) {
                matrixChoice = "matrix4";
                setClientConnect();
                matrixFromServer();
            } else if (ae.getSource() == this.matrix5Button) {
                matrixChoice = "matrix5";
                setClientConnect();
                matrixFromServer();
            }
        }
    }

    public void setClientConnect(){
        clientConnect.setUserMatrixRequest(matrixChoice);
    }

    public void atConnect(){
        String valueServerAddress = textFieldServer.getText();
        statusTextField.setForeground(Color.decode("#EFBF00"));
        statusTextField.setText("Connect: " + valueServerAddress);
    }

    public void statusConnected(){
        String colorDecodeGreen = "#38A100";
        statusTextField.setForeground(Color.decode(colorDecodeGreen));
        statusTextField.setText("Connected");
    }

    public void statusDisconnect(){
        statusError("Disconnect");
    }

    public void statusConnectProblem(){
        statusError("Connect Error, try Again");
    }

    public void statusIPAddressError(){
        statusError("Connect Error, IP address is wrong! try Again");
    }
    public void statusError(String statusMessage){
        String colorDecodeRed = "#DF0101";
        statusTextField.setForeground(Color.decode(colorDecodeRed));
        statusTextField.setText(statusMessage);
    }

    public void matrixFromServer() {
        try {
            matrixResult = clientConnect.catchMatrixFromServer();
            deployMatrixPanel();
        } catch (IOException e){
            statusConnectProblem();
            statusDisconnect();
        }
    }

    public void deployMatrixPanel(){
        setMatrixTableIndex();
        matrixDisplayedGraphically();

    }

    public void setMatrixTableIndex(){
        int indexCounter = 11;
        int endOfMatrixTable = 16;
        int newMatrixTablesBegin = 21;
        int nextColumn = 10;
        for (int index = 0; index < labelIndexArray.length; index++){
            labelIndexArray[index].setName(String.valueOf(indexCounter));
            indexCounter++;
            if (indexCounter == endOfMatrixTable){
                indexCounter = newMatrixTablesBegin;
                endOfMatrixTable += nextColumn;
                newMatrixTablesBegin += nextColumn;
            }
        }
    }

    public void matrixDisplayedGraphically(){
        String usedMatrixSpace = "X";
        String unusedMatrixSpace = "O";
        for (int index = 0; index < labelIndexArray.length; index++){
            String indexFromMatrixPanel = labelIndexArray[index].getName();
            if (isSameIndexMatrixResultMatrixPanel(indexFromMatrixPanel)){
                setLabelText(labelIndexArray[index], usedMatrixSpace);
            } else {
                setLabelText(labelIndexArray[index], unusedMatrixSpace);
            }
        }
    }

    public boolean isSameIndexMatrixResultMatrixPanel(String indexFromMatrixPanel){
        for (int index = 0; index < matrixResult.size(); index++){
            if (matrixResult.get(index) != null && matrixResult.get(index).equals(indexFromMatrixPanel)){
                return true;
            }
        }
        return false;
    }

    public void setLabelText(JLabel label, String text){
        label.setText(text);
    }
}

