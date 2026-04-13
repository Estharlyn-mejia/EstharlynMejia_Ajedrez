# EstharlynMejia_Ajedrez

Proyecto de ajedrez desarrollado en JavaFX.

## Descripción
Este proyecto implementa un juego de ajedrez con interfaz gráfica utilizando JavaFX.

## Estructura
- src: código fuente
- resources: imágenes del tablero y piezas

## Actaulización

## Estructura
- Ahora se agrego las imagenes de las fichas, tanto blancas, como negras
- se ajusto a panel, donde se encuentran el tablero, ya que no lo especifique

## Complicación
- el compilador no podia encontrar las imagenes y la ubicacion de la mismas. 

## Actualización 2

## Estructura
- Se le agrego la pantalla de inicio del juego
- Ahora tiene sonido, tanto en la pantalla de inicio, como en la pantalla de juego, aun faltan sonidos de las piezas.
- Se le cambio el App, para empezar con la pantalla de incio.
- se le agrego la funcion del audio al lauch.json.

## Complicaciones
- Desde la ultima actualizacion buscando como se le pone el audio, y intendo vincular todo.
- Y varios problemas para encontrar la direccion del audio.


## Actualización 3

## Estructura
- Se agrego la posibilidad del que el peon se pueda mover y comer otras piezas, tambien se bloquea y no puede comer en vertical
- Se le agrego un logo al menú de inicio
- Ahora marca si es negro o blanco, o sea, ahora se juega por turno, ademas tambien marca donde se puede mover
- Ahora tiene sonido al  mover una pieza(peón)

## Complicaciones

- que no se complico, con decir que hasta la URL del sonido, que no tenia nada que ver, se perdio y no lo queria encontrar
- Implementar nuevo metodo
- Menejar movimientos usando los ejes X y Y
- Ajustar el tablero, para que no pierda la forma

## Actualización 4

## Estructura
- Solo se agrego una imagen a la pantalla de inicio
- Se agregaron las funciones de las demas piezas que faltaban(Torre, Caballo, Alfil, Reina y rey)
- Estan todas funcionando y capturando

## Complicaciones

- Ya se tenia la base, con el peon, y solo quedo agregarles cambios a lo que ya se tenia
- Coliciones de piezas
- No se repetaba el turno con algunas fichas
- Se cambiaba una ficha y afectaba a todo lo creado
- Se le agregaron los atributos de torre y alfil a reyna y me lo leía como falso, y decidi hacerla desde cero.


## Actualizacion 5

## Estructura

- Se agrego ventana final, jaque y jaquemate
- se le dio las funciones de las ventanas de inicio
- Se le pudo sonido al jaque
- Falta la corronacion y el enroque

## Complicaciones

- Verificar los jaque, ya que cuando se le implemento el bloquear los movimento, no se podia seguir
- Verificar si una pieza podria cubrir
- Verificar si el rey podia escapar, todo eso se complico demadiado
- Y las rutas, eso nunca puede faltar


## Actualizacion 6

- Me mate con el codigo para que de el jaque y se pueda mover el rey, pero por una estraña razón no me deja 
- Me quiero romper la laptop
- Agregue la opción de reiniciar y ventana final de nuevo, pero mejoradas
- cambie algunas cosas del codigo para agreggar el enroque y la corronacion
- solo se convierte en reina
- descarte la idea de ponerle un video final, eso ya no se va a hacer, me harte en verdad
- las dirrecion se podrian ir al diablo, el rey de pobria ir al diblo, el enroque tambien.

## Ajedrez - Black Clover Edition

**Estudiante:** Estharlyn Mejia Regalado (2023-0443)

## 1. Descripción del proyecto

Este proyecto es una implementación del juego de **Ajedrez** con temática del anime **Black Clover**, desarrollado en **Java** utilizando **JavaFX** para la interfaz gráfica.

El juego permite a dos jugadores enfrentarse en modo **Jugador vs Jugador (local)** en el mismo dispositivo. Incluye todas las reglas básicas del ajedrez y los movimientos especiales: **enroque** y **coronación de peones**. Además, cuenta con detección visual y sonora de **jaque** y **jaque mate**.

La interfaz está ambientada en el universo de Black Clover, con fondo animado, música temática y un diseño visual atractivo que mejora la experiencia del usuario.

## 2. Funcionalidades implementadas

### Funcionalidades completas:
- Modo de juego **Jugador vs Jugador (local)**
- Movimientos completos de todas las piezas (Peón, Torre, Caballo, Alfil, Reina y Rey)
- Movimientos especiales:
  - **Enroque** (corto y largo)
  - **Coronación de peones** (se transforma automáticamente en Reina)
- Detección de **jaque** con resaltado rojo y efecto de sonido
- Detección de **jaque mate** con ventana final y video temático
- Sistema de turnos con indicador visual
- Resaltado de movimientos válidos con puntos rojos
- Sonidos de fondo y efectos de movimiento
- Botones de **Nueva Partida** y **Volver al Menú**
- Pantalla de inicio con temática Black Clover
- Ventana final al terminar la partida

### Funcionalidades pendientes:
- No se implementó modo contra Bot (solo 1 vs 1)

## 3. Requisitos previos

- **Java JDK** 21 o superior
- **JavaFX SDK** 25.0.2 (o compatible)
- IDE recomendado: **Visual Studio Code** con extensión de Java o **IntelliJ IDEA**

## 4. Cómo ejecutar el proyecto

### Desde Visual Studio Code (recomendado):

1. Abrir la carpeta del proyecto en VS Code
2. Asegurarse de que la configuración en `.vscode/launch.json` y `settings.json` esté correcta (ya incluida en el proyecto)
3. Presionar `F5` o ir a **Run and Debug** → "Current File"
4. También puedes ejecutar la clase principal: `App.java`

### Configuración importante:
El proyecto ya incluye las referencias a JavaFX en los archivos de configuración. Solo asegúrate de tener la ruta correcta de tu JavaFX SDK en:
- `.vscode/settings.json`

## 5. Estructura del proyecto

- **`src/`** → Código fuente principal
  - `App.java` → Clase principal que inicia la aplicación
  - `Controller.java` → Controlador de la pantalla de inicio
  - `Tablero.java` → Clase principal que contiene toda la lógica del juego (movimientos, jaque, enroque, coronación, etc.)
  - `TableroController.java` → Controlador del tablero
  - `Audio.java` → Manejo de música de fondo y efectos de sonido
  - `VentanaFinalController.java` → Controlador de la pantalla final
  - `VentanaVideoController.java` → Controlador del video al ganar

- **`src/resources/`** → Recursos del juego
  - `image/` → Imágenes de piezas, tablero y elementos de Black Clover
  - `audio/` → Música de fondo y efectos de sonido
  - `video/` → Video de victoria

- **`FXML`** → Archivos de interfaz (VentanaInicio.fxml, Tablero.fxml, VentanaFinal.fxml, etc.)

## 6. Decisiones de diseño

- Se utilizó un enfoque **orientado a objetos** con una clase principal `Tablero` que maneja tanto la lógica del juego como la interacción con la interfaz.
- Los movimientos de las piezas se validan en tiempo real con verificación de jaque para evitar movimientos ilegales.
- Se separó la lógica de audio en una clase independiente (`Audio.java`) para facilitar su reutilización.
- Se implementó el enroque y la coronación respetando las reglas oficiales del ajedrez.
- Se priorizó una interfaz limpia y temática inspirada en **Black Clover** para mejorar la experiencia del usuario.

## 7. Autor

**Estharlyn Mejia Regalado**  
Matrícula: 2023-0443  
Fecha de entrega: Abril 2026

---
