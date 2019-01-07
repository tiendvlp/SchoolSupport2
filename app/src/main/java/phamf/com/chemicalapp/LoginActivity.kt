package phamf.com.chemicalapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLog
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast

import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import com.schoolsupport.app.dmt91.schoolsupport.RegisterActivity
import com.schoolsupport.app.dmt91.schoolsupport.model.MemberWrite
import com.schoolsupport.app.dmt91.schoolsupport.model.Signin
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_login.*
import phamf.com.chemicalapp.LoginViewModel
import phamf.com.chemicalapp.Model.AppDataSingleton
import phamf.com.chemicalapp.databinding.ActivityLoginBinding
import phamf.com.chemicalapp.databinding.FinalInfoFinishTimesBinding
import phamf.com.chemicalapp.supportClass.getViewModel
import java.io.InputStream
import java.util.*

@Suppress("UNUSED_PARAMETER")
class LoginActivity : AppCompatActivity(), FacebookCallback<LoginResult> {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mFbCallback : CallbackManager
    private lateinit var apiClient : GoogleApiClient
    private val RCSignin : Int = 111
    private lateinit var FireAuth : FirebaseAuth
    private val listPermisson : List<String> = Arrays.asList("email", "public_profile")
    private lateinit var mFBLoginManager : LoginManager
    private lateinit var mViewModel : LoginViewModel
    private lateinit var mAuthCallback : Signin.Event
    private lateinit var mDialog : AlertDialog
    private lateinit var mFinalFinishBinding : FinalInfoFinishTimesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        addControls()
        addEvents()
        setup()
    }

    private fun setup() {

        var dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(mFinalFinishBinding.root)
        dialogBuilder.setCancelable(false)
        mDialog = dialogBuilder.create()
        FireAuth = FirebaseAuth.getInstance()
        FireAuth.signOut()

        mFBLoginManager = LoginManager.getInstance()
        mFbCallback = CallbackManager.Factory.create()
        mFBLoginManager.registerCallback(mFbCallback, this)

        val sio = GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestEmail()
                .build()
        val apiClient =  GoogleApiClient.Builder(this).enableAutoManage(this, (::onGGSigninConnectionFailed))
                .addApi(Auth.GOOGLE_SIGN_IN_API, sio).build()
        this.apiClient = apiClient
    }

    private fun addControls () {
        mViewModel = getViewModel()
        mFinalFinishBinding = DataBindingUtil.inflate(layoutInflater, R.layout.final_info_finish_times, null, false)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mViewModel.enable.value = true
        mFinalFinishBinding.mModel = mViewModel
        mBinding.mModel = mViewModel
        mBinding.setLifecycleOwner(this)
        mFinalFinishBinding.setLifecycleOwner(this)
        mFinalFinishBinding.imgAvatar.isDrawingCacheEnabled = true
        mFinalFinishBinding.imgAvatar.buildDrawingCache()
    }

    private fun addEvents () {
        mAuthCallback = Signin.Event(::loginWithFirebaseSuccess, ::loginWithFirebaseFailed, ::onUserNotVerifyAccount)
        mBinding.btnGGLogin.setOnClickListener((::onBtnGGLoginClick))
        mBinding.btnFBLogin.setOnClickListener((::onBtnFbLoginClick))
        mBinding.btnLogin.setOnClickListener((::onBtnLoginClick))
        mBinding.btnRegister.setOnClickListener(::onBtnRegisterClick)
        mFinalFinishBinding.btnSubmit.setOnClickListener(::onBtnSubmitTheLastInfoClicked)
        mFinalFinishBinding.imgAvatar.setOnClickListener({
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(5,5)
                    .setFixAspectRatio(true)
                    .start(this)
        })
    }

    private fun onBtnSubmitTheLastInfoClicked (view:View?) {
        var bitmap : Bitmap = (mFinalFinishBinding.imgAvatar.drawable as BitmapDrawable).bitmap
        if (bitmap.byteCount == 0) {
            Toast.makeText(this, "You must choose avatar", Toast.LENGTH_SHORT).show()
        } else {
            val callback : LoginViewModel.Event = LoginViewModel.Event(::createUserSuccess,::createUserFailed)
            mViewModel.addNewMember(bitmap, callback)
        }
    }

    private fun createUserFailed (e:java.lang.Exception?) {
        Toast.makeText(this, "Vui lòng kiểm tra lại kết nối của bạn", Toast.LENGTH_SHORT).show()
    }

    private fun createUserSuccess () {
        FirebaseDatabase.getInstance().reference
                .child("Member")
                .child(AppDataSingleton.getInstance().currentUser.Id)
                .child("isActivated")
                .setValue(true)
        mDialog.dismiss()
    }

    // Facebook's event
    override fun onSuccess(result: LoginResult?) {
        fbLoginHandle(result)
    }

    private fun onUserNotVerifyAccount () {
        mDialog.show()
    }

    // Facebook's event
    override fun onCancel() {
        Toast.makeText(this, "Đăng nhập Facebook thất bại", Toast.LENGTH_SHORT).show()
    }

    // Facebook's event
    override fun onError(error: FacebookException?) {
        Toast.makeText(this, "Đăng nhập Facebook thất bại", Toast.LENGTH_SHORT).show()
    }

    private fun fbLoginHandle (result: LoginResult?) {
        val token : String = result!!.accessToken!!.token
        val credential : AuthCredential = FacebookAuthProvider.getCredential(token)
        mViewModel.signinWithCredential(credential, mAuthCallback)
    }

    override fun onStart() {
        super.onStart()
        FireAuth.addAuthStateListener ((::onAuthenticationStateChanged))
    }

    override fun onStop() {
        super.onStop()
        FireAuth.removeAuthStateListener ((::onAuthenticationStateChanged))
    }

    private fun onBtnGGLoginClick (v:View?) {
        GGSigin()
    }

    private fun onBtnFbLoginClick (v:View?) {
        mFBLoginManager.logInWithReadPermissions(this, listPermisson)
    }

    private fun onBtnLoginClick (v:View?) {
        loading(true)
        firebaseLogin()
    }

    private fun onBtnRegisterClick (v:View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun firebaseLogin () {
        mViewModel.siginWithEmailandPassword(edtEmail.text.toString(), mBinding.edtPassword.text.toString(), mAuthCallback)
    }

    private fun onGGSigninConnectionFailed (result: ConnectionResult) {
        Toast.makeText(this, "Kết nối không thành công", Toast.LENGTH_SHORT).show()
    }

    private fun GGSigin () {
        val ggSigninIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient)
        startActivityForResult(ggSigninIntent, RCSignin)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.RCSignin) {
            if (resultCode == Activity.RESULT_OK) {
                googleLoginHandle(data!!)
            } else {
                Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result : CropImage.ActivityResult  = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                val resultUri : Uri = result.getUri()
                val readStream : InputStream = contentResolver.openInputStream(resultUri)
                val bitmap : Bitmap = BitmapFactory.decodeStream(readStream)
                mFinalFinishBinding.imgAvatar.setImageBitmap(bitmap)
                mFinalFinishBinding.imgAvatar.scaleType = ImageView.ScaleType.FIT_XY
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error : Exception = result.getError()
                Log.e("activityResult", "submit dialog get img failed: " + error.toString())
            }
        }
        else {
            mFbCallback.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun googleLoginHandle (data:Intent) {
        val result : GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        val account : GoogleSignInAccount = result.signInAccount!!
        val credential : AuthCredential =  GoogleAuthProvider.getCredential(account.idToken, null)
        mViewModel.signinWithCredential(credential, mAuthCallback)
    }

    private fun loginWithFirebaseSuccess (task: Task<AuthResult>) {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, MainActivity::class.java))

            finish()
    }

    private fun loginWithFirebaseFailed (task: Exception?) {
        Toast.makeText(this, "Đăng nhập thất bại, vui lòng kiểm tra lại tài khoản hoặc kết nối của bạn", Toast.LENGTH_SHORT).show()
        loading(false)
    }

    private fun onAuthenticationStateChanged (auth : FirebaseAuth) {
        val user : FirebaseUser? = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "Đăng xuất", Toast.LENGTH_SHORT).show ()
        }
    }

   private fun loading (isLoading : Boolean) {
       mViewModel.enable.value = !isLoading
       if (isLoading) {
           mBinding.overlay.visibility = View.VISIBLE
           mBinding.loading.visibility = View.VISIBLE
       } else {
           mBinding.overlay.visibility = View.GONE
           mBinding.loading.visibility = View.GONE
       }
   }
}
