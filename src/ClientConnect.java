import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rlukas on 27.08.2015.
 */
public class ClientConnect {
    public final  int portNumber = 8090;
    public String ipAddress = "";
    public  String userMatrixRequest = "";
    public List<String> matrixDataFromServer = new ArrayList<>();
    public boolean isIpAddressCorrect = false;

    public List<String> catchMatrixFromServer() throws IOException {
        Socket clientSocket = setClientSocket();
        DataOutputStream outToServer = outputStreamToServer(clientSocket);
        BufferedReader inFormServer = inputStreamFromServer(clientSocket);
        outputStringToServer(outToServer, userMatrixRequest);
        readInMatrix(inFormServer);
        clientSocket.close();
        return giveMatrixFromServer();
    }

    public  void setUserMatrixRequest(String matrixChoice){
        userMatrixRequest = matrixChoice;
    }

    public  Socket setClientSocket() {
        Socket socket;
        try {
            socket = new Socket(ipAddress, portNumber);
            isIpAddressCorrect = true;
            return socket;
        } catch (IOException connectError) {
            isIpAddressCorrect = false;
        }
        return null;
    }

    public boolean isIpAddressUsable(){
        setClientSocket();
        return isIpAddressCorrect;
    }

    public  DataOutputStream outputStreamToServer(Socket clientSocket) throws IOException {
        return new DataOutputStream(clientSocket.getOutputStream());
    }

    public  BufferedReader inputStreamFromServer(Socket clientSocket) throws IOException {
        BufferedReader inFormServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return inFormServer;
    }

    public  void outputStringToServer(DataOutputStream outputStream, String outputString) throws IOException {
        outputStream.writeBytes(outputString + '\n');
    }

    public List<String> readInMatrix(BufferedReader inFormServer) throws IOException {
        int endOfMatrix = 6;
        for (int index = 0; index < endOfMatrix; ++index) {
            String inputStringFromServer = inFormServer.readLine();
            if (inputStringFromServer != null && inputStringFromServer.equals("used")){
                matrixDataFromServer.add(inFormServer.readLine().toString());
            } else if (inputStringFromServer.equals("end")){
                index = endOfMatrix;
            }
        }
        return matrixDataFromServer;
    }

    public List<String> giveMatrixFromServer(){
        return matrixDataFromServer;
    }

}
