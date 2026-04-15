# IRLWrapped

 
Esta es una aplicación móvil que permite gestionar fotos y vivencias en espacios de tiempo que elija el usuario, siendo indicada para un viaje, recopilatorios mensuales o anuales, entre otros. Para ello, 
se divide en dos partes: la creación de recopilaciones y la generación de resúmenes. Además, para hacer la experiencia al usuario más cómoda se usará la lógica de guardado de datos offline-first.

## Requisitos previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

- **Android Studio**
- **SDK 24** o superior
- **Un dispositivo Android** físico o **emulador** configurado

## Pasos para desplegar el proyecto

Sigue estos pasos en orden para ejecutar la aplicación

### 1. Clonar el repositorio

Abre una terminal y ejecuta:

git clone [https://github.com/rilojuancana-dot/irl-wrapped.git](https://github.com/rilojuancana-dot/irl_wrapped.git)

### 2. Abrir el proyecto en Android Studio

- Inicia Android Studio.
- Selecciona **File → Open**.
- Ve a la carpeta donde clonaste el repositorio y elígela.
- Espera a que Android Studio indexe los archivos y descargue las dependencias (puede tomar unos minutos).
  - Entre estas dependecias se encuentran las referentes a Jetpack Compose, JUnit, Room o Retrofit.

### 3. Sincronizar el proyecto con Gradle

Una vez abierto, Android Studio sincronizará Gradle automáticamente. Si no es así:

- Ve a **File → Sync Project with Gradle Files**.

### 4. Configurar un dispositivo o emulador

#### Opción A: Usar un dispositivo físico
- Activa el **modo desarrollador** y la **depuración USB** en el teléfono.
- Conecta el teléfono por USB al ordenador.

#### Opción B: Crear un emulador virtual
- En Android Studio, ve a **Tools -> Device Manager**.
- Haz clic en **Create device**.
- Selecciona un modelo de teléfono (ej. Medium Phone).
- Completa la creación y enciende el emulador.

### 5. Ejecutar la aplicación

- En Android Studio, asegúrate de que el teléfono/emulador aparezca en la barra superior (al lado del botón **Run 'app'**).
- Haz clic en el botón **Run 'app'** (triángulo verde).
- Espera a que la app compile, instale y se inicie automáticamente.
