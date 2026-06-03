# Baixa o driver JDBC oficial do Microsoft SQL Server (Maven Central).
$ErrorActionPreference = "Stop"
$jarName = "mssql-jdbc-12.6.1.jre11.jar"
$jarUrl = "https://repo1.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/12.6.1.jre11/$jarName"
$dest = Join-Path $PSScriptRoot $jarName

Write-Host "Baixando $jarName ..."
Invoke-WebRequest -Uri $jarUrl -OutFile $dest -UseBasicParsing
Write-Host "Salvo em: $dest"
Write-Host "No NetBeans: Propriedades do projeto -> Bibliotecas -> adicionar JAR."
