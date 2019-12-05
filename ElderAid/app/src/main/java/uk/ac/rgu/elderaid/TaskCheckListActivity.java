package uk.ac.rgu.elderaid;

        import androidx.appcompat.app.AppCompatActivity;

        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.DefaultItemAnimator;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.Dialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;
        import android.widget.Spinner;

        import java.util.ArrayList;

public class TaskCheckListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton addTaskbnt;
    private ImageButton btnshowSideNav;
    private LinearLayout task;
    private ImageButton btnSOS;
    private ImageButton btnHome;

    private ArrayList<Task> taskList = new ArrayList<>();
    private RecyclerView recyclerView_task;
    private TaskAdapter tAdapter;

    private TaskDao taskDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_check_list);

        btnSOS = (ImageButton) findViewById(R.id.btnSOS);
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSOSPhone();
            }
        });

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });;

        btnshowSideNav = (ImageButton) findViewById(R.id.btnMenu);
        btnshowSideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavDialog();
            }
        });

        addTaskbnt = (ImageButton) findViewById(R.id.Add);
        addTaskbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTaskDialog();
            }
        });

        task = (LinearLayout) findViewById(R.id.Task);
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewTaskDialog();
            }
        });

        //Get the database instance
        ElderaidDatabase db = ElderaidDatabase.getDatabase(this);
        //Get the DAO from the database
        this.taskDao =db.tDao();

        recyclerView_task = (RecyclerView) findViewById(R.id.rvTasks);

        tAdapter = new TaskAdapter(taskList, this);
        RecyclerView.LayoutManager tLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_task.setLayoutManager(tLayoutManager);
        recyclerView_task.setItemAnimator(new DefaultItemAnimator());
        recyclerView_task.setAdapter(tAdapter);

        prepareTaskData();

    }

    private void prepareTaskData(){
        Task task = new Task("Take Pills", "11/12/2019", "15:00", "daily", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." );
    }


    public void openViewTaskDialog(){
        final Dialog viewTaskDialog = new Dialog(this);
        viewTaskDialog.setContentView(R.layout.dialog_view_task);
        viewTaskDialog.setTitle("View Add Task");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(viewTaskDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        viewTaskDialog.show();
        viewTaskDialog.getWindow().setAttributes(lp);

        viewTaskDialog.show();


    }
    public void openNavDialog() {
        final Dialog sideNavDialog = new Dialog(this);


        sideNavDialog.setContentView(R.layout.dialog_side_navigation);
        sideNavDialog.setTitle("Navigation View");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(sideNavDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        sideNavDialog.getWindow().setAttributes(lp);

        Button linkHome = (Button) sideNavDialog.findViewById(R.id.linkHome);
        linkHome.setOnClickListener(this);

        Button linkCalendar = (Button) sideNavDialog.findViewById(R.id.linkCalendar);
        linkCalendar.setOnClickListener(this);

        Button linkMaps = (Button) sideNavDialog.findViewById(R.id.linkMaps);
        linkMaps.setOnClickListener(this);

        Button linkTaskList = (Button) sideNavDialog.findViewById(R.id.linkTaskList);
        linkTaskList.setOnClickListener(this);

        Button linkContacts = (Button) sideNavDialog.findViewById(R.id.linkContacts);
        linkContacts.setOnClickListener(this);

        Button linkMedInfo = (Button) sideNavDialog.findViewById(R.id.linkMedInfo);
        linkMedInfo.setOnClickListener(this);

        Button linkPrescription = (Button) sideNavDialog.findViewById(R.id.linkPrescription);
        linkPrescription.setOnClickListener(this);

        Button linkPreferences = (Button) sideNavDialog.findViewById(R.id.linkPreferences);
        linkPreferences.setOnClickListener(this);

        Button btnClose = (Button) sideNavDialog.findViewById(R.id.closeBtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavDialog.cancel();
            }
        });

        sideNavDialog.show();

    }
        public void openAddTaskDialog(){
        final Dialog addTaskDialog = new Dialog(this);
        addTaskDialog.setContentView(R.layout.dialog_add_task);
        addTaskDialog.setTitle("View Add Task");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(addTaskDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        addTaskDialog.show();
        addTaskDialog.getWindow().setAttributes(lp);

        addTaskDialog.show();
    }

    //    ***********************************

    private void launchSOSPhone() {
        // create the Intent with the action to dial
        Intent intent = new Intent(Intent.ACTION_DIAL);
        // set the data to the phone number
        intent.setData(Uri.parse("tel:+4401224272600"));

        // check the intent can be resolved by the device
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        // Side nav
        if (v.getId() == R.id.linkHome) {
            Intent intent = new Intent(
                    getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkCalendar) {
            Intent intent = new Intent(
                    getApplicationContext(), CalendarActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkMaps) {
            Intent intent = new Intent(
                    getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkTaskList) {
            Intent intent = new Intent(
                    getApplicationContext(), TaskCheckListActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkContacts) {
            Intent intent = new Intent(
                    getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkMedInfo) {
            Intent intent = new Intent(
                    getApplicationContext(), MedicalInfoActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkPrescription) {
            Intent intent = new Intent(
                    getApplicationContext(), PrescriptionLevelActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.linkPreferences) {
            Intent intent = new Intent(
                    getApplicationContext(), PreferencesActivity.class);
            startActivity(intent);
        }

    }
}
