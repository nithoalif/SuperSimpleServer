package PluginsAndRequest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test untuk request processor
 * @author Ibrohim Kholilul Islam / 13513090
 */
public class RequestProcessorTest {


    @Test
    public void testAddRequest() throws Exception {
        RequestProcessor requestProcessorObject = new RequestProcessor();
        Request mockRequest = mock(Request.class);

        int before = requestProcessorObject.getRequestCount();
        requestProcessorObject.addRequest(mockRequest);
        int after = requestProcessorObject.getRequestCount();

        assertEquals(before + 1, after);
    }
}