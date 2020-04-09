package com.cs580.android.spaceapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class APOD extends Activity {

    private String in = "";
     private String copyright = "", date = "", explanation = "", hdurl = "",
            media_type = "", service_version = "", title = "", mUrl = "";

    @Override
    protected void onCreate(Bundle savedInstance ) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_apod);
        callAPOD();
    }

    public void callAPOD() {
        Uri.Builder build = new Uri.Builder();
        build.scheme("https")
                .authority("api.nasa.gov")
                .appendPath("planetary")
                .appendPath("apod")
                .appendQueryParameter("api_key", "");
        String url = build.build().toString();
        Log.d("url", url);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                ( Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    TextView t1 = findViewById(R.id.apodTitle);
                    TextView t2 = findViewById(R.id.apodDescription);
                    ImageView imageView = findViewById(R.id.APODImage);

                    @Override
                    public void onResponse(JSONObject response) {
                        in = response.toString();
//                        textView.setText( "Response: " + response.toString());
                        parseJSON( response );
                        t1.setText(title);
                        Picasso.get().load(hdurl).into(imageView);
                        t2.setText(explanation);
                        Log.d("response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("callAPOD", error.getMessage() );
                    }
                });
            queue.add(jsonObjectRequest);


    }

    private void parseJSON( JSONObject r ) {

        try {
            copyright = r.getString("copyright");
            Log.d( "JSON Response", copyright );

            date = r.getString( "date" );
            Log.d( "JSON Response", date );

            explanation = r.getString( "explanation" );
            Log.d( "JSON Response", explanation );

            hdurl = r.getString( "hdurl" );
            Log.d( "JSON Response", hdurl );

            media_type = r.getString( "media_type" );
            Log.d( "JSON Response", media_type );

            service_version = r.getString( "service_version" );
            Log.d( "JSON Response", service_version );

            title = r.getString( "title" );
            Log.d( "JSON Response", title );

            mUrl = r.getString( "url" );
            Log.d( "JSON Response", mUrl );

        } catch( JSONException e ) {
            Log.e( "JSONException", e.getMessage() );
        }
    }

    public static Bitmap loadImageUrl(String u ){
        try {
            URL url = new URL(u);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (Exception e ) {
            Log.e( "loadImageUrl", e.getMessage());
            return null;
        }

//        try {
//            InputStream input = (InputStream) new URL(u).getContent();
//            Drawable drawable = Drawable.createFromStream(input, "src name" );
//            return drawable;
//        } catch ( Exception e ) {
//            Log.e( "loadImageUrl", e.getMessage() );
//            return null;
//        }
    }


}

