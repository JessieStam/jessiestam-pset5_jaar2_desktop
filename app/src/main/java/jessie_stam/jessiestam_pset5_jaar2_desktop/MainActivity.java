package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView screen_list_list;
    DBHelper db_helper;
    ArrayAdapter todo_adapter;
    ArrayList<String> todo_list_list;
    TodoManager todo_manager;
    String todo_list_title;
    EditText user_list_input;
    String clicked_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen_list_list = new ListView(this);
        screen_list_list = (ListView) findViewById(R.id.todo_list_list);

        user_list_input = (EditText) findViewById(R.id.user_list_input);

        todo_list_list = new ArrayList<>();

        // initialize new DBHelper object
        db_helper = new DBHelper(this);

        // create new adapter and set to listview
        todo_adapter = new ArrayAdapter<>
                (this, R.layout.listview_layout,R.id.listview_text, todo_list_list);
        screen_list_list.setAdapter(todo_adapter);

        // construct new manager
        todo_manager = TodoManager.getOurInstance();

        /**
         * When item is clicked, go to Second Activity
         */
        screen_list_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clicked_list = (String) parent.getItemAtPosition(position);

                // move to SecondActivity
                Intent listItems = new Intent(view.getContext(), SecondActivity.class);

                listItems.putExtra("list_name", clicked_list);

                startActivity(listItems);
            }
        });

    }



    public void addNewList(View view) {

        // get item for the list and add to listview
        todo_list_title = user_list_input.getText().toString();
        todo_list_list.add(todo_list_title);
        todo_adapter.notifyDataSetChanged();

        // create new TodoItem object and add to list
        todo_manager.createList(todo_list_title);

        // add item to the SQLite
        //db_helper.create(new_item);

        // clear the input line after text is added
        user_list_input.getText().clear();

    }
}
