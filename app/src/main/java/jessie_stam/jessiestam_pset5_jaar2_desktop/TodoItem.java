package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jessie on 4-10-2016.
 */

public class TodoItem implements Parcelable {

    // fields for id, item and status
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

    private TodoItem(Parcel in) {
        id = in.readInt();
        todo_item = in.readString();
        current_status = in.readString();
        todo_list = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(todo_item);
        out.writeString(current_status);
        out.writeString(todo_list);
    }

    public static final Parcelable.Creator<TodoItem> CREATOR = new Parcelable.Creator<TodoItem>() {
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[0];
        }
    };
}
