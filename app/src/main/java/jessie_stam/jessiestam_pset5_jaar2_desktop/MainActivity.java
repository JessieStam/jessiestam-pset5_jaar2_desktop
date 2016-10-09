package jessie_stam.jessiestam_pset5_jaar2_desktop;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {

    TodoManager todo_manager;
    String todo_list_title;
    EditText user_list_input;
    FrameLayout fragment_container;
    FragmentManager fragment_manager;
    FragmentTransaction fragment_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment();

        user_list_input = (EditText) findViewById(R.id.user_list_input);

        // construct new manager
        todo_manager = TodoManager.getOurInstance();


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
}
