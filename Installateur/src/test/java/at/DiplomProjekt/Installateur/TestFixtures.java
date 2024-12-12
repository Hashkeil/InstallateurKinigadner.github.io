package at.DiplomProjekt.Installateur;

import at.DiplomProjekt.Installateur.account.Account;
import at.DiplomProjekt.Installateur.customer.Customer;
import at.DiplomProjekt.Installateur.invoice.Invoice;
import at.DiplomProjekt.Installateur.invoice.InvoiceItem;
import at.DiplomProjekt.Installateur.invoice.InvoiceStatus;
import at.DiplomProjekt.Installateur.product.Product;
import at.DiplomProjekt.Installateur.security.Privilege;
import at.DiplomProjekt.Installateur.security.Role;
import at.DiplomProjekt.Installateur.security.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class TestFixtures {



    public static Role role() {

        return Role.builder()
                .name("ADMIN")
                .privileges(Collections.singleton(privilege()))
                .build();
    }

    // Privilege
    public static Privilege privilege(){

        return Privilege.builder()
                .name("READ_WRITE")
                .build();
    }



    // InvoiceStatus
    public static InvoiceStatus createInvoiceStatus(String name) {
        return new InvoiceStatus(name);
    }



    // User
    public static User createUserJohn(Account account) {

        return User.builder()
                .id(11L)
                .email("john@gmail.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .enabled(true)
                .account(account)
                .build();
    }

    public static User createUserAlice(Account account) {
        User user = User.builder()
                .email("alice@gmail.com")
                .password("password123")
                .firstName("Alice")
                .lastName("Smith")
                .enabled(true)
                .account(account)
                .build();


        return user;
    }

    // Customer


    public static Account createAccountForUserAlice() {

        return Account.builder()
                .company("ABC Company")
                .address("456 Oak St")
                .city("CityA")
                .state("StateA")
                .zip("54321")
                .phone("987-654-3210")
                .email("alice@abccompany.com")
                .website("www.abccompany.com")
                .active(true)
         //       .createdAt(LocalDateTime.now())
                .build();
    }

    public static Account createAccountForUserJohn() {
        return Account.builder()
                //.id(101L)
                .company("XYZ Company")
                .address("789 Pine St")
                .city("CityX")
                .state("StateX")
                .zip("67890")
                .phone("456-789-0123")
                .email("john@xyzcompany.com")
                .website("www.xyzcompany.com")
                .active(true)
              //  .createdAt(LocalDateTime.now())
                .build();
    }



    // Customer
    public static Customer createCustomerFarida() {
        return Customer.builder()
              //  .id(20L)
                .firstName("Farida")
                .lastName("hash")
                .email("sample.customer@gmail.com")
                .phone("123-456-7890")
                .companyName("Sample Company")
                .build();
    }

    public static Customer createCustomerAnna() {
        return Customer.builder()
             //   .id(21L)
                .firstName("Anna")
                .lastName("Smith")
                .email("Anna@gmail.com")
                .phone("123-456-7890")
                .companyName("ABC Company")
                //.account(createAccountForCustomerAnna())
                .build();
    }

    public static Account createAccountForCustomerFarida() {
        return Account.builder()
          //      .id(200L)
                .company("Farida's Company")
                .address("456 Oak St")
                .city("City")
                .state("State")
                .zip("56789")
                .phone("987-654-3210")
                .email("farida@company.com")
                .website("www.faridascompany.com")
                .active(true)
               // .createdAt(LocalDateTime.now())
                .build();
    }

    // Account for Customer Anna
    public static Account createAccountForCustomerAnna() {
        return Account.builder()
                //  .id(211L)
                .company("Anna's Company")
                .address("789 Pine St")
                .city("City")
                .state("State")
                .zip("98765")
                .phone("987-654-3210")
                .email("anna@company.com")
                .website("www.annascompany.com")
                .active(true)
                //    .createdAt(LocalDateTime.now())
                .build();
    }

    // Invoice

    public static Invoice createSampleInvoice(User user, Customer customer) {
        InvoiceStatus paidStatus = createInvoiceStatus("Paid");

        return Invoice.builder()
                .invoiceNum(123)
                .name("Sample Invoice")
                .invoiceItems(new HashSet<>())
                .invoiceStatus(paidStatus)
                .notes("Some notes")
                .subtotal(BigDecimal.valueOf(100.00))
                .tax(BigDecimal.valueOf(10.00))
                .total(BigDecimal.valueOf(110.00))
                .user(user)
                .customer(customer)
                .build();
    }


    public static Invoice createInvoiceForAlice(User user, Customer customer) {
        InvoiceStatus paidStatus = createInvoiceStatus("UnPaid");

        Account userAccount = user.getAccount();
        if (userAccount == null) {throw new IllegalArgumentException("User must have an associated account");}

        // Create Invoice instance
        Invoice invoice = Invoice.builder()
                .invoiceNum(124)
                .name("Invoice for Alice")
                .invoiceItems(new HashSet<>())
                .invoiceStatus(paidStatus)
                .notes("Additional notes")
                .subtotal(BigDecimal.valueOf(150.00))
                .tax(BigDecimal.valueOf(15.00))
                .total(BigDecimal.valueOf(165.00))
                .user(user)
                .customer(customer)
                .build();

        return invoice;
    }



    // InvoiceItem
    public static InvoiceItem createSampleInvoiceItem(Product product, Invoice invoice) {
        return InvoiceItem.builder()
                .quantity(2)
                .unitPrice(BigDecimal.valueOf(50.00))
                .lineSubtotal(BigDecimal.valueOf(100.00))
                .taxRate(BigDecimal.valueOf(10.00))
                .taxAmount(BigDecimal.valueOf(10.00))
                .amount(BigDecimal.valueOf(110.00))
                .product(product)
                .invoice(invoice)
                .build();
    }

    public static InvoiceItem createInvoiceItemForAlice(Product product, Invoice invoice) {
        return InvoiceItem.builder()
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(30.00))
                .lineSubtotal(BigDecimal.valueOf(90.00))
                .taxRate(BigDecimal.valueOf(9.00))
                .taxAmount(BigDecimal.valueOf(8.10))
                .amount(BigDecimal.valueOf(98.10))
                .product(product)
                .invoice(invoice)
                .build();
    }

    public static Product product1() {
        return Product.builder()
                .name("Sample Product")
                .description("This is a sample product")
                .sku("SKU123")
                .isService(false)
                .build();
    }
    public static Product product2() {
        return Product.builder()
                .name("Sample Product 2")
                .description("This is a sample product 2")
                .sku("SKU1234")
                .isService(true)
                .build();
    }



}
