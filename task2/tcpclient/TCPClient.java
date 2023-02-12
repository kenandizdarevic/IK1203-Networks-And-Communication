package tcpclient;
import java.net.*;

import javax.naming.ldap.SortControl;

import java.io.*;



public class TCPClient {
    public boolean shutdown; 
    public Integer timeout;
    public Integer limit;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        this.shutdown = shutdown;
        this.timeout = timeout;
        this.limit = limit;
    }

    private static int BUFFERSIZE = 1024;

    /**
     * Establishes a connection to a server, sends & recieves data
     * @param hostname server to establish a connection with
     * @param port which port to use for connection
     * @param toServerBytes byte[] to send to the server
     * @return byte[] with data recieved from server
     * @throws IOException
     */
    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException, SocketTimeoutException {
        
        byte[] fromServerBuffer = new byte[BUFFERSIZE];
        ByteArrayOutputStream dataFromServer = new ByteArrayOutputStream();      
        
        try (Socket clientSocket = new Socket(hostname, port)) {
            clientSocket.getOutputStream().write(toServerBytes);
            if (shutdown) {
                clientSocket.shutdownOutput();
            }

            if (timeout != null) {
                clientSocket.setSoTimeout(timeout);
            }

            InputStream inputStreamFromServer = clientSocket.getInputStream();
            int data = 0;
            int bytesRecieved = 0;
            while (((data = inputStreamFromServer.read(fromServerBuffer)) != -1) && (limit == null || bytesRecieved + data <= limit)) {
                dataFromServer.write(fromServerBuffer, 0, data);
                fromServerBuffer = new byte[BUFFERSIZE];
                bytesRecieved += data;
            }
            clientSocket.close();
        }
        catch (SocketTimeoutException exception) {
            return dataFromServer.toByteArray();
        }
        catch (IOException exception) {
            throw new IOException("Something went wrong: ", exception);
        }
        return dataFromServer.toByteArray();
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

/*
 * Shutdown: Om shutdown = true, clientSocket.shutdownOutput(), ske efter jag har skickat data på socket
 * Timeout: Om jag inte tar emot efter x sekunder, skriv ut error och returnera array
 * Limit: Får ej ta emot mer än limit bytes, måste vara prick limit
 */