package com.example.parliamentmembers.ui.screens

import TopBar
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.parliamentmembers.R
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import com.example.parliamentmembers.ui.AppViewModelProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MemberScreen(
    navCtrl: NavController,
    memberVM: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val navBackStackEntry = navCtrl.currentBackStackEntryAsState()
    val scrollState = rememberScrollState()
    val member: ParliamentMember by memberVM.member.collectAsState()
    val memberExtra: ParliamentMemberExtra by memberVM.memberExtra.collectAsState()
    val memberLocal: ParliamentMemberLocal by memberVM.memberLocal.collectAsState()

    LaunchedEffect(navBackStackEntry) { memberVM.getData() }

    Scaffold(
        topBar = {
            TopBar(
                title = "",
                canNavigateBack = true,
                onNavigateUp = { navCtrl.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(8.dp)
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 1f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                ) {
                    if (member.minister) {
                        Text(
                            text = "Minister",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Text(
                        text = "${member.firstname} ${member.lastname}",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Heteka ID ",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${member.hetekaId}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Seat",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${member.seatNumber}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Party",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${member.party.uppercase()}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Born Year",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${memberExtra.bornYear}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Constituency",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${memberExtra.constituency}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            if (!memberExtra.twitter.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Twitter",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("X")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mark as favorite",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("M")
                }
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Note",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            if (memberLocal.note.isNullOrEmpty()) "+" else "Edit",
                            modifier = Modifier.clickable {
                                navCtrl.navigate(EnumScreens.NOTE.withParam(member.hetekaId.toString()))
                            }
                        )
                    }

                    if (!memberLocal.note.isNullOrEmpty()) {
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = memberLocal.note!!,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

class MemberViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository,
): ViewModel() {
    private var hetekaId: String? = savedStateHandle.get<String>("param")
    private val _member = MutableStateFlow<ParliamentMember>(
        ParliamentMember(
            0,
            0,
            "lastname",
            "firstname",
            "party",
            false,
            ""
        )
    )
    private val _memberExtra = MutableStateFlow<ParliamentMemberExtra>(
        ParliamentMemberExtra(
            0,
            null,
            0,
            ""
        )
    )
    private val _memberLocal = MutableStateFlow<ParliamentMemberLocal>(
        ParliamentMemberLocal(
            0,
            false,
            null
        )
    )

    val member: StateFlow<ParliamentMember> = _member
    val memberExtra: StateFlow<ParliamentMemberExtra> = _memberExtra
    val memberLocal: StateFlow<ParliamentMemberLocal> = _memberLocal

    init { getData() }

    fun getData() = viewModelScope.launch {
        val memberFlow = async { dataRepo.getMemberWithId(hetekaId!!.toInt()).first() }
        val memberExtraFlow = async { dataRepo.getMemberExtraWithId(hetekaId!!.toInt()).first() }
        val memberLocalFlow = async { dataRepo.getMemberLocalWithId(hetekaId!!.toInt()).first() }

        _member.emit(memberFlow.await())
        _memberExtra.emit(memberExtraFlow.await())
        _memberLocal.emit(memberLocalFlow.await())
    }
}