package pl.waldo.gqltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.schedulers.Schedulers
import queries.GetNotificationsQuery

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountId = ""

        val apolloClient = ApolloClient.builder()
                .serverUrl("")
                .build()

        val a = Rx2Apollo
                .from(apolloClient.query(
                GetNotificationsQuery(accountId, Input.fromNullable(0), Input.fromNullable(1110))))
        a.subscribeOn(Schedulers.io())
          .subscribe({

          }, {

          })

    }
}