#!/bin/bash
export DOCKER_HOST=unix:///var/run/docker.sock  # Garante o uso do socket correto

echo "🚀 Iniciando o Docker e a aplicação..."

# Garante que o Docker está rodando
if ! systemctl is-active --quiet docker; then
    echo "🔧 Iniciando o Docker..."
    sudo systemctl start docker
fi

# Aguarde o Docker inicializar completamente
TIMEOUT=60  # Aumente o tempo limite para 60 segundos
SECONDS=0
while ! docker info >/dev/null 2>&1; do
    if [ $SECONDS -ge $TIMEOUT ]; then
        echo "❌ O Docker não iniciou dentro do tempo limite de $TIMEOUT segundos."
        exit 1
    fi
    echo "⏳ Aguardando o Docker iniciar... ($SECONDS s)"
    sleep 2
done

echo "🐳 Docker está rodando!"

# Para garantir que não há containers rodando
if [ "$(docker ps -q)" ]; then
    docker stop $(docker ps -q)
fi

if [ "$(docker ps -a -q)" ]; then
    docker rm $(docker ps -a -q)
fi

# Construir as imagens, se necessário
docker compose build  

# Subir os containers
docker compose up -d

# Iniciar a aplicação
docker compose logs -f