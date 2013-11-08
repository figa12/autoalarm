package dk.figz.autoalarm;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.Format;

public class MainActivity extends Activity implements View.OnClickListener {

    private Cursor cursor = null;

    private static final String[] COLS = new String[]
            {
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DTSTART
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, COLS, null, null, null);

        cursor.moveToFirst();

        Button b = (Button) findViewById(R.id.next);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.previous);
        b.setOnClickListener(this);
        onClick(findViewById(R.id.previous));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) findViewById(R.id.data);

        String title = "N/A";

        Long start = 0L;

        switch (v.getId()) {
            case R.id.next:
                if (!cursor.isLast()) cursor.moveToNext();
                break;
            case R.id.previous:
                if (!cursor.isFirst()) cursor.moveToPrevious();
                break;

        }

        Format df = DateFormat.getDateFormat(this);
        Format tf = DateFormat.getTimeFormat(this);

        try {
            title = cursor.getString(0);

            start = cursor.getLong(1);

        } catch (Exception e) {
            //ignore
        }
        textView.setText(title + " on " + df.format(start) + " at " + tf.format(start));
    }
}