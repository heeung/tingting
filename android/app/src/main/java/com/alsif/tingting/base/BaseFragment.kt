package com.alsif.tingting.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alsif.tingting.R
import com.alsif.tingting.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar

private const val TAG = "BaseFragment"
// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    protected val binding get() = _binding!!

    private lateinit var _mActivity: MainActivity
    protected val mActivity get() = _mActivity

    private lateinit var _imm: InputMethodManager
    protected val imm get() = _imm


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mActivity = context as MainActivity
        _imm = ContextCompat.getSystemService(mActivity, InputMethodManager::class.java) as InputMethodManager
    }

//    override fun onDestroyView() {
//        _binding = null
//        Log.d(TAG, "onDestroyView: 프레그먼트가 destroy 되었습니다.")
//        super.onDestroyView()
//    }

    // TODO 문제 발생시 onDestroyView로..
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 스낵바를 띄웁니다. 커스텀 하려면 type 분기를 추가하고 사용하세요.
     */
    fun showSnackbar(view: View, type: String, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
        snackbar.show()
    }

    /**
     * 소프트 키보드를 올립니다.
     */
    fun showKeyBoard(editText: View) {
        imm.showSoftInput(editText, 0)
    }

    /**
     * 소프트 키보드를 내립니다.
     */
    fun hideKeyBoard(editText: View) {
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    /**
     * 포커스를 주고, 소프트 키보드를 올립니다.
     */
    fun focusAndShowKeyBoard(editText: View) {
        editText.requestFocus()
        showKeyBoard(editText)
    }
}