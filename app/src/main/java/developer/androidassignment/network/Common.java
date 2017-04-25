package developer.androidassignment.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager.BadTokenException;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class Common {
    private static final int PERMISSION_REQUEST_CODE = 1;
    protected static final char[] hexArray;

    private static ProgressDialog loadingProgressDialog;
    private static final AtomicInteger sNextGeneratedId;
    public static SimpleDateFormat sdf;
    public static SimpleDateFormat sdf_format;
    public static SimpleDateFormat un_sdf_format;
    public static SimpleDateFormat un_sdf_format_month;
    public static SimpleDateFormat un_sdf_format_s;
    public static SimpleDateFormat un_sdf_format_time;

    static {
        hexArray = "0123456789ABCDEF".toCharArray();
        sdf = new SimpleDateFormat("MMM d,yy");
        sdf_format = new SimpleDateFormat("yyyy-MM-dd");
        un_sdf_format = new SimpleDateFormat("dd-MM-yy");
        un_sdf_format_s = new SimpleDateFormat("dd-MM-yyyy");
        un_sdf_format_month = new SimpleDateFormat("MMM yy");
        un_sdf_format_time = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        sNextGeneratedId = new AtomicInteger(PERMISSION_REQUEST_CODE);
    }


    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (BadTokenException e) {
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return dialog;
    }

    public static void showDialog(Context context) {
        if (loadingProgressDialog != null) {
            loadingProgressDialog.dismiss();
        }
        loadingProgressDialog = ProgressDialog.show(context, "", "Loading, it will take more time..", true, false);
    }

    public static void disMissDialog() {
        if (loadingProgressDialog != null) {
            loadingProgressDialog.dismiss();
            loadingProgressDialog = null;
        }
    }



}
