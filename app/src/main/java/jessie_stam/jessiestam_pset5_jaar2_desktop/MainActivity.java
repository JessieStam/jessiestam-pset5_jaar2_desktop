package jessie_stam.jessiestam_pset5_jaar2_desktop;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

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

        createFragment();

        user_list_input = (EditText) findViewById(R.id.user_list_input);

        // construct new manager
        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(this);
        db_list = new ArrayList<>();

    }

    public void addNewList(View view) {

        // get item for the list and add to listview
        todo_list_title = user_list_input.getText().toString();

        // create new TodoItem object and add to list
        todo_manager.createList(todo_list_title);

        // clear the input line after text is added
        user_list_input.getText().clear();

        refreshFragment();

    }

    public void createFragment() {

        //list_fragment = new TodoListFragment();
        if (fragment_container == null) {
            fragment_container = new FrameLayout(this);
            fragment_container = (FrameLayout) findViewById(R.id.todo_list_list);

            fragment_manager = getSupportFragmentManager();
            fragment_transaction = fragment_manager.beginTransaction();
        }
        fragment_transaction.add(R.id.todo_list_list, TodoListFragment.newInstance()).commit();

    }

    public void refreshFragment() {

        fragment_manager = getSupportFragmentManager();
        fragment_transaction = fragment_manager.beginTransaction();

        fragment_transaction.replace(R.id.todo_list_list, TodoListFragment.newInstance()).commit();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        todo_manager.clearLists();
//
//        // read SQLite database
//        db_list = db_helper.read_item();
//
//        // iterate over TodoItems in databases
//        for (HashMap<String, String> hashmap : db_list) {
//
//            // save id, title and status
//            //String retrieved_id = hashmap.get("_id");
//            String retrieved_list = hashmap.get("todo_list");
////            String retrieved_title = hashmap.get("todo_text");
////            String retrieved_status = hashmap.get("current_status");
//
//            todo_manager.createList(retrieved_list);
//
////            // recreate TodoItem and put in list
////            TodoItem new_item = todo_manager.createItem(retrieved_list, retrieved_title);
////            new_item.setId(Integer.parseInt(retrieved_id));
////            new_item.setCurrentStatus(retrieved_status);
//
//        }
//    }
}

