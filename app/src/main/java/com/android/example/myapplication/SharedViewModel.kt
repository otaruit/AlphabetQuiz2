import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel() : ViewModel() {
    // LiveDataを使って状態を管理
    val isForegroundChanged = MutableLiveData<Boolean>()
}
