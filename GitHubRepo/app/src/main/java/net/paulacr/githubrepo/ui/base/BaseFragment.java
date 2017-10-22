package net.paulacr.githubrepo.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import net.paulacr.githubrepo.R;

public class BaseFragment extends Fragment {

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
}
