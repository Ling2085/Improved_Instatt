// Generated by view binder compiler. Do not edit!
package com.example.auth.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.auth.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityStudentBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final FragmentContainerView FrameLayout;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  @NonNull
  public final ConstraintLayout constraintLayout2;

  @NonNull
  public final ImageButton ibMenu;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final DrawerLayout main;

  @NonNull
  public final NavigationView navView;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView tvInstatt;

  private ActivityStudentBinding(@NonNull DrawerLayout rootView,
      @NonNull FragmentContainerView FrameLayout,
      @NonNull BottomNavigationView bottomNavigationView,
      @NonNull ConstraintLayout constraintLayout2, @NonNull ImageButton ibMenu,
      @NonNull LinearLayout linearLayout2, @NonNull DrawerLayout main,
      @NonNull NavigationView navView, @NonNull TextView textView4, @NonNull TextView tvInstatt) {
    this.rootView = rootView;
    this.FrameLayout = FrameLayout;
    this.bottomNavigationView = bottomNavigationView;
    this.constraintLayout2 = constraintLayout2;
    this.ibMenu = ibMenu;
    this.linearLayout2 = linearLayout2;
    this.main = main;
    this.navView = navView;
    this.textView4 = textView4;
    this.tvInstatt = tvInstatt;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityStudentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityStudentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_student, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityStudentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.FrameLayout;
      FragmentContainerView FrameLayout = ViewBindings.findChildViewById(rootView, id);
      if (FrameLayout == null) {
        break missingId;
      }

      id = R.id.bottomNavigationView;
      BottomNavigationView bottomNavigationView = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationView == null) {
        break missingId;
      }

      id = R.id.constraintLayout2;
      ConstraintLayout constraintLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout2 == null) {
        break missingId;
      }

      id = R.id.ibMenu;
      ImageButton ibMenu = ViewBindings.findChildViewById(rootView, id);
      if (ibMenu == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      DrawerLayout main = (DrawerLayout) rootView;

      id = R.id.nav_view;
      NavigationView navView = ViewBindings.findChildViewById(rootView, id);
      if (navView == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.tvInstatt;
      TextView tvInstatt = ViewBindings.findChildViewById(rootView, id);
      if (tvInstatt == null) {
        break missingId;
      }

      return new ActivityStudentBinding((DrawerLayout) rootView, FrameLayout, bottomNavigationView,
          constraintLayout2, ibMenu, linearLayout2, main, navView, textView4, tvInstatt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
