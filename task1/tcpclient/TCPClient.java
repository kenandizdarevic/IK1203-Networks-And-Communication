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
        
            // Pre-allocate byte buffer
            byte[] fromServerBuffer = new byte[BUFFERSIZE];
            
            int data;

            // Open a connection to "hostname" at port with Socket()
            Socket clientSocket = new Socket(hostname, port);

            // Send bytes via socket to server
            clientSocket.getOutputStream().write(toServerBytes);

            /* Set up ByteArrayOutputStream */
            ByteArrayOutputStream dataFromServer = new ByteArrayOutputStream();
        
        try(InputStream inputStreamFromServer = clientSocket.getInputStream()) {
            while ((data = inputStreamFromServer.read(fromServerBuffer)) != -1) {
                dataFromServer.write(fromServerBuffer);
                fromServerBuffer = new byte[BUFFERSIZE];
            }
            clientSocket.close();
            return dataFromServer.toByteArray();
        }
        catch (IOException exception) {
            throw new IOException("Something went wrong: ", exception);
        }
    }

    public byte[] askServer(String hostname, int port) throws IOException {
        return askServer(hostname, port, null);
    }
}
