package alan.androidframe.projectutils.rxjava;

import java.util.HashMap;
import java.util.Set;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Alan on 2018/4/26.
 */

public class RxEventBusHelper {

    private HashMap<Integer, Observable> observableMap = new HashMap<>();

    public void addObservable(Integer tag, Class cls, Action1 action1) {
        Observable observable = RxEventBus.getInstance().registerAndSubScribe(tag, cls, action1);
        observableMap.put(tag, observable);
    }

    public void unRegister() {
        if (observableMap == null) {
            return;
        }
        Set<Integer> keySet = observableMap.keySet();
        for (Integer key : keySet) {
            RxEventBus.getInstance().unRegister(key, observableMap.get(key));
        }
        observableMap.clear();
    }


}
