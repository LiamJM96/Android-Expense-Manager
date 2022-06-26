package com.liam.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "expenses_database.db";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_EXPENSE = "expense";
    private static final String KEY_ID = "_id";
    private static final String KEY_EXPENSE_CLAIMED_COLUMN = "EXPENSE_CLAIMED_COLUMN";
    private static final String KEY_EXPENSE_TOTAL_COLUMN = "EXPENSE_TOTAL_COLUMN";
    private static final String KEY_VAT_INCLUDED_COLUMN = "VAT_INCLUDED_COLUMN";
    private static final String KEY_DATE_RECEIPT_COLUMN = "DATE_RECEIPT_COLUMN";
    private static final String KEY_DATE_ADDEDTOAPP_COLUMN = "DATE_ADDEDTOAPP_COLUMN";
    private static final String KEY_DATE_EXPENSEPAID_COLUMN = "DATE_EXPENSEPAID_COLUMN";
    private static final String KEY_TEXT_SUMMARY_COLUMN = "TEXT_SUMMARY_COLUMN";
    private static final String KEY_IMAGE_URI_COLUMN = "IMAGE_URI_COLUMN";

    private static final String CREATE_DATABASE_TABLE_EXPENSE = "CREATE TABLE "
            + DATABASE_TABLE_EXPENSE + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_EXPENSE_CLAIMED_COLUMN + " TEXT,"
            + KEY_EXPENSE_TOTAL_COLUMN + " TEXT,"
            + KEY_VAT_INCLUDED_COLUMN + " TEXT,"
            + KEY_DATE_RECEIPT_COLUMN + " TEXT,"
            + KEY_DATE_ADDEDTOAPP_COLUMN + " TEXT,"
            + KEY_DATE_EXPENSEPAID_COLUMN + " TEXT,"
            + KEY_TEXT_SUMMARY_COLUMN + " TEXT,"
            + KEY_IMAGE_URI_COLUMN + " TEXT )";

    // constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    // Create SQL Table onCreate
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE_TABLE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS '" + DATABASE_TABLE_EXPENSE + "'");
        onCreate(db);
    }

    public void addExpenseMember(String expense_claimed, String expense_total, String VAT_included,
                                 String date_receipt, String date_addedToApp, String date_expensePaid,
                                 String text_summary, String uriText) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_EXPENSE_CLAIMED_COLUMN, expense_claimed);
        newValues.put(KEY_EXPENSE_TOTAL_COLUMN, expense_total);
        newValues.put(KEY_VAT_INCLUDED_COLUMN, VAT_included);
        newValues.put(KEY_DATE_RECEIPT_COLUMN, date_receipt);
        newValues.put(KEY_DATE_ADDEDTOAPP_COLUMN, date_addedToApp);
        newValues.put(KEY_DATE_EXPENSEPAID_COLUMN, date_expensePaid);
        newValues.put(KEY_TEXT_SUMMARY_COLUMN, text_summary);
        newValues.put(KEY_IMAGE_URI_COLUMN, uriText);
        db.insert(DATABASE_TABLE_EXPENSE, null, newValues);
    }

    public ArrayList<ExpenseModel> getAllExpenseMembers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseModel> ExpenseModelArrayList = new ArrayList<ExpenseModel>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_EXPENSE;
        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            ExpenseModel expenseModel = new ExpenseModel();

            expenseModel.setID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            expenseModel.setExpense_Claimed(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_EXPENSE_CLAIMED_COLUMN))));
            expenseModel.setExpense_Total(cursor.getDouble(cursor.getColumnIndex(KEY_EXPENSE_TOTAL_COLUMN)));
            expenseModel.setVAT_Included(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_VAT_INCLUDED_COLUMN))));
            expenseModel.setDate_Receipt(cursor.getString(cursor.getColumnIndex(KEY_DATE_RECEIPT_COLUMN)));
            expenseModel.setDate_AddedToApp(cursor.getString(cursor.getColumnIndex(KEY_DATE_ADDEDTOAPP_COLUMN)));
            expenseModel.setDate_ExpensePaid(cursor.getString(cursor.getColumnIndex(KEY_DATE_EXPENSEPAID_COLUMN)));
            expenseModel.setText_summary(cursor.getString(cursor.getColumnIndex(KEY_TEXT_SUMMARY_COLUMN)));
            expenseModel.setUriText(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URI_COLUMN)));

            ExpenseModelArrayList.add(expenseModel);

        }
        cursor.close();
        return ExpenseModelArrayList;
    }

    public void updateExpenseMember(int id, String expense_claimed, String expense_total,
                                    String VAT_included, String date_receipt, String date_addedToApp,
                                    String date_expensePaid, String text_summary, String uriText) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(KEY_EXPENSE_CLAIMED_COLUMN, expense_claimed);
        updatedValues.put(KEY_EXPENSE_TOTAL_COLUMN, expense_total);
        updatedValues.put(KEY_VAT_INCLUDED_COLUMN, VAT_included);
        updatedValues.put(KEY_DATE_RECEIPT_COLUMN, date_receipt);
        updatedValues.put(KEY_DATE_ADDEDTOAPP_COLUMN, date_addedToApp);
        updatedValues.put(KEY_DATE_EXPENSEPAID_COLUMN, date_expensePaid);
        updatedValues.put(KEY_TEXT_SUMMARY_COLUMN, text_summary);
        updatedValues.put(KEY_IMAGE_URI_COLUMN, uriText);
        String where = KEY_ID + " = ?";
        String whereArgs[] = new String[]{String.valueOf(id)};
        db.update(DATABASE_TABLE_EXPENSE, updatedValues, where, whereArgs);
    }

    public void deleteExpenseMember(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_EXPENSE, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DATABASE_TABLE_EXPENSE);
    }

}
