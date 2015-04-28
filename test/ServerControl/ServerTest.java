package ServerControl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ibrohim on 14/04/15.
 */
public class ServerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    private void assertEmptyOutput(){
        String testOutput = outContent.toString();
        Assert.assertEquals("", testOutput);
        String testError = errContent.toString();
        Assert.assertEquals("", testError);
    }

    @Test
    public void atomConstructorTest(){
        Server atomObject = new Server();
        Assert.assertNotNull(atomObject);

        assertEmptyOutput();
    }

    @Test
    public void atomRunTest(){
        final AsynchronousServerSocketChannel socket = mock(AsynchronousServerSocketChannel.class);
        final AsynchronousSocketChannel client = mock(AsynchronousSocketChannel.class);
        final ByteBuffer buffer = mock(ByteBuffer.class);

        when(socket.accept()).thenReturn(CompletableFuture.<AsynchronousSocketChannel>completedFuture(null));

        Server atomObject = new Server() {
            @Override
            protected void listenSocket() {
                server = socket;
            }

            protected void assignJob(Future<AsynchronousSocketChannel> assignedClient) {
                try {
                    Assert.assertNull(assignedClient.get());
                    cancelJobs();
                }
                catch (Exception thrownException) {
                    thrownException.printStackTrace();
                }
            }

        };

        atomObject.run();
        assertEmptyOutput();
    }
}