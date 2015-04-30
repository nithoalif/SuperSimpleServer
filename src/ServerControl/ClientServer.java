package ServerControl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.util.concurrent.Future;

/**
 * Class ClientServer
 * 
 * Kelas yang berfungsi untuk menerima request dari klien
 */

public class ClientServer {
    protected AsynchronousSocketChannel client;
    protected ByteBuffer readBuffer;
    protected ByteBuffer writeBuffer;
    protected Future<Integer> readBufferStatus;
    protected Future<Integer> writeBufferStatus;

    /**
     * Konstruktor untuk ClientServer
     * @param _client klien yang ditangani
     * @param handler objek callback
     */
    public ClientServer(AsynchronousSocketChannel _client, CompletionHandler<Integer, Object> handler){
        client = _client;
        receiveMessage(handler);
    }

    /**
     * Mematikan ClientServer
     * @throws IOException
     */
    public void bury() throws IOException {
        client.close();
    }

    /**
     * Mengetes apakah pengiriman telah selesai
     * @return boolean Pengiriman selesai atau belum
     */
    public boolean isWriteComplete() {
        return (writeBufferStatus!=null) && (writeBufferStatus.isDone());
    }

    /**
     * Mengambil pesan dari Client
     * @return pesan dari Client
     */
    public String getMessage() {
        readBuffer.flip();
        String result = new String(readBuffer.array());
        readBuffer.clear();
        return result;
    }

    /**
     * Mengirimkan pesan ke klien
     * @param message pesan dari klien
     * @throws CharacterCodingException
     */
    public void sendMessage(byte[] message) throws CharacterCodingException {
        char[] buffer = new char[message.length];
        for(int i = 0; i < buffer.length; i++) {
            char c = (char)(message[i]&0x00FF);
            buffer[i] = c;
        }

        writeBuffer =  ByteBuffer.wrap(message);
        writeBufferStatus = client.write(writeBuffer);
    }
    
    /**
     * Menerima pesan dari klien
     * @param handler objek callback
     */
    protected void receiveMessage(CompletionHandler<Integer, Object> handler) {
        readBuffer = ByteBuffer.allocate(1024);
        client.read(readBuffer, this , handler);
    }
}
