package com.nishad.facedin;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.*;

import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.rest.RESTException;

import java.util.UUID;

/**
 * Created by Victor on 11/15/2015.
 */
public class Initializer {

    public Initializer(){

    }

    public static void init() throws RESTException {

        String URL = "https://scontent-atl3-1.xx.fbcdn.net/hphotos-xap1/v/t1.0-9/65506_751590428221638_9166919660993343404_n.jpg?oh=177ff93d762e39d185f1525c1edacb28&oe=56BEF95D";

        FaceServiceClient f = new FaceServiceClient("54f644646cd5493aa460a719136482ac");

        f.createPersonGroup("testGroup", "Hack Princeton Group", "Sample data");

        Face[] friend1faces = f.detect(URL, false, false, false, false);
        UUID myFaces[] = {friend1faces[0].faceId};

        CreatePersonResult friend1;
        friend1 = f.createPerson("testGroup", myFaces, "Victor", "Thats me!");

    }


}
