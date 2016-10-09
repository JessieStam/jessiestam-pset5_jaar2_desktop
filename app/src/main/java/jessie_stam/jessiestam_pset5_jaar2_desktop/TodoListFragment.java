package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Jessie on 7-10-2016.
 */

public class TodoListFragment extends ListFragment {

    ArrayAdapter todolist_adapter;
    MainActivity main;
    //ListView screen_list;
    ArrayList<String> todo_list_list;
    TodoManager todo_manager;

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }

    public static TodoListFragment newInstance() {

        TodoListFragment fragment = new TodoListFragment();
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("test", "fragment created");

        return inflater.inflate(R.layout.todolist_fragment, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // plaats hier de lijst in de listview (nadat de lijst is opgehaald

        main = new MainActivity();

        Log.d("test", "activity created");

        todo_manager = TodoManager.getOurInstance();

        todo_list_list = todo_manager.getListTitleStrings();

        if (todo_list_list.size() != 0) {
            Log.d("test", "todo_list_list is niet leeg");
        }
        else {
            Log.d("test", "todo_list_list is leeg");

        }

        Log.d("test", "printing todo_list_list now");

        for (String list : todo_list_list) {
            Log.d("test", "todo_list_list title: " + list);
        }

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

                //todo_list_list = todo_manager.getListTitleStrings();

                for (String list : todo_list_list) {
                    Log.d("test", "todo_list_list_title after click: " + list);
                }

                Log.d("test", "clicked item: " + remove_list);

                todo_manager.deleteList(remove_list);

                todo_list_list.remove(remove_list);

                for (String list : todo_list_list) {
                    Log.d("test", "todo_list_list_title after remove: " + list);
                }

                todolist_adapter.notifyDataSetChanged();

                return true;
            }
        });

    }

    public void refreshList(ArrayList<String> list){



    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        todo_list_list = todo_manager.getListTitleStrings();
//
//        if (todo_list_list.size() != 0) {
//            Log.d("test", "todo_list_list is niet leeg");
//        }
//        else {
//            Log.d("test", "todo_list_list is leeg");
//        }
//
//        // Populate list with our static array of titles.
//        setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.listview_layout,
//                R.id.listview_text, todo_list_list));
//
//    }

//    @Override
//    public void onListItemClick(ListView screen_list_list, View view, int position, long id) {
//        super.onListItemClick(screen_list_list, view, position, id);
//
//        // save title, move to second
//        String clicked_list = (String) screen_list_list.getItemAtPosition(position);
//        main.moveToSecond(view, clicked_list);
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}