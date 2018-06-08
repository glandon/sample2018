package njupt.simbaba.com.app2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class B2 extends AppCompatActivity {
    private List<Student> students = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b2);


        students.add(new Student(12, "jim"));
        students.add(new Student(13, "tom"));
        students.add(new Student(11, "sun"));
        students.add(new Student(14, "andy"));
        students.add(new Student(12, "teddy"));

        ListView listView = findViewById(R.id.list_view);

        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Object getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;

            if (convertView == null) {
                convertView = LayoutInflater.from(B2.this).inflate(R.layout.list_item, null);

                vh = new ViewHolder();
                vh.icon = convertView.findViewById(R.id.icon);
                vh.name = convertView.findViewById(R.id.name);
                vh.age = convertView.findViewById(R.id.age);
                vh.delete = convertView.findViewById(R.id.btn_del);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Student student = students.get(position);
            vh.name.setText(student.name);
            vh.age.setText("age: " + student.age);

            vh.delete.setOnClickListener(v -> {
                students.remove(position);
                mAdapter.notifyDataSetChanged();
            });

            return convertView;
        }
    }

    class ViewHolder{
        ImageView icon;
        TextView name;
        TextView age;
        Button delete;
    }

    class Student{
        int age;
        String name;

        Student(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}
