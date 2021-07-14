package edu.niu.cs.z1888485.views;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  MainScreenActivity                             *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: MainScreenActivity is used to define methods like  *
 *          onCreateOptionsMenu, onOptionsItemSelected,        *
 *          displayDataOnScreen and starOverCalled.            *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.niu.cs.z1888485.R;
import edu.niu.cs.z1888485.model.DatabaseHelper;
import edu.niu.cs.z1888485.model.Item;
import edu.niu.cs.z1888485.controller.MyAdapter;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listView;
    ArrayAdapter<Item> adapter;
    TextView headingTextView;
    /****************************************************************
     *                                                              *
     * Method Name:  onCreate                                       *
     *                                                              *
     *                                                              *
     * Purpose: This method has displayDataOnScreen which displays  *
     *          the list of selected items. If nothing selected it  *
     *          will show no items. It also has option to add and   *
     *          delete items.                                       *
     ***************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        listView = findViewById(R.id.list_view);
        headingTextView = findViewById(R.id.heading_text_view);
        databaseHelper = new DatabaseHelper(this);

        displayDataOnScreen();
    }

    /****************************************************************
     *                                                              *
     * Method Name:  onCreateOptionsMenu                            *
     *                                                              *
     *                                                              *
     * Purpose: This method will return true if items are added in  *
     *          menu.                                               *
     ***************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.database_menu, menu);
        return true;
    }

    /****************************************************************
     *                                                              *
     * Method Name:  onOptionsItemSelected                          *
     *                                                              *
     *                                                              *
     * Purpose: This method will handle item selection. It will     *
     *          start activity for option selected like add, delete *
     *          or start over.                                      *
     ***************************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(AddActivity.class);
                return true;
            case R.id.action_delete:
                startActivity(DeleteItemActivity.class);
                return true;
            case R.id.start_over:
                starOverCalled();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /****************************************************************
     *                                                              *
     * Method Name:  startActivity                                  *
     *                                                              *
     *                                                              *
     * Purpose: In this method, intent is created and it is used to *
     *          start activity.                                     *
     ***************************************************************/
    public void startActivity(Class<?> calledActivity) {
        Intent myIntent = new Intent(this, calledActivity);
        this.startActivity(myIntent);
    }

    /****************************************************************
     *                                                              *
     * Method Name:  displayDataOnScreen                            *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to retrieve items from list.    *
     *          If items are added it will show items, if not it    *
     *          will display message as no items added.             *
     ***************************************************************/
    private void displayDataOnScreen() {
        // to retrieve the items
        ArrayList<Item> itemDataArrayList = databaseHelper.retrieveItems();

        // check if list is empty or items added.
        if(itemDataArrayList.isEmpty()){
            headingTextView.setText(R.string.list_is_empty);
        }else{
            headingTextView.setText(R.string.item_is_not_empty);
        }

        for(Item item : itemDataArrayList){
            Log.i("datais", item.getDescription());
        }
        adapter = new MyAdapter(this,  R.layout.item_list_view_checkbox, itemDataArrayList);
        listView.setAdapter(adapter);
    }

    /****************************************************************
     *                                                              *
     * Method Name:  starOverCalled                                 *
     *                                                              *
     *                                                              *
     * Purpose: This method gets called when user selects to start  *
     *          over the list. It will delete add items and start   *
     *          fresh with empty list. OnClickListener is used to   *
     *          start the activity again. It will show toast message*
     *          if list is cleared or show error if it failed to    *
     *          delete the list items.                              *
     * ***************************************************************/
    private void starOverCalled(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Boolean resultOfOperation = databaseHelper.deleteAll();
                        if(resultOfOperation){
                            Toast.makeText(MainScreenActivity.this, "List cleared!", Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(MainScreenActivity.this, "Error occur!, try again!", Toast.LENGTH_SHORT).show();
                        }
                        displayDataOnScreen();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainScreenActivity.this);
        builder.setMessage("Clear entire list?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayDataOnScreen();
    }
}