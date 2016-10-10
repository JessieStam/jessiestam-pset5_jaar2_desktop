package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Many TodoLists - SecondActivity
 *
 * Jessie Stam
 *
 * Lets a user create a new to-do item. Uses a Fragment to display the list.
 */

public class SecondActivity extends MainActivity {

    TextView todo_list_title;
    String list_title;
    String todo_list_item;
    EditText user_item_input;
    TodoManager todo_manager;
    DBHelper db_helper;
    TodoItemFragment fragment;

    //FrameLayout fragment_container;
    FragmentManager fragment_manager;
    FragmentTransaction fragment_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // construct Fragment, EditText, Manager and Database
        createFragment();
        user_item_input = (EditText) findViewById(R.id.user_item_input);

        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(this);

        // fetch app title from TodoListFragment and print to screen
        Bundle extras = getIntent().getExtras();
        list_title = extras.getString("list_title");

        todo_list_title = (TextView) findViewById(R.id.todo_list_list_title);
        todo_list_title.setText(list_title);

        // refresh the Fragment
        refreshFragment();
    }


    /**
     * Adds new to-do item to the ListView
     */
    public void addNewItem(View view) {

        // get user's input for to-do item
        todo_list_item = user_item_input.getText().toString();

        // create new TodoItem object and add to list and SQLite database
        TodoItem new_item = todo_manager.createItem(list_title, todo_list_item);
        db_helper.create(new_item);

        // clear the input line after text is added and update Fragment
        user_item_input.getText().clear();
        refreshFragment();
    }

    /**
     * Creates a new instance of the Fragment class
     */
    public void createFragment() {

        fragment_manager = getSupportFragmentManager();
        fragment_transaction = fragment_manager.beginTransaction();

        fragment = TodoItemFragment.newInstance();

        // add to-do list title to the Fragment class
        Bundle title = new Bundle();

        title.putString("list_title", list_title);
        fragment.setArguments(title);

        fragment_transaction.add(R.id.todo_item_list, fragment).commit();
    }


    /**
     * Updates the Fragment class by replacing the old Fragment with a new instance of Fragment
     */
    public void refreshFragment() {

        fragment_manager = getSupportFragmentManager();
        fragment_transaction = fragment_manager.beginTransaction();

        TodoItemFragment fragment = TodoItemFragment.newInstance();
        Bundle title = new Bundle();

        title.putString("list_title", list_title);
        fragment.setArguments(title);

        fragment_transaction.replace(R.id.todo_item_list, fragment).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // reset list of TodoItem items and list of to-do item title strings
        todo_manager.clearItemList();

        // read SQLite database and iterate over TodoItems in database
        db_list = db_helper.read_item();

        for (HashMap<String, String> hashmap : db_list) {

            // save id, list, title and status
            String retrieved_id = hashmap.get("_id");
            String retrieved_list = hashmap.get("todo_list");
            String retrieved_title = hashmap.get("todo_text");
            String retrieved_status = hashmap.get("current_status");

            // recreate TodoList for every TodoList in database
            todo_manager.createList(retrieved_list);

            // recreate TodoItem and for every TodoItem in database
            TodoItem new_item = todo_manager.createItem(retrieved_list, retrieved_title);
            new_item.setId(Integer.parseInt(retrieved_id));
            new_item.setCurrentStatus(retrieved_status);
        }
        // update Fragment
        refreshFragment();
    }
}
