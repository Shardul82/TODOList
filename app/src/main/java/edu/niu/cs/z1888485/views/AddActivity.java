package edu.niu.cs.z1888485.views;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  AddActivity                                    *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: AddActivity is used to add items into the list.    *
 *          The methods defined are onCreate, hideKeyboard and *
 *          displayDataOnScreen                                *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.niu.cs.z1888485.R;
import edu.niu.cs.z1888485.model.DatabaseHelper;
import edu.niu.cs.z1888485.model.Item;
import edu.niu.cs.z1888485.controller.MyAdapter;

import java.util.ArrayList;

import static edu.niu.cs.z1888485.R.string.error_adding_data;
import static edu.niu.cs.z1888485.R.string.error_empty_edit_text;
import static edu.niu.cs.z1888485.R.string.new_item_added;

public class AddActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Button addButton;
    Button backButton;
    EditText enterItemEditText;
    ListView listView;
    ArrayAdapter<Item> adapter;
    TextView headingTextView;

    /****************************************************************
     *                                                              *
     * Method Name:  onCreate                                       *
     *                                                              *
     *                                                              *
     * Purpose: This method has displayDataOnScreen which displays  *
     *         the list of items. It also has backButton to navigate*
     *         to previous screen. OnClickListener is used here to  *
     *         add the item after typing in EditText field. It has  *
     *         two buttons ADD and BACK. After adding new item it   *
     *         will display that item in list format below.         *
     *                                                              *
     ***************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addButton = findViewById(R.id.add_button);
        backButton = findViewById(R.id.back_button);
        enterItemEditText = findViewById(R.id.item_edit_text);
        listView = findViewById(R.id.list_view);
        headingTextView = findViewById(R.id.heading_text_view);
        databaseHelper = new DatabaseHelper(this);

        // Add item in the list
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = enterItemEditText.getText().toString();
                if(itemText.isEmpty()){
                    Toast.makeText(AddActivity.this, error_empty_edit_text, Toast.LENGTH_SHORT).show();
                }else{
                  Boolean isInsertionSuccessful =  databaseHelper.insertItem(itemText);
                  if(isInsertionSuccessful) {
                      Toast.makeText(AddActivity.this, new_item_added, Toast.LENGTH_SHORT).show();
                      enterItemEditText.getText().clear();
                      hideKeyboard(AddActivity.this);
                      displayDataOnScreen();
                  }else{
                      Toast.makeText(AddActivity.this, error_adding_data, Toast.LENGTH_SHORT).show();
                  }
                }
            }
        });

        // backButton to navigate to previous screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hideKeyboard(AddActivity.this);
        displayDataOnScreen();
    }

    /****************************************************************
     *                                                              *
     * Method Name:  hideKeyboard                                   *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to hide keyboard after typing   *
     *          in the EditText field.                              *
     *                                                              *
     ***************************************************************/
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /****************************************************************
     *                                                              *
     * Method Name:  displayDataOnScreen                            *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to display items after adding.  *
     *          If list is empty it will show No item added.        *
     *                                                              *
     ***************************************************************/
    private void displayDataOnScreen() {
        ArrayList<Item> itemDataArrayList = databaseHelper.retrieveItems();

        if(itemDataArrayList.isEmpty()){
            headingTextView.setText(R.string.list_is_empty);
        }else{
            headingTextView.setText(R.string.item_is_not_empty);
        }

        for(Item item : itemDataArrayList){
            Log.i("datais", item.getDescription());
        }
        adapter = new MyAdapter(this,  R.layout.item_list_view, itemDataArrayList);
        listView.setAdapter(adapter);
    }
}