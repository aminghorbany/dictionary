package com.example.dictionary.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.db.DictionaryDataBase
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.db.SharedPrefsManager
import com.example.dictionary.utils.showLongToast
import com.opencsv.CSVReaderBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding
    private lateinit var navController: NavController
    @Inject lateinit var database: DictionaryDataBase
    @Inject lateinit var sharedPrefsManager: SharedPrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            navController = findNavController(R.id.navHost)
            bottomNav.setupWithNavController(navController)
            bottomNav.selectedItemId = R.id.searchFragment
        }

        if (sharedPrefsManager.getIsFirstRun()) {
            lifecycleScope.launch {
                readCsvAndInsertData(this@MainActivity, database)
            }
            sharedPrefsManager.setIsFirstRun(false)
        }
    }

    //handel onBackPress
    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    private fun readCsvAndInsertData(context: Context, database: DictionaryDataBase) {
        val inputStream = context.assets.open("db.csv")
        val reader = CSVReaderBuilder(InputStreamReader(inputStream, Charsets.UTF_8)).build()

        val entries = mutableListOf<DictionaryEntity>()
        reader.readAll().forEach { row ->
            if (row.size >= 2) {
                val englishWord = row[0]
                val persianWord = row[1]
                entries.add(DictionaryEntity(englishWord = englishWord, persianWord = persianWord , isFavorite = false))
            }
        }
        reader.close()
        CoroutineScope(Dispatchers.IO).launch {
            database.dictionaryDao().insertAllWords(entries)
        }
    }
}