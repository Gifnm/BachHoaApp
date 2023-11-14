package ioc.app.bachhoa.ultil;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import ioc.app.bachhoa.R;

public class Message {
    private Context context;

    public Message(Context context) {
        this.context = context;
    }

    public void messageSucceed(View view, String message) {
        // Implement the logic for successful message
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackground(ContextCompat.getDrawable(context, R.drawable.green_background));
        // Đặt vị trí từ bên trên xuống
        Snackbar.SnackbarLayout.LayoutParams layoutParams = (Snackbar.SnackbarLayout.LayoutParams) snackbarView.getLayoutParams();
        layoutParams.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(layoutParams);
        snackbar.show();
    }

    public void messageFailed(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackground(ContextCompat.getDrawable(context, R.drawable.red_background));

        // Đặt vị trí từ bên trên xuống
        Snackbar.SnackbarLayout.LayoutParams layoutParams = (Snackbar.SnackbarLayout.LayoutParams) snackbarView.getLayoutParams();
        layoutParams.gravity = Gravity.TOP;
        snackbarView.setLayoutParams(layoutParams);

        snackbar.show();
    }
}
