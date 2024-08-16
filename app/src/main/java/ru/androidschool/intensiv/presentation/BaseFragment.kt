package ru.androidschool.intensiv.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.androidschool.intensiv.R
import timber.log.Timber

abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    protected val binding: Binding get() = _binding!!
    private var _binding: Binding? = null

    protected val rxCompositeDisposable = CompositeDisposable()

    protected val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        rxCompositeDisposable.clear()
    }

    fun handleError(error: Throwable?) {
        showToast(R.string.load_data_error)
        Timber.e(error)
    }

    fun showToast(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), resources.getText(stringRes), Toast.LENGTH_SHORT).show()
    }
}
