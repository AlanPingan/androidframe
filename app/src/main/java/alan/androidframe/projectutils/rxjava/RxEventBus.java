package alan.androidframe.projectutils.rxjava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import alan.androidframe.projectutils.CommonUtils;
import alan.androidframe.projectutils.WeakHandler;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Alan on 2018/4/26.
 */

public class RxEventBus {

    private ConcurrentHashMap<Integer, List<Subject>> subjectMapper = null;
    private WeakHandler weakHandler = new WeakHandler();

    public RxEventBus() {
        subjectMapper = new ConcurrentHashMap<>();
    }

    public static RxEventBus getInstance() {
        return RxEventBusHolder.instance;
    }

    private static class RxEventBusHolder {
        private static final RxEventBus instance = new RxEventBus();

    }

    public <T> Observable<T> registerAndSubScribe(Integer tag, Class<T> cls, Action1<? super T> action1) {
        Observable<T> observable = register(tag);
        observable.subscribe(action1);
        return observable;
    }


    private <T> Observable<T> register(Integer tag) {
        if (null == tag) {
            return null;
        }
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = Collections.synchronizedList(new ArrayList<Subject>());
            subjectMapper.put(tag, subjectList);
        }
        Subject<T, T> subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    public void unRegister(Integer tag, Observable observable) {
        if (tag == null) {
            return;
        }
        List<Subject> subjectList = subjectMapper.get(tag);
        if (subjectList != null) {
            subjectList.remove(observable);
            if (subjectList.isEmpty()) {
                subjectMapper.remove(tag);
            }
        }
    }

    public void post(Integer tag, Object content) {
        if (tag == null) {
            return;
        }
        List<Subject> list = subjectMapper.get(tag);
        if (CommonUtils.hasItem(list)) {
            for (Subject subject : list) {
                subject.onNext(content);
            }
        }
    }

    public void delayPost(final Integer tag, final Object content, Long delay) {
        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                post(tag, content);
            }
        }, delay);
    }
}
