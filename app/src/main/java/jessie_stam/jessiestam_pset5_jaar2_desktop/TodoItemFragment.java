package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jessie on 7-10-2016.
 */

public class TodoItemFragment extends ListFragment {

    ArrayAdapter todoitem_adapter;
    ArrayList<String> todo_item_list;
    TodoManager todo_manager;
    String list_title;
    DBHelper db_helper;
    ArrayList<HashMap<String, String>> db_list;

    String update_todo;
    String current_status;

    public static TodoItemFragment newInstance() {

        TodoItemFragment fragment = new TodoItemFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle title = getArguments();
        list_title = title.getString("list_title");

        return inflater.inflate(R.layout.todoitem_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(getActivity());
        db_list = new ArrayList<>();

        todo_item_list = todo_manager.getItemTitleStrings(list_title);

        // Populate list with our array of titles.
        todoitem_adapter = new ArrayAdapter<>(getActivity(), R.layout.listview_layout,
                R.id.listview_text, todo_item_list);

        setListAdapter(todoitem_adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position,
                                           long id) {

                // remove the item at the touched position and update data
                String remove_item = (String) adapterView.getItemAtPosition(position);
                int remove_id = todo_manager.getTodoItem(remove_item).getId();

                todo_manager.deleteItem(remove_item);
                todo_item_list.remove(remove_item);
                todoitem_adapter.notifyDataSetChanged();

                //remove title from the SQLite
                db_helper.delete(remove_id);

                return true;
            }
        });

    }

    @Override
    public void onListItemClick(ListView screen_list_list, View view, int position, long id) {
        super.onListItemClick(screen_list_list, view, position, id);

        // read SQLite database to get status of clicked item
        db_list = db_helper.read_item();
        String clicked_item = (String) screen_list_list.getItemAtPosition(position);

        // iterate over TodoItems and its entries in database list
        for (HashMap<String, String> hashmap : db_list) {
            for (Map.Entry<String, String> hashmap_entry : hashmap.entrySet()) {

                //when matching TodoItem text for clicked item is found, get status
                if (hashmap_entry.toString().equals("todo_text=" + clicked_item)) {
                    update_todo = hashmap.get("todo_text");
                    current_status = hashmap.get("current_status");

                    Log.d("test", "current_status of " + hashmap_entry.toString() + "is " + current_status);
                }
            }
        }

        // change the background color of clicked item
        if (current_status != null) {
            changeItemColor(view, current_status);
        }

        // update status of clicked item in SQLite database
        db_helper.update(todo_manager.getTodoItem(clicked_item));

    }

    public void changeItemColor(View view, String status) {

        switch(status) {
            case ("unfinished"):
                view.setBackgroundColor(Color.GRAY);
                break;
            case ("finished"):
                view.setBackgroundColor(Color.WHITE);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
