package com.jodelapp.jodelandroidv3.usecases.location;

import android.location.Address;

import com.jodelapp.jodelandroidv3.analytics.AnalyticsController;
import com.jodelapp.jodelandroidv3.api.JodelApi;
import com.jodelapp.jodelandroidv3.api.model.LocationUpdateResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

/**
 * Created by Admin on 13.12.2017.
 */
@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class BackupAppLocationRemotelyImpl implements BackupAppLocationRemotely{

    @DexIgnore
    public BackupAppLocationRemotelyImpl() {
    }

    @DexIgnore
    @Inject
    public BackupAppLocationRemotelyImpl(JodelApi jodelApi, AnalyticsController analyticsController) {

    }


    @DexIgnore
    @Override
    public Single<LocationUpdateResponse> call(Address address) {
        return null;
    }
}
