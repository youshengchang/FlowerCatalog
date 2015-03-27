package com.hanselandpetal.catalog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Initialize the TextView for vertical scrolling
		output = (TextView) findViewById(R.id.textView);
		output.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<MyTask>();



//        for(int i = 0; i < 100; i++){
//            updateDisplay("Line" + i);
//        }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_do_task) {
			MyTask task = new MyTask();
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Param 1", "Param 2", "Param 3");
		}
		return false;
	}

	protected void updateDisplay(String message) {

        output.append(message + "\n");
	}

    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            updateDisplay("Starting task.");
            if(tasks.size() == 0){
                pb.setVisibility(View.VISIBLE);

            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {

            for(int i = 0; i < params.length; i++){
                publishProgress("Working with " + params[i]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            updateDisplay(result);
            tasks.remove(this);
            if(tasks.size() == 0){
                pb.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplay(values[0]);
        }
    }

}