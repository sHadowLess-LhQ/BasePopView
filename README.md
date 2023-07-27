#  BasePopView

#### 软件架构

个人自用XPop封装基类（支持ViewBinding）

#### 安装教程

Step 1. 添加maven仓库地址和配置

```
     //旧AndroidStudio版本
     //build.gradle
     allprojects {
         repositories {
            ...
              maven { url 'https://jitpack.io' }
         }
     }
     
     //新AndroidStudio版本
     //settings.gradle
     dependencyResolutionManagement {
          repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
          repositories {
            ...
             maven { url 'https://jitpack.io' }
          }
      }
```

```
     //主项目的build.gradle中加入
     //新AndroidStudio版本
     android {
      ...
       buildFeatures {
         viewBinding true
          }
     }
     
     //主项目的build.gradle中加入
     //旧AndroidStudio版本
     android {
      ...
       viewBinding {
         enable = true
          }
     }
```

Step 2. 添加依赖

a、克隆引入

直接下载源码引入model

b、远程仓库引入

[![](https://jitpack.io/v/com.gitee.shadowless_lhq/base-pop-view.svg)](https://jitpack.io/#com.gitee.shadowless_lhq/base-pop-view)

```
     dependencies {
        implementation 'com.gitee.shadowless_lhq:base-pop-view:Tag'
        implementation 'com.github.li-xiaojun:XPopup:2.9.4'
    }
```

c、混淆规则

```
-dontwarn com.lxj.xpopup.widget.**
-keep class com.lxj.xpopup.widget.**{*;}
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
```

#### 使用说明

```
     //创建xml后，点击编译，填入需要绑定的视图
     //支持ViewBinding
     //支持LifecycleProvider
     //支持监听Activity的声明周期
     //click监听已做快速点击处理
     //共有9种基类封装弹窗
     //BaseBottomPopView - 底部弹出弹窗
     //BaseBubbleHorizontalAttachPopupView - 水平弹出可依附气泡弹窗
     //BaseCenterPopView - 居中弹窗
     //BaseDrawerPopupView - 实现Drawer的弹窗
     //BaseFullScreenPopupView - 全屏弹窗
     //BaseHorizontalAttachPopView - 水平弹出可依附视图弹窗
     //BasePositionPopupView - 自定义方向弹窗
     //BaseVerticalAttachPopView - 垂直弹出可依附视图弹窗
     //BaseVerticalBubbleAttachPopupView - 垂直弹出可依附气泡弹窗
     //【注】：内部使用Fragment，需要设置isViewMode为true
     //继承示例
     public class TestPopView extends BaseCenterPopView<PopAddCardViewBinding>{

         public TestPopView(@NonNull Context context) {
            super(context);
         }
         
      
         public TestPopView(@NonNull Context context, Lifecycle lifecycle) {
              super(context, lifecycle);
         }
         
         @NonNull
         @Override
         protected PopAddCardViewBinding setBindView(View v) {
            return PopAddCardViewBinding.bind(v);
         }
         
         @Override
         protected void setLayoutId() {
             return R.layout.pop_addCard_view;
         }
         
         @Override
         protected boolean isDefaultBackground() {
            return false;
         }
        
         @Override
         protected void initView() {
       
         }

         @Override
         protected void initListener() {
       
         }
         
         @Override
         protected void click(View v) {
              
         }
      }
```
