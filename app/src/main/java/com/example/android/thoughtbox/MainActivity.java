package com.example.android.thoughtbox;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST
        ReadInThought1();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Linked to my new thought button
    public void NewThought(View view)
    {
        // Moves over to the activity allowing a new thought to be written
        startActivity( new Intent( MainActivity.this, NewThought.class ) );
    }

    private void ReadInThought1(  )
    {
        // Set a filename
        String filename = "Thought1";
        // An empty string to hold the content of the file
        String content = "";
        // View to set the content to
        TextView textView = (TextView) findViewById( R.id.main_text_view );

        try {
            // Find the file by filename
            FileInputStream fis = openFileInput(filename);
            // byte array used to read the content of the file, as it was saved in bytes
            // fis.available returns how much byte information is in the file
            byte[] input = new byte[fis.available()];
            while ( fis.read(input) != -1 )
            {
                // This just reads through the whole file
            }
            // Set the content of the string, converting the bytes to a string
            content += new String(input);
            // Set the text
            textView.setText(content);
            // ALWAYS CLOSE THE FILE
            fis.close();
        }catch ( Exception e )
        {
            Log.e("ReadInThought1", "Was unable to read in Thought1");
        }

    }

}
