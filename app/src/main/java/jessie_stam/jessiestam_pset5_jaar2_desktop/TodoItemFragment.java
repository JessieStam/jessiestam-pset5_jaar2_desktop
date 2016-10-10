package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
 * Many TodoLists - TodoItemFragment
 *
 * Jessie Stam
 *
 * Displays a list of to-do item. On long-click, an item is removed from the ListView and databases.
 * When an item is clicked, the color changes to show that the to-do has been finished.
 */

public class TodoItemFragment extends ListFragment {

    ArrayAdapter todoitem_adapter;
    ArrayList<String> todo_item_list;
    TodoManager todo_manager;
    String list_title;
    DBHelper db_helper;
    ArrayList<HashMap<String, String>> db_list;
    ListView list_view;
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
        // Inflate the layout for this fragment, fetch to-do list title from Second Activity
        Bundle title = getArguments();
        list_title = title.getString("list_title");

        return inflater.inflate(R.layout.todoitem_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // construct Manager, Database, ArrayList to put into the ListView, and its Adapter
        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(getActivity());
        db_list = new ArrayList<>();

        todo_item_list = todo_manager.getItemTitleStrings(list_title);

        // Populate list with our array of titles.
        todoitem_adapter = new ArrayAdapter<>(getActivity(), R.layout.listview_layout,
                R.id.listview_text, todo_item_list);

        setListAdapter(todoitem_adapter);

        /**
         * When item is long-clicked, delete it
         */
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position,
                                           long id) {

                // construct string and id for item to be deleted
                String remove_item = (String) adapterView.getItemAtPosition(position);
                int remove_id = todo_manager.getTodoItem(remove_item).getId();

                // delete item from Manager, database and ListView and update ListView
                todo_manager.deleteItem(remove_item);
                todo_item_list.remove(remove_item);
                todoitem_adapter.notifyDataSetChanged();

                db_helper.delete(remove_id);

                return true;
            }
        });
    }

    /**
     * When list item is clicked, change its background color to "complete" the to-do
     */
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
                }
            }
        }

        // change the background color of clicked item according to status
        if (current_status != null) {
            changeItemColor(view, current_status);
        }

        // update status of clicked item in SQLite database
        db_helper.update(todo_manager.getTodoItem(clicked_item));
    }

    /**
     * Changes the background color of to-do item
     */
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
}
