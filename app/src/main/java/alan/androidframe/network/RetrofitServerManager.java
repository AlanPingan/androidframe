package alan.androidframe.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alan on 2018/3/15.
 */

public class RetrofitServerManager {
    private static final int DEFAULT_CONNECTION_TIME_OUT = 5;
    private static final int DEFAULT_READ_WRITE_TIME_OUT = 15;
    private Retrofit retrofit;

    private RetrofitServerManager() {
        createRetrofit();
    }

    private OkHttpClient getOkHttpClient() {
        //创建OkHttpClient实例
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECTION_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_WRITE_TIME_OUT, TimeUnit.SECONDS);
        //添加参数拦截器
        builder.addInterceptor(getInterceptor());
        return builder.build();
    }

    private Interceptor getInterceptor() {
        HttpInterceptor httpInterceptor = new HttpInterceptor.Builder()
                .addHeadStringParams("username", "alan")
                .addHeadStringParams("password", "666")
                .build();
        return httpInterceptor;
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.INSTANCE.getBASE_URL())
                .client(getOkHttpClient())//添加OkHttpClient
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //使用静态内部类实现单例
    private static class Holder {
        public static final RetrofitServerManager INSTANCE = new RetrofitServerManager();
    }

    /**
     * 获取Retrofit实例
     *
     * @return
     */
    public static RetrofitServerManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取相应的server
     *
     * @param server Server 的 Class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> server) {
        return retrofit.create(server);
    }

}
