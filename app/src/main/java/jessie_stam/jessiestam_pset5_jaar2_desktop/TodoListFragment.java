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
import java.util.HashMap;

/**
 * Created by Jessie on 7-10-2016.
 */

public class TodoListFragment extends ListFragment {

    ArrayAdapter todolist_adapter;
    ArrayList<TodoItem> todo_items_list;
    ArrayList<String> todo_list_list;
    ArrayList<HashMap<String, String>> db_list;
    TodoManager todo_manager;
    DBHelper db_helper;

    public static TodoListFragment newInstance() {

        TodoListFragment fragment = new TodoListFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.todolist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //main = new MainActivity();
        todo_manager = TodoManager.getOurInstance();

        db_helper = new DBHelper(getActivity());

        todo_items_list = new ArrayList<>();
        todo_list_list = todo_manager.getListTitleStrings();

        // Populate list with our array of titles.
        todolist_adapter = new ArrayAdapter<>(getActivity(), R.layout.listview_layout,
                R.id.listview_text, todo_list_list);

        setListAdapter(todolist_adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position,
                                           long id) {

                // remove the item at the touched position and update data
                String remove_list = (String) adapterView.getItemAtPosition(position);

                ArrayList<String> delete_items = new ArrayList<>();

                todo_items_list = todo_manager.getItemList();

                if (todo_items_list != null) {
                    for (TodoItem item : todo_items_list) {
                        if (item.getTodoList().equals(remove_list)) {
                            //todo_manager.deleteItem(item.getTitle());

                            delete_items.add(item.getTitle());

                            int remove_id = item.getId();
                            db_helper.delete(remove_id);
                        }
                    }

                    for (String item : delete_items) {
                        todo_manager.deleteItem(item);
                    }
                }

                todo_manager.deleteList(remove_list);
                todo_list_list.remove(remove_list);
                todolist_adapter.notifyDataSetChanged();

                return true;
            }
        });

    }

    @Override
    public void onListItemClick(ListView screen_list_list, View view, int position, long id) {
        super.onListItemClick(screen_list_list, view, position, id);

        // save title, move to second
        String clicked_list = (String) screen_list_list.getItemAtPosition(position);

        // MainActivity activity = (MainActivity) getActivity();

        Intent moveToSecond = new Intent(getActivity(), SecondActivity.class);
        moveToSecond.putExtra("list_title", clicked_list);

        TodoListFragment.this.startActivity(moveToSecond);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        todo_manager.clearListList();
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
//            todolist_adapter.notifyDataSetChanged();
//
//            // recreate TodoItem and put in list
////            TodoItem new_item = todo_manager.createItem(retrieved_list, retrieved_title);
////            new_item.setId(Integer.parseInt(retrieved_id));
////            new_item.setCurrentStatus(retrieved_status);
//        }

//        }

}