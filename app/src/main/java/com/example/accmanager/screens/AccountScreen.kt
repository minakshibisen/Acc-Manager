package com.example.accmanager.screens

import  android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.accmanager.EmptyScreen
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.accmanager.DestinationScreens
import com.example.accmanager.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var isAddAccountDialogVisible by remember { mutableStateOf(false) }
    var accounts by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        floatingActionButton = {
            FAB(onFabClick = { isAddAccountDialogVisible = true })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Accounts",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )

            SearchBar(
                query = searchQuery,
                onQueryChanged = { searchQuery = it },
                onSearch = { }
            )


            if (accounts.isEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                EmptyScreen()
            } else {
                accounts.forEach { account ->
                    AccountCard(navController = navController, accountName = account)
                }
            }
        }

        if (isAddAccountDialogVisible) {
            AddAccountDialog(
                onClose = { isAddAccountDialogVisible = false },
                onAddAccount = { accountName ->
                    accounts = accounts + accountName
                    isAddAccountDialogVisible = false
                }
            )
        }
    }
}

@Composable
fun FAB(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClick,
        shape = CircleShape,
        modifier = Modifier.padding(15.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add Status",
            tint = Color.White
        )
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search"
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddAccountDialog(onClose: () -> Unit, onAddAccount: (String) -> Unit) {
    var accountName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(10.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add New Account",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = onClose) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Account Name")

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = accountName,
                    onValueChange = { newValue -> accountName = newValue },
                    label = { Text("Enter Account Name") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Red,
                        unfocusedBorderColor = Color.Red,
                        textColor = Color.Red
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(18.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    listOf(
                        "November Expenses",
                        "Cash",
                        "Salary",
                        "Savings",
                        "Investment"
                    ).forEach { suggestion ->
                        SuggestionChip(suggestion)
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        onAddAccount(accountName)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                ) {
                    Text("Add", color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        },
        dismissButton = {}
    )
}

@Composable
fun SuggestionChip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFD6E4FF) // Light blue color
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}

@Composable
fun AccountCard(navController: NavController, accountName: String) {
    val context = LocalContext.current
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
//            BottomSheetContent(
//                onDeleteClicked = {  },
//                onRenameClicked = { }
//            )
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    // Navigate to the DetailScreen with account details
                    navController.navigate(
                        DestinationScreens.AccountDetailScreen.createRoute(
                            accountName
                        )
                    )

                },
            shape = RoundedCornerShape(12.dp),
            elevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = accountName,
                        color = Color.Blue
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_horiz_24),
                        contentDescription = "More options",
                        tint = Color.Blue,
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                // bottomSheetState.show()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.Gray.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "₹ 0", color = Color.Black)
                        Text(text = "Balance", color = Color.Gray)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "₹ 0", color = Color.Green)
                        Text(text = "Credit", color = Color.Green)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "₹ 0", color = Color.Red)
                        Text(text = "Debit", color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(onDeleteClicked: () -> Unit, onRenameClicked: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Delete",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDeleteClicked() }
                .padding(vertical = 8.dp),
            color = Color.Red
        )
        Text(
            text = "Rename",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onRenameClicked() }
                .padding(vertical = 8.dp),
            color = Color.Blue
        )
    }
}

