package com.edmondapps.utils.android.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.edmondapps.utils.android.activity.DualPaneActivity;
import com.edmondapps.utils.android.activity.SinglePaneActivity;

/**
 * An {@code Annotation} containing a unique identifier of a {@code Fragment}.<br/>
 * Passed to {@code FragmentManager} for transactions and retrieval.
 * <p/>
 * Apply this {@code Annotation} to your {@code Fragment} class if the tag is
 * static.<br/>
 * Otherwise, use the methods provided by the implementing classes like
 * {@link SinglePaneActivity#onCreateFragmentTag()} to return a dynamically
 * generated tag.
 * 
 * @see SinglePaneActivity
 * @see DualPaneActivity
 * 
 * @author Edmond
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FragmentName {
	/**
	 * {@code String} to identify the {@code Fragment}.
	 */
	String value();
}
