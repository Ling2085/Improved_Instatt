// Generated by view binder compiler. Do not edit!
package com.example.auth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.auth.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAbsentFormLecturerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnApproved;

  @NonNull
  public final Button btnDecline;

  @NonNull
  public final ImageButton iBback;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView1;

  @NonNull
  public final TextView textView12;

  @NonNull
  public final TextView textView13;

  @NonNull
  public final TextView textView14;

  @NonNull
  public final TextView textView15;

  @NonNull
  public final TextView textView9;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvModule;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvReason;

  @NonNull
  public final TextView tvStudentID;

  @NonNull
  public final TextView tvTime;

  private ActivityAbsentFormLecturerBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnApproved, @NonNull Button btnDecline, @NonNull ImageButton iBback,
      @NonNull LinearLayout linearLayout, @NonNull ConstraintLayout main,
      @NonNull TextView textView, @NonNull TextView textView1, @NonNull TextView textView12,
      @NonNull TextView textView13, @NonNull TextView textView14, @NonNull TextView textView15,
      @NonNull TextView textView9, @NonNull TextView tvDate, @NonNull TextView tvModule,
      @NonNull TextView tvName, @NonNull TextView tvReason, @NonNull TextView tvStudentID,
      @NonNull TextView tvTime) {
    this.rootView = rootView;
    this.btnApproved = btnApproved;
    this.btnDecline = btnDecline;
    this.iBback = iBback;
    this.linearLayout = linearLayout;
    this.main = main;
    this.textView = textView;
    this.textView1 = textView1;
    this.textView12 = textView12;
    this.textView13 = textView13;
    this.textView14 = textView14;
    this.textView15 = textView15;
    this.textView9 = textView9;
    this.tvDate = tvDate;
    this.tvModule = tvModule;
    this.tvName = tvName;
    this.tvReason = tvReason;
    this.tvStudentID = tvStudentID;
    this.tvTime = tvTime;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAbsentFormLecturerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAbsentFormLecturerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_absent_form_lecturer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAbsentFormLecturerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnApproved;
      Button btnApproved = ViewBindings.findChildViewById(rootView, id);
      if (btnApproved == null) {
        break missingId;
      }

      id = R.id.btnDecline;
      Button btnDecline = ViewBindings.findChildViewById(rootView, id);
      if (btnDecline == null) {
        break missingId;
      }

      id = R.id.iBback;
      ImageButton iBback = ViewBindings.findChildViewById(rootView, id);
      if (iBback == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView1;
      TextView textView1 = ViewBindings.findChildViewById(rootView, id);
      if (textView1 == null) {
        break missingId;
      }

      id = R.id.textView12;
      TextView textView12 = ViewBindings.findChildViewById(rootView, id);
      if (textView12 == null) {
        break missingId;
      }

      id = R.id.textView13;
      TextView textView13 = ViewBindings.findChildViewById(rootView, id);
      if (textView13 == null) {
        break missingId;
      }

      id = R.id.textView14;
      TextView textView14 = ViewBindings.findChildViewById(rootView, id);
      if (textView14 == null) {
        break missingId;
      }

      id = R.id.textView15;
      TextView textView15 = ViewBindings.findChildViewById(rootView, id);
      if (textView15 == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = ViewBindings.findChildViewById(rootView, id);
      if (textView9 == null) {
        break missingId;
      }

      id = R.id.tvDate;
      TextView tvDate = ViewBindings.findChildViewById(rootView, id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tvModule;
      TextView tvModule = ViewBindings.findChildViewById(rootView, id);
      if (tvModule == null) {
        break missingId;
      }

      id = R.id.tvName;
      TextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tvReason;
      TextView tvReason = ViewBindings.findChildViewById(rootView, id);
      if (tvReason == null) {
        break missingId;
      }

      id = R.id.tvStudentID;
      TextView tvStudentID = ViewBindings.findChildViewById(rootView, id);
      if (tvStudentID == null) {
        break missingId;
      }

      id = R.id.tvTime;
      TextView tvTime = ViewBindings.findChildViewById(rootView, id);
      if (tvTime == null) {
        break missingId;
      }

      return new ActivityAbsentFormLecturerBinding((ConstraintLayout) rootView, btnApproved,
          btnDecline, iBback, linearLayout, main, textView, textView1, textView12, textView13,
          textView14, textView15, textView9, tvDate, tvModule, tvName, tvReason, tvStudentID,
          tvTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
