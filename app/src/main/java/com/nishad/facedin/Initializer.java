package com.nishad.facedin;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.*;

import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.rest.RESTException;

import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Victor on 11/15/2015.
 */
public class Initializer {

    public static void init(FaceServiceClient ext) throws RESTException {

        final FaceServiceClient f = ext;

        //final String URL = "https://scontent-atl3-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/65506_751590428221638_9166919660993343404_n.jpg?oh=177ff93d762e39d185f1525c1edacb28&oe=56BEF95D";
        final String URL = "http://media.licdn.com/media/p/2/005/07d/3c2/05ca0a4.jpg";

//        final FaceServiceClient f = new FaceServiceClient("54f644646cd5493aa460a719136482ac");
//        Log.d("FaceServiceClient", "create f");

        Face[] friend1faces = f.detect(URL, false, false, false, false);

        Log.d("friend1faces", "Detect my face");

        f.createPersonGroup("testGroup", "Hack Princeton Group", "Sample data");
        Log.d("createPersonGroup", "create testgroup");

        UUID myFaces[] = {friend1faces[0].faceId};
        Log.d("myFaces[]", "init myfaces[]");

        CreatePersonResult friend1;
        friend1 = f.createPerson("testGroup", myFaces, "Victor", "Thats me!");
        Log.d("friend1", "Create Person");


        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {

                            Log.d("test","test");
                            //publishProgress("Detecting...");
                            Face[] friend1faces = f.detect(
                                    URL, false, false, false, false);
                            //if (friend1faces == null)
                            //{
                            //    publishProgress("Detection Finished. Nothing detected");
                            //    return null;
                            //}
                            //publishProgress(
                            //       String.format("Detection Finished. %d face(s) detected",
                            //                friend1faces.length));
                            //return result;

                            f.createPersonGroup("testGroup", "Hack Princeton Group", "Sample data");
                            Log.d("createPersonGroup", "create testgroup");

                            UUID myFaces[] = {friend1faces[0].faceId};
                            Log.d("myFaces[]", "init myfaces[]");

                            CreatePersonResult friend1;
                            friend1 = f.createPerson("testGroup", myFaces, "Victor", "Thats me!");
                            Log.d("friend1", "Create Person");

                            return friend1faces;

                        } catch (Exception e) {
                            publishProgress("Detection failed");
                            return null;
                        }
                    }
                    @Override
                    protected void onPreExecute() {
                        //detectionProgressDialog.show();

                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //detectionProgressDialog.setMessage(progress[0]);

                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        //detectionProgressDialog.dismiss();

                        Log.d("onPostExec", "onPostExecute() returned: " + result);

                        //if (result == null) return;
                        //ImageView imageView = (ImageView)findViewById(R.id.imageView1);



                        //imageView.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                        //imageBitmap.recycle();

                    }
                };
        detectTask.execute();

    }


}
