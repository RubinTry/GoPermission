# GoPermission
A very lightweight permission request framework on Android platform

How to implement?

<h2>Step 1.</h2> Add it in your root build.gradle at the end of repositories:

```groovy
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

<br>
<br>

<h2>Step 2.</h2> Add the dependency

```groovy
	dependencies {
	        implementation 'com.github.rubintry:GoPermission:v1.0.6'
	}
```


How to use it?

kotlin

```kotlin
   GoPermission
            .permissions(the permissions you want to request)
            .request { allGrant, grantedPermissions, deniedPermissions ->
                //This is a code block for callback.
            }
```

java


```java
   GoPermission
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new Callback() {
                    @Override
                    public void onResult(boolean allGrant, @NotNull String[] grantedPermissions, @NotNull String[] deniedPermissions) {
                         //This is a code block for callback.
                    }
                });
```
