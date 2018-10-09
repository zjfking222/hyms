package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.service.mm.ReceiptAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mm")
public class ReceiptAgendaController {

    @Autowired
    private ReceiptAgendaService receiptAgendaService;

}
