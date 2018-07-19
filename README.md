# Simple Tweaker

A really simple reflection-filled mod loader for Minecraft 1.13 servers

### building

why would you want to?
```sh
git clone https://github.com/general-programming/simple-tweaker
cd simple-tweaker
gradle shadowJar
# jar outputted to build/libs
```

if you want to write a tweak, follow the steps above but additionally run `gradle install`.

you can then add lines to your own build.gradle:

```groovy
repositories {
    // ...
    mavenLocal()
}

dependencies {
    // ...
    compile "gq.genprog:simpletweaker:0.1.0"
}
```

### running

start your minecraft server as follows:
```
java -javaagent:simpletweaker-0.1.0-all.jar -jar minecraft_server.jar nogui
```

### tweaks

mods are known as "tweaks" (or more precisely "tweak holders")  
they are jar files that lives in the `tweaks/` subfolder of the server directory  
they contain a top-level file, `tweak.json`, along with the actual classes

#### tweak.json format

```json
{
  "id": "lowercase-id", // should be unique & lowercase
  "name": "Human Readable Name",
  "tweaks": [ // array of tweak class names (implement ITweak)
    "my.package.tweak.MyTweak"
  ]
}
```
