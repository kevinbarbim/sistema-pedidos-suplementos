# Publica o projeto no repositorio NOVO da faculdade (separado do CRUD do ensino medio).
# Antes de rodar: crie o repo vazio em https://github.com/new?name=sistema-pedidos-suplementos
# (sem README, sem .gitignore, sem licenca)

$ErrorActionPreference = "Stop"
$repoUrl = "https://github.com/kevinbarbim/sistema-pedidos-suplementos.git"

Set-Location $PSScriptRoot

if (-not (git remote get-url faculdade 2>$null)) {
    git remote add faculdade $repoUrl
    Write-Host "Remote 'faculdade' adicionado."
} else {
    git remote set-url faculdade $repoUrl
}

Write-Host "Enviando branch main para $repoUrl ..."
git push -u faculdade main

Write-Host ""
Write-Host "Pronto! Repositorio da faculdade:"
Write-Host "https://github.com/kevinbarbim/sistema-pedidos-suplementos"
