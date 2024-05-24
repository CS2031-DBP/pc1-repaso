# repaso-a-cloud: Despliegue de una Aplicación Spring Boot en Amazon Web Services

## Instrucciones :page_facing_up:

- Individual.
- Tiempo 120 minutos.

## Introducción :mega:

En este laboratorio, aprenderás cómo desplegar una aplicación web desarrollada con Spring Boot en [Amazon Web Services (AWS)](https://aws.amazon.com/). Utilizaremos varios servicios de AWS, incluyendo [AWS EC2](https://aws.amazon.com/es/ec2/) para ejecutar la aplicación, [AWS S3](https://aws.amazon.com/es/s3) para almacenar el proyecto empaquetado y [AWS RDS](https://aws.amazon.com/es/rds) con PostgreSQL como base de datos. Este laboratorio te proporcionará experiencia práctica en el despliegue de aplicaciones en la nube utilizando servicios populares de AWS. Sin embargo, debes notar que el proceso que vamos a seguir es manual, más adelante, durante el curso, aprenderemos a automatizarlo.

## Objetivos :dart:

- Desplegar una aplicación Spring Boot en una instancia de AWS EC2.
- Configurar y asegurar una base de datos PostgreSQL en AWS RDS.
- Almacenar archivos en AWS S3.
- Comprender los conceptos básicos de la infraestructura en la nube de AWS.
- Familiarizarse con las herramientas y servicios necesarios para el despliegue de aplicaciones en AWS.

## Requisitos Previos :white_check_mark:

1. **Cuenta en AWS**: Debes acceder a la cuenta brindada a través de AWS Academy. Recuerda que contarás con ~100USD de créditos disponibles, sé cauteloso con su uso (e.g., recuerda liberar los recursos en los servicios que ya no uses).
2. **Código de la aplicación**: Debes utilizar el código que se desarrolló en el Laboratorio 2.01, debería estar listo para el despliegue.

## Tools :wrench:

* Cliente HTTP: Puedes emplear [Postman](https://www.postman.com/) para realizar los requests HTTP.
* Cliente PostgreSQL: Puedes utilizar herramientas como [pgAdmin](https://www.pgadmin.org/), [DBeaver](https://dbeaver.io/) o el cliente de línea de comandos (CLI) *psql* para gestionar la base de datos en AWS RDS.

## Procedimiento :memo:

#### Ingresa a la Consola de AWS

1. Accede al curso `Cloud Foundations` de [AWS Academy](https://awsacademy.instructure.com/), revisa tu correo para encontrar la invitación.
2. Haz clic en la sección `Modules`, busca el módulo llamado `Sandbox` y accede a ese recurso.
3. Haz clic en el botón superior derecho `Start Lab` para iniciar el laboratorio.
4. Espera hasta que inicialice, se mostrará un mensaje `Lab status: ready`.
5. Haz clic en el botón `AWS` para ingresar a la consola de AWS en una nueva pestaña del navegador.

---

### Crear una instancia de Amazon EC2 para alojar la aplicación
Una vez que hayas iniciado sesión en la consola, busca y selecciona "EC2" en la lista de servicios de AWS.

Elige una imagen con Ubuntu o Amazon Linux, luego elige un tier de instancia y crea la máquina.

---

### Configurar Amazon RDS con PostgreSQL como base de datos

**Navegar a AWS RDS:**

Para cambiar de servicio, busca y selecciona "RDS" en la lista de servicios de AWS.

**Crear una instancia de base de datos:**

Haz clic en `Create database` para comenzar el proceso de creación de una nueva instancia de base de datos.

**Configurar opciones del motor de base de datos:**

Configura las opciones disponibles:

- _Choose a database creation method_: Elige  `Easy create` (o puedes elegir `Standard create` y aprender más opciones).

- Configuration:

-- _Engine type_: Elige `PostgreSQL` como el motor de base de datos que deseas utilizar.

-- _DB instance size_: Selecciona `Dev/Test` o `Free tier`. La elección afectará a la configuración predeterminada y a las opciones disponibles.

-- _DB instance identifier_: Introduce el nombre de la instancia de tu base de datos.

-- _Master username_: Introduce un username válido, usaremos ese valor para configurar la aplicación.

-- _Master password_: Puedes utilizar la que AWS autogenera o introducir una password segura.

**Configurar la conexión entre EC2 y RDS:**

Selecciona la opción `Connect to an EC2 compute resource`, luego selecciona la instancia de EC2 que has creado anteriormente.

Revise todas las configuraciones que has establecido para asegurarte de que sean correctas y da clic en el botón `Create database`. Esto iniciará el proceso de creación de la base de datos.

AWS comenzará a aprovisionar la instancia de base de datos. La creación de la instancia de base de datos puede llevar algunos minutos. Puedes hacer un seguimiento del progreso en la consola de AWS.

**Obtención del endpoint y puerto** :rotating_light:

Después de dar clic en `Create database`, verás un botón `View credential details` y será la única oportunidad que tengas para visualizar el master password si has decidido autogenerarla.
 
---

### Actualizar la configuración de la aplicación para conectarse a la base de datos

1. Agregar las siguientes propiedades en `application.properties`:

:rotating_light: Nota: Reemplazar `HOST`, `PORT`, `MASTER_USERNAME` y `MASTER_PASSWORD` por los valores que se produzcan en `AWS RDS`.

```
spring.datasource.url=jdbc:postgresql://HOST:PORT/postgres
spring.datasource.username=MASTER_USERNAME
spring.datasource.password=MASTER_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

---

### Empacar nuestra aplicación
Abre una terminal y dirígete a la ruta de tu proyecto. Se puede emplear el IDE o el comando `mvnw` usando los siguientes comandos:

```
$ ./mvnw package
```

Esto producirá un archivo `.jar` que usaremos para subirlo a Amazon S3.

### Configurar Amazon S3 para almacenar archivos

Para cambiar de servicio, busca y selecciona "S3" en la lista de servicios de AWS.

#### Crear un nuevo bucket
1.  En la página principal de Amazon S3, haz clic en el botón `Create bucket`.
2.  Selecciona una región para tu bucket (i.e. us-east-1). La elección de la región puede afectar el rendimiento y el costo, sin embargo en el sandbox de AWS Academy solo tenemos una elección.
3.  Ingresa un nombre único para tu bucket. Los nombres de los buckets deben ser únicos en todo AWS. Puedes seguir las [recomendaciones de AWS](https://docs.aws.amazon.com/AmazonS3/latest/userguide/bucketnamingrules.html) para nombrar buckets.
4.  Puedes configurar opciones adicionales como el registro de acceso, políticas de versión, etc. Para este laboratorio, puedes dejar las opciones predeterminadas.
5.  Haz clic en `Create bucket` para crearlo.

#### Subir el archivo .jar al bucket
1.  Una vez que hayas creado el bucket, selecciona el bucket haciendo clic en su nombre en la lista de buckets.
2.  En la parte superior de la página del bucket, verás un botón que dice `Upload`. Haz clic en él.
3.  En la ventana de carga, puedes arrastrar y soltar tu archivo `.jar` en el área designada o hacer clic en `Add files` para buscar y seleccionar el archivo desde tu computadora.
4.  Puedes configurar permisos de acceso y opciones de almacenamiento, como la clase de almacenamiento y la gestión de versiones.
5.  Haz clic en `Upload` para subir el archivo .jar al bucket.

Una vez que hayas completado estos pasos, tu archivo .jar estará almacenado en el bucket de Amazon S3 que creaste. Puedes acceder a él y gestionar sus permisos y propiedades desde la consola de AWS o a través de la API de S3.

---

### Copiar la aplicación Spring Boot en la instancia de EC2
Para copiar un archivo de Amazon S3 a una instancia de Amazon EC2, utilizaremos la interfaz de línea de comandos de AWS (AWS CLI).

```
$ aws s3 cp s3://nombre-del-bucket/ruta-al-archivo.jar /local-archivo.jar
```
:rotating_light: Nota:
- Reemplaza `nombre-del-bucket` con el nombre de tu bucket S3.
- Reemplaza `ruta-al-archivo` con la ruta al archivo en S3 que deseas copiar.
- Reemplaza `/ruta-local-en-la-instancia-ec2` con la ubicación local en tu instancia de EC2 donde deseas que se copie el archivo.

Después de ejecutar el comando, verifica que el archivo se ha copiado correctamente en tu instancia de EC2 en la ubicación especificada.

---

### Ejecutar la aplicación Spring boot en la instancia de EC2

Para ejecutar la aplicación utiliza el siguiente comando:

```
$ java -jar local-archivo.jar
```

---

### Evidencia: Probar y verificar el despliegue de la aplicación

<!-- Puedes emplear las tools descritas anteriormente en su respectiva sección -->
<!-- Añadir screenshots que demuestre la evidencia -->
<!-- Ejemplo de añadir image en markdown: ![descripcion de la imagen](img/path_de_la_imagen) -->
<!-- Más información en: https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#images -->

## Conclusiones

En este laboratorio, has aprendido cómo desplegar una aplicación Spring Boot en AWS utilizando varios servicios, incluyendo Amazon EC2, Amazon RDS con PostgreSQL y Amazon S3. Has adquirido experiencia en configurar los servicios y la configuración de bases de datos en la nube. Este conocimiento es fundamental para desplegar aplicaciones en entornos de producción en AWS y te ayudará a comprender mejor los conceptos de la nube y las herramientas asociadas.

Finalmente, es necesario indicar que es fundamental automatizar estos procesos, más adelante lo veremos en el curso.