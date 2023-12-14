package es.upm.etsiinf.haveaduck.ui.patocamara;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import es.upm.etsiinf.haveaduck.databinding.FragmentPatocamaraBinding;

public class PatoCamaraFragment extends Fragment {

    private FragmentPatocamaraBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PatoCamaraViewModel patoCamaraViewModel =
                new ViewModelProvider(this).get(PatoCamaraViewModel.class);

        binding = FragmentPatocamaraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
