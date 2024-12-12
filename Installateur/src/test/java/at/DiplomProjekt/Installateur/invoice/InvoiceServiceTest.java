package at.DiplomProjekt.Installateur.invoice;

import at.DiplomProjekt.Installateur.TestFixtures;
import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.customer.CustomerRepository;
import at.DiplomProjekt.Installateur.invoice.dto.CreateInvoiceResponse;
import at.DiplomProjekt.Installateur.invoice.dto.InvoiceFormModel;
import at.DiplomProjekt.Installateur.invoice.dto.InvoiceItemFormModel;
import at.DiplomProjekt.Installateur.invoice.dto.InvoiceItemResponse;
import at.DiplomProjekt.Installateur.product.Product;
import at.DiplomProjekt.Installateur.product.ProductService;
import at.DiplomProjekt.Installateur.security.CustomUserDetails;
import at.DiplomProjekt.Installateur.security.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
@Transactional
class InvoiceServiceTest {


    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private InvoiceStatus statusRepository;
    @Mock
    private InvoiceStatusRepository invoiceStatusRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private InvoiceItemRepository invoiceItemRepository;
    @Mock
    private ProductService productService;
    @InjectMocks
    private InvoiceService invoiceService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateInvoiceItem() {
        // Mocking dependencies
        InvoiceItemFormModel invoiceItemFormModel = new InvoiceItemFormModel();
        invoiceItemFormModel.setProductName("Sample Product");
        invoiceItemFormModel.setProductDescription("This is a sample product");
        invoiceItemFormModel.setUnitPrice(BigDecimal.valueOf(50));
       // invoiceItemFormModel.setItemQuantity("2");
        //invoiceItemFormModel.setTaxRate("10.00");

        Product product = TestFixtures.product1();

        when(productService.findProductById(anyLong())).thenReturn(Optional.of(product));
        when(invoiceItemRepository.save(any(InvoiceItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Perform the test
        InvoiceItemResponse invoiceItemResponse = invoiceService.createInvoiceItem(invoiceItemFormModel);

        // Assertions
        assertNotNull(invoiceItemResponse);
       // assertEquals("Sample Product", invoiceItemResponse.getProductName());

        assumeThat(invoiceItemRepository).isNotNull();
        assumeThat(invoiceItemRepository.findAll()).isNotEmpty();

        // Verify that productService.findProductById was called
        verify(productService, times(1)).findProductById(anyLong());
    }
    @Test
    void testCreateInvoice() {
        InvoiceFormModel invoiceFormModel = new InvoiceFormModel();
        invoiceFormModel.setCustomerId("1");
        invoiceFormModel.setInvoiceNote("Test Note");
        invoiceFormModel.setInvoiceItems(new ArrayList<>()); // Add mock invoice items if needed

        CustomUserDetails userDetails = mock(CustomUserDetails.class); // Setup your CustomUserDetails mock
        User user = mock(User.class); // Setup your User mock
        Account account = mock(Account.class); // Setup your Account mock

        when(userDetails.getUser()).thenReturn(user);
        when(user.getAccount()).thenReturn(account);

        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        when(invoiceRepository.save(any(Invoice.class))).thenAnswer(i -> i.getArguments()[0]);

        Optional<InvoiceStatus> draftStatus = Optional.of(new InvoiceStatus("DRAFT"));
        when(invoiceStatusRepository.findByName("DRAFT")).thenReturn(draftStatus);

        CreateInvoiceResponse response = invoiceService.createInvoice(invoiceFormModel, userDetails);

        assertNotNull(response);
        assertEquals("Test Note", response.getInvoiceNote());
        // Add more assertions based on your method logic and expected outcomes
        assumeThat(invoiceRepository).isNotNull();
        assumeThat(invoiceRepository.findAll()).isNotEmpty();

        verify(customerRepository, times(1)).findById(1L);
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    void testCreateDraftInvoice() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class); // Setup your CustomUserDetails mock
        User user = mock(User.class); // Setup your User mock

        when(userDetails.getUser()).thenReturn(user);

        Optional<InvoiceStatus> draftStatus = Optional.of(new InvoiceStatus("DRAFT"));
        when(invoiceStatusRepository.findByName("DRAFT")).thenReturn(draftStatus);

        Invoice draftInvoice = invoiceService.createDraftInvoice(userDetails);

        assertNotNull(draftInvoice);
        assertEquals("DRAFT", draftInvoice.getInvoiceStatus().getName());
        // Verify the draft name contains the current date in the expected format
        assertTrue(draftInvoice.getName().startsWith("Draft [created "));

  //      verify(statusRepository, times(1)).findByName("DRAFT");
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }
}