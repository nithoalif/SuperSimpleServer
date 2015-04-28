import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by ibrohim on 14/04/15.
 */
public class AtomTest {

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
        Atom atomObject = new Atom();
        Assert.assertNotNull(atomObject);

        assertEmptyOutput();
    }

    @Test
    public void atomRunTest(){
        final AsynchronousServerSocketChannel socket = mock(AsynchronousServerSocketChannel.class);
        final AsynchronousSocketChannel client = mock(AsynchronousSocketChannel.class);
        final ByteBuffer buffer = mock(ByteBuffer.class);

        when(socket.accept()).thenReturn(CompletableFuture.<AsynchronousSocketChannel>completedFuture(null));

        Atom atomObject = new Atom() {
            @Override
            protected void listenSocket() {
                server = socket;
            }

            @Override
            protected void acceptConnectedClient() throws InterruptedException {
                if (client.isDone()) {
                    try {
                        Assert.assertNull(client.get());
                        cancelJobs();
                    }
                    catch (Exception thrownException) {
                        thrownException.printStackTrace();
                    }
                    client = server.accept();
                }
            }

        };

        atomObject.run();
        assertEmptyOutput();
    }
}