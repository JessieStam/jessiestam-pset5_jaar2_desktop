package jessie_stam.jessiestam_pset5_jaar2_desktop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jessie on 4-10-2016.
 */

public class TodoList implements Parcelable {

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

    private TodoList(Parcel in) {
        id = in.readInt();
        todo_list = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(todo_list);
    }

    public static final Parcelable.Creator<TodoList> CREATOR = new Parcelable.Creator<TodoList>() {
        public TodoList createFromParcel(Parcel in) {
            return new TodoList(in);
        }

        @Override
        public TodoList[] newArray(int size) {
            return new TodoList[0];
        }
    };
}
