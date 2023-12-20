package es.upm.etsiinf.haveaduck.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import es.upm.etsiinf.haveaduck.DownloadPatosThread;
import es.upm.etsiinf.haveaduck.MainActivity;
import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.adapters.ImageOnlyPatoAdapter;
import es.upm.etsiinf.haveaduck.databinding.FragmentHomeBinding;
import es.upm.etsiinf.haveaduck.model.ImageOnlyPato;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button btn_reload = root.findViewById(R.id.inicio_main_reload_button);
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread th = new Thread(new DownloadPatosThread((MainActivity)getActivity(), HomeFragment.this));
                th.start();
            }
        });

        Thread th = new Thread(new DownloadPatosThread((MainActivity)getActivity(), HomeFragment.this));
        th.start();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void startDownload(){
        root.findViewById(R.id.inicio_main_reload_button).setEnabled(false);
        root.findViewById(R.id.inicio_main_list_progressBar).setVisibility(View.VISIBLE);
        root.findViewById(R.id.text_home).setVisibility(View.VISIBLE);
    }

    public void finishDownload(List<ImageOnlyPato> results){
        //ArrayAdapter<Planet> adapter = new ArrayAdapter<Planet>(this, android.R.layout.activity_list_item, results);
        ImageOnlyPatoAdapter adapter = new ImageOnlyPatoAdapter(this, results);
        ((GridView)root.findViewById(R.id.inicio_main_list)).setAdapter(adapter);
        //progressDialog.dismiss();

        root.findViewById(R.id.inicio_main_reload_button).setEnabled(true);
        root.findViewById(R.id.inicio_main_list_progressBar).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.text_home).setVisibility(View.INVISIBLE);
    }

}