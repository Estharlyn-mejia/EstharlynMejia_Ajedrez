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

## Estudiante: Estharlyn Mejia Regalado (2023-0443)

Juego: Ajedrez
Descripción del juego:
El proyecto consiste en una implementación completa y funcional del juego de Ajedrez desarrollada en Java con JavaFX, con una temática inspirada en el anime Black Clover.
Esta versión incluye todas las reglas básicas del ajedrez y los movimientos especiales: enroque y coronación de peones. Además, cuenta con la correcta detección de jaque y jaque mate.
El sistema permite jugar en modo Jugador vs Jugador (local), donde dos personas pueden enfrentarse en el mismo dispositivo.
La interfaz gráfica está ambientada en el universo de Black Clover, utilizando imágenes, fondos y elementos visuales del anime para crear una experiencia inmersiva y atractiva. El tablero es totalmente interactivo, con piezas representadas mediante imágenes de alta calidad, resaltado de movimientos válidos y un sistema de turnos con validación en tiempo real.
El código está estructurado utilizando programación orientada a objetos, aplicando buenos principios de diseño, manejo eficiente de eventos, separación clara entre la lógica del juego y la interfaz, y un diseño modular que facilita el mantenimiento y posibles expansiones.
Controles:
Para realizar un movimiento:

Haz clic izquierdo sobre una pieza de tu color (según el turno actual) para seleccionarla.
Los movimientos válidos se resaltarán automáticamente con círculos rojos en las casillas disponibles.
Haz clic izquierdo sobre una de las casillas resaltadas para realizar el movimiento.
Puedes cancelar la selección haciendo clic en cualquier otra casilla que no esté resaltada.

En el juego están disponibles los botones:

Nueva Partida: Reinicia el juego actual.
Volver al Menú: Regresa al menú principal.
