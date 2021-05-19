package cn.rubintry.gopermission;

import org.jetbrains.annotations.NotNull;

/**
 * @author logcat
 */
public interface Callback {

    /**
     * 权限请求回调
     * @param allGrant
     * @param grantedPermissions
     * @param deniedPermissions
     */
    void onResult(boolean allGrant,
                  @NotNull String[] grantedPermissions,
                  @NotNull String[] deniedPermissions);
}