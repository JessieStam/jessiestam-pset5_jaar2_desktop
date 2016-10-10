package jessie_stam.jessiestam_pset5_jaar2_desktop;

/**
 * Many TodoLists - TodoList
 *
 * Jessie Stam
 *
 * A helper class with getters and setters that creates TodoList items.
 */

public class TodoList {

    // fields for id and item
    private int id;
    private String todo_list;

    // constructor
    public TodoList(String new_string) { todo_list = new_string; }

    // methods for title
    public String getTitle() { return todo_list; }
    public void setTitle(String new_title) { todo_list = new_title; }

    // methods for id
    public Integer getId() { return id; }
    public void setId(Integer new_id) { id = new_id; }
}
