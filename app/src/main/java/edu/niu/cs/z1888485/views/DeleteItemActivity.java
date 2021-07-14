package edu.niu.cs.z1888485.views;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  DeleteItemActivity                             *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: DeleteItemActivity is used to delete items from    *
 *          the list. The methods defined are onCreate and     *
 *          displayDataOnScreen.                               *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import edu.niu.cs.z1888485.R;
import edu.niu.cs.z1888485.model.DatabaseHelper;
import edu.niu.cs.z1888485.model.Item;
import edu.niu.cs.z1888485.controller.MyAdapter;

import java.util.ArrayList;

public class DeleteItemActivity extends AppCompatActivity {

    Button backButton;
    ListView listView;
    ArrayAdapter<Item> adapter;
    TextView headingTextView;
    DatabaseHelper databaseHelper;

    /****************************************************************
     *                                                              *
     * Method Name:  onCreate                                       *
     *                                                              *
     *                                                              *
     * Purpose: This method has displayDataOnScreen which displays  *
     *         the list of items. It also has backButton to navigate*
     *         to previous screen. OnClickListener is used here to  *
     *         delete the item after selecting.                     *
     *                                                              *
     ***************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        listView = findViewById(R.id.list_view);
        headingTextView = findViewById(R.id.heading_text_view);
        databaseHelper = new DatabaseHelper(this);

        displayDataOnScreen();
        // backButton is used to navigate to previous screen
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // delete item on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                ArrayList<Item> itemDataArrayList = databaseHelper.retrieveItems();

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                databaseHelper.deleteItem(itemDataArrayList.get(position));
                                displayDataOnScreen();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                // Pop up message asking Yes or No to delete item.
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteItemActivity.this);
                builder.setMessage("delete item: "+ itemDataArrayList.get(position)
                        .getDescription() + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                displayDataOnScreen();
            }
        });
    }

    /****************************************************************
     *                                                              *
     * Method Name:  displayDataOnScreen                            *
     *                                                              *
     *                                                              *
     * Purpose: This method is used to display items to delete.     *
     *          If list is empty it will show No item added.        *
     *                                                              *
     ***************************************************************/
    private void displayDataOnScreen() {
        ArrayList<Item> itemDataArrayList = databaseHelper.retrieveItems();

        if(itemDataArrayList.isEmpty()){
            headingTextView.setText(R.string.list_is_empty);
        }else{
            headingTextView.setText(R.string.click_on_item_to_delete);
        }

        for(Item item : itemDataArrayList){
            Log.i("datais", item.getDescription());
        }
        adapter = new MyAdapter(this,  R.layout.item_list_view_radio_button, itemDataArrayList);
        listView.setAdapter(adapter);
    }
}