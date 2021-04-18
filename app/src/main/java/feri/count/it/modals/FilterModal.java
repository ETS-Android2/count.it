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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import feri.count.it.R;
import feri.count.it.activities.MenuActivity;
import feri.count.it.fragments.EntryFragment;

public class FilterModal extends DialogFragment {
    public static final String TAG = FilterModal.class.getSimpleName();

    public AlertDialog.Builder builder;
    public LayoutInflater inflater;
    public View view;
    public Dialog dialog;

    public CheckBox checkBoxDiet;
    public CheckBox checkBoxRecepies;
    public CheckBox checkBoxFood;
    public Button btnApply;

    private boolean isSelectedDiet;
    private boolean isSelectedRecipes;
    private boolean isSelectedFood;

    public static FilterModal newInstance(boolean isSelectedDiet, boolean isSelectedRecipes, boolean isSelectedFood) {
        FilterModal frag = new FilterModal();

        frag.setSelectedDiet(isSelectedDiet);
        frag.setSelectedRecipes(isSelectedRecipes);
        frag.setSelectedFood(isSelectedFood);

        return frag;
    }

    public void bindGui(View view) {
        this.checkBoxDiet = (CheckBox) view.findViewById(R.id.checkBoxDiet);
        this.checkBoxRecepies = (CheckBox) view.findViewById(R.id.checkBoxRecepies);
        this.checkBoxFood = (CheckBox) view.findViewById(R.id.checkBoxRecepies2);
        this.btnApply = (Button) view.findViewById(R.id.button_filter);
    }

    public void displayDataInGui() {
        checkBoxDiet.setChecked(this.isSelectedDiet);
        checkBoxRecepies.setChecked(this.isSelectedRecipes);
        checkBoxFood.setChecked(this.isSelectedFood);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.builder = this.builder != null ? this.builder : new AlertDialog.Builder(getActivity());
        this.inflater = this.inflater != null ? this.inflater : getActivity().getLayoutInflater();
        this.view = this.view != null ? this.view : inflater.inflate(R.layout.modal_filter, null);


        builder.setView(view);
        this.dialog = dialog != null ? this.dialog : builder.create();

        //bindGui(view);

        /*btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "i was clicked!!");
                setSelectedOptionsFromForm();
                dismiss();
            }
        });*/

        //return dialog;
        return this.dialog;
    }

    public boolean isSelectedDiet() {
        return isSelectedDiet;
    }

    public void setSelectedDiet(boolean selectedDiet) {
        isSelectedDiet = selectedDiet;

    }

    public boolean isSelectedRecipes() {
        return isSelectedRecipes;
    }

    public void setSelectedRecipes(boolean selectedRecipes) {
        isSelectedRecipes = selectedRecipes;

    }

    public boolean isSelectedFood() {
        return isSelectedFood;
    }

    public void setSelectedFood(boolean selectedFood) {
        isSelectedFood = selectedFood;

    }

    public void setSelectedOptionsFromForm() {
        this.isSelectedDiet = checkBoxDiet.isChecked();
        this.isSelectedRecipes = checkBoxRecepies.isChecked();
        this.isSelectedFood = checkBoxFood.isChecked();
    }
}
