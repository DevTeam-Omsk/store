package Some_objects

import android.app.Application
import android.content.Context


class ContextPasser : Application() {
    var context: Context

    init {
        context = applicationContext
    }
}