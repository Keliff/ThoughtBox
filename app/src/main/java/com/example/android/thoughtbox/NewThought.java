package com.example.android.thoughtbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;

public class NewThought extends ActionBarActivity {

    // This is the method that is called when your activity is created
    // Any instantiation should be handled here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thought);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_thought, menu);
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

    // Cancel the new thought making process, and returning to the main menu
    public void ReturnToMain( View view )
    {
        // Moves over to the activity allowing a new thought to be written
        startActivity( new Intent( NewThought.this, MainActivity.class ) );
    }

    public void SaveThought( View view )
    {
        // Have to set a filename
        String fileName = "Thought1"; // Test name for now
        // Have to get the id of the editText
        EditText editText = (EditText) findViewById( R.id.new_thought_edit_text );
        // Get the string of the editText in question
        String string = editText.getText().toString();
        // New output stream
        FileOutputStream outputStream;

        try
        {
            // Context.Mode_Private means that only the app that creates this on internal storage can read it
            outputStream = openFileOutput( fileName, Context.MODE_PRIVATE );
            // Writing the file to storage
            outputStream.write( string.getBytes() );
            // ALWAYS CLOSE THE FILE
            outputStream.close();
        } catch ( Exception e )
        {
            Log.e("SaveThought", "Error when saving the new thought to internal storage.");
            // Error when creating the file
        }

        ReturnToMain( (Button)findViewById( R.id.new_thought_save_button ) );

    }
}
