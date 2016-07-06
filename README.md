# MyApp
POI Test
文件在手机sd卡上
Android version 是4.x会出现下面的错误。5.0以上的能成功运行。

现在报错：
  05-30 15:25:33.628 17821-17821/? E/AndroidRuntime: FATAL EXCEPTION: main、
  
  Process: com.example.administrator.myapp, PID: 17821
  
  java.lang.NoClassDefFoundError: org.openxmlformats.schemas.wordprocessingml.x2006.main.DocumentDocument$Factory
   at org.apache.poi.xwpf.usermodel.XWPFDocument.onDocumentRead(XWPFDocument.java:134)
  at org.apache.poi.POIXMLDocument.load(POIXMLDocument.java:159)
  
   at org.apache.poi.xwpf.usermodel.XWPFDocument.<init>(XWPFDocument.java:116)
   
   at com.example.administrator.myapp.CustomXWPFDocument.<init>(CustomXWPFDocument.java:28)
   
  at com.example.administrator.myapp.WordUtil.generateWord(WordUtil.java:40)
  
    at com.example.administrator.myapp.MainActivity$1.onClick(MainActivity.java:53)
    
     at android.view.View.performClick(View.java:4463)
     
     at android.view.View$PerformClick.run(View.java:18770)
     
       at android.os.Handler.handleCallback(Handler.java:808)
       
         at android.os.Handler.dispatchMessage(Handler.java:103)
         
         at android.os.Looper.loop(Looper.java:193)
         
         at android.app.ActivityThread.main(ActivityThread.java:5333)
         
         at java.lang.reflect.Method.invokeNative(Native Method)
         
        at java.lang.reflect.Method.invoke(Method.java:515)
        
        
         at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:824)
         
         at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:640)
         
          at dalvik.system.NativeStart.main(Native Method)
