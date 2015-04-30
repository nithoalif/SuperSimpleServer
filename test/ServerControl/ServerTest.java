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
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Test untuk server
 * @author Ibrohim Kholilul Islam / 13513090
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
        Assert.assertEquals("", testOutput.trim());
        String testError = errContent.toString();
        Assert.assertEquals("", testError.trim());
    }

    @Test
    public void atomConstructorTest(){
        Server atomObject = new Server();
        Assert.assertNotNull(atomObject);

        //assertEmptyOutput();
    }

    @Test
    public void serverRunTest(){
        final AsynchronousServerSocketChannel _socket = mock(AsynchronousServerSocketChannel.class);
        final AsynchronousSocketChannel client = mock(AsynchronousSocketChannel.class);
        final ByteBuffer buffer = mock(ByteBuffer.class);
        
        Server serverObject = new Server() {
            @Override
            protected void listenSocket() {
                socket = _socket;
                canceled = true;
            }
        };

        String testError = errContent.toString();
        Assert.assertEquals("", testError.trim());
    }
}