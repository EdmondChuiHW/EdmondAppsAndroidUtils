package com.edmondapps.utils.android.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.app.Activity;

import com.edmondapps.utils.android.activity.UpableActivity;

/**
 * Specifies a parent {@code Activity} for the "up" button affordance.
 * 
 * @see UpableActivity
 * 
 * @author Edmond
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentActivity {
	Class<? extends Activity> value();
}
