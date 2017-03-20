package com.moodtunes.moodtunes_v2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import utils.Constants;

/**
 * A class that uses {@link FaceDetector} to detect the prominent face in the
 * image, crop it out, and return the face's smiling probability.
 * <br>
 * <b>NOTE</b>: Call <i>initialize()</i> before calling <i>cropImage()</i> or
 * <i>getSmilingProbability()</i>.
 *
 */
public class FaceScanner {
    /**
     * Incoming {@link Bitmap}.
     */
    private Bitmap faceBitmap;

    /**
     * Application context.
     */
    private Context mContext;

    /**
     * The {@link FaceDetector}.
     */
    private FaceDetector faceDetector;

    /**
     * {@link Frame} to hold the {@link Bitmap}.
     */
    private Frame frame;

    /**
     * Array of {@link Face faces}.
     */
    private SparseArray<Face> faces;

    /**
     * Constructor.
     *
     * @param context Application context.
     * @param bMap Image bitmap.
     */
    public FaceScanner(final Context context, final Bitmap bMap) {
        mContext = context;
        faceBitmap = bMap;
    }

    /**
     * Initializing method. This method should be called first!
     * This method initializes the {@link FaceDetector} and builds the
     * {@link Frame}.
     */
    public void initialize() {
        faceDetector = new FaceDetector.Builder(mContext)
                .setProminentFaceOnly(true)
                .setTrackingEnabled(false)
                .setMode(FaceDetector.ACCURATE_MODE)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        frame = new Frame.Builder()
                .setBitmap(faceBitmap)
                .build();
    }

    /**
     * A method to detect the prominent face and crop the image.
     * If no face is detected/face cannot be cropped, the original
     * bitmap is returned.
     *
     * @return Cropped {@link Bitmap} image.
     */
    public Bitmap cropImage() {

        faces = faceDetector.detect(frame);

        if (faces.size() > 0) {

            Log.d(Constants.FACE_SCANNER_CLASS, "Detected " + faces.size()
             + "face(s) in the image.");

            // Get 1st face detected.
            Face face = faces.valueAt(0);
            float startX = face.getPosition().x;
            float startY = face.getPosition().y;

            faces.clear();

            return Bitmap.createBitmap(faceBitmap,
                    (int) startX, (int) startY,
                    (int) face.getWidth() + Constants.BITMAP_EXTRA_PADDING,
                    (int) face.getHeight() + Constants.BITMAP_EXTRA_PADDING);
        }

        return faceBitmap;
    }

    /**
     * A method to detect the smiling probability of a person in an image.
     * Note that the method requires <i>initialize()</i> to be called first.
     * If smiling probability is detected, returns a value between 0 & 1.
     * Else, returns 0.
     *
     * @param bitmap Image bitmap.
     * @return Smiling probability.
     */
    public double getSmilingProbability(final Bitmap bitmap) {

        frame = new Frame.Builder()
                .setBitmap(bitmap)
                .build();

        faces = faceDetector.detect(frame);

        if (faces.size() > 0) {
            // 1st detected face.
            Face face = faces.valueAt(0);

            return face.getIsSmilingProbability();
        }

        return 0.0;
    }
}
