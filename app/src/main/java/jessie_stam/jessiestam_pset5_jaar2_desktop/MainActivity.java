package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {

    //ListView screen_list_list;
    DBHelper db_helper;
    //ArrayAdapter todo_adapter;
    //ArrayList<String> todo_list_list;
    TodoManager todo_manager;
    String todo_list_title;
    EditText user_list_input;
    //String clicked_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //screen_list_list = new ListView(this);
        //screen_list_list = (ListView) findViewById(R.id.todo_list_list);

        user_list_input = (EditText) findViewById(R.id.user_list_input);

        //todo_list_list = new ArrayList<>();

        // initialize new DBHelper object
        db_helper = new DBHelper(this);

        // construct new manager
        todo_manager = TodoManager.getOurInstance();

    }

    public void addNewList(View view) {

        // get item for the list and add to listview
        todo_list_title = user_list_input.getText().toString();
        //todo_list_list.add(todo_list_title);
        //todo_adapter.notifyDataSetChanged();

        // create new TodoItem object and add to list
        todo_manager.createList(todo_list_title);

        // add item to the SQLite
        //db_helper.create(new_item);

        // clear the input line after text is added
        user_list_input.getText().clear();

    }

    public void deleteList(String delete_list) {

        todo_manager.deleteList(delete_list);

    }

    public void moveToSecond(View view, String clicked_list) {

        // move to SecondActivity
        Intent listItems = new Intent(view.getContext(), SecondActivity.class);

        listItems.putExtra("list_name", clicked_list);
        startActivity(listItems);

    }

    // maak change listview class - gebruik daar fragment
}
