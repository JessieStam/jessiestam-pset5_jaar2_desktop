package jessie_stam.jessiestam_pset5_jaar2_desktop;

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
 * Created by Jessie on 7-10-2016.
 */

public class TodoItemFragment extends ListFragment {

    ArrayAdapter todoitem_adapter;
    ArrayList<String> todo_item_list;
    TodoManager todo_manager;
    String list_title;
    DBHelper db_helper;
    ArrayList<TodoItem> item_list;

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
                todo_manager.deleteItem(remove_item);
                todo_item_list.remove(remove_item);
                todoitem_adapter.notifyDataSetChanged();

                //remove title from the SQLite
                int remove_id = getTodoItem(remove_item).getId();

                db_helper.delete(remove_id);

                return true;
            }
        });

    }

    @Override
    public void onListItemClick(ListView screen_list_list, View view, int position, long id) {
        super.onListItemClick(screen_list_list, view, position, id);



    }

    public TodoItem getTodoItem(String item_name) {

        item_list = todo_manager.getItemList();

        for (TodoItem item : item_list) {
            if (item.getTitle().equals(item_name)) {
                return item;
            }
        }
        return null;
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
