package es.upm.etsiinf.haveaduck.ui.patocamara;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PatoCamaraViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PatoCamaraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cargando patos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
