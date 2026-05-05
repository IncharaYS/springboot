package com.tcs.parcel_management.controller;

import com.tcs.parcel_management.dto.InvoiceResponseDTO;
import com.tcs.parcel_management.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<InvoiceResponseDTO> generateInvoice(
            @PathVariable String bookingId) {

        return ResponseEntity.ok(
                invoiceService.generateInvoice(bookingId)
        );
    }


    @GetMapping("/download/{bookingId}")
    public ResponseEntity<byte[]> downloadInvoicePdf(
            @PathVariable String bookingId) {

        byte[] pdf = invoiceService.downloadInvoicePdf(bookingId);

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=invoice-" + bookingId + ".pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}
