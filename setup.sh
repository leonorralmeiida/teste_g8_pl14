#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Utilização: $0 <nome> <email>"
    exit 1
fi

git config --global user.name "$1"
git config --global user.email "$2"

echo "Configuração global do git atualizada:"
echo "Nome:  $(git config --global user.name)"
echo "Email: $(git config --global user.email)"
