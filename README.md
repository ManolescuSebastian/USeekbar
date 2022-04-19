<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/banner_1.png" width="100%"></img>


Useekbar is a library that provides custom seekbar UI components.

Project Demo
-----
<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/useekbar_demo.gif" width="30%"></img>

How to
------
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
         repositories {
	...
	maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency

```gradle
dependencies {
         implementation 'com.github.ManolescuSebastian:USeekbar:1.0'
}
```

HOW IT WORKS
------

### SeekbarD0

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D0.jpg" width="40%"></img>

```kotlin
<com.tekydevelop.useekbar.SeekbarD0
    android:id="@+id/seekbarTooltipDemo"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:clipToPadding="false"
    android:max="100"
    android:progress="30"
    android:shape="oval"
    android:splitTrack="false"
    app:textColor="@color/teal_200" />
```

### SeekbarD1

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D1.jpg" width="40%"></img>
```kotlin
<com.tekydevelop.useekbar.SeekbarD1
     android:id="@+id/seekbar_one"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:max="100"
     android:progress="50"
     android:progressTint="@color/purple_500"
     android:thumbTint="@color/purple_500"
     app:backgroundTooltipColor="@color/purple_500"
     app:textColor="@color/purple_500" />
```

### SeekbarD2

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D2.jpg" width="40%"></img>
```kotlin
<com.tekydevelop.useekbar.SeekbarD2
     android:id="@+id/seekbar_two"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="1000"
     android:paddingLeft="20dp"
     android:paddingTop="10dp"
     android:paddingRight="20dp"
     android:paddingBottom="10dp"
     android:progress="500"
     android:progressTint="@color/demo_color_1"
     android:splitTrack="false"
     android:thumbTint="@color/demo_color_1"
     app:backgroundTooltipColor="@color/demo_color_1"
     app:backgroundTooltipColorFill="@color/black"
     app:showTooltipText="true"
     app:steps="10"
     app:textColor="@color/demo_color_1"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="10dp" />
```

### SeekbarD3

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D3.jpg" width="40%"></img>
```kotlin
<com.tekydevelop.useekbar.SeekbarD3
     android:id="@+id/seekbar_three"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="1000"
     android:paddingTop="15dp"
     android:paddingBottom="15dp"
     android:progress="200"
     android:progressTint="@color/demo_color_2"
     android:splitTrack="false"
     android:thumbTint="@color/demo_color_2"
     app:backgroundTooltipColor="@color/demo_color_2"
     app:backgroundTooltipColorFill="@color/black"
     app:showTooltipText="true"
     app:steps="10"
     app:textColor="@color/demo_color_2"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="15dp" />
```

### SeekbarD4

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D4.jpg" width="40%"></img>
```kotlin
<com.tekydevelop.useekbar.SeekbarD4
     android:id="@+id/seekbar_four"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="1000"
     android:paddingTop="15dp"
     android:paddingBottom="15dp"
     android:progress="500"
     android:progressTint="@color/demo_color_3"
     android:splitTrack="false"
     android:thumbTint="@color/demo_color_3"
     app:backgroundTooltipColor="@color/demo_color_3"
     app:backgroundTooltipColorFill="@color/demo_color_3"
     app:showTooltipText="true"
     app:steps="100"
     app:textColor="@color/demo_color_3"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="15dp" />
```


### SeekbarD5

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D5.jpg" width="40%"></img>

```kotlin
<com.tekydevelop.useekbar.SeekbarD5
     android:id="@+id/seekbar_five"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="100"
     android:paddingTop="20dp"
     android:paddingBottom="20dp"
     android:progress="50"
     android:progressTint="@color/demo_color_4"
     android:splitTrack="false"
     android:thumbTint="@color/demo_color_4"
     app:backgroundTooltipColor="@color/demo_color_4"
     app:backgroundTooltipColorFill="@color/black"
     app:inactiveTooltipColor="@color/grey"
     app:showTooltipText="true"
     app:steps="10"
     app:textColor="@color/demo_color_4"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="20dp" />
```

### SeekbarD6

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D6.jpg" width="40%"></img>
```kotlin
<com.tekydevelop.useekbar.SeekbarD6
     android:id="@+id/seekbar_six"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="100"
     android:paddingTop="20dp"
     android:paddingBottom="20dp"
     android:progress="30"
     android:progressTint="#ED6A5A"
     android:splitTrack="false"
     android:thumbTint="#ED6A5A"
     app:backgroundTooltipColor="#ED6A5A"
     app:backgroundTooltipColorFill="@color/black"
     app:inactiveTooltipColor="@color/grey"
     app:showTooltipText="true"
     app:steps="10"
     app:textColor="#ED6A5A"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="20dp" />
```


### SeekbarD7

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D7.jpg" width="40%"></img>

```kotlin
<com.tekydevelop.useekbar.SeekbarD7
     android:id="@+id/seekbar_seven"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="100"
     android:paddingTop="20dp"
     android:paddingBottom="20dp"
     android:progress="30"
     android:progressTint="#EEBA0B"
     android:splitTrack="false"
     android:thumbTint="#EEBA0B"
     app:backgroundTooltipColor="#EEBA0B"
     app:backgroundTooltipColorFill="#EEBA0B"
     app:inactiveTooltipColor="@color/grey"
     app:showTooltipText="true"
     app:steps="10"
     app:textColor="#EEBA0B"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="20dp" />
```


### SeekbarD8 - top values

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D8.jpg" width="40%"></img>

```kotlin
<com.tekydevelop.useekbar.SeekbarD8
     android:id="@+id/seekbar_eight"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="10"
     android:progress="5"
     android:progressTint="#379634"
     android:splitTrack="false"
     android:thumbTint="#379634"
     app:backgroundTooltipColor="#379634"
     app:backgroundTooltipColorFill="@color/grey"
     app:botText="false"
     app:showTextOnBottom="false"
     app:showTooltipText="true"
     app:steps="1"
     app:textColor="#379634"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="20dp" />
```


### SeekbarD8 - bottom values

<img src="https://github.com/ManolescuSebastian/USeekbar/blob/main/github_images/D9.jpg" width="40%"></img>

```kotlin
<com.tekydevelop.useekbar.SeekbarD8
     android:id="@+id/seekbar_eight_1"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginTop="20dp"
     android:background="@android:color/transparent"
     android:max="10"
     android:progress="2"
     android:progressTint="#A8DADC"
     android:splitTrack="false"
     android:thumbTint="#A8DADC"
     app:backgroundTooltipColor="#A8DADC"
     app:backgroundTooltipColorFill="@color/grey"
     app:botText="true"
     app:showTextOnBottom="false"
     app:showTooltipText="true"
     app:steps="1"
     app:textColor="#A8DADC"
     app:tooltipTextSize="15sp"
     app:topTextAreaPadding="20dp" />
```


License
------
         

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
