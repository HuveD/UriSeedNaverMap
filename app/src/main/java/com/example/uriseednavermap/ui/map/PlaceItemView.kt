package com.example.uriseednavermap.ui.map

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uriseednavermap.data.model.Place


@Composable
fun PlaceItemView(place: Place) {
    val context = LocalContext.current;
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 장소명
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = place.place_name,
                color = Color.Blue,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
            )
            // 카테고리명
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = place.category_name.split(">").last().trim(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = Color.Gray,
            )
        }
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 거리
            Text(
                text = "${place.distance}m",
                color = Color.Gray,
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.Gray,
            )
            // 주소
            Text(
                text = place.address_name,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 번호
            ClickableText(
                modifier = Modifier.padding(end = 16.dp),
                text = AnnotatedString(text = place.phone),
                style = TextStyle(color = Color.Blue, fontSize = 12.sp),
                onClick = {
                    val number = place.phone;
                    if (number.isNotEmpty()) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("tel:$number"),
                            ),
                        )
                    }
                },
            )
            // 길찾기
            ClickableText(
                modifier = Modifier.padding(end = 16.dp),
                text = AnnotatedString(text = "지도"),
                style = TextStyle(color = Color.Gray, fontSize = 12.sp),
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("geo:${place.y},${place.x}"),
                        ),
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun ItemPreview() {
    PlaceItemView(
        Place(
            id = "place",
            place_name = "name",
            category_name = "category",
            category_group_code = "categoryGroupCode",
            category_group_name = "categoryGroupName",
            phone = "02-002-2323",
            address_name = "addressNameaddressNameaddressNameaddressNameaddressNameaddressName",
            road_address_name = "roadAddressName",
            x = "39",
            y = "137",
            place_url = "url",
            distance = "170",
        )
    )
}