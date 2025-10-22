# Uistify-backend
Backend del proyecto Uistify para el curso Entornos de Programación


## Diseño base de datos

![Base de datos](docs/db%20design.png)

## REST API

### Módulo de usuarios e inicio de sesión
- **POST _/api/auth/register_** Registro del usuario
- **POST _/api/auth/login_** Inicio de sesión de usuario

### Módulo de canciones
- **GET _/api/songs_** Lista parcial de todas las canciones
- **GET _/api/songs/<idCanción>_** Canción por id

### Módulo de playlists del usuario
Para este módulo se debe autenticar con un JWT en el encabezado. 
- **GET _/api/playlists_** Lista de playlists
- **POST _/api/playlists_** Creación de playlists
- **PUT _/api/playlists_** Actualización de una playlist
- **GET _/api/playlists/&lt;idPlaylist&gt;_** Detalle y canciones de playlist
- **DELETE _/api/playlists/&lt;idPlaylist&gt;_** Eliminar la playlist
- **POST _/api/playlists/&lt;idPlaylist&gt;/songs/&lt;idCanción&gt;_** Añade la canción a la playlist
- **DELETE _/api/playlists/&lt;idPlaylist&gt;/songs/&lt;idCanción&gt;_** Elimina la canción de la playlist

## Planes a futuro
- Permitir reordenar las canciones en las playlists.
- Implementar endpoints de artistas junto a sus álbumes y canciones
- Compartir playlist de manera pública
- Crear módulo para permitir al usuario ser un artista y subir sus canciones.
