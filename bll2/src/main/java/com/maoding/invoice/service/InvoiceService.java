package com.maoding.invoice.service;

import com.maoding.invoice.dto.InvoiceEditDTO;
import com.maoding.invoice.dto.InvoiceInfoDTO;

public interface InvoiceService {

    /**
     * 保存发票信息（项目收款填写发票信息）
     */
    String saveInvoice(InvoiceEditDTO dto) throws Exception;

    /**
     * 获取发票信息
     */
    InvoiceInfoDTO getInvoice(String invoiceId);

    /**
     * 获取收款方名
     */
    String getInvoiceReceiveCompanyName(String invoiceId);
}
