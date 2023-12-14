package es.upm.etsiinf.haveaduck.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import es.upm.etsiinf.haveaduck.R;
import es.upm.etsiinf.haveaduck.model.ImageOnlyPato;

public class ImageOnlyPatoAdapter extends BaseAdapter {

    private Fragment ctx;
    private List<ImageOnlyPato> data;

    public ImageOnlyPatoAdapter(Fragment ctx, List<ImageOnlyPato> data){
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view ==null){
            LayoutInflater layoutInflater = ctx.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.pato_image_list_layout, null);
        }
        ((ImageView)view.findViewById(R.id.pato_item_image_inicio)).setImageBitmap((data.get(position).getImageBtm()));

        return view;
    }

}
