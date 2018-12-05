import static org.junit.Assert.*;

import org.junit.Test;


public class InvoiceServiceTest {

	@Test
	public void testThatSendingAnInvalidInvoiceWillThrowAnException() {

		InvoiceService service = new InvoiceService();
		
		Invoice invalidInvoice = new Invoice("38378373X", "Jack Jones");
		
		service.raiseInvoice(invalidInvoice);
		
		// test that an exception is thrown.
		
	}

}
