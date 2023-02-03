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
     * @param toServerBytes bytes to send to the server
     * @return
     * @throws IOException
     */
    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        
        // Pre-allocate byte buffer
        byte[] fromServerBuffer = new byte[BUFFERSIZE];
        
        // Open a connection to "hostname" at port with Socket()
        Socket clientSocket = new Socket(hostname, port);

        // Send bytes via socket to server
        clientSocket.getOutputStream().write(toServerBytes);

        /* Set up ByteArrayOutputStream */
        
        // Recieve data from server
        while (/* Exit when server is done sending data */) {
            // Save data sent from server
            /*
             * Is a loop necessary? Can I use any other functions
             * to automatically read all data and store it at the
             * correct location?
             */
        }

        // Terminate connection
        clientSocket.close();
        // Return data recieved from server
        return fromServerBuffer;
    }

    public byte[] askServer(String hostname, int port) throws IOException {
        return null;
    }

    /* TCPClient ska:
     * Skicka och ta emot bytes på OutputStream/InputStream objekt
     * OutputStream.write() skickar ut data
     * InputSteram.read() läser data
     * Ska kunna kommunicera med vilken server som helst
     * askServer läser all data från servern fram tills att servern stänger förbinelsen
     * När förbindelsen är stängd returneras all data, måste lagra all data som servern skickar
     * Använder datatyp som växer dynamiskt --> bufferutrymmet växer, ByteArrayOutputStream.write()
     * Ligger i en loop, läs från InputStream, efter varje läsning flyttar man från statiska
     * buffern till ByteArrayOutpuStream objektet med .write()
     */
}
