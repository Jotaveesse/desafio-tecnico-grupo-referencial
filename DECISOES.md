1. Que situações o enunciado não cobria e o que você decidiu em cada uma?
	O número da linha inválida exibido é contado a partir da primeira linha, incluindo cabeçalho, começando do 1. Isso permite verificar qual é a linha que deu problema mais facilmente se o arquivo CSV for aberto em um editor de planilhas.
	Linhas com limite de velocidade igual à zero também foram consideradas inválidos e suas linhas ignoradas.
	Placas com letras minúsculas foram convertidas para letrar maiúculas.
	
2. Qual foi a parte mais difícil e como você resolveu?
	Extrair os episódios corretamente a partir das linhas válidas. Resolvi analisando o que aconteceria com cada linha válida e moldando o código para tratar todas as situações em que eles poderiam se encontrar, por exemplo: primeira linha de um episódio, linha final de um episódio, linha sem excesso de velocidade.

3. O que você faria diferente com mais tempo?
	Utilizaria uma biblioteca para estilizar os componentes e iria fazer uma estilização mais modular, ao invés de uma global.
	Iria refatorar a classe ParserLinhas para deixar o código mais organizado e legível.
	Iria medir a performace do código para identificar se o sistema iria se sobrecarregar caso o CSV seja muito grande.