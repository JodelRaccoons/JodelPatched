package com.jodelapp.jodelandroidv3.view.gesture;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.jodelapp.jodelandroidv3.api.model.Post;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.jp.TSnackbar;

import java.io.File;

import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexReplace;

import static com.jodelapp.jodelandroidv3.JodelApp.staticContext;

/**
 * ? For copy post / download images
 */
@DexEdit(contentOnly = true, target = "PostGestureManager$JodelGestureListener")
class JodelGestureListener extends SimpleOnGestureListener {
    @DexIgnore
    private Post touchedJodel;

    @DexReplace
    public boolean onDoubleTap(MotionEvent motionEvent) {
        String imageUrl = touchedJodel.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) staticContext.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("JodelPost", touchedJodel.getMessage()));
                TSnackbar.make("Copied text", TSnackbar.LENGTH_SHORT);
            }
            return true;
        }

        if (imageUrl.startsWith("url:"))
            imageUrl = imageUrl.substring(4);

        if (imageUrl.startsWith("//"))
            imageUrl = "https:" + imageUrl;

        String filename = new File(imageUrl).getName();

        ResponseListener listener = new ResponseListener(filename);
        RequestQueue requestQueue = Volley.newRequestQueue(staticContext);
        ImageRequest req = new ImageRequest(
                imageUrl,
                listener,
                0,
                0,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                listener
        );
        requestQueue.add(req);
        return true;
    }
}

class ResponseListener implements Response.Listener<Bitmap>, Response.ErrorListener {
    private String filename;

    ResponseListener(String filename) {
        this.filename = filename;
    }

    @Override
    public void onResponse(Bitmap bitmap) {
        com.snatik.storage.Storage jpStorage = new com.snatik.storage.Storage(staticContext);
        String path = jpStorage.getExternalStorageDirectory();

        String jpDir = path + File.separator + "JodelPatched";
        String jpImages = jpDir + File.separator + "images";

        if (!jpStorage.isDirectoryExists(jpDir))
            jpStorage.createDirectory(jpDir);

        if (!jpStorage.isDirectoryExists(jpImages))
            jpStorage.createDirectory(jpImages);

        String filePath = jpImages + File.separator + filename;

        jpStorage.createFile(filePath, bitmap);
        TSnackbar.make("Downloaded image", TSnackbar.LENGTH_SHORT);

        JPUtils.issueMediaScanner(filePath);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("JodelPatched", error.getLocalizedMessage());
    }
}