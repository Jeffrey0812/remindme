package id.ac.ui.cs.mobileprogramming.jeffrey.remindme.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.MyNotificationPublisher
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.R
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.entity.Todo
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.NOTIFICATION
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.NOTIFICATION_CHANNEL_ID
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.NOTIFICATION_ID
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.default_notification_channel_id
import id.ac.ui.cs.mobileprogramming.jeffrey.remindme.utils.Constants.pickImage
import kotlinx.android.synthetic.main.activity_create_todo.*
import java.text.SimpleDateFormat
import java.util.*

class CreateTodoActivity : AppCompatActivity() {

    var todo: Todo? = null

    private var notificationDate = ""
    private var notificationTime = ""

    private lateinit var imageView: ImageView
    private lateinit var buttonImage: Button
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        title = if (todo != null) getString(R.string.EditTodo) else getString(R.string.createTodo)

        // Date
        var calendarDate = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendarDate.set(Calendar.YEAR, year)
            calendarDate.set(Calendar.MONTH, monthOfYear)
            calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            date_field.text = sdf.format(calendarDate.time)
            notificationDate = date_field.text.toString()
        }

        date_field.setOnClickListener {
            DatePickerDialog(
                    this@CreateTodoActivity, dateSetListener,
                    calendarDate.get(Calendar.YEAR),
                    calendarDate.get(Calendar.MONTH),
                    calendarDate.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Time
        var calendarTime = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendarTime.set(Calendar.HOUR_OF_DAY, hour)
            calendarTime.set(Calendar.MINUTE, minute)
            time_field.text = SimpleDateFormat("HH:mm").format(calendarTime.time)
            notificationTime = time_field.text.toString()
        }

        time_field.setOnClickListener {
            TimePickerDialog(
                    this@CreateTodoActivity, timeSetListener,
                    calendarTime.get(Calendar.HOUR_OF_DAY),
                    calendarTime.get(Calendar.MINUTE),
                    false
            ).show()
        }

        // Category
        val spin = findViewById<Spinner>(R.id.category_field)
        val model = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        model.fetchCategoryItems().observe(this, Observer { spinnerData ->
            val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerData
            )
            spin.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val todo: Todo? = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.todo = todo
            if (todo != null) {
                prePopulateData(todo)
            }
        }

        // Image
        imageView = findViewById(R.id.image_View)
        buttonImage = findViewById(R.id.button_image)
        buttonImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        // Save Button
        val buttonSave: Button = findViewById(R.id.button_save)
        buttonSave.setOnClickListener {
            // Delay
            if (validateFields()) {
                val dft = SimpleDateFormat("dd.MM.yyyy HH:mm")
                val mAlertDateTime = "$notificationDate $notificationTime"
                val date = dft.parse(mAlertDateTime)
                val currentDate = Date()
                val diff = date.time - currentDate.time

                if (todo != null) {
                    scheduleNotification(getNotification(title_field.text.toString(), description_field.text.toString()), diff)
                    saveExistingTodo()
                } else {
                    scheduleNotification(getNotification(title_field.text.toString(), description_field.text.toString()), diff)
                    saveNewTodo()
                }
            }
        }

    }

    private fun prePopulateData(todo: Todo) {
        title_field.setText(todo.title)
        description_field.setText(todo.description)
        date_field.text = todo.date
        time_field.text = todo.time
        image_View.setImageURI(Uri.parse(todo.image))
    }

    private fun saveNewTodo() {
            if (validateFields()) {
                val todo = Todo(
                        todoId = 0,
                        title = title_field.text.toString(),
                        description = description_field.text.toString(),
                        date = date_field.text.toString(),
                        time = time_field.text.toString(),
                        image = imageUri.toString(),
                        catTodoId = 1,
                        userTodoId = 1
                )
                val intent = Intent()
                intent.putExtra(Constants.INTENT_OBJECT, todo)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

    private fun saveExistingTodo() {
            if (validateFields()) {
                val data: Todo? = intent.getParcelableExtra(Constants.INTENT_OBJECT)
                val todo = data?.todoId?.let { it1 -> Todo(
                        todoId = it1,
                        title = title_field.text.toString(),
                        description = description_field.text.toString(),
                        date = date_field.text.toString(),
                        time = time_field.text.toString(),
                        image = imageUri.toString(),
                        catTodoId = 1,
                        userTodoId = 1
                ) }
                val intent = Intent()
                intent.putExtra(Constants.INTENT_OBJECT, todo)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

    private fun validateFields(): Boolean {
        if (title_field.text?.isEmpty()!!) {
            title_container.error = getString(R.string.pleaseEnterTitle)
            title_field.requestFocus()
            return false
        }
        if (date_field.text?.toString()?.contains("Date")!!) {
            Log.i("TAG", "MASUK")
            title_container.error = getString(R.string.pleaseEnterDate)
            date_field.requestFocus()
            return false
        }
        if (time_field.text?.toString()?.contains("Time")!!) {
            title_container.error = getString(R.string.pleaseEnterTime)
            time_field.requestFocus()
            return false
        }
        return true
    }

    // Notifications
    private fun scheduleNotification(notification: Notification, delay: Long) {
        val notificationIntent = Intent(this, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager: AlarmManager = (getSystemService(Context.ALARM_SERVICE) as AlarmManager)
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    private fun getNotification(title: String, content: String): Notification {
        val builder = NotificationCompat.Builder(this, default_notification_channel_id)
        builder.setContentTitle(title)
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel(true)
        builder.setShowWhen(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.priority = NotificationCompat.PRIORITY_MAX
        return builder.build()
    }

    // Image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }


}