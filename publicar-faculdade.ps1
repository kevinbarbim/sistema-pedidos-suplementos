# Publica o projeto no repositório da faculdade (SQL Server).
# Repositório: https://github.com/kevinbarbim/sistema-pedidos-suplementos-sqlserver

$ErrorActionPreference = "Stop"
$repoUrl = "https://github.com/kevinbarbim/sistema-pedidos-suplementos-sqlserver.git"

Set-Location $PSScriptRoot

if (-not (git remote get-url origin 2>$null)) {
    git remote add origin $repoUrl
    Write-Host "Remote 'origin' adicionado."
} else {
    git remote set-url origin $repoUrl
}

Write-Host "Enviando branch main para $repoUrl ..."
git push -u origin main

Write-Host ""
Write-Host "Pronto! Repositorio:"
Write-Host "https://github.com/kevinbarbim/sistema-pedidos-suplementos-sqlserver"
