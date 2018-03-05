# wearplus


# Algunas tecnologías

    Java: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    Gradle: https://gradle.org/
    spring: https://spring.io/
    JAX-RS: JAX-RS
    Jrrsey: https://jersey.github.io/index.html
    hibernate: http://hibernate.org/
    postgresql: https://www.postgresql.org/download/

    Node.js: https://nodejs.org/
    Yarn: https://yarnpkg.org/
    Webpack: https://webpack.github.io/
    Angular CLI: https://cli.angular.io/
    BrowserSync: http://www.browsersync.io/
    Karma: http://karma-runner.github.io/
    Jasmine: http://jasmine.github.io/2.0/introduction.html
    Protractor: https://angular.github.io/protractor/
    Leaflet: http://leafletjs.com/
    DefinitelyTyped: http://definitelytyped.org/
    
## Desarrollo

Antes de compilar este proyecto, debe instalar y configurar las siguientes dependencias:

1. [Node.js] []: Node para ejecutar un servidor web de desarrollo y construir el proyecto.
2. [Yarn] []: Yarn para administrar dependencias de Node.

Después de instalar Node, debería poder ejecutar el siguiente comando para instalar herramientas de desarrollo.
Solo necesitará ejecutar este comando cuando las dependencias cambien en [package.json] (package.json).

    yarn install

Se utiliza yarn scripts y [Webpack] [] como el sistema de compilación.

Intalar PostgreSQL 10.3 Released en su maquina y ejecutar los siguientes querys, para iniciar la base de datos.

```
#!sql
CREATE USER wearplus WITH PASSWORD 'wearplus';

CREATE DATABASE wearplus ENCODING 'UTF8';

GRANT ALL PRIVILEGES ON DATABASE wearplus TO wearplus;
```

[https://www.postgresql.org/download/](https://www.postgresql.org/download/)

Ejecute los siguientes comandos en dos terminales separados para rjrcutar el entorno de desarrollo, donde su navegador
se actualiza automáticamente cuando los archivos cambian en su disco duro.

    ./gradlew
    yarn start

[Yarn][] is also used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in [package.json](package.json). You can also run `yarn update` and `yarn install` to manage dependencies.
Add the `help` flag on any command to see how you can use it. For example, `yarn help update`.
[Yarn][] también se usa para administrar las dependencias de CSS y JavaScript utilizadas en esta aplicación. Puede actualizar las dependencias a una versión más nueva en [package.json] (package.json). También puede ejecutar `yarn update` y `yarn install` para gestionar las dependencias.
Agregue `help` en cualquier comando para ver cómo puede usarlo. Por ejemplo, `yarn help update'.

El comando `yarn run` mostrará una lista de todos los scripts disponibles para ejecutar en este proyecto.

### Utilizando angular-cli

También puede usar [Angular CLI][] para generar algún código de cliente personalizado.

Por ejemplo, el siguiente comando:

     ng generate component my-component

generará los archivos:
    create src/main/webapp/app/my-component/my-component.component.html
    create src/main/webapp/app/my-component/my-component.component.ts
    update src/main/webapp/app/app.module.ts


## Building para producción

Para optimizar la aplicación wearplus para producción, ejecute:

    ./gradlew -Pprod clean bootRepackage

Esto miniatizará los archivos CSS y JavaScript del cliente. También modificará `index.html` para que haga referencia a estos nuevos archivos. Para asegurarse de que todo funcionó, ejecute:

    java -jar build/libs/*.war

Luego ir hacia [http://localhost:8080](http://localhost:8080) en el navegador.

## Prueba

Para iniciar las pruebas de su aplicación, ejecute:

    ./gradlew test

## Utilizar Docker (opcional)

Puede usar Docker para mejorar su experiencia de desarrollo. Hay disponible una cantidad de configuraciones de compuerta de docker en la carpeta [src/main/docker](src/main/docker) para iniciar los servicios de terceros requeridos.

Por ejemplo, para iniciar una base de datos postgresql en un contenedor acoplable, ejecute:

     docker-compose -f src/main/docker/postgresql.yml up -d

Para detenerlo y eliminar el contenedor, ejecute:

     docker-compose -f src/main/docker/postgresql.yml down

También puede adaptar completamente su aplicación y todos los servicios de los que depende.
Para lograr esto, primero crea una imagen de portada de tu aplicación ejecutando:

     ./gradlew bootRepackage -Pprod buildDocker

Entonces corre:

      docker-compose -f src/main/docker/app.yml up -d

Para obtener más información, consulte [Using Docker and Docker-Compose][], esta página también contiene información sobre docker-compose sub-generator (`jhipster docker-compose`), que puede generar configuraciones de docker para uno o varios JHipster. aplicaciones.
