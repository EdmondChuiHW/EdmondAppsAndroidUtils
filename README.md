EdmondAppsAndroidUtils
======================
This is an Android library which aimed to reduce common boiler-plate code.
All commits to the master branch include Javadoc.

It is under active development so the API is not frozen yet.
Detailed documentation will indicate binary-incompatiable changes in future commits.

I am 17-years-old student, so please tell me if you have any suggetions/comments on my code.

Basic
----------------------
Simple API Level check:
    import static com.edmondapps.utils.android.Utils.hasHoneyComb;
    if(hasHoneyComb()) {...}

Activity & Fragments
----------------------
SinglePaneActvity:
    public class PresidentsActivity extends SinglePaneActivity {
    @Override
	  protected Fragment onCreateFragment() {
		  return new PresidentsFragment();
	  }
  }

DualPaneActivity:
    public class MainTabletActivity extends DualPaneActivity {
	  @Override
	  protected Fragment onCreateFragment() {
		  return new PresidentsFragment();
	  }

	  @Override
	  protected Fragment onCreateDetailFragment() {
		  return new PresidentFragment();
	  }
  }

Dependencies
----------------------
[ActionBarSherlock]
