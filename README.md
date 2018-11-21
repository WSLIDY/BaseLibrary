一、使用版本升级功能时/选择头像-拍照-照片库：

1、确保开启权限

2、在res--> xml-->file_paths.xml 文件

 <resources>
        
     <paths>
     
        <external-path
        
            name="camera_photos"
            
            path="" />
            
    </paths>
    
    <paths>
    
        <external-path path="Android/data/包名/"    name="files_root" />
        
        <external-path path="." name="external_storage_root" />
        
    </paths>
    
 </resources>

3、在清单配置文件下

  <application
  
        ...
        
        <provider
        
            android:name="android.support.v4.content.FileProvider"
            
            android:authorities="${applicationId}.fileprovider"
            
            android:exported="false"
            
            android:grantUriPermissions="true">
            
            <meta-data
            
                android:name="android.support.FILE_PROVIDER_PATHS"
                
                android:resource="@xml/file_paths" />
                
        </provider>

    </application>
