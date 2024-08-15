package ru.androidschool.intensiv.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import io.reactivex.rxjava3.core.Observable
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.SearchToolbarBinding
import java.util.Timer
import java.util.TimerTask

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    lateinit var binding: SearchToolbarBinding

    private var hint: String = ""
    private var isCancelVisible: Boolean = true

    private var timer = Timer()
    private var lastSearch = ""
    private val anySpace = "\\s".toRegex()

    private val observer: Observable<String> = Observable.create { emitter ->
        binding.searchEditText.addTextChangedListener { editable ->
            timer.cancel()
            editable?.takeIf { it.length > MIN_LENGTH }?.replace(anySpace, EMPTY)?.let {
                if (it.length > MIN_LENGTH && it != lastSearch) {
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            emitter.onNext(it)
                            lastSearch = it
                        }
                    }, DELAY)
                }
            }
        }
    }

    fun observer(): Observable<String> = observer

    init {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
    }

    fun setText(text: String?) {
        binding.searchEditText.setText(text)
    }

    fun clear() {
        binding.searchEditText.setText("")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = SearchToolbarBinding.inflate(LayoutInflater.from(context), this, true)
        binding.searchEditText.hint = hint
        binding.deleteTextButton.setOnClickListener {
            binding.searchEditText.text.clear()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.searchEditText.afterTextChanged { text ->
            if (!text.isNullOrEmpty() && !binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && binding.deleteTextButton.isVisible) {
                binding.deleteTextButton.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val MIN_LENGTH = 3
        private const val EMPTY = ""
        private const val DELAY = 500L
    }
}
