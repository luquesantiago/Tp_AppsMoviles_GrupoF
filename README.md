# TP Apps Móviles — Catálogo de películas (Kotlin)

App Android desarrollada en equipo para el seminario de aplicaciones móviles. Catálogo de películas que consume la API de **TMDB**, con reproducción de trailers, registro de usuarios y compras.

## Funcionalidades

- **Catálogo de películas populares** desde la API de TMDB, con imágenes (Glide)
- **Trailers** integrados con el reproductor de YouTube (busca el video en español y cae a inglés si no hay)
- **Registro y login** de usuarios con persistencia local
- **Compras** guardadas en base de datos local

## Arquitectura y tecnologías

Patrón **MVVM** con Repository:

- **Kotlin** + XML layouts
- **Room** (entidades y DAOs para usuarios y compras)
- **Retrofit + Gson** para la API de TMDB, con **coroutines**
- **ViewModel + LiveData** para el estado de la UI
- **RecyclerView** con adapters para catálogo y compras
- **Glide** para carga de imágenes

## Configuración

1. Cloná el repo y abrilo en Android Studio
2. Conseguí una API key gratuita de [TMDB](https://www.themoviedb.org/settings/api) y agregala en tu `local.properties`: `TMDB_API_KEY=tu_key_aca`
3. Sync de Gradle y ejecutar

## Integrantes

- Santiago Luque
- Elias Ricarte
- Benjamin Abbatiello
