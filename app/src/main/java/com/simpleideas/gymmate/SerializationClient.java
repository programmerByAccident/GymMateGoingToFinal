package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Geprge on 12/4/2016.
 */

public class SerializationClient {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static byte[] serializeExerciseTemplateObject(ExerciseTemplate exerciseTemplate) {

        ByteArrayOutputStream returned = new ByteArrayOutputStream();

        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {

                o.writeObject(exerciseTemplate);
            }

            returned = b;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return returned.toByteArray();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException{

        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }

    }
}
