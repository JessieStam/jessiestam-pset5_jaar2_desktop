package jessie_stam.jessiestam_pset5_jaar2_desktop;

/**
 * Many TodoLists - TodoItem
 *
 * Jessie Stam
 *
 * A helper class with getters and setters that creates TodoItem items.
 */

public class TodoItem {

    // fields for id, item, status and list
    private int id;
    private String todo_item;
    private String current_status;
    private String todo_list;

    // constructor
    public TodoItem(String new_string) { todo_item = new_string; }

    // methods for title
    public String getTitle() { return todo_item; }
    public void setTitle(String new_title) { todo_item = new_title; }

    // methods for id
    public Integer getId() { return id; }
    public void setId(Integer new_id) { id = new_id; }

    // methods for current_status
    public String getCurrentStatus() { return current_status; }
    public void setCurrentStatus(String new_status) { current_status = new_status; }

    // methods for list
    public String getTodoList() { return todo_list; }
    public void setTodoList(String new_list) { todo_list = new_list; }
}
