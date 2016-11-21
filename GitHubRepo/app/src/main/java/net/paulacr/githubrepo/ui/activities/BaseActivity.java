package net.paulacr.githubrepo.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.utils.MessageEvents;

import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@EActivity
public class BaseActivity extends AppCompatActivity {

    private EventBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = EventBus.getDefault();
    }

    private ProgressDialog dialog;

    public void showProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.dialog_title));
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.show();
    }

    public void dismissProgressDialog() {
        if(dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * EventBus deve registrar a activity ao iniciar, e desregistrar ao encerrar.
     */
    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

}
