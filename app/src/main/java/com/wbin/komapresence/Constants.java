package com.wbin.komapresence;

import android.content.res.Resources;

public class Constants {

    public static int dpToPx(int dp){
        return Integer.valueOf(String.valueOf(dp*(Resources.getSystem().getDisplayMetrics().density)).replace(".0",""));
    }

}
