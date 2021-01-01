package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.R
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Category
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants
import kotlinx.android.synthetic.main.activity_create_category.*

class CreateCategoryActivity : AppCompatActivity() {
    var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val category: Category? = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.category = category
            if (category != null) {
                prePopulateData(category)
            }
        }

        title = if (category != null) getString(R.string.EditCategory) else getString(R.string.createCategory)

        val button: Button = findViewById(R.id.button_save)
        button.setOnClickListener {
            if(category != null) {
                saveExistingCategory()
            }
            else {
                saveNewCategory()
            }
        }
    }

    private fun prePopulateData(category: Category) {
        category_name_field.setText(category.name)
    }

    private fun saveNewCategory() {
        if (validateFields()) {
            val category = Category(categoryId = 0, name = category_name_field.text.toString())
            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, category)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun saveExistingCategory() {
        if (validateFields()) {
            val data: Category? = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            val todo = data?.categoryId?.let { it1 -> Category(categoryId = it1, name = category_name_field.text.toString()) }
            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, todo)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        if (category_name_field.text?.isEmpty()!!) {
            category_title_container.error = getString(R.string.pleaseEnterCategory)
            category_name_field.requestFocus()
            return false
        }
        return true
    }

}