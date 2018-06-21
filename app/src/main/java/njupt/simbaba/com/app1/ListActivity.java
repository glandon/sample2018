package njupt.simbaba.com.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ArrayList<Student> mStudents;
    private StudentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mStudents = new ArrayList<>();
        mStudents.add(new Student("Jim", 10, true, R.drawable.mm));
        mStudents.add(new Student("Tom", 9, true, R.drawable.mouse));
        mStudents.add(new Student("Andy", 12, true, R.drawable.uncle));
        mStudents.add(new Student("Sunny", 13, true, R.drawable.mouse));
        mStudents.add(new Student("Trump", 9, true, R.drawable.mm));
        mStudents.add(new Student("Dandy", 11, false, R.drawable.uncle));

        ListView listView = findViewById(R.id.list_view);

        mAdapter = new StudentAdapter();
        listView.setAdapter(mAdapter);

        findViewById(R.id.btn_delete).setOnClickListener(v->{
            EditText input = findViewById(R.id.which);
            int who = Integer.parseInt(input.getText().toString());
            mStudents.remove(who);
            mAdapter.notifyDataSetChanged();
        });
    }

    class StudentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStudents.size();
        }

        @Override
        public Object getItem(int position) {
            return mStudents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.icon = convertView.findViewById(R.id.icon);
                viewHolder.name = convertView.findViewById(R.id.name);
                viewHolder.details = convertView.findViewById(R.id.details);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Student student = (Student) getItem(position);
            viewHolder.icon.setImageResource(student.icon);
            viewHolder.name.setText(student.name);
            viewHolder.details.setText(String.format("%s, %s岁", student.sex?"男":"女", student.age));

            return convertView;
        }
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        TextView details;
    }

    class Student {
        String name;
        int age;
        boolean sex;
        int icon;

        Student(String name, int age, boolean sex, int icon) {
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.icon = icon;
        }
    }
}
