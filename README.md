# 🃏 Blackjack - Proyecto Integrador POO

Este proyecto es una implementación del clásico juego de cartas Blackjack (o 21), desarrollado como proyecto final para la materia Programación Orientada a Objetos (POO).

El objetivo principal no es solo crear el juego, sino diseñar un sistema robusto, mantenible y escalable, aplicando patrones de diseño fundamentales como **MVC (Modelo-Vista-Controlador)** y **Observer**.

## 📌 Estado Actual: Aplicación de Consola

Actualmente, el proyecto es una **aplicación de consola** completamente funcional.

Toda la lógica del negocio (el "Modelo") está 100% desacoplada de la interfaz de usuario (la "Vista"). Esto significa que el Modelo no tiene idea de que existe una consola; simplemente envía notificaciones de eventos (como `CARTA_REPARTIDA` o `FIN_RONDA`) gracias al patrón Observer.

## 💻 Tecnologías y Patrones Clave

* **Lenguaje:** Java
* **Patrones de Diseño:**
    * **Modelo-Vista-Controlador (MVC):** La arquitectura central que separa la lógica del juego (M), la interfaz de usuario (V) y la entrada del usuario (C).
    * **Observador (Observer):** Utilizado para que el Modelo notifique a la Vista de cualquier cambio de estado sin acoplarse a ella.
    * **Principio de Responsabilidad Única (SRP):** Cada clase (como `Mano`, `Bankroll`, `Ronda`) tiene una única y clara responsabilidad.

## 🏛️ Arquitectura del Modelo

* `Partida` y `Ronda`: Actúan como los "Sujetos" (Observables) principales, orquestando el flujo del juego.
* Aclarar la diferencia entre `Jugador` y `Participante`. Jugador se refiere a la persona, Participante al rol de una persona en una mesa. Esto permite gestionar mejor el bankroll de un jugador.
* `ParticipanteBase`: Es la clase abstracta de la que heredan `Partipante` y `Crupier`, evitando duplicación de código.
* `Mano`, `Carta`, `Bankroll`, `Apuesta`: Clases de entidad que encapsulan reglas de negocio específicas.


<img width="1559" height="1765" alt="umlFinal" src="https://github.com/user-attachments/assets/d39f3577-7c92-40b3-866a-4a0dee7d54ab" />

## 📸 Screenshots de la Aplicación (Versión de Consola)

A continuación, se muestran algunas capturas de pantalla del flujo principal del juego en su implementación actual por consola:

**Inicio del Juego y Ronda de Apuestas**

<img width="600" height="281" alt="juego1" src="https://github.com/user-attachments/assets/e282fb08-c59e-43fb-9d4b-c618939507ff" />

<img width="412" height="212" alt="juego2" src="https://github.com/user-attachments/assets/10a11649-aa2a-4247-b90c-30e013ad13e4" />

**Durante la Partida (Pidiendo Cartas)**

<img width="735" height="670" alt="juego3" src="https://github.com/user-attachments/assets/ac492d26-4cf4-4ea9-ad70-4cc09c8d72ce" />


## 🚀 Cómo Ejecutar

1.  Clona este repositorio:
    ```bash
    git clone [https://github.com/tu-usuario/tu-repo.git](https://github.com/tu-usuario/tu-repo.git)
    ```
2.  Navega al directorio del proyecto:
    ```bash
    cd tu-repo
    ```
3.  Compila y ejecuta la aplicación:
    *Ejecutar la clase `BlackjackApp.java` desde tu IDE favorito*

## 🛣️ Roadmap y Futuras Implementaciones

El diseño actual se construyó para permitir las siguientes mejoras:

* [**Próximo Paso**] **Implementación de Interfaz Gráfica (GUI):**
    * El siguiente objetivo es sumarle otra vista, una interfaz gráfica (usando JavaFX).
    * Gracias al patrón Observer, la nueva vista gráfica solo necesitará registrarse como un `Observador` más, y recibirá los mismos eventos que la consola, sin necesidad de modificar nada del Modelo.

* [**A Futuro**] **Implementación en Red (Multijugador):**
    * El diseño también contempla una futura implementación en red.
    * El Controlador puede adaptarse para recibir entradas no solo del teclado local, sino a través de RMI, permitiendo que múltiples jugadores (Clientes) se conecten a una `Partida` (Servidor).

## 👨‍💻 Autor

* **Calvo, Mateo** - *[matucalv0](https://github.com/matucalv0)*
