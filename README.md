[![](https://jitpack.io/v/HadiDadkhah99/ShadowLayout.svg)](https://jitpack.io/#HadiDadkhah99/ShadowLayout)

# Android shadow layout


### Step 1. Add the JitPack repository to your build file

			
```groovy
allprojects {
		repositories {
	                //...
			maven { url 'https://jitpack.io' }
		}
	}
```



### Step 2. Add the dependency

```groovy
dependencies {
	       implementation 'com.github.HadiDadkhah99:ShadowLayout:$last_version'
	}
```

## How to use it

![](http://www.dadkhahhadi.ir/github/shadowLayoutimg.png)

### You must use this way to use it
```xml
<AnyLayout>

      <com.foc.libs.shadowLayoutPro.ShadowLayout
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:layout_width="match_parent"
        android:padding="8dp"
        app:shadow_color="#4E404040"
        app:shadow_value="15">

        <EditText
            android:background="@drawable/edit_text_shape"
            android:layout_height="45dp"
            android:layout_marginHorizontal="24dp"
            android:layout_width="match_parent"
            android:paddingEnd="8dp"
            android:paddingStart="8dp"
            android:text="Hello World!"
            android:textColor="@color/black" />
            
    </com.foc.libs.shadowLayoutPro.ShadowLayout>


</AnyLayout>
```
### Margin shadow (left , top , right , bottom)
```xml
<AnyLayout>

      <com.foc.libs.shadowLayoutPro.ShadowLayout
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:layout_width="match_parent"
        android:padding="8dp"
	app:shadow_margin_left="1dp"
        app:shadow_margin_top="1dp"
        app:shadow_margin_bottom="1dp"
        app:shadow_margin_right="1dp"
        app:shadow_dx="0dp"
        app:shadow_dy="0dp"
        app:shadow_color="#4E404040"
        app:shadow_value="15">

        <EditText
            android:background="@drawable/edit_text_shape"
            android:layout_height="45dp"
            android:layout_marginHorizontal="24dp"
            android:layout_width="match_parent"
            android:paddingEnd="8dp"
            android:paddingStart="8dp"
            android:text="Hello World!"
            android:textColor="@color/black" />
            
    </com.foc.libs.shadowLayoutPro.ShadowLayout>


</AnyLayout>
```

### Only show shadow (Show shadow for any customization)
```xml
<AnyLayout>
    <com.foc.libs.shadowLayoutPro.ShadowLayout
        ...
        app:shadow_just="true">
	    
	    <...>

    </com.foc.libs.shadowLayoutPro.ShadowLayout>
```

### Show shadows with gradient
1.First create a class to inherit from ShadowBehavior like this:
```java
public class MyBehavior extends ShadowBehavior {

    @Override
    public int getEndColor() {
        return super.getEndColor();
    }

    @Override
    public int getStartColor() {
        return super.getStartColor();
    }

    @Override
    public int getAngle() {
        return 45;
    }
}
```
2. then set created class packege name to shadow_behavior like this:

```xml
    <com.foc.libs.shadowLayoutPro.ShadowLayout
        ...
        app:shadow_behavior="YOUR_PACKE_NAME.MyBehavior">
	
	    <...>
	    
    </com.foc.libs.shadowLayoutPro.ShadowLayout>
```


