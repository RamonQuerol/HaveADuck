package es.upm.etsiinf.haveaduck.ui.favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.CompletePatoAdapter;
import es.upm.etsiinf.haveaduck.bd.HandlerBD;
import es.upm.etsiinf.haveaduck.databinding.FragmentFavoritosBinding;

public class FavoritosFragment extends Fragment {

    private FragmentFavoritosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritosViewModel favoritosViewModel =
                new ViewModelProvider(this).get(FavoritosViewModel.class);

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //Se cogen los patos de la base de datos y se añaden a la lista
        HandlerBD handlerBD = new HandlerBD(root.getContext());
        handlerBD.open();
        CompletePatoAdapter adapter = new CompletePatoAdapter(this, handlerBD.getAllFavorites());
        handlerBD.close();

        ((GridView)root.findViewById(R.id.favoritos_main_list)).setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}