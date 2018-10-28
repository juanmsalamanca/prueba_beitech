# Aplicación de prueba con el api last.Fm
Esta aplicación consume el api restfull de last.fm https://www.last.fm/api para buscar artistas musicales, sus álbumes y canciones.

La arquitectura está basada en componentes 
•	vista
•	Presentación
•	Persistencia
•	Cliente-api

## Vista 
La vista se basa en un solo Activity que permite mostrar dos fragments; cada fragment representa una sección de la aplicación.
### Primer fragment INICIO
El primer fragment tiene tres listas artistas, álbumes y canciones, cuando se pulsa el artista se consultan los álbumes de este, pulsando el álbum se ve una lista de canciones que pertenecen al álbum.
Los datos que se consulten en el primer fragment son almacenados en una base de datos sqlite y pueden ser consultados sin internet.

### Segundo fragment BUSQUEDA
El segundo fragmente muestra tres pestañas, artistas, álbumes y canciones, y un campo de búsqueda, en este campo se busca usando el api la coincidencia y se muestran en una grilla los resultados clasificados en cada pestaña.
Estos datos no se almacenan en el celular solo funciona con internet.

## Presentación 
La capa de presentación permite capturar los eventos de las vistas e interactuar con las capas de más bajo nivel.




## Persistencia
La persistencia de la aplicación está basada en una base de datos SQLITE que contiene tres tablas: artistas, álbumes y canciones. 
Para interactuar con la base de datos se crearon objetos de tipo DAO y DTO.

### Cliente-api
La conexión con el api de last.fm se manejó como una capa independiente que contiene la estructura necesaria para invocar los servicios web y transformar su respuesta de formato JSON a objetos DTO.







