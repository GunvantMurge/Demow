package com.abyz.infotech.warroomapp.service;

import android.content.Context;
import android.content.Intent;

public interface IWebservice {
    void fetchAndInsertData(final Intent intent, final Context context);
}
