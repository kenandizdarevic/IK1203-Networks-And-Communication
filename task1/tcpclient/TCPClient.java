package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public TCPClient() {
    }

    // Used to set up buffer with 1024 bytes
    private static int BUFFERSIZE = 1024;

    /**
     * Establishes a connection to a server, sends & recieves data
     * @param hostname server to establish a connection with
     * @param port which port to use for connection
     * @param toServerBytes byte[] to send to the server
     * @return byte[] with data recieved from server
     * @throws IOException
     */
    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        
            byte[] fromServerBuffer = new byte[BUFFERSIZE];
            int data;

            Socket clientSocket = new Socket(hostname, port);
            clientSocket.getOutputStream().write(toServerBytes);

            ByteArrayOutputStream dataFromServer = new ByteArrayOutputStream();
        
        try(InputStream inputStreamFromServer = clientSocket.getInputStream()) {
            while ((data = inputStreamFromServer.read(fromServerBuffer)) != -1) {
                dataFromServer.write(fromServerBuffer, 0, data);
                fromServerBuffer = new byte[BUFFERSIZE];
            }
            clientSocket.close();
            return dataFromServer.toByteArray();
        }
        catch (IOException exception) {
            throw new IOException("Something went wrong: ", exception);
        }
    }
    
   /**
    * Helper function for askServer()
    * @param hostname server to establish a connection with
    * @param port which port to use for connection
    * @return byte[] with data recieved from server
    * @throws IOException
    */
    public byte[] askServer(String hostname, int port) throws IOException {
        return askServer(hostname, port, null);
    }
}