package com.liam.assignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // TODO: Expense claimed needs to be changed
    // TODO: Date receipt added to app needs to be changed by inserting the current date when adding a new receipt
    // TODO: Clean MainActivity.java onCreate method, a lot of unused/reused variables
    // TODO: ADD Image view
    // TODO: When selecting VAT, maybe increase added total amount by the VAT percentage
    // TODO: VAT TOTAL with Total

    private ListView listView;
    private ArrayList<ExpenseModel> expenseModelArrayList;
    private ExpenseCustomAdapter expenseCustomAdapter;
    private DatabaseHelper databaseHelper;
    //private Spinner spinner_filterExpensePaid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_expense_members);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.filter_expense_paid,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_filter = (Spinner) findViewById(R.id.spinner_filterExpenseClaimed);
        spinner_filter.setAdapter(arrayAdapter);

        listView = (ListView) findViewById(R.id.list_view);

        databaseHelper = new DatabaseHelper(this);

        expenseModelArrayList = databaseHelper.getAllExpenseMembers();

        expenseCustomAdapter = new ExpenseCustomAdapter(this, expenseModelArrayList);
        listView.setAdapter(expenseCustomAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UpdateDeleteActivity.class);

                intent.putExtra("expense", expenseModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_get_all_expense_members);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.filter_expense_paid,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner_filter = (Spinner) findViewById(R.id.spinner_filterExpenseClaimed);
        spinner_filter.setAdapter(arrayAdapter);

        listView = (ListView) findViewById(R.id.list_view);

        databaseHelper = new DatabaseHelper(this);

        expenseModelArrayList = databaseHelper.getAllExpenseMembers();

        expenseCustomAdapter = new ExpenseCustomAdapter(this, expenseModelArrayList);
        listView.setAdapter(expenseCustomAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UpdateDeleteActivity.class);

                intent.putExtra("expense", expenseModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_item_insert_expense) {
            Intent intent2 = new Intent(this, InsertExpense.class);
            startActivity(intent2);
        }
        else if (id == R.id.menu_item_deleteAll_expense) {
            databaseHelper.deleteAll();

            Toast.makeText(MainActivity.this, "Deleted All Expenses Successfully.", Toast.LENGTH_SHORT).show();
            Intent intent3 = new Intent(MainActivity.this, MainActivity.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent3);
        }
        else if (id == R.id.menu_item_exit) {
            this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    private class FilterExpenses {


    }
}
