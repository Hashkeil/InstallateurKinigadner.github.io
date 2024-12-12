package at.DiplomProjekt.Installateur.invoice;

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(String message) {
        super(message);
    }
}