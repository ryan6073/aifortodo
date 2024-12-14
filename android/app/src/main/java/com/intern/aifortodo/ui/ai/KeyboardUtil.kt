import android.view.View
import androidx.appcompat.app.AppCompatActivity

object KeyboardUtil {
    fun setupKeyboardListener(activity: AppCompatActivity, rootView: View?) {
        if (rootView == null) {
            throw IllegalArgumentException("Root view cannot be null")
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            // 检查键盘是否弹出
            val isKeyboardVisible = keypadHeight > screenHeight * 0.15

            if (isKeyboardVisible) {
                // 键盘弹出，调整布局
                rootView.translationY = -keypadHeight.toFloat()
            } else {
                // 键盘收起，还原布局
                rootView.translationY = 0f
            }
        }
    }
}
