package com.example.android.thoughtbox;

import android.app.ActionBar;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TEST
//        ReadInThought1();
//        PopulateInternalStoargeThoughts();
//        PopulateTextViews();
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

//    private void ReadInThought1(  )
//    {
//        // Set a fileName
//        String fileName = "Thought1";
//        // View to set the content to
//        TextView textView = (TextView) findViewById( R.id.main_text_view );
//
//        try
//        {
//            FileInputStream fis = openFileInput( fileName );
//            ObjectInputStream ois = new ObjectInputStream( fis );
//            Thought thought = (Thought) ois.readObject();
//            textView.setText( thought.data );
//            ois.close();
//            fis.close();
//        } catch ( Exception e )
//        {
//            Log.e("ReadInThought1", "Was unable to read in Thought1");
//        }
//
//        FileList();
//
//    }

    private void FileList()
    {
        String path = getFilesDir().getPath();
        Log.d("Files", "Path: " + path );
        File f = new File(path);
        File[] files = f.listFiles();
        Log.d("Files", "Size: " + files.length);
        for ( int i = 0; i < files.length; i++ )
        {
            Log.d("Files", "FileName: " + files[i].getName() );
        }

    }

    /**
     * A temporary method I'm using to populate the internal storage with thoughts
     */
    private void PopulateInternalStoargeThoughts()
    {
        Thought thought1 = new Thought( "Thought 1", "Shower" );
        Thought thought2 = new Thought( "Thought 2", "Shower" );
        Thought thought3 = new Thought( "Thought 3", "Shower" );

        SaveThoughtInternal( thought1.data, thought1 );
        SaveThoughtInternal( thought2.data, thought2 );
        SaveThoughtInternal(thought3.data, thought3);

    }

    /**
     * A method that takes in a Thought and saves it to internal storage
     */
    private void SaveThoughtInternal( String fileName, Thought thought )
    {
        try
        {
            FileOutputStream fos = openFileOutput( fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream( fos );
            oos.writeObject( thought );
            oos.close();
            fos.close();
        } catch ( Exception e )
        {
            Log.e("Saving", "Error when saving the new thought to internal storage.");
        }

    }

    /**
     * Another simple text method that goes through internal storage and then tries to populate the main activity window with the thoughts as text views
     */
    private void PopulateTextViews()
    {
        LinearLayout layout = (LinearLayout)findViewById(R.id.linear_layout_main_text_vertical);
        float dp = convertDpToPixel(50f, this);

//        TextView test1 = new TextView( this, null, R.style.thought_text_view );
//        test1.setText(  ); Use this idea to set the textView equal to the thought
//        layout.addView(test1);

        // I want to iterate through the files
        // And when I get there, with each file in Internal Storage, I want to use that information to supplant the textView
        // And then add that view to the hierarchy

        File[] files = ReturnFileListAtPath( getFilesDir().getPath() );

//        int[] attrs = { android.R.attr.layout_width, android.R.attr.layout_gravity, android.R.attr.layout_height, android.R.attr.layout_marginTop, android.R.attr.hint };
//        TypedArray ta = obtainStyledAttributes( R.style.thought_text_view, attrs);

        for ( int i = 0; i < files.length; i++ )
        {

//            textView.setTextAppearance( getApplicationContext(), R.attr.thoughtAttr );
            try {
                TextView textView = new TextView( this );
                FileInputStream fis = openFileInput( files[i].getName() );
                ObjectInputStream ois = new ObjectInputStream(fis);
                Thought thought = (Thought) ois.readObject();
                textView = ThoughtStyleTextView( textView, (int)dp );

                textView.setText(thought.data);

                layout.addView( textView );
            } catch ( Exception e )
            {
                Log.e("Loading", "PopulateTextViews was unable to load in a thought correctly");
            }
        }

    }

    private TextView ThoughtStyleTextView( TextView textView, int topMargin )
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)textView.getLayoutParams();
        params.setMargins(0, topMargin, 0, 0);

        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        textView.setLayoutParams(params);
        textView.setHint( "Thoughts go here" );

        return textView;
    }

    /**
     * @param path The file path in question
     * @return The list of files at that path
     */
    private File[] ReturnFileListAtPath( String path )
    {
        File f = new File(path);
        File[] files = f.listFiles();

        return files;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * http://bit.ly/1YgQoPF Credit
     *
     * @param px A value in px (pixels) unit. Which we need to convert into dp
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    private static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * http://bit.ly/1YgQoPF Credit
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}






















