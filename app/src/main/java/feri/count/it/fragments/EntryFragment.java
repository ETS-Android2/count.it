package feri.count.it.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import feri.count.it.R;
import feri.count.it.activities.MenuActivity;

public class EntryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_entry, null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((MenuActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.maximum_blue, ((MenuActivity) requireActivity()).getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((MenuActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.maximum_blue));
        }
        ((MenuActivity) requireActivity()).getSupportActionBar().hide();

        return rootView;
    }
}