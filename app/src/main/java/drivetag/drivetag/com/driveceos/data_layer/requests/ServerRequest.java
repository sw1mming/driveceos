package drivetag.drivetag.com.driveceos.data_layer.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yuriy on 3/22/17.
 */

public abstract class ServerRequest<T> {


    public ServerRequest() {
        setupService();
    }

    /**
     * 1. create constructor
     * 2. create method setupService
     * 3. call setupService in constr
     * 4. in SignInRequest overide setupService
     *
     * */

//    public List<T> objects = new ArrayList<>();
    public T response;

    public static final String HOST = "https://dev.drivetagdev.com/";
    private static final int CONNECT_TIMEOUT = 35;
    private static final int WRITE_TIMEOUT = 35;
    private static final int READ_TIMEOUT = 80;
    protected static final int LOGIN_TIMEOUT = 10;


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

    /**
     *  Completion handler interface for request.
     */

    public interface ServerCompletionHandler<T> {
//        void completionHandler(T response, ServerRequest request);
        void completionHandler(ServerRequest request);
        void completionHandlerWithError(String error);
    }
}
