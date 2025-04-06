package com.example.heartbeat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class DateIdeasActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var datesButton: TextView
    private lateinit var aboutButton: TextView
    private lateinit var authButton: TextView

    private val dateIdeas = listOf(
        DateIdea(
            R.drawable.brr,
            "СВИДАНИЯ НА ПРИРОДЕ",
            "Наслаждайтесь романтическим ужином на природе, в уединенном месте, специально подобранном для вас. Мягкие пледы, вкусные закуски и прекрасные виды — все это создаст атмосферу уюта и близости"
        ),
        DateIdea(
            R.drawable.home,
            "СВИДАНИЯ ДОМА",
            "Свидания дома могут быть уютными, романтичными и полными теплых моментов"
        ),
        DateIdea(
            R.drawable.street,
            "СВИДАНИЯ, ПРОГУЛКИ ПО УЛИЦЕ",
            "Иногда просто прогулка по улице может стать замечательным свиданием"
        ),
        DateIdea(
            R.drawable.sky,
            "СВИДАНИЯ ПОД КРАСИВЫМ НЕБОМ",
            "Бывает, что просто посмотреть на небо может быть замечательным и незабываемым свиданием"
        ),
        DateIdea(
            R.drawable.far_sky,
            "СВИДАНИЯ ДАЛЕКО ОТ ДОМА",
            "Поехать со своим любимым человеком на далёкие расстояния может быть замечательным и незабываемым свиданием"
        ),
        DateIdea(
            R.drawable.winter_street,
            "СВИДАНИЯ ЗИМОЙ",
            "Времена года не имеют значения, главное, что вы вместе. Зимние свидания могут быть замечательными"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_date_ideas)

            // Hide the default action bar
            supportActionBar?.hide()

            initializeViews()
            setupViewPager()
            setupClickListeners()

        } catch (e: Exception) {
            Log.e("DateIdeasActivity", "Error in onCreate: ${e.message}")
            Toast.makeText(this, "Произошла ошибка при загрузке идей", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initializeViews() {
        try {
            viewPager = findViewById(R.id.viewPager)
            datesButton = findViewById(R.id.datesButton)
            aboutButton = findViewById(R.id.aboutButton)
            authButton = findViewById(R.id.authButton)
        } catch (e: Exception) {
            Log.e("DateIdeasActivity", "Error initializing views: ${e.message}")
            throw e
        }
    }

    private fun setupViewPager() {
        try {
            val adapter = DateIdeasAdapter(
                dateIdeas,
                onPrevClick = { 
                    if (viewPager.currentItem > 0) {
                        viewPager.currentItem = viewPager.currentItem - 1
                    }
                },
                onNextClick = {
                    if (viewPager.currentItem < dateIdeas.size - 1) {
                        viewPager.currentItem = viewPager.currentItem + 1
                    }
                }
            )
            viewPager.adapter = adapter

            // Add page change callback for logging
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("DateIdeasActivity", "Selected page: $position")
                }
            })

        } catch (e: Exception) {
            Log.e("DateIdeasActivity", "Error setting up ViewPager: ${e.message}")
            throw e
        }
    }

    private fun setupClickListeners() {
        try {
            datesButton.setOnClickListener {
                // Already on dates screen
            }

            aboutButton.setOnClickListener {
                // TODO: Navigate to About screen
            }

            authButton.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        } catch (e: Exception) {
            Log.e("DateIdeasActivity", "Error setting up click listeners: ${e.message}")
            throw e
        }
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
            // Clean up ViewPager
            viewPager.adapter = null
        } catch (e: Exception) {
            Log.e("DateIdeasActivity", "Error in onDestroy: ${e.message}")
        }
    }
} 