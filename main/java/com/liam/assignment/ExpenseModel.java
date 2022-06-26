package com.liam.assignment;

import java.io.Serializable;

public class ExpenseModel implements Serializable {

    private int id;

    private boolean expense_claimed;
    private double expense_total;
    private boolean VAT_included;
    private String date_receipt;
    private String date_addedToApp;
    private String date_expensePaid;
    private String text_summary;
    private String uriText;
    //image to be added

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean getExpense_Claimed() {
        return expense_claimed;
    }

    public void setExpense_Claimed(boolean expense_claimed) {
        this.expense_claimed = expense_claimed;
    }

    public double getExpense_Total() {
        return expense_total;
    }

    public void setExpense_Total(double expense_total) {
        this.expense_total = expense_total;
    }

    public boolean getVAT_Included() {
        return VAT_included;
    }

    public void setVAT_Included(boolean VAT_included) {
        this.VAT_included = VAT_included;
    }

    public String getDate_Receipt() {
        return date_receipt;
    }

    public void setDate_Receipt(String date_receipt) {
        this.date_receipt = date_receipt;
    }

    public String getDate_AddedToApp() {
        return date_addedToApp;
    }

    public void setDate_AddedToApp(String date_addedToApp) {
        this.date_addedToApp = date_addedToApp;
    }

    public String getDate_ExpensePaid() {
        return date_expensePaid;
    }

    public void setDate_ExpensePaid(String date_expensePaid) {
        this.date_expensePaid = date_expensePaid;
    }

    public String getText_Summary() {
        return text_summary;
    }

    public void setText_summary(String text_summary) {
        this.text_summary = text_summary;
    }

    public String getUriText(){
        return uriText;
    }

    public void setUriText(String uriText) {
        this.uriText = uriText;
    }
}
