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

    private CheckBox checkBoxDiet;
    private CheckBox checkBoxRecepies;
    private CheckBox checkBoxFood;
    private Button btnApply;

    private boolean isSelectedDiet;
    private boolean isSelectedRecipes;
    private boolean isSelectedFood;

    public static FilterModal newInstance(/*boolean isSelectedDiet, boolean isSelectedRecipes, boolean isSelectedFood*/) {
        FilterModal frag = new FilterModal();

        /*frag.setSelectedDiet(isSelectedDiet);
        frag.setSelectedRecipes(isSelectedRecipes);
        frag.setSelectedFood(isSelectedFood);*/

        return frag;
    }

    private void bindGui(View view) {
        this.checkBoxDiet = (CheckBox) view.findViewById(R.id.checkBoxDiet);
        this.checkBoxRecepies = (CheckBox) view.findViewById(R.id.checkBoxRecepies);
        this.checkBoxFood = (CheckBox) view.findViewById(R.id.checkBoxRecepies2);
        this.btnApply = (Button) view.findViewById(R.id.button_filter);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.modal_filter, null);


        builder.setView(view);
        Dialog dialog = builder.create();

        bindGui(view);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "i was clicked!!");

                setSelectedOptionsFromForm();

                dismiss();

                //fragmentManager.beginTransaction().remove(f).commit();
                //finish();
            }
        });

        return dialog;
    }

    public boolean isSelectedDiet() {
        return isSelectedDiet;
    }

    public void setSelectedDiet(boolean selectedDiet) {
        isSelectedDiet = selectedDiet;
        checkBoxDiet.setChecked(this.isSelectedDiet);
    }

    public boolean isSelectedRecipes() {
        return isSelectedRecipes;
    }

    public void setSelectedRecipes(boolean selectedRecipes) {
        isSelectedRecipes = selectedRecipes;
        checkBoxRecepies.setChecked(this.isSelectedRecipes);
    }

    public boolean isSelectedFood() {
        return isSelectedFood;
    }

    public void setSelectedFood(boolean selectedFood) {
        isSelectedFood = selectedFood;
        checkBoxFood.setChecked(this.isSelectedFood);
    }

    private void setSelectedOptionsFromForm() {
        this.isSelectedDiet = checkBoxDiet.isSelected();
        this.isSelectedRecipes = checkBoxRecepies.isSelected();
        this.isSelectedFood = checkBoxFood.isSelected();
    }
}
