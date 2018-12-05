/**
 * Defines the behaviour of any Invoicing Data    
 * Access Object in our system
 */
public interface InvoicingDAO 
{
   public void save(Invoice newInvoice);
   public void delete(Invoice oldInvoice);
   public void update(Invoice invoiceToCancel);
}
