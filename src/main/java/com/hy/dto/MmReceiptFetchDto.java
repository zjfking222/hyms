package com.hy.dto;


public class MmReceiptFetchDto {

    private MmReceiptDto receipt;
    private MmReceiptStayFetchDto[] stay;
    private MmReceiptAgendaFetchDto[] agenda;
    private MmReceiptDineFetchDto[] dines;

    public MmReceiptDto getReceipt() {
        return receipt;
    }

    public void setReceipt(MmReceiptDto receipt) {
        this.receipt = receipt;
    }

    public MmReceiptStayFetchDto[] getStay() {
        return stay;
    }

    public void setStay(MmReceiptStayFetchDto[] stay) {
        this.stay = stay;
    }

    public MmReceiptAgendaFetchDto[] getAgenda() {
        return agenda;
    }

    public void setAgenda(MmReceiptAgendaFetchDto[] agenda) {
        this.agenda = agenda;
    }

    public MmReceiptDineFetchDto[] getDines() {
        return dines;
    }

    public void setDines(MmReceiptDineFetchDto[] dines) {
        this.dines = dines;
    }
}
