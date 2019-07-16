package net.coding4ever.roedhi.myjobscheduler;

/*import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;*/

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    Button btnStart, btnCancel;
    private static final int JOB_ID = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnStart.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_cancel:
                cancelJob();
                break;
        }
    }

    private void startJob() {

        /*
        Cek running job terlebih dahulu
         */
        if (isJobRunning(this)) {
            Toast.makeText(this, "Job Service is already scheduled", Toast.LENGTH_SHORT).show();
            return;
        }


        ComponentName mServiceComponent = new ComponentName(this, GetCurrentWeatherJobService.class);

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, mServiceComponent);

        /*
        Kondisi network,
        NETWORK_TYPE_ANY, berarti tidak ada ketentuan tertentu
        NETWORK_TYPE_UNMETERED, adalah network yang tidak dibatasi misalnya wifi
        */
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        /*
        Kondisi device, secara default sudah pada false
        false, berarti device tidak perlu idle ketika job ke trigger
        true, berarti device perlu dalam kondisi idle ketika job ke trigger
        */
        builder.setRequiresDeviceIdle(false);

        /*
        Kondisi charging
        false, berarti device tidak perlu di charge
        true, berarti device perlu dicharge
        */
        builder.setRequiresCharging(false);

        /*
        Periode interval sampai ke trigger
        Dalam milisecond, 1000ms = 1detik
        */
        // builder.setPeriodic(5 * 1000);

        // Android Nougat dan diatasnya method JobInfo#setPriodi
        builder.setMinimumLatency(1 * 1000); // wait at least

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.schedule(builder.build());
        }

        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
    }

    private void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(JOB_ID);
        }
        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
    }

    /*
    Gunakanlah method ini untuk melakukan pengecekan "apakah job dengan id 10 sudah berjalan ?"
     */
    private boolean isJobRunning(Context context) {
        boolean isScheduled = false;

        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        if (scheduler != null) {
            for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
                if (jobInfo.getId() == JOB_ID) {
                    isScheduled = true;
                    break;
                }
            }
        }

        return isScheduled;
    }
}
