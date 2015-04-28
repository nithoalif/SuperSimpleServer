import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

/**
 * Created by ibrohim on 14/04/15.
 */
public class ElectronTest {

    @Test
    public void testReceiveMessage() throws Exception {
        ArgumentCaptor<ByteBuffer> argument = ArgumentCaptor.forClass(ByteBuffer.class);
        final AsynchronousSocketChannel mockClient = mock(AsynchronousSocketChannel.class);
        when(mockClient.read(argument.capture())).thenReturn(CompletableFuture.<Integer>completedFuture(0));

        Electron electronObject = new Electron(mockClient);
        Assert.assertEquals(true, electronObject.isDead());
    }

    @Test
    public void testGetMessage() throws Exception {
        ArgumentCaptor<ByteBuffer> argument = ArgumentCaptor.forClass(ByteBuffer.class);
        final AsynchronousSocketChannel mockClient = mock(AsynchronousSocketChannel.class);
        when(mockClient.read(argument.capture())).then(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                argument.getValue().put(new byte[] {48,49,50});
                return null;
            }
        }).thenReturn(CompletableFuture.<Integer>completedFuture(0));

        Electron electronObject = new Electron(mockClient);
        Assert.assertEquals("012", electronObject.getMessage().substring(0,3));
    }
}