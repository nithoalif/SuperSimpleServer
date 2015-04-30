package ServerControl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

/**
 * Test untuk clientserver
 * @author Ibrohim Kholilul Islam / 13513090
 */
public class ClientServerTest {

    @Test
    public void testReceiveMessage() throws Exception {
        ArgumentCaptor<ByteBuffer> argument = ArgumentCaptor.forClass(ByteBuffer.class);
        final AsynchronousSocketChannel mockClient = mock(AsynchronousSocketChannel.class);
        when(mockClient.read(argument.capture())).thenReturn(CompletableFuture.<Integer>completedFuture(0));

        CompletionHandler<Integer, Object> handlerClient =
            new CompletionHandler<Integer, Object>() {
            
            @Override
            public void completed(Integer result, Object attachment) {
                // ok
            }
            
            @Override
            public void failed(Throwable e, Object attachment) {
                assert(false);
            }
        };
        
        ClientServer electronObject = new ClientServer(mockClient, handlerClient);
    }

    @Test
    public void testGetMessage() throws Exception {
        ArgumentCaptor<ByteBuffer> argument = ArgumentCaptor.forClass(ByteBuffer.class);
        final AsynchronousSocketChannel mockClient = mock(AsynchronousSocketChannel.class);
        when(mockClient.read(argument.capture())).then(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                argument.getValue().put(new byte[] {48,49,50});
                return null;
            }
        }).thenReturn(CompletableFuture.<Integer>completedFuture(0));
        
        CompletionHandler<Integer, Object> handlerClient =
            new CompletionHandler<Integer, Object>() {
            
            @Override
            public void completed(Integer result, Object attachment) {
                ClientServer client = (ClientServer) attachment;
                Assert.assertEquals("012",client.getMessage().substring(0, 3));
            }
            
            @Override
            public void failed(Throwable e, Object attachment) {
                assert(false);
            }
        };
        
        ClientServer electronObject = new ClientServer(mockClient, handlerClient);
        
    }
}