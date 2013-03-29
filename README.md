EdmondAppsAndroidUtils
======================
This is an Android library which aimed to reduce common boiler-plate code.  
This library covers logging, Activities, Fragments, SQLite databases, asynchronous operations, JSON, animations and more.  

All commits to the master branch include Javadoc.  
This lirary is under active development so the API is not frozen yet.  
Detailed documentation will indicate any binary-incompatiable changes in future commits.  

I am 17-years-old student, so please tell me if you have any suggetions/comments on my code.  

Samples
======================
Basic
----------------------
Simple API Level check:
```
import static com.edmondapps.utils.android.Utils.hasHoneyComb;
if(hasHoneyComb()) {
    doCoolStuff();
}
```

Easy Fragments intergration
----------------------
Automatic Fragment tags:
```
@FragmentName("MyUniqueStringTag")
public class PresidentFragment extends SherlockFragment{
  // That's all you need!
}
```
DualPaneActivity:
```
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
```
Automatic "UP" navigation
----------------------
```
@ParentActivity(PresidentsActivity.class)
public class PresidentActivity extends UpableActivity {
  // That's all you need!
}
```

More
----------------------
Check out the [sample app] [4] on the [Play store (soon)] [5].  

Dependencies
======================
* [ActionBarSherlock] [1]  
* [NineOldAndroids] [2]  
* [google-gson] [3]  

[1]: https://github.com/JakeWharton/ActionBarSherlock
[2]: https://github.com/JakeWharton/NineOldAndroids
[3]: https://code.google.com/p/google-gson/
[4]: https://github.com/chuihinwai/EdmondAppsAndroidUtilsSample
[5]: https://play.google.com/store/apps/details?id=com.edmondapps.android.sample

Developed By
======================
* Edmond Chui - <chui.hinwai@gmail.com>

License
======================
```
Copyright 2013 Edmond Chui

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
