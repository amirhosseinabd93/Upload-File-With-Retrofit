package ir.arcagroup.uploadfilewithretrofit;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ir.arcagroup.uploadfilewithretrofit.model.UploadResponse;
import ir.arcagroup.uploadfilewithretrofit.webService.APIClient;
import ir.arcagroup.uploadfilewithretrofit.webService.APIInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button uploadBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadBtn = findViewById(R.id.btn_upload);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                try {
                    startActivityForResult(intent, 100);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    String type = getFileExtension(data.getData());
                    sendUploadRequest(getBytes(inputStream) , type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendUploadRequest(byte[] bytes , String type) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),bytes);

        MultipartBody.Part file = MultipartBody.Part.createFormData("file" , "myImage." + type ,requestFile);

        Call<UploadResponse> call = apiInterface.upload(file);

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

            }
        });
    }

    private byte[] getBytes(InputStream is) throws IOException {
       try {
            byte[] buff = new byte[inputStream.available()];
            while (inputStream.read(buff) > 0) ;
            return buff;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFileExtension(Uri uri) {
        return mime.getSingletone().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}
