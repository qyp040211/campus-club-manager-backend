# 临时修改当前会话的环境变量，切换到Java 21
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# 验证Java版本
Write-Host "当前Java版本："
java -version

# 验证Maven版本
Write-Host "\n当前Maven版本："
mvn -version