package ir.arcagroup.uploadfilewithretrofit.webService;


import ir.arcagroup.uploadfilewithretrofit.model.UploadResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @Multipart
    @POST("upload")
    Call<UploadResponse> upload(@Part MultipartBody.Part body) ;
}
