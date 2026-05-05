package com.tcs.parcel_management.service;

import com.tcs.parcel_management.dto.InvoiceResponseDTO;

public interface InvoiceService {

    InvoiceResponseDTO generateInvoice(String bookingId);

    byte[] downloadInvoicePdf(String bookingId);
}