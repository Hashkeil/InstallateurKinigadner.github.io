package at.DiplomProjekt.Installateur.invoice;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.customer.CustomerService;
import at.DiplomProjekt.Installateur.invoice.dto.*;
import at.DiplomProjekt.Installateur.product.IProductService;
import at.DiplomProjekt.Installateur.product.ProductNotFoundException;
import at.DiplomProjekt.Installateur.product.ProductService;
import at.DiplomProjekt.Installateur.security.AuthenticatedUser;
import at.DiplomProjekt.Installateur.security.AuthenticationFacade;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.IAuthenticationFacade;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.itextpdf.html2pdf.HtmlConverter;
import java.io.ByteArrayOutputStream;


import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private IAuthenticationFacade authenticationFacade;
    private CustomerService customerService;
    private IProductService productService;
    private IInvoiceService invoiceService;
    @Autowired
    private SpringTemplateEngine templateEngine;


    @Autowired
    public InvoiceController(AuthenticationFacade authenticationFacade,CustomerService customerService, ProductService productService, InvoiceService invoiceService) {
        this.authenticationFacade = authenticationFacade;
        this.productService = productService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }



    @PostMapping("")
    public String createInvoice(
            @ModelAttribute("invoice") InvoiceFormModel invoiceForm,
            Model model,
            @AuthenticatedUser CustomUserDetails userDetails
    ) {
        logger.info("Received InvoiceFormModel: " + invoiceForm);

        // Add more logging here if needed
        invoiceForm.setStatus(Invoice.InvoiceStatusEnum.DRAFT);
        CreateInvoiceResponse invoiceResponse = invoiceService.createInvoice(invoiceForm, userDetails);
        logger.info("Created InvoiceResponse: " + invoiceResponse);

        // Retrieve the created invoice for the preview
        InvoiceViewResponse previewInvoice = invoiceService.findInvoiceById(invoiceResponse.getInvoiceId());

        // Add the invoice data to the model for the preview page
        model.addAttribute("invoice", previewInvoice);

        // Redirect to the "Invoice Details" page with the generated invoice ID
        return "redirect:/invoices/preview/" + invoiceResponse.getInvoiceId();
    }





    @GetMapping("/create")
    public String showCreateForm(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null) {
            // Kontodaten sammeln
            String userName = userDetails.getFirstName() + " " + userDetails.getLastName();
                       model.addAttribute("userName", userName);
                       model.addAttribute("products", productService.findAllProducts());

            // Erstellen einer Liste von Rechnungsartikel
            List<InvoiceItemResponse> invoiceItems = new ArrayList<>();
            invoiceItems.add(new InvoiceItemResponse());
            invoiceItems.add(new InvoiceItemResponse());

                       model.addAttribute("invoice", new InvoiceResponse());
                       model.addAttribute("invoiceItems", invoiceItems);
                       model.addAttribute("item", new InvoiceItemResponse());
                       model.addAttribute("invoiceNumber", generateInvoiceNumber());

            // Kundenliste abrufen und zum Modell hinzufügen
            List<Customer> customersList = customerService.getAllCustomers();
            model.addAttribute("customers_list", customersList);

        }  else {
            logger.error("User details are null. User might not be authenticated.");
            return "redirect:/login";
        }
        return "invoice_form";
    }



    public String generateInvoiceNumber() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return formatter.format(now);
    }

    @GetMapping("/getAllInvoices")
    public String getAllInvoices(
            @RequestParam(value = "message", required = false) String message,
            Model model
    ) {
        List<InvoiceListItemResponse> invoices= invoiceService.findAllInvoices();

        model.addAttribute("list", invoices);
        model.addAttribute("message", message);
        return "invoice_list";
    }





    @PutMapping("/{id}")
    public @ResponseBody CreateInvoiceResponse updateInvoice(
            @ModelAttribute("invoice") CreateInvoiceRequest invoiceRequest,
            Model model,
            @PathVariable Long id) { logger.info("Updating invoice # " + id);

        return null;

    }



    @GetMapping("/preview/{invoiceId}")
    public String previewInvoice(@PathVariable Long invoiceId, Model model) {
        // Ensure the correct invoiceId is passed
        logger.info("Previewing Invoice ID: " + invoiceId);

        // Fetch the invoice and its details
        InvoiceViewResponse invoice = invoiceService.findInvoiceById(invoiceId);
        logger.info("Retrieved Invoice: " + invoice);

        // Add the invoice data to the model
        model.addAttribute("invoice", invoice);

        return "invoice_view";
    }




    private String prepareInvoiceHtmlContent(Long invoiceId) {
        // Fetch the invoice and its details
        InvoiceViewResponse invoice = invoiceService.findInvoiceById(invoiceId);

        // Prepare the context with the invoice data
        Context context = new Context();
        context.setVariable("invoice", invoice);

        // Render the Thymeleaf template
        return templateEngine.process("invoice_view", context); // Ensure the template name matches
    }

    @PostMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable("id") long invoiceId) {
        try {
            invoiceService.deleteInvoice(invoiceId);
            return "redirect:/invoices/list";
        } catch (InvoiceNotFoundException e) {
            return "redirect:/invoices/list?error=ProductNotFound";
        }
    }


    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdfInvoice(@PathVariable Long id) {
        try {
            // Fetch invoice data by ID from the service
            InvoiceViewResponse invoice = invoiceService.findInvoiceById(id);

            // Prepare data for Thymeleaf template
            Context context = new Context();
            context.setVariable("invoice", invoice);

            // Render Thymeleaf template to HTML
            String htmlContent = templateEngine.process("invoice_view", context);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                // Convert HTML to PDF using iText
                HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlContent.getBytes()), outputStream);

                // Prepare response headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "invoice_" + id + ".pdf");

                // Return PDF file as response
                return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            // Handle exception
            logger.error("Error generating PDF invoice for invoice ID: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

