package com.example.accmanager.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountDetailScreen(navController: NavController, accountName: String) {
    var selectedTab by remember { mutableStateOf("All") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle action here */ },
                backgroundColor = Color(0xFF1976D2)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 56.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = accountName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 0.dp, bottom = 0.dp)
                )
                TabLayout(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                HeaderRow()
                TransactionList(selectedTab)
            }

            FooterSection(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}
@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1976D2))
            .padding(vertical = 8.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Date ↑", color = Color.White)
        Text("Notes", color = Color.White)
        Text("Amount", color = Color.White)
    }
}
@Composable
fun FooterSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1976D2))
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Credit", color = Color.White)
                Text("15,000", color = Color.White)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Debit", color = Color.White)
                Text("0", color = Color.White)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Balance", color = Color.White)
                Text("15,000", color = Color.White)
            }
        }
    }
}
@Composable
fun TabLayout(selectedTab: String, onTabSelected: (String) -> Unit) {
    val tabs = listOf("All", "Credit", "Debit")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F5))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEach { tab ->
            TabItem(
                title = tab,
                isSelected = tab == selectedTab,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = if (isSelected) Color.LightGray else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = if (isSelected) Color.White else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            color = Color.Black
        )
    }
}

@Composable
fun TransactionList(selectedTab: String) {
    val transactions = listOf(
        Transaction("06/11/2024", "Salary", "₹15,000", true),
        Transaction("05/11/2024", "Groceries", "₹2,000", false)
        // Add more transactions here
    )

    val filteredTransactions = when (selectedTab) {
        "Credit" -> transactions.filter { it.isCredit }
        "Debit" -> transactions.filter { !it.isCredit }
        else -> transactions
    }

    Column() {
        filteredTransactions.forEach { transaction ->
            TransactionItem(
                date = transaction.date,
                note = transaction.note,
                amount = transaction.amount,
                isCredit = transaction.isCredit
            )
        }
    }
}

data class Transaction(val date: String, val note: String, val amount: String, val isCredit: Boolean)

@Composable
fun TransactionItem(date: String, note: String, amount: String, isCredit: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = date, color = Color.Gray)
        Text(text = note, color = Color.Black)
        Text(
            text = amount,
            color = if (isCredit) Color(0xFF4CAF50) else Color(0xFFFF5722)
        )
    }

}