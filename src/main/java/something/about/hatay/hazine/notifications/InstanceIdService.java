package something.about.hatay.hazine.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "InstanceIdService";
    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG,  recent_token);
    }


}



