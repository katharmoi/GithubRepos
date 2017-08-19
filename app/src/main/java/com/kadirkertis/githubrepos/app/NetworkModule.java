package com.kadirkertis.githubrepos.app;

import android.content.Context;
import android.support.test.espresso.IdlingResource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kadirkertis.githubrepos.BuildConfig;
import com.kadirkertis.githubrepos.IdlingResources;
import com.kadirkertis.githubrepos.githubService.GithubApi;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Kadir Kertis on 3.8.2017.
 */
@Module
public class NetworkModule {

    @AppScope
    @Provides
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        if(BuildConfig.DEBUG){
            IdlingResources.registerOkHttp(client);
        }
        return client;
    }

    @AppScope
    @Provides
    public ObjectMapper provideObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    @AppScope
    @Provides
    public JacksonConverterFactory provideJacksonConverter(ObjectMapper objectMapper) {
        return  JacksonConverterFactory.create(objectMapper);
    }

    @AppScope
    @Provides
    public RxJava2CallAdapterFactory provideRxJavaCallAdapter(){
        return RxJava2CallAdapterFactory.create();
    }

    @AppScope
    @Provides
    public Retrofit provideRetrofit(JacksonConverterFactory jacksonConverterFactory,
                                    RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                                    OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @AppScope
    @Provides
    public GithubApi provideGithubApi(Retrofit retrofit){
        return  retrofit.create(GithubApi.class);
    }

    @AppScope
    @Provides
    public Picasso providePicasso(Context context, OkHttpClient okHttpClient){
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

}
