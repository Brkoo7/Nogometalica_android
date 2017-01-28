package ibrkovic.fesb.hr.nogometalica;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by adagelic on 09/01/17.
 */
public class UserListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public UserListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return DataStorage.allUserList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.user_list_item, parent, false);
        }

        TextView id = (TextView) convertView.findViewById(R.id.userIdTv);
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView score = (TextView) convertView.findViewById(R.id.userScore);

        User currentUser = DataStorage.allUserList.get(position);

        id.setText(String.valueOf(currentUser.getId()));
        userName.setText(currentUser.getName());

        score.setText(currentUser.getRezultat());

        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
