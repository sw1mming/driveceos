package drivetag.drivetag.com.driveceos.data_layer.requests;

import java.util.concurrent.TimeUnit;
import com.google.gson.JsonObject;
import drivetag.drivetag.com.driveceos.helpers.JsonObjectHelper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yuriy on 3/22/17.
 */

public abstract class ServerRequest<T> {
    public static final String HOST = "https://dev.drivetagdev.com/";
    private static final int CONNECT_TIMEOUT = 35;
    private static final int WRITE_TIMEOUT = 35;
    private static final int READ_TIMEOUT = 80;

    boolean isFailure;
    public T response;
    String error;


    /** Interface. */

    ServerRequest() {
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
                .build();
        return new Retrofit.Builder()
                .baseUrl(HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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

    /**
     *  Completion handler interface for request.
     */
    public interface ServerCompletionHandler {
        void completionHandler(ServerRequest request);
        void completionHandlerWithError(String error);
    }
}
