package alan.androidframe.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alan on 2018/3/15.
 */

public class HttpInterceptor implements Interceptor {

    private Map<String, String> httpHeadParamsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        //新的Request
        Request.Builder builder = oldRequest.newBuilder();
        builder.method(oldRequest.method(), oldRequest.body());
        //将新参数添加到header中
        if (httpHeadParamsMap.size() > 0) {
            for (Map.Entry<String, String> entry : httpHeadParamsMap.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        return chain.proceed(builder.build());
    }

    public static class Builder {
       private HttpInterceptor httpInterceptor;

        public Builder() {
            httpInterceptor = new HttpInterceptor();
        }

        public Builder addHeadStringParams(String key, String value) {
            httpInterceptor.httpHeadParamsMap.put(key, value);
            return this;
        }

        public Builder addHeadIntParams(String key, int value) {
            addHeadStringParams(key, String.valueOf(value));
            return this;
        }

        public Builder addHeadLongParams(String key, long value) {
            addHeadStringParams(key, String.valueOf(value));
            return this;
        }

        public Builder addHeadFloatParams(String key, float value) {
            addHeadStringParams(key, String.valueOf(value));
            return this;
        }

        public Builder addHeadDoubleParams(String key, double value) {
            addHeadStringParams(key, String.valueOf(value));
            return this;
        }

        public HttpInterceptor build() {
            return httpInterceptor;
        }

    }
}
