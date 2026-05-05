package com.tcs.parcel_management.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.tcs.parcel_management.dto.InvoiceResponseDTO;
import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.InvoiceEntity;
import com.tcs.parcel_management.entity.PaymentEntity;
import com.tcs.parcel_management.exception.BusinessValidationException;
import com.tcs.parcel_management.exception.ResourceNotFoundException;
import com.tcs.parcel_management.repository.BookingRepository;
import com.tcs.parcel_management.repository.InvoiceRepository;
import com.tcs.parcel_management.repository.PaymentRepository;
import com.tcs.parcel_management.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public InvoiceResponseDTO generateInvoice(String bookingId) {

        BookingEntity booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        PaymentEntity payment = paymentRepository.findByBooking(booking)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found for booking"));

        if (invoiceRepository.existsByBooking(booking)) {
            throw new BusinessValidationException(
                    "Invoice already generated for this booking");
        }

        String invoiceNumber = generateInvoiceNumber();

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setBooking(booking);
        invoice.setPayment(payment);
        invoice.setGeneratedAt(LocalDateTime.now());

        invoiceRepository.save(invoice);

        return new InvoiceResponseDTO(
                invoiceNumber,
                "Invoice generated successfully"
        );
    }

    private String generateInvoiceNumber() {
        long count = invoiceRepository.count();
        return "INV" + (9001 + count);
    }


    @Override
    public byte[] downloadInvoicePdf(String bookingId) {

        BookingEntity booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        PaymentEntity payment = paymentRepository.findByBooking(booking)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        InvoiceEntity invoice = invoiceRepository.findByBooking(booking)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invoice not generated"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            document.add(new Paragraph("PARCEL MANAGEMENT INVOICE"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
            document.add(new Paragraph("Booking ID: " + booking.getBookingId()));
            document.add(new Paragraph("Payment ID: " + payment.getPaymentId()));
            document.add(new Paragraph("Transaction ID: " + payment.getTransactionId()));
            document.add(new Paragraph("Customer: " + booking.getCustomer().getName()));
            document.add(new Paragraph("Receiver: " + booking.getReceiverName()));
            document.add(new Paragraph("Amount Paid: ₹" + payment.getAmount()));
            document.add(new Paragraph("Payment Date: " + payment.getPaymentDate()));

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF");
        }
    }
}