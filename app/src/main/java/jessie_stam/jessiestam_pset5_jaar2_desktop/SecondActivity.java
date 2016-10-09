package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Jessie on 4-10-2016.
 */

public class SecondActivity extends MainActivity {

    TextView todo_list_title;
    String list_title;
    String todo_list_item;
    EditText user_item_input;
    TodoManager todo_manager;
    DBHelper db_helper;

    FrameLayout fragment_container;
    FragmentManager fragment_manager;
    FragmentTransaction fragment_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        refreshFragment();

        Bundle extras = getIntent().getExtras();
        list_title = extras.getString("list_title");

        todo_list_title = (TextView) findViewById(R.id.todo_list_list_title);
        todo_list_title.setText(list_title);

        user_item_input = (EditText) findViewById(R.id.user_item_input);

        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(this);

    }

    public void addNewItem(View view) {

        todo_list_item = user_item_input.getText().toString();

        // create new TodoItem object and add to list
        TodoItem new_item = todo_manager.createItem(list_title, todo_list_item);

        // add item to the SQLite
        db_helper.create(new_item);

        // clear the input line after text is added
        user_item_input.getText().clear();

        refreshFragment();

    }

//    public String addListTitle() {
//        return list_title;
//    }

//    public void createFragment() {
//
//        fragment_manager = getSupportFragmentManager();
//        fragment_transaction = fragment_manager.beginTransaction();
//
//        TodoItemFragment fragment = TodoItemFragment.newInstance();
//        Bundle title = new Bundle();
//
//        title.putString("list_title", list_title);
//        fragment.setArguments(title);
//
//        fragment_transaction.add(R.id.todo_item_list, fragment).commit();
//    }

    public void refreshFragment() {

        fragment_manager = getSupportFragmentManager();
        fragment_transaction = fragment_manager.beginTransaction();

        TodoItemFragment fragment = TodoItemFragment.newInstance();
        Bundle title = new Bundle();

        title.putString("list_title", list_title);
        fragment.setArguments(title);

        fragment_transaction.replace(R.id.todo_item_list, fragment).commit();

    }


}
