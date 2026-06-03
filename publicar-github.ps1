# Cria o repositório no GitHub e envia o código (requer gh auth login).
# Uso: .\publicar-github.ps1

$ErrorActionPreference = "Stop"
$repoName = "sistema-pedidos-suplementos-sqlserver"

Set-Location $PSScriptRoot

if (-not (Get-Command gh -ErrorAction SilentlyContinue)) {
    Write-Host "Instale o GitHub CLI: https://cli.github.com/"
    Write-Host "Depois: gh auth login"
    exit 1
}

$exists = gh repo view "kevinbarbim/$repoName" 2>$null
if (-not $exists) {
    Write-Host "Criando repositório kevinbarbim/$repoName ..."
    gh repo create $repoName --public --source=. --remote=origin --description "MPV gestão loja de suplementos - Java Swing + SQL Server (FATEC)"
} else {
    if (-not (git remote get-url origin 2>$null)) {
        git remote add origin "https://github.com/kevinbarbim/$repoName.git"
    }
}

git branch -M main
git push -u origin main

Write-Host ""
Write-Host "Repositório publicado:"
Write-Host "https://github.com/kevinbarbim/$repoName"
