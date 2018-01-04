package com.jodelapp.jodelandroidv3.utilities;


import android.app.Application;

import com.jodelapp.jodelandroidv3.model.Storage;

import java.io.File;

import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * ? For account management
 */
@DexEdit(contentOnly = true)
public class UniqueDeviceIdentifier {

    @DexWrap
    private static String generateValue(Application application, Storage jStorage) {
        com.snatik.storage.Storage jpStorage = new com.snatik.storage.Storage(application.getApplicationContext());
        String path = jpStorage.getExternalStorageDirectory();

        String jpDir = path + File.separator + "JodelPatched";
        String jpFile = jpDir + File.separator + "udi";
        if (!jpStorage.isDirectoryExists(jpDir))
            jpStorage.createDirectory(jpDir);

        if (jpStorage.isFileExist(jpFile)) {
            String privateUID = jpStorage.readTextFile(jpFile);
            jStorage.setUniqueDeviceIdentifier(privateUID);
            return privateUID;
        }

        String uid = generateValue(application, jStorage);

        jpStorage.createFile(jpFile, uid);

        return uid;
    }

}
