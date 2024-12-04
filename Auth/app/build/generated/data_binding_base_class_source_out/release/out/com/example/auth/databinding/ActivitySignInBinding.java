// Generated by view binder compiler. Do not edit!
package com.example.auth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.auth.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignInBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnSignIn;

  @NonNull
  public final TextInputEditText etEmail;

  @NonNull
  public final TextInputLayout etEmailLayout;

  @NonNull
  public final TextInputEditText etPassword;

  @NonNull
  public final TextInputLayout etPasswordLayout;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView tvEmailError;

  @NonNull
  public final TextView tvPasswordError;

  @NonNull
  public final TextView tvSIgnIn;

  @NonNull
  public final TextView tvSIgnIn3;

  private ActivitySignInBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnSignIn,
      @NonNull TextInputEditText etEmail, @NonNull TextInputLayout etEmailLayout,
      @NonNull TextInputEditText etPassword, @NonNull TextInputLayout etPasswordLayout,
      @NonNull ImageView imageView, @NonNull ConstraintLayout main, @NonNull TextView tvEmailError,
      @NonNull TextView tvPasswordError, @NonNull TextView tvSIgnIn, @NonNull TextView tvSIgnIn3) {
    this.rootView = rootView;
    this.btnSignIn = btnSignIn;
    this.etEmail = etEmail;
    this.etEmailLayout = etEmailLayout;
    this.etPassword = etPassword;
    this.etPasswordLayout = etPasswordLayout;
    this.imageView = imageView;
    this.main = main;
    this.tvEmailError = tvEmailError;
    this.tvPasswordError = tvPasswordError;
    this.tvSIgnIn = tvSIgnIn;
    this.tvSIgnIn3 = tvSIgnIn3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignInBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sign_in, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignInBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSignIn;
      Button btnSignIn = ViewBindings.findChildViewById(rootView, id);
      if (btnSignIn == null) {
        break missingId;
      }

      id = R.id.etEmail;
      TextInputEditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.etEmailLayout;
      TextInputLayout etEmailLayout = ViewBindings.findChildViewById(rootView, id);
      if (etEmailLayout == null) {
        break missingId;
      }

      id = R.id.etPassword;
      TextInputEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.etPasswordLayout;
      TextInputLayout etPasswordLayout = ViewBindings.findChildViewById(rootView, id);
      if (etPasswordLayout == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.tvEmailError;
      TextView tvEmailError = ViewBindings.findChildViewById(rootView, id);
      if (tvEmailError == null) {
        break missingId;
      }

      id = R.id.tvPasswordError;
      TextView tvPasswordError = ViewBindings.findChildViewById(rootView, id);
      if (tvPasswordError == null) {
        break missingId;
      }

      id = R.id.tvSIgnIn;
      TextView tvSIgnIn = ViewBindings.findChildViewById(rootView, id);
      if (tvSIgnIn == null) {
        break missingId;
      }

      id = R.id.tvSIgnIn3;
      TextView tvSIgnIn3 = ViewBindings.findChildViewById(rootView, id);
      if (tvSIgnIn3 == null) {
        break missingId;
      }

      return new ActivitySignInBinding((ConstraintLayout) rootView, btnSignIn, etEmail,
          etEmailLayout, etPassword, etPasswordLayout, imageView, main, tvEmailError,
          tvPasswordError, tvSIgnIn, tvSIgnIn3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}