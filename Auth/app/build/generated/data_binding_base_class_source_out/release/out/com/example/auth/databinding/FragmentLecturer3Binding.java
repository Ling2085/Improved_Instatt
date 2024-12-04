// Generated by view binder compiler. Do not edit!
package com.example.auth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.auth.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentLecturer3Binding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final RecyclerView rvUsers;

  @NonNull
  public final TextView tvTitle;

  private FragmentLecturer3Binding(@NonNull FrameLayout rootView, @NonNull ConstraintLayout main,
      @NonNull RecyclerView rvUsers, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.main = main;
    this.rvUsers = rvUsers;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLecturer3Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLecturer3Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_lecturer3, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLecturer3Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.main;
      ConstraintLayout main = ViewBindings.findChildViewById(rootView, id);
      if (main == null) {
        break missingId;
      }

      id = R.id.rvUsers;
      RecyclerView rvUsers = ViewBindings.findChildViewById(rootView, id);
      if (rvUsers == null) {
        break missingId;
      }

      id = R.id.tvTitle;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new FragmentLecturer3Binding((FrameLayout) rootView, main, rvUsers, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
