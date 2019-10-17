package uk.ac.rgu.elderaid;

        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;

public class TaskCheckListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton addTaskbnt;
    private ImageButton btnshowSideNav;
    private LinearLayout task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_check_list);

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

        sideNavDialog.show();
        sideNavDialog.getWindow().setAttributes(lp);

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
    @Override
    public void onClick(View v) {

    }
}
