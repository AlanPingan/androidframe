package alan.androidframe.network;

import alan.androidframe.entity.Student;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Alan on 2018/3/14.
 */

public interface RequestInterface {

    @GET("/student")
    Call<Student> getStudent();

    @HTTP(method = "GET", path = "/student/{id}", hasBody = false)
    Call<Student> getStudentInfo(@Path("id") int id);

    //表单格式的请求 @FormUrlEncoded标记
    @HTTP(method = "POST", path = "/student/login", hasBody = true)
    @FormUrlEncoded
    //此注解表示为表单类型的请求，同时下面的参数必须加上@Field
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    //@Multipart 标记适用于有文件上传的场景
    @POST("/register")
    @Multipart
    //@Part后面可以跟 requestBody、Multipart、任意类型
    Call<ResponseBody> register(@Part("username") RequestBody username, @Part("password") RequestBody password, @Part MultipartBody.Part file);
}
