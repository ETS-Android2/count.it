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
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import feri.count.it.R;
import feri.count.it.activities.MenuActivity;
import feri.count.it.fragments.EntryFragment;

public class TagModal extends DialogFragment {
    public static final String TAG = TagModal.class.getSimpleName();

    public AlertDialog.Builder builder;
    public LayoutInflater inflater;
    public View view;
    public Dialog dialog;

    public RadioButton radioButtonBreakfast;
    public RadioButton radioButtonLunch;
    public RadioButton radioButtonDinner;
    public RadioButton radioButtonSnack;
    public Button btnApply;

    private boolean isSelectedBreakfast;
    private boolean isSelectedLunch;
    private boolean isSelectedDinner;
    private boolean isSelectedSnack;

    public static TagModal newInstance(boolean isSelectedBreakfast, boolean isSelectedLunch, boolean isSelectedDinner, boolean isSelectedSnack) {
        TagModal frag = new TagModal();

        frag.setSelectedBreakfast(isSelectedBreakfast);
        frag.setSelectedLunch(isSelectedLunch);
        frag.setSelectedDinner(isSelectedDinner);
        frag.setSelectedSnack(isSelectedSnack);

        return frag;
    }

    public void bindGui(View view) {
        this.radioButtonBreakfast = (RadioButton) view.findViewById(R.id.radioButton);
        this.radioButtonLunch = (RadioButton) view.findViewById(R.id.radioButton2);
        this.radioButtonDinner = (RadioButton) view.findViewById(R.id.radioButton3);
        this.radioButtonSnack = (RadioButton) view.findViewById(R.id.radioButton4);
        this.btnApply = (Button) view.findViewById(R.id.button_filter);
    }

    public void displayDataInGui() {
        radioButtonBreakfast.setChecked(this.isSelectedBreakfast);
        radioButtonLunch.setChecked(this.isSelectedLunch);
        radioButtonDinner.setChecked(this.isSelectedDinner);
        radioButtonSnack.setChecked(this.isSelectedSnack);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.builder = this.builder != null ? this.builder : new AlertDialog.Builder(getActivity());
        this.inflater = this.inflater != null ? this.inflater : getActivity().getLayoutInflater();
        this.view = this.view != null ? this.view : this.inflater.inflate(R.layout.modal_tag, null);

        this.builder.setView(this.view);
        this.dialog = this.dialog != null ? this.dialog : builder.create();

        /*Button btnApply = (Button) view.findViewById(R.id.button_filter);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "i was clicked!!");
                dismiss();
            }
        });*/

        return this.dialog;
    }

    public boolean isSelectedBreakfast() {
        return isSelectedBreakfast;
    }

    public void setSelectedBreakfast(boolean selectedBreakfast) {
        isSelectedBreakfast = selectedBreakfast;
    }

    public boolean isSelectedLunch() {
        return isSelectedLunch;
    }

    public void setSelectedLunch(boolean selectedLunch) {
        isSelectedLunch = selectedLunch;
    }

    public boolean isSelectedDinner() {
        return isSelectedDinner;
    }

    public void setSelectedDinner(boolean selectedDinner) {
        isSelectedDinner = selectedDinner;
    }

    public boolean isSelectedSnack() {
        return isSelectedSnack;
    }

    public void setSelectedSnack(boolean selectedSnack) {
        isSelectedSnack = selectedSnack;
    }

    public void setSelectedOptionsFromForm() {
        this.isSelectedBreakfast = radioButtonBreakfast.isChecked();
        this.isSelectedLunch = radioButtonLunch.isChecked();
        this.isSelectedDinner = radioButtonDinner.isChecked();
        this.isSelectedSnack = radioButtonSnack.isChecked();
    }
}
