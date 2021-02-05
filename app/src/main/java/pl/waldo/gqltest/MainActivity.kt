package pl.waldo.gqltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
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
        val accountId = "1N56ZUb6Zp7oiw7nowhbz8r"
        val authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50X2lkIjoiYWFhMjUwMzgtYjVjNi00MmFkLWEzYzktZTY5Y2U2Yzg5MDViIiwicm9sZXMiOltdLCJpc3MiOiJ3YWxkbzpjb3JlIiwiZ3JhbnRzIjpbXSwiZXhwIjoxNjE1MTIwNDI5LCJpYXQiOjE2MTI1Mjg0Mjl9.oN45VuGTBF3iIU9Pip0k-ieVPAj674duEaYFzGDBlBQ"

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

        val q = apolloClient.query(GetNotificationsQuery(accountId, Input.fromNullable(10000), Input.fromNullable(0)))
        val a = Rx2Apollo.from(q)
        this.d = a.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {

                })

    }
}