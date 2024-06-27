# Codex Naturalis

<img src="https://www.craniocreations.it/storage/media/products/19/41/Codex_scatola+ombra.png" width=192px height=192px align="right" />

La prova finale del corso di **Ingegneria del Software** tenuto al Politecnico di Milano (2023/2024) consiste nello sviluppo di una versione virtuale, scritta in Java, del gioco da tavolo *Codex Naturalis*, creato dalla Cranio Creations.
É possibile trovare il gioco completo [qui](https://www.craniocreations.it/prodotto/codex-naturalis).

## Membri del gruppo
* [Mario Pesca](https://github.com/ziomekk-dev)
* [Lorenzo Simone](https://github.com/LorenzoSimone02)
* [Salvatore Tuand](https://github.com/Sa1vatoreTuand)
* [Xuwen Ye](https://github.com/xuwenye01)

**Docente**: Cugola Gianpaolo Saverio

**Voto**: -/30

## Funzionalità Implementate
| Funzionalità            | Stato |
|:------------------------|:-----:|
| Regole semplificate     |   ✅   |
| Regole complete         |   ✅   |
| Socket                  |   ✅   |
| RMI                     |   ✅   |
| GUI                     |   ✅   |
| TUI                     |   ✅   |
| Chat                    |   ✅   |
| Persistenza connessioni |   ✅   |
| Partite multiple        |   ✅   |
| Persistenza partite     |   ✅   |

#### Legenda
[✅]() Implementato &nbsp;&nbsp;&nbsp;&nbsp;[⛔]() Non implementato

## Tests

| Package    | Classi testate | Copertura |
|:-----------|:---------------|:---------:|
| Controller | //             |    //     |
| Model      | 35/35          |   100%    |

## Utilizzo dei jar
### Client

```bash
java -jar GC24-client.jar serverIp
```
Nel caso non venga specificato un serverIp, il client utilizzerà `localhost` come default.

#### Linux

```bash
java -Djava.rmi.server.hostname=$clientIp -jar GC24-client.jar serverIp
```
Nel caso il client venga avviato su di un sistema operativo Linux, va specificato il proprio ip nella VM-option `Djava.rmi.server.hostname`.

### Server

```bash
java -jar GC24-server.jar
```

#### Linux

```bash
java -jar GC24-server.jar serverIp
```

Nel caso il server venga avviato su di un sistema operativo Linux, va specificato `serverIp` come argomento.


<hr>

Codex Naturalis | © 2024 - Cranio Creations
