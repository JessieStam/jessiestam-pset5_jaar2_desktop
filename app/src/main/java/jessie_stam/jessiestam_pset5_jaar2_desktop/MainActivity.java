package jessie_stam.jessiestam_pset5_jaar2_desktop;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Many TodoLists - MainActivity
 *
 * Jessie Stam
 *
 * Lets the user create a new list in which to-do items can be stored. Uses a fragment to display
 * the list of to-do lists. When a list is clicked, the app moves on to second activity to give
 * users the option to add items to their list.
 */
public class MainActivity extends FragmentActivity {

    TodoManager todo_manager;
    String todo_list_title;
    EditText user_list_input;
    FrameLayout fragment_container;
    FragmentManager fragment_manager;
    FragmentTransaction fragment_transaction;
    DBHelper db_helper;
    ArrayList<HashMap<String, String>> db_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // construct Fragment, EditText, Manager and Database
        createFragment();
        user_list_input = (EditText) findViewById(R.id.user_list_input);

        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(this);
        db_list = new ArrayList<>();

    }

    /**
     * Adds new to-do list to the ListView
     */
    public void addNewList(View view) {

        // get user'ss input for to-do list title
        todo_list_title = user_list_input.getText().toString();

        // get list of existing to-do lists and check if the list does not already exist
        ArrayList<String> used_titles = todo_manager.getListTitleStrings();

        // if list already exists, let user know
        for (String title : used_titles) {
            if (todo_list_title.equals(title)) {
                Toast.makeText(this, "This list already exists!", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        // create new TodoItem object and add to list
        todo_manager.createList(todo_list_title);

        // clear the input line after text is added and update Fragment
        user_list_input.getText().clear();
        refreshFragment();

    }

    /**
     * Creates a new instance of the Fragment class
     */
    public void createFragment() {

        // if container is null construct it, begin transaction and add Fragment to container
        if (fragment_container == null) {
            fragment_container = new FrameLayout(this);
            fragment_container = (FrameLayout) findViewById(R.id.todo_list_list);

            fragment_manager = getSupportFragmentManager();
            fragment_transaction = fragment_manager.beginTransaction();
        }
        fragment_transaction.add(R.id.todo_list_list, TodoListFragment.newInstance()).commit();
    }


    /**
     * Updates the Fragment class by replacing the old Fragment with a new instance of Fragment
     */
    public void refreshFragment() {

        fragment_manager = getSupportFragmentManager();
        fragment_transaction = fragment_manager.beginTransaction();

        fragment_transaction.replace(R.id.todo_list_list, TodoListFragment.newInstance()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // reset list of TodoList items and list of to-do list title strings
        todo_manager.clearListList();

        // read SQLite database and iterate over TodoItems in the database
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

