# GoPermission
一个轻量级的安卓权限请求框架

如何引入?

<h2>第一步.</h2> 将如下语句添加进项目跟目录下的 build.gradle:

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

<h2>第二步.</h2> 添加以下依赖

```groovy
	dependencies {
	        implementation 'com.github.rubintry:GoPermission:v1.0.6'
	}
```


如何使用?

kotlin

```kotlin
   GoPermission
            .permissions(the permissions you want to request)
            .request { allGrant, grantedPermissions, deniedPermissions ->
                //这里执行回调代码块
            }
```

java


```java
   GoPermission
                .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new Callback() {
                    @Override
                    public void onResult(boolean allGrant, @NotNull String[] grantedPermissions, @NotNull String[] deniedPermissions) {
                         //这里执行回调代码块.
                    }
                });
```
