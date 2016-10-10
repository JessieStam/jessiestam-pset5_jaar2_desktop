package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Many TodoLists - TodoListFragment
 *
 * Jessie Stam
 *
 * Displays a list of to-do lists. On long-click, a list is removed from the ListView and databases.
 * When a list is clicked, the app moves on to Second Activity and TodoItemFragment is opened.
 */
public class TodoListFragment extends ListFragment {

    ArrayAdapter todolist_adapter;
    ArrayList<TodoItem> todo_items_list;
    ArrayList<String> todo_list_list;
    TodoManager todo_manager;
    DBHelper db_helper;

    public static TodoListFragment newInstance() {

        TodoListFragment fragment = new TodoListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this Fragment
        return inflater.inflate(R.layout.todolist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // construct Manager, Database, ArrayList to put into the ListView, and its Adapter
        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(getActivity());

        todo_items_list = new ArrayList<>();
        todo_list_list = todo_manager.getListTitleStrings();

        todolist_adapter = new ArrayAdapter<>(getActivity(), R.layout.listview_layout,
                R.id.listview_text, todo_list_list);

        setListAdapter(todolist_adapter);

        /**
         * When list item is long-clicked, delete it
         */
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position,
                                           long id) {

                // construct string and list for item to be deleted
                String remove_list = (String) adapterView.getItemAtPosition(position);
                ArrayList<String> delete_items = new ArrayList<>();

                // iterate over TodoItems to get the items that are in the clicked list
                todo_items_list = todo_manager.getItemList();

                if (todo_items_list != null) {
                    for (TodoItem item : todo_items_list) {
                        // if items are in the clicked list, add to list of items to remove
                        if (item.getTodoList().equals(remove_list)) {

                            delete_items.add(item.getTitle());

                            // get id for items in list and remove from database
                            int remove_id = item.getId();
                            db_helper.delete(remove_id);
                        }
                    }

                    // delete items in the delete list from ListView
                    for (String item : delete_items) {
                        todo_manager.deleteItem(item);
                    }
                }

                // delete the clicked list from Manager and from ListView and update ListView
                todo_manager.deleteList(remove_list);
                todo_list_list.remove(remove_list);
                todolist_adapter.notifyDataSetChanged();

                return true;
            }
        });

    }

    /**
     * When list item is clicked, move to Second Activity
     */
    @Override
    public void onListItemClick(ListView screen_list_list, View view, int position, long id) {
        super.onListItemClick(screen_list_list, view, position, id);

        // save to-do list title, move to Second Activity
        String clicked_list = (String) screen_list_list.getItemAtPosition(position);

        Intent moveToSecond = new Intent(getActivity(), SecondActivity.class);
        moveToSecond.putExtra("list_title", clicked_list);

        TodoListFragment.this.startActivity(moveToSecond);
    }

}