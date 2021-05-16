/**
 * Nike.SNKRS Coding Assessment, Thomas Sunderland.
 *
 * LinkedIn: https://www.linkedin.com/in/thomas-sunderland/
 * Medium: https://medium.com/@tsunderland77
 * StackOverflow: https://stackoverflow.com/users/4739877/thomas-sunderland
 */
package com.nike.snkrs.sunderland.ui.view


//region import directives

import android.annotation.SuppressLint
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.nike.snkrs.sunderland.R
import com.nike.snkrs.sunderland.databinding.FragmentWebViewBinding
import com.nike.snkrs.sunderland.ui.viewmodel.ViewModelWebView
import com.nike.snkrs.sunderland.util.Logger
import com.nike.snkrs.sunderland.util.tryCatch
import com.nike.snkrs.sunderland.util.tryCatchWithLogging

//endregion import directives


/**
 * Used to display web pages within the app
 * @author Thomas Sunderland. 2021 MAY 15
 */
class FragmentWebView : Fragment() {

    //region data members

    /**
     * Reference to the view model
     */
    private val viewModel by viewModels<ViewModelWebView>()

    /**
     * Reference to the binding for this fragment
     */
    private lateinit var binding: FragmentWebViewBinding

    /**
     * Reference to the target url to load
     */
    private val args: FragmentWebViewArgs by navArgs()
    //endregion data members


    //region lifecycle overrides

    /**
     * Called to have the fragment instantiate its user interface view. This is optional, and non-graphical
     * fragments can return null. This will be called between onCreate(Bundle) and onActivityCreated(Bundle).
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to. The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    //@formatter:off
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentWebViewBinding.inflate(inflater, container, false).apply {
            tryCatch {
                this@FragmentWebView.binding = this
                viewModel = this@FragmentWebView.viewModel
                lifecycleOwner = this@FragmentWebView
            }
        }.root
    }
    //@formatter:on

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    //@formatter:off
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tryCatchWithLogging({
            // call into base class implementation
            super.onViewCreated(view, savedInstanceState)

            // configure progress bar
            configureProgressBar()

            // configure our web view
            configureWebView()
        })
    }
    //@formatter:on
    //endregion lifecycle overrides


    //region private methods

    /**
     * Configures our progress bar
     */
    private fun configureProgressBar() {
        tryCatch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.progressBar.indeterminateDrawable?.colorFilter = BlendModeColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.nike_red), BlendMode.SRC_IN
                )
            } else {
                @Suppress("DEPRECATION")
                binding.progressBar.indeterminateDrawable?.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.nike_red),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    /**
     * Configures our WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        //@formatter:off
        tryCatch {
            if (args.targetUrl.isNotBlank()) {
                binding.webView.apply {
                    // configure the web view settings
                    settings.apply {
                        allowContentAccess = true
                        domStorageEnabled = true
                        javaScriptEnabled = true
                    }

                    // show the progress of loading pages
                    webChromeClient = object : WebChromeClient() {

                        /**
                         * Tell the host application the current progress of loading a page.
                         * @param view The WebView that initiated the callback.
                         * @param progress Current page loading progress, represented by an integer between 0 and 100.
                         */
                        override fun onProgressChanged(view: WebView, progress: Int) {
                            try {
                                // show / hide indefinite progress view
                                if (progress < 100) {
                                    binding.progressBar.visibility = View.VISIBLE
                                } else {
                                    binding.progressBar.visibility = View.GONE
                                }
                            } catch (ex: Exception) {
                                Logger.w(ex)
                            }
                        }
                    }

                    webViewClient = object : WebViewClient() {

                        /**
                         * Report an error to the host application. These errors are unrecoverable
                         * (i.e. the main resource is unavailable).
                         */
                        @Suppress("DEPRECATION")
                        override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                            tryCatch {
                                Logger.w(Exception("Error received when loading site: (Code: ${errorCode}, Description: ${description})"))
                            }
                            super.onReceivedError(view, errorCode, description, failingUrl)
                        }

                        /**
                         * Report web resource loading error to the host application. These errors usually indicate
                         * inability to connect to the server. Note that unlike the deprecated version of the callback,
                         * the new version will be called for any resource (iframe, image, etc.), not just for the main
                         * page. Thus, it is recommended to perform minimum required work in this callback.
                         */
                        @RequiresApi(api = 23)
                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                            tryCatch {
                                error?.let {
                                    Logger.w(Exception("Error received when loading site: (Code: ${it.errorCode}, Description: ${it.description})"))
                                }
                            }
                            super.onReceivedError(view, request, error)
                        }

                        /**
                         * Notifies the host application that an HTTP error has been received from the server while loading a resource.
                         */
                        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                            tryCatch {
                                errorResponse?.let {
                                    Logger.w(Exception("HTTP error received when loading site: (Code: ${it.statusCode}, Reason: ${it.reasonPhrase})"))
                                }
                            }
                            super.onReceivedHttpError(view, request, errorResponse)
                        }

                        /**
                         * Notifies the host application that an SSL error occurred while loading a
                         * resource. The host application must call either {@link SslErrorHandler#cancel} or
                         * {@link SslErrorHandler#proceed}. Note that the decision may be retained for use in
                         * response to future SSL errors. The default behavior is to cancel the load.
                         */
                        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                            tryCatch {
                                error?.let {
                                    Logger.w(Exception("SSL error received when loading site: '$it'"))
                                }
                            }
                            handler?.proceed()
                        }
                    }

                    // load the url specified
                    loadUrl(args.targetUrl)
                }
            }
        }
        //@formatter:on
    }
    //endregion private methods
}