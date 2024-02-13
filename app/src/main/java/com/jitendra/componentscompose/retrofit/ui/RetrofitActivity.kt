package com.developersmarket.componentscompose.retrofit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersmarket.componentscompose.ui.theme.ComponentsComposeTheme
import com.developersmarket.componentscompose.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@AndroidEntryPoint
class RetrofitActivity() : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComponentsComposeTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    GETData(mainViewModel)
                }
            }

        }
    }


    @Composable fun GETData(mainViewModel: MainViewModel) {
        when (val result = mainViewModel.response.value) {
            is ApiState.Success -> {
                LazyColumn {
                    items(result.data.products) { response ->
                       val title = response.title
                        val description = response.description
                        val thumbnail = response.thumbnail
                        val ID = response.id.toString()

                       setUI(title,description,thumbnail,ID)

                        Log.v("APiSucess ==> ",""+response.toString())
                    }
                }
            }

            is ApiState.Failure -> {
                Text(text = "${result.msg}")
            }

            ApiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            ApiState.Empty -> {

            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable  fun setUI(title: String, description: String, thumbnail: String, ID: String) {

        Card(onClick = {
               val intent = Intent(applicationContext, ProductDetailsScreen::class.java)
                  //intent.putExtra("Prodcut_Id", ID)
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                  startActivity(intent)
                       },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 14.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(4.dp),) {
            Column(modifier = Modifier) {

            Box(modifier = Modifier.fillMaxSize()){

            Image(painter = rememberAsyncImagePainter(thumbnail),
                   contentDescription = null, modifier = Modifier
                    .fillMaxWidth().height(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                     contentScale = ContentScale.Crop,)
            }

                Text(text = title,
                     modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp))

                Text(text = description,
                     modifier = Modifier.padding(start = 10.dp, top = 7.dp, bottom = 5.dp, end = 10.dp)
                )
            }
        }
    }

}
