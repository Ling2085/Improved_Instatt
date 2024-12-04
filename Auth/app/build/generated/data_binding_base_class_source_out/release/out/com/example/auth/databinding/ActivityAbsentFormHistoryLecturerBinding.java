// Generated by view binder compiler. Do not edit!
package com.example.auth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public final class ActivityAbsentFormHistoryLecturerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton iBback;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final RecyclerView rvHistoryLecturer;

  @NonNull
  public final TextView textView;

  private ActivityAbsentFormHistoryLecturerBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton iBback, @NonNull ConstraintLayout main,
      @NonNull RecyclerView rvHistoryLecturer, @NonNull TextView textView) {
    this.rootView = rootView;
    this.iBback = iBback;
    this.main = main;
    this.rvHistoryLecturer = rvHistoryLecturer;
    this.textView = textView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAbsentFormHistoryLecturerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAbsentFormHistoryLecturerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_absent_form_history_lecturer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAbsentFormHistoryLecturerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iBback;
      ImageButton iBback = ViewBindings.findChildViewById(rootView, id);
      if (iBback == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.rvHistoryLecturer;
      RecyclerView rvHistoryLecturer = ViewBindings.findChildViewById(rootView, id);
      if (rvHistoryLecturer == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      return new ActivityAbsentFormHistoryLecturerBinding((ConstraintLayout) rootView, iBback, main,
          rvHistoryLecturer, textView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}