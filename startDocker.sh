#!/bin/bash
export DOCKER_HOST=unix:///home/gabriel/.docker/desktop/docker.sock

echo "🚀 Iniciando o Docker e a aplicação..."

if ! systemctl is-active --quiet docker; then
    echo "🔧 Iniciando o Docker..."
    sudo systemctl start docker
fi

TIMEOUT=60
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

echo "🛠️  Compilando a aplicação Spring Boot..."
./mvnw clean package -DskipTests || { echo "❌ Erro ao compilar a aplicação!"; exit 1; }

echo "🛑 Parando e removendo todos os containers, redes e volumes..."
docker compose down -v

echo "🚀 Subindo os serviços com Docker Compose..."
docker compose up -d

echo "🎉 Aplicação iniciada com sucesso!"
