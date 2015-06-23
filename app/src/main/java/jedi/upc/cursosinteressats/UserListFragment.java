package jedi.upc.cursosinteressats;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class UserListFragment extends Fragment {
    ArrayList<String[]> users;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user_list, container, false);

        putList(v);
        registerForContextMenu(v.findViewById(R.id.userList));

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_user_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteUser:
                showDialog("Estàs segur que vols eliminar l'interessat?", usuaris.get(info.position)[0]);
                putList(v);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void putList(View v) {
        BaseDades bd = new BaseDades(getActivity().getApplicationContext());
        Cursor cursor = bd.getUsers();

        while (cursor.moveToNext()) {
            String[] row = new String[5];
            for (int i = 0; i < 5; ++i) row[i] = cursor.getString(i);
            users.add(row);
        }
        String[] columns = { "name", "_id" };
        int items[] = new int[] { R.id.txtName, R.id.txtMail };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                R.layout.user_list_row, cursor, columns, items);
        ListView userList = (ListView) v.findViewById(R.id.userList);
        userList.setAdapter(adapter);

    }
}
