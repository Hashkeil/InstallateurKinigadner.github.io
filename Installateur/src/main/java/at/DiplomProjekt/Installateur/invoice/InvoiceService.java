package at.DiplomProjekt.Installateur.invoice;


import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.account.dto.AccountViewResponse;
import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.customer.CustomerRepository;
import at.DiplomProjekt.Installateur.customer.dto.CustomerResponse;
import at.DiplomProjekt.Installateur.invoice.dto.*;
import at.DiplomProjekt.Installateur.product.Product;
import at.DiplomProjekt.Installateur.product.ProductNotFoundException;
import at.DiplomProjekt.Installateur.product.ProductService;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService implements IInvoiceService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    private InvoiceItemRepository invoiceItemRepository;
    private InvoiceRepository invoiceRepository;
    private ProductService productService;
    private CustomerRepository customerRepository;
    private InvoiceStatusRepository statusRepository;
    private ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public InvoiceService(
            InvoiceItemRepository invoiceItemRepository,
            InvoiceRepository invoiceRepository,
            ProductService productService,
            CustomerRepository customerRepository,
            InvoiceStatusRepository statusRepository,
            ModelMapper modelMapper
    ) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.productService = productService;
        this.customerRepository = customerRepository;
        this.statusRepository = statusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InvoiceItemResponse createInvoiceItem(InvoiceItemFormModel invoiceItem) {
        logger.info(invoiceItem.toString());

        Product product;

        if (invoiceItem.getProductId() != null && !invoiceItem.getProductId().isEmpty()) {
            Long productId = Long.valueOf(invoiceItem.getProductId());
            Optional<Product> optionalProduct = productService.findProductById(productId);
            product = optionalProduct.orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
        } else {
            product = new Product(invoiceItem.getProductName(), invoiceItem.getProductDescription());
            productService.createProduct(product);
        }

        int quantity =invoiceItem.getItemQuantity();
        BigDecimal unitPrice = invoiceItem.getUnitPrice();
        BigDecimal taxRate = (invoiceItem.getTaxRate());

        BigDecimal itemSubtotal = calculateSubtotal(unitPrice, quantity);
        BigDecimal taxAmount = calculateSalesTax(taxRate, itemSubtotal);
        BigDecimal itemTotal = itemSubtotal.add(taxAmount);

    InvoiceItem item = new InvoiceItem();
    item.setInvoice(invoiceItem.getInvoice());
    item.setQuantity(quantity);
    item.setUnitPrice(unitPrice);
    item.setLineSubtotal(itemSubtotal);
    item.setTaxRate(taxRate);
    item.setTaxAmount(taxAmount);
    item.setAmount(itemTotal);
    item.setProduct(product);

    InvoiceItem savedInvoiceItem = invoiceItemRepository.save(item);

    return modelMapper.map(savedInvoiceItem, InvoiceItemResponse.class);
}

    private int parseOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String parseOrDefault(String value, String defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        } else {
            return value;
        }
    }


    @Override
    public CreateInvoiceResponse createInvoice(InvoiceFormModel invoiceFormModel, CustomUserDetails userDetails) {
        Account account = userDetails.getUser().getAccount();
        Invoice invoice = createDraftInvoice(userDetails);

        Customer customer = null;
        if (invoiceFormModel.getCustomerId() != null && !invoiceFormModel.getCustomerId().isEmpty()) {
            String cleanedCustomerId = invoiceFormModel.getCustomerId().replaceAll("[^0-9.]", "");

            try {
                Long customerId = Long.valueOf(cleanedCustomerId);
                customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid customer ID: " + invoiceFormModel.getCustomerId(), e);
            }
        }

        if (customer != null) {invoice.setCustomer(customer);}

        invoice.setNotes(invoiceFormModel.getInvoiceNote());

        List<InvoiceItemResponse> invoiceItemResponseList = new ArrayList<>();
        invoiceFormModel.getInvoiceItems().forEach(item -> {item.setInvoice(invoice);invoiceItemResponseList.add(createInvoiceItem(item));});

        BigDecimal invoiceSubtotal = BigDecimal.ZERO;
        BigDecimal invoiceTax = BigDecimal.ZERO;

        for (InvoiceItemResponse item : invoiceItemResponseList) {
            invoiceSubtotal = invoiceSubtotal.add(item.getLineSubtotal());
            invoiceTax = invoiceTax.add(item.getTaxAmount());
        }

        BigDecimal invoiceTotal = invoiceSubtotal.add(invoiceTax);

        invoice.setSubtotal(invoiceSubtotal);
        invoice.setTax(invoiceTax);
        invoice.setTotal(invoiceTotal);

        logger.info("Invoice Subtotal: " + invoiceSubtotal);
        logger.info("Invoice Tax: " + invoiceTax);
        logger.info("Invoice Total: " + invoiceTotal);

        // Set the response details
        CreateInvoiceResponse invoiceResponse = new CreateInvoiceResponse();
        invoiceResponse.setInvoiceItems(invoiceItemResponseList);
        invoiceResponse.setAccountCompany(account.getCompany());
        invoiceResponse.setAccountAddress(account.getAddress());
        invoiceResponse.setAccountCity(account.getCity());
        invoiceResponse.setAccountState(account.getState());
        invoiceResponse.setAccountZip(account.getZip());
        invoiceResponse.setAccountEmail(account.getEmail());
        invoiceResponse.setAccountPhone(account.getPhone());
        invoiceResponse.setAccountWebsite(account.getWebsite());
        invoiceResponse.setInvoiceId(invoice.getId());
        invoiceResponse.setInvoiceName(invoice.getName());
        invoiceResponse.setInvoiceNumber(invoice.getInvoiceNum());
        invoiceResponse.setInvoiceStatus(invoice.getInvoiceStatus().getName());
        invoiceResponse.setInvoiceNote(invoice.getNotes());

        LocalDateTime createdAt = invoice.getCreatedAt();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:m a");
        invoiceResponse.setInvoiceCreatedAt(createdAt.format(formatter));
        invoiceResponse.setInvoiceUpdatedAt(createdAt.format(formatter));

        logger.info(invoiceResponse.toString());

        return invoiceResponse;
    }

    @Override
    @Transactional
    public Invoice createDraftInvoice(CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        if (!entityManager.contains(user)) {
            user = entityManager.merge(user);
        }

        LocalDateTime dateTime = LocalDateTime.now();
        String createdDate = dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:m a"));
        String draftName = "Draft [created " + createdDate + "]";

        // Create a new InvoiceStatus object instead of fetching from the database
        InvoiceStatus status = new InvoiceStatus("DRAFT");

        Invoice invoice = new Invoice();
        invoice.setName(draftName);
        invoice.setInvoiceStatus(status);
        invoice.setUser(user);

        // Save the invoice to generate an ID and invoiceNum
        invoiceRepository.save(invoice);

        // Set the invoice number using the generated invoiceNum and a prefix
        invoice.setInvoiceNum(invoice.getInvoiceNum());

        // Save the invoice again to update the invoice number
        invoiceRepository.save(invoice);

        return invoice;
    }





    @Override public List<InvoiceListItemResponse> findAllInvoices() {

        Iterable<Invoice> invoices = invoiceRepository.findAll();

        List<InvoiceListItemResponse> invoiceListItems = new ArrayList<>();

        invoices.forEach(invoice -> {
            InvoiceListItemResponse listItem = new InvoiceListItemResponse();
            listItem.setInvoiceId(invoice.getId());
            listItem.setInvoiceName(invoice.getName());
            listItem.setInvoiceNumber(invoice.getInvoiceNum());
            listItem.setInvoiceStatus(invoice.getInvoiceStatus().getName());

            listItem.setCustomerId(invoice.getCustomer().getId());
            String customerName = invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName();
            listItem.setCustomerName(customerName);

            listItem.setUserId(invoice.getUser().getId());
            String userName = invoice.getUser().getFirstName() + " " + invoice.getUser().getLastName();
            listItem.setUserName(userName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            listItem.setInvoiceCreatedAt(invoice.getCreatedAt().format(formatter));

            invoiceListItems.add(listItem);
        });

        return invoiceListItems;
    }

    @Override public InvoiceViewResponse findInvoiceById(Long id) throws EntityNotFoundException {

        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        Invoice invoice = optionalInvoice.orElseThrow(() -> new EntityNotFoundException("Invoice not found!"));

        User user = invoice.getUser();
        
        Account account = user.getAccount();
        AccountViewResponse accountView = new AccountViewResponse(
        		account.getId(),
        		account.getCompany(),
        		account.getAddress(),
        		account.getCity(),
        		account.getState(),
        		account.getZip(),
        		account.getPhone(),
        		account.getEmail(),
        		account.getWebsite()
        );

        Customer customer = invoice.getCustomer();
        CustomerResponse customerView = null;
        if (customer != null) {
            customerView = new CustomerResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getCompanyName(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getZip()
            );
        }


        List<InvoiceItemResponse> invoiceItems = new ArrayList<>();
        invoice.getInvoiceItems().forEach(item -> {
        	InvoiceItemResponse invoiceItemView = new InvoiceItemResponse();
        	invoiceItemView.setInvoiceItemId(item.getId());
        	invoiceItemView.setProductName(item.getProduct().getName());
        	invoiceItemView.setProductDescription(item.getProduct().getDescription());
        	invoiceItemView.setQuantity(item.getQuantity());
        	invoiceItemView.setTaxRate(item.getTaxRate());
        	invoiceItemView.setAmount(item.getAmount());
        	invoiceItems.add(invoiceItemView);
        });

        InvoiceViewResponse invoiceView = new InvoiceViewResponse();
        invoiceView.setInvoiceId(invoice.getId());
        invoiceView.setInvoiceName(invoice.getName());
        invoiceView.setInvoiceNote(invoice.getNotes());
        invoiceView.setInvoiceNumber(invoice.getInvoiceNum());
        invoiceView.setInvoiceStatus(invoice.getInvoiceStatus().getName());
        invoiceView.setInvoiceSubtotal(invoice.getSubtotal());
        invoiceView.setInvoiceTax(invoice.getTax());
        invoiceView.setInvoiceTotal(invoice.getTotal());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        invoiceView.setInvoiceCreatedAt(invoice.getCreatedAt().format(formatter));
        invoiceView.setInvoiceUpdatedAt(invoice.getUpdatedAt().format(formatter));
        
        invoiceView.setInvoiceItems(invoiceItems);
        invoiceView.setAccount(accountView);
        invoiceView.setCustomer(customerView);
        
        invoiceView.setUserName(user.getFirstName() + " " + user.getLastName());
        if (customerView != null) {
            invoiceView.setCustomer(customerView);
        }
        return invoiceView;
    }


    public BigDecimal calculateSubtotal(BigDecimal unitPrice, int quantity) {
        BigDecimal qty = new BigDecimal(quantity);
        return unitPrice.multiply(qty);
    }

    public BigDecimal calculateSalesTax(BigDecimal taxRate, BigDecimal subtotal) {
        BigDecimal taxDecimal = taxRate.divide(BigDecimal.valueOf(100));
        BigDecimal taxAmount = taxDecimal.multiply(subtotal);
        return taxAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculateTotal(BigDecimal tax, BigDecimal subtotal) {
        return subtotal.add(tax).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal cleanNumericString(String value) {
        String cleanedValue = value.replaceAll("[^0-9.]", "");
        cleanedValue = cleanedValue.replaceAll("\\.{2,}", ".");

        int dotIndex = cleanedValue.indexOf('.');
        if (dotIndex != -1) {
            cleanedValue = cleanedValue.substring(0, dotIndex + 1) +
                    cleanedValue.substring(dotIndex + 1).replace(".", "");
        }
        return new BigDecimal(cleanedValue);
    }

    public void deleteInvoice(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invoice with id " + id + " not found");
        }
    }
}
