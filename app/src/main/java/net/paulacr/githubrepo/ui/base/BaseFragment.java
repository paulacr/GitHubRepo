package net.paulacr.githubrepo.ui.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import net.paulacr.githubrepo.R;

import org.greenrobot.eventbus.Subscribe;

public abstract class BaseFragment extends Fragment {

    private ProgressDialog dialog;

    public void showProgressDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle(getString(R.string.dialog_title));
        dialog.setMessage(getString(R.string.dialog_message));
        dialog.show();
    }

    public void dismissProgressDialog() {
        if(dialog != null) {
            dialog.dismiss();
        }
    }

    @Subscribe
    public abstract void getResponse(Object message);
}
