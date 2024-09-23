package ru.androidschool.intensiv.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.androidschool.intensiv.R

abstract class BaseFragment<State, Effect, Action,
        ViewModel : BaseViewModel<State, Effect, Action>,
        Binding : ViewBinding> : Fragment() {

    protected val binding: Binding get() = _binding!!
    private var _binding: Binding? = null

    lateinit var viewModel: ViewModel

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

    abstract fun createVm(): ViewModel

    abstract fun renderState(state: State)

    abstract fun renderEffect(effect: Effect)

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        resolveVm()
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        rxCompositeDisposable.clear()
    }

    fun showToast(@StringRes stringRes: Int) {
        Toast.makeText(requireContext(), resources.getText(stringRes), Toast.LENGTH_LONG).show()
    }

    fun showMessage(@StringRes title: Int, @StringRes message: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(resources.getString(title))
            setMessage(resources.getString(message))
            setPositiveButton(resources.getString(R.string.button_got_it)) { dialog, _ -> dialog.dismiss() }
        }.show()
    }

    @Suppress("UNCHECKED_CAST")
    private fun resolveVm() {
        val viewModelObject = createVm()
        viewModel = ViewModelProvider(
            viewModelStore,
            object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return viewModelObject as T
                }
            }
        )[viewModelObject::class.java]
    }

    private fun subscribeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach { currentState -> currentState?.run(::renderState) }
                    .collect()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.onEach(::renderEffect).collect()
            }
        }
    }
}
