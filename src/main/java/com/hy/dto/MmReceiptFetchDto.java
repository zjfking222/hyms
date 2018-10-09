package com.hy.dto;


import java.util.List;

public class MmReceiptFetchDto {

    private MmReceiptDto receipt;
    private List<MmReceiptStayFetchDto> stay;
    private List<MmReceiptAgendaFetchDto> agenda;
    private List<MmReceiptDineFetchDto> dines;

    public MmReceiptDto getReceipt() {
        return receipt;
    }

    public void setReceipt(MmReceiptDto receipt) {
        this.receipt = receipt;
    }

    public List<MmReceiptStayFetchDto> getStay() {
        return stay;
    }

    public void setStay(List<MmReceiptStayFetchDto> stay) {
        this.stay = stay;
    }

    public List<MmReceiptAgendaFetchDto> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<MmReceiptAgendaFetchDto> agenda) {
        this.agenda = agenda;
    }

    public List<MmReceiptDineFetchDto> getDines() {
        return dines;
    }

    public void setDines(List<MmReceiptDineFetchDto> dines) {
        this.dines = dines;
    }
}
