package com.uber.flickrgallery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.uber.flickrgallery.common.ui.FragmentCallback;
import com.uber.flickrgallery.view.GalleryFragment;

public class HomeActivity extends AppCompatActivity implements FragmentCallback {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(savedInstanceState != null)
            return;

        getSupportFragmentManager().beginTransaction().add(R.id.container, new GalleryFragment()).commit();
    }

    @Override
    public void showSpinner(String message) {

        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(message);
        }
        this.mProgressDialog.show();
    }

    @Override
    public void dismissSpinner() {

        if(this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog.cancel();
            this.mProgressDialog = null;
        }
    }

    @Override
    public void showErrorDialog(String title, String message) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}
