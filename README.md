# Desafio Técnico: Análise de Excesso de Velocidade

## Pré-requisitos
- Java 21 ou superior (`java -version`)
- Node.js 20 ou superior (`node -v`)

## Como rodar

### Backend (porta 8080)
```bash
cd backend
.\mvnw.cmd spring-boot:run
```

### Frontend (porta 4200), em outro terminal
```bash
cd frontend
npm install
npm start
```

Abra http://localhost:4200 e envie o arquivo `leituras.csv`.

## Executar testes
```bash
cd backend
./mvnw test
```