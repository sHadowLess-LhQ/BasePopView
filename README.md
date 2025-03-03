#  BasePopView

#### 软件架构

个人自用XPop封装基类（支持ViewBinding）

## 【注】：layout的名称命名要规范，需要"_"分隔，不然无法通过名称拿到layout的id

例：xxx_xxx.xml、xxx_xxx_xxx_xxx.xml

## ==懒，不想down大佬的源码去添加==

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
        implementation 'com.github.li-xiaojun:XPopup:2.10.0'
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
     //click监听已做快速点击处理，请实现antiShakingClick接口方法
     //如果单个Pop需要动态使用不同的布局文件，请给BaseActivity的泛型类型
     //传递ViewBinding，并重写setBindViewClass模板方法,传递不同ViewBinding类
     //如果不想用默认的反射去动态使用不同的布局，请重写inflateView模板方法
     //手动传递和实现ViewBinding类的实例
     //如果有反射加载视图慢的情况，请重写inflateView方法，手动实现ViewBinding类创建
     //需要更改点击防抖时间阈值，请重写isFastClick，在超类调用传递时间
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
     public class TestPopView extends BaseCenterPopView<ViewBinding>{

         public TestPopView(@NonNull Context context) {
            super(context);
         }
          
         @Override
         protected ViewBinding inflateView() {
            //可重写后实现视图初始化
            return super.inflateView();
         }
         
         @Override
         protected boolean isDefaultBackground() {
            //是否使用内置默认背景
            //不使用弹窗背景为透明
            return false;
         }
         
         @Override
         protected void initObject() {
            //初始化对象
         }
         
         @Override
         protected void initView() {
            //初始化视图
         }
         
         @Override
         protected void initViewListener() {
            //初始化视图事件
         }
         
         @Override
         protected void initDataListener() {
            //初始化数据事件
         }
         
         @Override
         protected void initData() {
            //初始化数据
         }
         
         @Override
         public Class<ViewBinding> setBindViewClass() {
              //反射动态布局
               Class<?> cls;
               if (i == 1) {
                  cls = ActivityMainBinding.class;
               } else {
                  cls = XxxBinding.class;
               }
               return (Class<ViewBinding>) cls;
         }
      
         @Override
         public boolean isFastClick(int time) {
              //传递需要的防抖时间阈值 
              return super.isFastClick(time);
         }
         
         @Override
          public ViewBinding inflateView(Object o, View view) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
              //手动动态布局或手动初始化布局
              int i = 0;
              if (i == 0){
                  return TestBinding.inflate(getLayoutInflater());
              }
              return Test1Binding.inflate(getLayoutInflater());
          }
      
         @Override
         public void antiShakingClick(View v) {
              super.antiShakingClick(v);
              //点击事件
         }
      }
```
