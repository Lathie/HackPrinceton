package com.nishad.facedin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.provider.*;


import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;
import com.microsoft.projectoxford.face.rest.RESTException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private String personName;
    private String personInfo;

    private final String[] Stackathon = {
            "https://media.licdn.com/media/p/2/005/07d/3c2/05ca0a4.jpg",
            "https://media.licdn.com/mpr/mpr/shrinknp_400_400/AAEAAQAAAAAAAAR6AAAAJDJlZDkwYjlkLTc3NzEtNDY0My1hNzc2LTI1YjYwNWFlMWQzMg.jpg",
            "https://media.licdn.com/mpr/mpr/shrinknp_400_400/AAEAAQAAAAAAAAcIAAAAJDRmZTliODRmLTJjODYtNDMzOC04MDRiLTMxNWVlYjc4MzVhOA.jpg",
            "https://media.licdn.com/media/p/2/000/1b0/2a0/2b55436.jpg",
            "https://media.licdn.com/media/AAEAAQAAAAAAAANBAAAAJDRkMGQxNTM3LWE3ZDktNDM4NC1hMDhkLTY1OWUxNTA2Y2JhOA.jpg",
            "https://media.licdn.com/media/p/3/000/276/109/2a1f78c.jpg", "https://media.licdn.com/media/p/2/005/08b/3df/3015779.jpg"};

    private final String[] StackathonNames = {
            "Pablo Pera Mira"
                    + "\n" +
                    "(https://www.linkedin.com/in/pablopera)",
            "Victor Niu"+
                    "\n"+
                    "(https://www.linkedin.com/in/victorniu)",
            "Pranay Prakash"+
                    "\n"+
                    "(https://www.linkedin.com/in/pranaygp)",
            "William Martin" +
                    "\n" +
                    "(https://www.linkedin.com/in/awmartin)",
            "Karim Atiyeh" +
                    "\n" +
                    "(https://www.linkedin.com/in/karimatiyeh)",
            "Scott Harley"+
                    "\n" +
                    "(https://www.linkedin.com/in/scotthartley)",
            "Steven Greenberg" +
                    "\n" +
                    "(https://www.linkedin.com/in/steven-greenberg-5363b232)"};

    private final String[] StackathonDescriptions = {
            "Co-founder of Androidsx. We develop and launch Android applications. We have launched more than 10 consumer apps that have reached over 15 million users." +
                "\n" +
                    "Summary of my previous positions:\n" +
                    "- Mobile lead at Pixable Inc, NY.\n" +
                    "- Backend engineer at CERN, Geneva.\n" +
                    "- Front-end engineer at Google, Zurich.\n" +
                    "- Researcher in Image Analysis in the UPV, Valencia.\n" +
                    "\n" +
                    "Based in NY.",
            "I like trains, and sleep.\n" +
                    "\n" +
                    "Also programming in moderation.",
            "I love coding.\n" +
                    "\n" +
                    "and HackPrinceton",
            "I build high-performance business tools, tailor processes for design and product innovation, develop product strategies for emerging technologies and markets, evangelize creative leadership and technology education.\n" +
                    "\n" +
                    "Website — http://anomalus.com\n" +
                    "Twitter – http://twitter.com/awmartin\n" +
                    "Code – http://github.com/awmartin",
            "Karim leads product development and technical strategy at Paribus Co. Before that, he worked on everything from natural language processing to computer vision, advanced hardware design and machine learning. He received his BA in Electrical and Computer Engineering and MS in Computer Science from Harvard University where he was a Siebel Scholar. Born and raised in Beirut, he knows how to bargain prices down and fights to get his friends the very best deals.\n" +
                    "\n" +
                    "Previously an Associate in Oliver Wyman's Corporate & Institutional Banking (CIB) practice, advising Investment Banks, Private Equity firms and other Financial Services institutions on a range of strategic issues including market entry, regulatory strategy, balance sheet optimization, organizational reengineering and competitive benchmarking.",
            "Venture Partner @ Metamorphic Ventures\n" +
                    "Columnist @ Inc. Magazine\n" +
                    "Startup Investor & Advisor @ Spire (raised $80M), Klink (acquired by ThinkingPhones), AirPR (acquired), Kimono Labs, CoverWallet, Hattery (acquired by Google & 1776), Impraise, 123.AI, Caliber\n" +
                    "Term Member @ Council on Foreign Relations\n" +
                    "Adventurer @ 70+ countries and counting\n" +
                    "\n" +
                    "Experience: \n" +
                    "Presidential Innovation Fellow @ The White House\n" +
                    "Partner @ Mohr Davidow Ventures\n" +
                    "Product & Operations @ Google & Facebook\n" +
                    "Research @ Harvard, Columbia, & Stanford\n" +
                    "Author @ MIT Press, Forbes, Foreign Policy, etc.\n" +
                    "\n" +
                    "Experienced entrepreneurship and VC speaker for Google.org (Tanzania), U.S. State Department (Senegal, Algeria, Turkey, Kenya), MIT Enterprise Forum (Turkey), MIT Technology Review (Mexico), Growth Days (Sweden), Global Entrepreneurship Summit (Malaysia, Kenya), DigiTalk (Bulgaria), at Columbia, White House, World Bank, IFC, and U.S. Senate Foreign Relations briefing (DC).\n" +
                    "\n" +
                    "6x Marathoner and Ironman 70.3 finisher",
            "No Summary"};


    private final int PICK_IMAGE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private ProgressDialog detectionProgressDialog;
    private FaceServiceClient faceServiceClient =
            new FaceServiceClient("dbbac76483ec4b6ba3673010a0efeb7b");

    private Initializer i = new Initializer();

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        Log.d("directory", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/FacedIn/");
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/FacedIn/");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("FacedIn", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton button1 = (FloatingActionButton)findViewById(R.id.button1);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                gallIntent.setType("image/*");
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                camIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                startActivityForResult(camIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        detectionProgressDialog = new ProgressDialog(this);

        // new fetch().execute("https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=77i229lw0svb65&redirect_uri=https%3A%2F%2Fgoogle.com&state=987654321&scope=r_basicprofile");
//        Log.d("LinkedIn Response", HTTPHelper.GET("https://www.linkedin.com/uas/oauth2/authorization?response_type=code&client_id=77i229lw0svb65&redirect_uri=https%3A%2F%2Fgoogle.com&state=987654321&scope=r_basicprofile"));

        new initializer().execute(Stackathon);
    }

    private class initializer extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                final FaceServiceClient f = faceServiceClient;

//                final String URL = "https://scontent-atl3-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/65506_751590428221638_9166919660993343404_n.jpg?oh=177ff93d762e39d185f1525c1edacb28&oe=56BEF95D";

//                Face[] friend1faces = new Face[10];

                for (int i = 0; i < params.length; i++){


                    final String URL = params[i];

                    Log.d("URL", "doInBackground() returned: " + URL);

                    Face[] friend1faces = f.detect(URL, false, false, false, false);
                    UUID myFaces[] = {friend1faces[0].faceId};
                    f.createPerson("1", myFaces, StackathonNames[i], StackathonDescriptions[i]);
                    Log.d("friend", "Create Person");

                }


//        final FaceServiceClient f = new FaceServiceClient("54f644646cd5493aa460a719136482ac");
//        Log.d("FaceServiceClient", "create f");

//                Face[] friend1faces = f.detect(URL, false, false, false, false);

//                Log.d("friend1faces", "Detect my face");

//                f.createPersonGroup("1", "Hack Princeton Group", "Sample data");
//                Log.d("createPersonGroup", "create testgroup");

//                UUID myFaces[] = {friend1faces[0].faceId};
//                Log.d("myFaces[]", "init myfaces[]");

//                CreatePersonResult friend1;
//                friend1 = f.createPerson("1", myFaces, "Victor", "Thats me!");
//                Log.d("friend1", "Create Person");

                TrainingStatus ts = f.trainPersonGroup("1");
//
//                while(ts.status.equals("running")){
//                    Log.d("Inception", ts.status);
//                    TimeUnit.SECONDS.sleep(2);
//                }

//                Log.d("initializer", "doInBackground() returned: " + ts.status);
            }
            catch(RESTException e){
                e.printStackTrace();
            }
//            catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            return null;
        }
    }

    private class fetch extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("LinkedIn Response", s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {

                // create HttpClient
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(params[0])
                        .build();

                Response response = client.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("test", "" + data.getData());
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
            Uri uri = fileUri;

            try {
//                Bitmap bitmap;
//                if(data==null){
//                    bitmap = (Bitmap)data.getExtras().get("data");
//                }else{
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//                }
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap);
                detectAndFrame(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Detect faces by uploading face images
    // Frame faces after detection
    private void detectAndFrame(final Bitmap imageBitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, IdentifyResult[]> detectTask =
                new AsyncTask<InputStream, String, IdentifyResult[]>() {
                    @Override
                    protected IdentifyResult[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0], false, false, false, false);

                            UUID[] thisImage = {result[0].faceId};

                            Log.d("FaceID", "doInBackground() returned: " + thisImage[0]);

                            return faceServiceClient.identity("1", thisImage, 1);
//
//                            if (result == null)
//                            {
//                                publishProgress("Detection Finished. Nothing detected");
//                                return null;
//                            }
//                            publishProgress(
//                                    String.format("Detection Finished. %d face(s) detected",
//                                            result.length));
//                            return result;
                        } catch (Exception e) {
                            publishProgress("Detection failed");
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                        detectionProgressDialog.show();

                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        detectionProgressDialog.setMessage(progress[0]);

                    }
                    @Override
                    protected void onPostExecute(IdentifyResult[] result) {
                        detectionProgressDialog.dismiss();

                        Log.d("onPostExec", "onPostExecute() returned: " + result);


                        if (result == null) return;

                        new GetPersonInfoTask().execute(result);
//                        ImageView imageView = (ImageView)findViewById(R.id.imageView1);
//
//
//
//                        imageView.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
//                        imageBitmap.recycle();

                    }
                };
        detectTask.execute(inputStream);
    }

    private class GetPersonInfoTask extends AsyncTask<IdentifyResult[], Void, Person> {

        @Override
        protected void onPostExecute(Person person) {
            super.onPostExecute(person);
//            Log.d("Identify", "onPostExecute() returned: " + person.name + person.userData);

            personName = person.name;
            personInfo = person.userData;


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView nameText = (TextView) findViewById(R.id.nameText);
                    nameText.setMovementMethod(LinkMovementMethod.getInstance());
                    TextView infoText = (TextView) findViewById(R.id.infoText);

                    nameText.setText(personName);
                    infoText.setText(personInfo);
                }
            });

        }

        @Override
        protected Person doInBackground(IdentifyResult[]... params) {
            try {
                return faceServiceClient.getPerson("1", params[0][0].candidates.get(0).personId);
            } catch (RESTException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        int stokeWidth = 2;
        paint.setStrokeWidth(stokeWidth);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
