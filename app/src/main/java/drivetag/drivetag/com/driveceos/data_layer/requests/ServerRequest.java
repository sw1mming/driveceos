package drivetag.drivetag.com.driveceos.data_layer.requests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yuriy on 3/22/17.
 */

public abstract class ServerRequest<T> {

    public T serverResponse;

    public static final String HOST = "https://dev.drivetagdev.com/";
    private static final int CONNECT_TIMEOUT = 35;
    private static final int WRITE_TIMEOUT = 35;
    private static final int READ_TIMEOUT = 80;

    public boolean isFailure;

    public String error;

    public String token;

    /** Interface. */

    public ServerRequest() {
        setupService();
    }

    protected Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder();

                        if (token != null) {
                            requestBuilder.addHeader("Authorization: Basic", token);
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }})

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        return retrofit;
    }

    public void setupService() {
    }

    public boolean isSucceedResponse(JsonObject responseObject) {

        if (JsonObjectHelper.hasValueFromKey("success", responseObject)) {
            if (responseObject.get("success").getAsBoolean()) {
                return true;
            } else {
                return false;
            }
        }

        if (JsonObjectHelper.hasValueFromKey("message", responseObject)) {
            String message = responseObject.get("message").getAsString();

            if (message.toLowerCase().equals("success")) {
                return true;
            }
        }

        return false;
    }


    public String errorFromResponse(JsonObject responseObject) {
        String message = null;

        if (JsonObjectHelper.hasValueFromKey("message", responseObject)) {
           message = responseObject.get("message").getAsString();
        }

        if (message == null) {
            message = ("Undefined error");
        }

        return  message;
    }

    public JsonElement handleSuccessResponse(Response<JsonElement> response) {
        if (response.isSuccessful()) {

            JsonElement jsonElement = response.body();

            if (jsonElement.isJsonObject()) {
                final JsonObject jsonObject = jsonElement.getAsJsonObject();

                if (!isSucceedResponse(jsonObject)) {
                    error = errorFromResponse(jsonObject);
                }

                return jsonObject;
            } else {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                return jsonArray;
            }
        } else {
            isFailure = true;

            try {
                error = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     *  Completion handler interface for request.
     */

    public interface ServerCompletionHandler<T> {
        void completionHandler(ServerRequest<T> request);
        void completionHandlerWithError(String error);
    }
}
