package com.liam.assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ExpenseCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ExpenseModel> ExpenseArrayList;
    private ViewHolder viewHolder;

    // constructor
    public ExpenseCustomAdapter(Context context, ArrayList<ExpenseModel> ExpenseArrayList)
    {
        this.context = context;
        this.ExpenseArrayList = ExpenseArrayList;
    }

    @Override
    public int getCount() {
        return ExpenseArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ExpenseArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // create a new convertview
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null, true);

            viewHolder = new ViewHolder(); // creating new viewHolder

            // assigning associated ViewHolder
            viewHolder.tv_expense_claimed = (TextView) convertView.findViewById(R.id.checkexpense_claimed);
            viewHolder.tv_expense_total = (TextView) convertView.findViewById(R.id.txtexpense_total);
            viewHolder.tv_VAT_included = (TextView) convertView.findViewById(R.id.checkexpense_VAT);
            viewHolder.tv_expense_withoutVAT_total = (TextView) convertView.findViewById(R.id.txtexpense_withoutVAT_total);
            viewHolder.tv_date_receipt = (TextView) convertView.findViewById(R.id.datepickexpense_incurred);
            viewHolder.tv_date_addedToApp = (TextView) convertView.findViewById(R.id.datepickexpense_addToApp);
            viewHolder.tv_date_expensePaid = (TextView) convertView.findViewById(R.id.datepickexpense_wasPaid);
            viewHolder.tv_text_summary = (TextView) convertView.findViewById(R.id.txtexpense_summary);
            viewHolder.tv_image_receipt = (TextView) convertView.findViewById(R.id.txt_image_receipt);
            viewHolder.iv_image_view = (ImageView) convertView.findViewById(R.id.image_view);

            convertView.setTag(viewHolder);
        }
        else {
            // as View exists, changes can be made to convertView e.g. setting text on a TextView
            // getTag returns viewHolder object set as a tag to the view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // setting the view holders for each view to display
        viewHolder.tv_expense_claimed.setText("Expense Claimed:" + ExpenseArrayList.get(position).getExpense_Claimed());

        viewHolder.tv_expense_total.setText("Total Amount: £" + ExpenseArrayList.get(position).getExpense_Total());
        viewHolder.tv_VAT_included.setText("VAT Included:" + ExpenseArrayList.get(position).getVAT_Included());

        if (!ExpenseArrayList.get(position).getVAT_Included()) {
            viewHolder.tv_expense_withoutVAT_total.setText("Total Amount w/ VAT: No VAT Added");
        }
        else {
            // remove 20% VAT to total amount
            viewHolder.tv_expense_withoutVAT_total.setText("Total Amount w/o VAT: £" +
                    ((ExpenseArrayList.get(position).getExpense_Total()) -
                            (ExpenseArrayList.get(position).getExpense_Total() * (0.2))));
        }

        // if date of expense paid was added
        if (ExpenseArrayList.get(position).getDate_Receipt().equals("Pick Date")) {
            viewHolder.tv_date_receipt.setText("Date Receipt was Incurred: No Date Added");
        }
        else {
            viewHolder.tv_date_receipt.setText("Date Receipt was Incurred:" + ExpenseArrayList.get(position).getDate_Receipt());
        }

        // date added to app is pre-selected so should never be empty
        viewHolder.tv_date_addedToApp.setText("Date Receipt added to app:" + ExpenseArrayList.get(position).getDate_AddedToApp());

        // if date of expense paid was added
        if (ExpenseArrayList.get(position).getDate_ExpensePaid().equals("Pick Date")) {
            viewHolder.tv_date_expensePaid.setText("Date Expense Paid: Not Paid");
        }
        else if (ExpenseArrayList.get(position).getDate_ExpensePaid().equals("Date Expense Paid: Not Paid")) {
            viewHolder.tv_date_expensePaid.setText("Date Expense Paid: Not Paid");
        }
        else {
            viewHolder.tv_date_expensePaid.setText("Date Expense Paid:" + ExpenseArrayList.get(position).getDate_ExpensePaid());
        }

        // if summary was added
        if (ExpenseArrayList.get(position).getText_Summary() == null) {
            viewHolder.tv_image_receipt.setText("Summary: No Summary Added");
        }
        else {
            viewHolder.tv_text_summary.setText("Summary:" + ExpenseArrayList.get(position).getText_Summary());
        }

        // if image was added
        if (ExpenseArrayList.get(position).getUriText() == null) {
            viewHolder.tv_image_receipt.setText("Receipt Added: No Image Selected");
        }
        else {
            viewHolder.tv_image_receipt.setText("Receipt Added: " + ExpenseArrayList.get(position).getUriText());
            viewHolder.iv_image_view.setImageURI(Uri.parse(ExpenseArrayList.get(position).getUriText()));
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_expense_claimed;
        private TextView tv_expense_total;
        private TextView tv_VAT_included;
        private TextView tv_expense_withoutVAT_total;
        private TextView tv_date_receipt;
        private TextView tv_date_addedToApp;
        private TextView tv_date_expensePaid;
        private TextView tv_text_summary;
        private TextView tv_image_receipt;
        private ImageView iv_image_view;
    }
}
