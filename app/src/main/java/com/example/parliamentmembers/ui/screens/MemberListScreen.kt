package com.example.parliamentmembers.ui.screens

import TopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.parliamentmembers.R
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.ui.viewmodels.AppViewModelProvider
import com.example.parliamentmembers.ui.viewmodels.MemberListViewModel

@Composable
fun MemberListScreen(
    navCtrl: NavController,
    backStackEntry: NavBackStackEntry,
    memListVM: MemberListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val imgBaseUrl = "https://avoindata.eduskunta.fi/"
    val navBackStackEntry = navCtrl.currentBackStackEntryAsState()
    val selectedType = backStackEntry.arguments?.getString("selected") ?: "Members"
    val pmList: List<Pair<ParliamentMember, Boolean>> by memListVM.pmList.collectAsState()

    LaunchedEffect(navBackStackEntry) { memListVM.getPMList() }

    Scaffold(
        topBar = {
            TopBar(
                title = selectedType.uppercase(),
                canNavigateBack = true,
                onNavigateUp = { navCtrl.navigateUp() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            items(pmList) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable { navCtrl.navigate(EnumScreens.MEMBER.withParams(it.first.hetekaId.toString())) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)).width(46.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(data = "${imgBaseUrl}${it.first.pictureUrl}")
                                        .apply(
                                            block = fun ImageRequest.Builder.() {
                                                placeholder(R.drawable.ic_launcher_foreground)
                                                error(R.drawable.ic_launcher_foreground)
                                            }
                                        )
                                        .build()
                                ),
                                contentDescription = null,
                                alignment = Alignment.TopCenter,
                                contentScale = ContentScale.FillHeight
                            )
                        }

                        Spacer(Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${it.first.firstname} ${it.first.lastname}",
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                style = MaterialTheme.typography.titleLarge
                            )
                            if (it.first.minister) {
                                Text(
                                    text = "Minister",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        when (it.second) {
                            true -> Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite icon",
                                tint = Color.Red,
                                modifier = Modifier.size(24.dp).clickable {
                                    memListVM.changeFavorite(it.first.hetekaId)
                                }
                            )
                            false -> Icon(
                                imageVector = Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite icon",
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.size(24.dp).clickable {
                                    memListVM.changeFavorite(it.first.hetekaId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}