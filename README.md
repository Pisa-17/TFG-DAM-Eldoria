# TFG-DAM-Eldoria
Este repositorio contiene el código fuente del proyecto Eldoria para el módulo de Desarrollo de Aplicaciones Multiplataforma (DAM). La documentación completa del proyecto se encuentra en un repositorio separado.

## Introducción
Eldoria es un videojuego en 2 dimensiones de aventura y acción con elementos de RPG. El juego se conecta a una base de datos MySQL desplegada en Docker.

## Tecnologías del Proyecto
<p align="center">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,docker,idea,linux,mysql,latex" alt="Tecnologías del Proyecto" />
  </a>
</p>

## Instalación
Para la isntalación hay dos posibilidades ya que se puede realizar en linux y windows

### Linux
Para configurar el entorno de desarrollo y ejecutar el proyecto, sigue estos pasos:

Clona este repositorio:
```bash
git clone https://github.com/tuusuario/TFG-DAM-Eldoria.git
```
Configura tu IDE para que la carpeta res sea una carpeta de recursos.
Asegúrate de tener Docker instalado y ejecutando.
Levanta los contenedores de Docker necesarios:
```bash
docker-compose up
```
Importa el proyecto en tu IDE y configura las dependencias necesarias.


### Windows

Primero descarga la [JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
A continuación usa el dockerfile para levantar el contenedor de Docker, y mantenlo activo mientras el juego esté operativo.

O si lo prefieres, clona el repositorio en tu IDE de preferencia, realiza un contenedor de docker con la imagen de docker del proyecto y por último asegurate de que los puertos estén bien puestos.

## Uso
Después de la instalación, puedes ejecutar el proyecto desde tu IDE. Asegúrate de que la base de datos MySQL esté corriendo en Docker.

## Problemas Conocidos
Al clonar este proyecto, es posible que encuentres un error indicando que no se pueden encontrar los archivos PNG necesarios. Esto probablemente se debe a que la carpeta "res" no está definida como carpeta de recursos en tu IDE. Asegúrate de configurar la carpeta "res" como una carpeta de recursos para que el proyecto funcione correctamente.

## Contribución
Si deseas contribuir al proyecto, sigue estos pasos:

- Haz un fork del repositorio.
- Crea una nueva rama (git checkout -b feature/nueva-feature).
- Realiza tus cambios y haz commit (git commit -m 'Añadir nueva feature').
- Empuja tus cambios a la rama (git push origin feature/nueva-feature).
- Abre un Pull Request.

## Enlaces de Interés
- [Documentación del Proyecto LaTeX](https://github.com/Pisa-17/documentationEldoriaTFG)
- [Assets del Juego](https://pixel-boy.itch.io/ninja-adventure-asset-pack)
- [Autor de los Assets](https://twitter.com/2Pblog1)
## Contactos
Para cualquier consulta o sugerencia, puedes contactar conmigo a través de RMO48806@gmail.com.
