package jessie_stam.jessiestam_pset5_jaar2_desktop;

import java.util.ArrayList;

/**
 * Created by Jessie on 4-10-2016.
 */

public class TodoManager {

    // define id and instance
    private Integer list_id = 0;
    private Integer item_id = 0;
    private static TodoManager ourInstance = null;
    private ArrayList<TodoList> todo_list_list = new ArrayList<>();
    private ArrayList<TodoItem> todo_item_list = new ArrayList<>();
    private ArrayList<String> todo_list_list_strings = new ArrayList<>();
    private ArrayList<String> todo_list_item_strings = new ArrayList<>();

    // construct the instance
    public static TodoManager getOurInstance(){

        if (ourInstance == null) {
            ourInstance = new TodoManager();
        }
        return ourInstance;
    }

    /**
     * Create TodoList for listview
     */
    public TodoList createList (String todo_list_string) {

        for (TodoList list : todo_list_list) {
            if (list.getTitle().equals(todo_list_string)) {
                // list already exists
                return list;
            }
        }

        // create new list and set id and title
        TodoList todo_list = new TodoList(todo_list_string);
        todo_list.setTitle(todo_list_string);
        todo_list.setId(list_id);

        // update id for next item
        list_id += 1;

        todo_list_list.add(todo_list);

        return todo_list;
    }

    /**
     * Create TodoItem for listview
     */
    public TodoItem createItem(String list_title, String todo_item_string) {

        // create new item and set id, list, title and status
        TodoItem todo_item = new TodoItem(todo_item_string);
        todo_item.setTodoList(list_title);
        todo_item.setTitle(todo_item_string);
        todo_item.setId(item_id);
        todo_item.setCurrentStatus("unfinished");

        // update id for next item
        item_id += 1;

        todo_item_list.add(todo_item);

        return todo_item;
    }

    public TodoItem getTodoItem(String item_name) {

        for (TodoItem item : todo_item_list) {
            if (item.getTitle().equals(item_name)) {
                return item;
            }
        }
        return null;
    }

    /*
     * Iterates over todo_list_list and checks if it's the list to be deleted
     */
    public void deleteList(String delete_list) {

        for (TodoList list : todo_list_list) {
            if (list.getTitle().equals(delete_list)) {
                todo_list_list.remove(list);
                break;
            }
        }
    }

    public void deleteItem(String delete_item) {
        for (TodoItem item : todo_item_list) {
            if (item.getTitle().equals(delete_item)) {
                todo_item_list.remove(item);
                break;
            }
        }
    }

    public ArrayList<TodoList> getListList() { return todo_list_list; }

    public ArrayList<String> getListTitleStrings() {

        if (todo_list_list_strings != null) {
            todo_list_list_strings = new ArrayList<>();
        }

        for (TodoList todo : todo_list_list) {
            String title = todo.getTitle();
            todo_list_list_strings.add(title);
        }
        return todo_list_list_strings;
    }

    public ArrayList<TodoItem> getItemList() { return todo_item_list; }

    public ArrayList<String> getItemTitleStrings(String list) {

        if (todo_list_item_strings != null) {
            todo_list_item_strings = new ArrayList<>();
        }

        for (TodoItem todo : todo_item_list) {
            if (todo.getTodoList().equals(list)) {
                String item = todo.getTitle();
                todo_list_item_strings.add(item);
            }
        }
        return todo_list_item_strings;

    }

    public void clearListList() {
        list_id = 0;

        todo_list_list.clear();
        //todo_item_list.clear();
        todo_list_list_strings.clear();
        //todo_list_item_strings.clear();
    }

    public void clearItemList() {
        todo_item_list.clear();
        todo_list_item_strings.clear();
    }
}
