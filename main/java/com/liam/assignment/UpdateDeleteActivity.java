package com.liam.assignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity {

    private ExpenseModel expenseModel;
    private EditText et_expense_total, et_date_receipt,
            et_date_expensePaid, et_text_summary;
    private Spinner spinner_expense_claimed, spinner_VAT_included;
    private DatePickerDialog datePickerDialog;

    private DatabaseHelper databaseHelper;
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView image_view;
    private String uriText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        Intent intent = getIntent();
        expenseModel = (ExpenseModel) intent.getSerializableExtra("expense");
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.trueorfalse,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner set_spinner_expense_claimed = (Spinner) findViewById(R.id.spinner_expense_claimed);
        Spinner set_spinner_VAT_included = (Spinner) findViewById(R.id.spinner_VAT_included);

        set_spinner_expense_claimed.setAdapter(arrayAdapter);
        set_spinner_VAT_included.setAdapter(arrayAdapter);

        databaseHelper = new DatabaseHelper(this);

        spinner_expense_claimed = (Spinner) findViewById(R.id.spinner_expense_claimed);
        et_expense_total = (EditText) findViewById(R.id.et_expense_total);
        spinner_VAT_included = (Spinner) findViewById(R.id.spinner_VAT_included);
        et_date_receipt = (EditText) findViewById(R.id.et_date_receipt);
        et_date_expensePaid = (EditText) findViewById(R.id.et_date_expensePaid);
        et_text_summary = (EditText) findViewById(R.id.et_text_summary);
        image_view = (ImageView) findViewById(R.id.image_viewImage);

        selectDate(findViewById(R.id.et_date_receipt));
        selectDate(findViewById(R.id.et_date_expensePaid));

        uriText = expenseModel.getUriText();

        setViews();
    }

    public void setViews() {

        if (Boolean.toString(expenseModel.getExpense_Claimed()).equals("false")) {
            spinner_expense_claimed.setSelection(1);
        }
        else if (Boolean.toString(expenseModel.getExpense_Claimed()).equals("true")) {
            spinner_expense_claimed.setSelection(0);
        }

        et_expense_total.setText(Double.toString(expenseModel.getExpense_Total()));

        if (Boolean.toString(expenseModel.getVAT_Included()).equals("false")) {
            spinner_VAT_included.setSelection(1);
        }
        else if (Boolean.toString(expenseModel.getVAT_Included()).equals("true")) {
            spinner_VAT_included.setSelection(0);
        }

        et_date_receipt.setText(expenseModel.getDate_Receipt());

        if (expenseModel.getDate_ExpensePaid().equals("Date Expense Paid: Not Paid")) {
            et_date_expensePaid.setText("Pick Date");
        }
        else {
            et_date_expensePaid.setText(expenseModel.getDate_ExpensePaid());
        }

        et_text_summary.setText(expenseModel.getText_Summary());

        if (!(expenseModel.getUriText() == null)) {
            image_view.setImageURI(Uri.parse(expenseModel.getUriText()));
        }
    }

    public void selectDate(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(UpdateDeleteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                if (view == et_date_receipt) {
                                    if (day >= 1 && day <= 9)
                                    {
                                        et_date_receipt.setText("0" + day + "/" + (month + 1) + "/" + year);
                                    }
                                    else {
                                        et_date_receipt.setText(day + "/" + (month + 1) + "/" + year);
                                    }
                                }
                                else if (view == et_date_expensePaid) {
                                    if (day >= 1 && day <= 9)
                                    {
                                        et_date_expensePaid.setText("0" + day + "/" + (month + 1) + "/" + year);
                                    }
                                    else {
                                        et_date_expensePaid.setText(day + "/" + (month + 1) + "/" + year);
                                    }
                                }
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void btnInsertImageOnClick(View view) {

        image_view = findViewById(R.id.image_viewImage);
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT );
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                uriText = imageUri.toString();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(UpdateDeleteActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(UpdateDeleteActivity.this, "No image has been selected. Try again",Toast.LENGTH_LONG).show();
        }
    }

    public void btnUpdateOnClick (View view) {

        if (et_expense_total.getText().toString().isEmpty()) {
            Toast.makeText(UpdateDeleteActivity.this, "Please enter expense total", Toast.LENGTH_SHORT).show();
        }
        else if (et_date_receipt.getText().toString().equals("Pick Date")) {
            Toast.makeText(UpdateDeleteActivity.this, "Please enter the Date of Receipt", Toast.LENGTH_LONG).show();
        }
        else if (spinner_expense_claimed.getSelectedItem().toString().equals("False")
                && !(et_date_expensePaid.getText().toString().equals("Pick Date"))) {
            Toast.makeText(UpdateDeleteActivity.this, "Please set expense claimed to true.", Toast.LENGTH_LONG).show();

        }
        else if (spinner_expense_claimed.getSelectedItem().toString().equals("True")
                && (et_date_expensePaid.getText().toString().equals("Pick Date"))) {
            Toast.makeText(UpdateDeleteActivity.this, "You cannot claim the expense without setting a date", Toast.LENGTH_LONG).show();
        }
        else {
            databaseHelper.updateExpenseMember(expenseModel.getID(),
                    spinner_expense_claimed.getSelectedItem().toString(),
                    et_expense_total.getText().toString(),
                    spinner_VAT_included.getSelectedItem().toString(),
                    et_date_receipt.getText().toString(),
                    expenseModel.getDate_AddedToApp(),
                    et_date_expensePaid.getText().toString(),
                    et_text_summary.getText().toString(),
                    uriText);

            Toast.makeText(UpdateDeleteActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateDeleteActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void btnDeleteOnClick (View view) {
        databaseHelper.deleteExpenseMember(expenseModel.getID());

        Toast.makeText(UpdateDeleteActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateDeleteActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
