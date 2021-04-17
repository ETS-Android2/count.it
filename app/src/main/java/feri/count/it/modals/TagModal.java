package feri.count.it.modals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import feri.count.it.R;
import feri.count.it.activities.MenuActivity;
import feri.count.it.fragments.EntryFragment;

public class TagModal extends DialogFragment {
    public static final String TAG = TagModal.class.getSimpleName();

    public static TagModal newInstance() {
        TagModal frag = new TagModal();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.modal_tag, null);


        builder.setView(view);
        Dialog dialog = builder.create();

        Button btnApply = (Button) view.findViewById(R.id.button_filter);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "i was clicked!!");
                dismiss();
            }
        });

        return dialog;
    }
}
