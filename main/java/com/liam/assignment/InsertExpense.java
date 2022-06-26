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

public class InsertExpense extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText et_expense_total,
            et_date_receipt, et_date_addedToApp,
            et_text_summary;
    private Spinner spinner_VAT_included;
    private DatePickerDialog datePickerDialog;
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView image_view;
    private String uriText;
    private final Calendar calendar = Calendar.getInstance();
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private int month = calendar.get(Calendar.MONTH);
    private int year = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_expense);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.trueorfalse,
                android.R.layout.simple_spinner_item);


        // setting ArrayAdapter for 'trueorfalse' spinner
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner set_spinner_VAT_included = (Spinner) findViewById(R.id.spinner_VAT_included);
        set_spinner_VAT_included.setAdapter(arrayAdapter);

        selectDate(findViewById(R.id.et_date_receipt));

        databaseHelper = new DatabaseHelper(this);

        et_expense_total = (EditText) findViewById(R.id.et_expense_total);
        spinner_VAT_included = (Spinner) findViewById(R.id.spinner_VAT_included);
        et_date_receipt = (EditText) findViewById(R.id.et_date_receipt);
        et_date_addedToApp = (EditText) findViewById(R.id.et_date_addedToApp);
        et_text_summary = (EditText) findViewById(R.id.et_text_summary);

        et_date_addedToApp.setText(day + "/" + (month + 1) + "/" + year);
    }

    public void btnInsertImageOnClick(View view) {

        image_view = findViewById(R.id.image_viewImage);
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
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
                Toast.makeText(InsertExpense.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(InsertExpense.this, "No image has been selected. Try again",Toast.LENGTH_LONG).show();
        }
    }

    // listener function which is stored within the 'selectDate' method
    // which is called when any dates are selected via the 'EditText'
    // date picker
    public void selectDate(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                datePickerDialog = new DatePickerDialog(InsertExpense.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                if (view == et_date_receipt) {
                                    et_date_receipt.setText(day + "/" + (month + 1) + "/" + year);
                                }
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    public void btnStoreOnClick(View view) {

        if (et_expense_total.getText().toString().isEmpty()) {
            Toast.makeText(InsertExpense.this, "Please enter expense total", Toast.LENGTH_SHORT).show();
        }
        else if (et_date_receipt.getText().toString().equals("Pick Date")) {
            Toast.makeText(InsertExpense.this, "Please enter the Date of Receipt", Toast.LENGTH_LONG).show();
        }
        else {
            databaseHelper.addExpenseMember(
                    "False",
                    et_expense_total.getText().toString(),
                    spinner_VAT_included.getSelectedItem().toString(),
                    et_date_receipt.getText().toString(),
                    et_date_addedToApp.getText().toString(),
                    "Date Expense Paid: Not Paid",
                    et_text_summary.getText().toString(),
                    uriText);

            Toast.makeText(InsertExpense.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
