import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by ibrohim on 26/04/15.
 */
public class RequestProcessorTest {


    @Test
    public void testAddRequest() throws Exception {
        RequestProcessor requestProcessorObject = new RequestProcessor();
        Request mockRequest = mock(Request.class);

        int before = requestProcessorObject.getRequestCount();
        requestProcessorObject.addRequest(mockRequest);
        int after = requestProcessorObject.getRequestCount();

        Assert.assertEquals(before + 1, after);
    }
}