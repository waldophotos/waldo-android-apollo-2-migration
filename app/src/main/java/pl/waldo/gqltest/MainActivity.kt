package pl.waldo.gqltest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import queries.GetNotificationsQuery

class MainActivity : AppCompatActivity() {
    private var d: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serverUrl = "https://core-graphql.dev.waldo.photos/gql"
        val accountId = "1TPSzRw6kEqn9gyRweX6hbj"
        val authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiZDVhZmZjMzAtM2ZlOC00OWQzLTlmNGYtMDE2Y2I2OTA2M2M2Iiwicm9sZXMiOltdLCJpc3MiOiJ3YWxkbzpjb3JlIiwiZ3JhbnRzIjpbXSwiZXhwIjoxNjE2Njk0MDk3LCJpYXQiOjE2MTQxMDIwOTd9._zo9bly9YR8O6xQsKMv8PSqsg5QmcsTI3mtHqMUitMI"

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                    request.header("Authorization", "Bearer $authToken")
                    chain.proceed(request.build())
                }
                .addInterceptor(HttpLoggingInterceptor().apply{
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()


        val apolloClient = ApolloClient.builder()
                .serverUrl(serverUrl)
                .okHttpClient(okHttpClient)
                .build()

        val q = apolloClient.query(GetNotificationsQuery(accountId))
        val a = Rx2Apollo.from(q)
        this.d = a.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })

    }
}