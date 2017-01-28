package ibrkovic.fesb.hr.nogometalica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by adagelic on 09/01/17.
 */
public class ScoreListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_layout);

        // fill data storage from database
        UserDataSource userDataSource = new UserDataSource(getApplicationContext());

        DataStorage.allUserList = userDataSource.getAllUser();

        ListView notesListView = (ListView) findViewById(R.id.notesListView);

        notesListView.setAdapter(new UserListAdapter(getApplicationContext()));

    }

}
