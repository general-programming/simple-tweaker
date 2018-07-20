# Simple Tweaker

A really simple reflection-filled mod loader for Minecraft 1.13 servers & written in Kotlin

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

```js
{
  "id": "lowercase-id", // should be unique & lowercase
  "name": "Human Readable Name",
  "tweaks": [ // array of tweak class names (implement ITweak)
    "my.package.tweak.MyTweak"
  ]
}
```

#### writing tweaks

tweak classes look something like the following:

```kotlin
class MyTweak: ITweak {
    // tweak stages are:
    // - INIT: your tweak is called before the server boots
    // - PRE_WORLD: your tweak is called right before the world is loaded
    // - POST_WORLD: your tweak is called after the world is loaded
    // it's recommended that you use POST_WORLD unless you have a reason to run beforehand
    override fun getTweakStage() = TweakStage.POST_WORLD

    override fun runTweak(ev: TweakRunEvent) {
        // this method is called according to your tweak stage
        // you also generally register commands here
    }
}
```

a full example (with commands) can be found in this repo: [InternalTweak.kt](https://github.com/general-programming/simple-tweaker/blob/master/src/main/kotlin/gq/genprog/simpletweaker/tweaks/builtin/InternalTweak.kt)

tweaks can also receive events:

```kotlin
@EventHandler fun onPlayerJoin(ev: PlayerJoinEvent) {
    // called when a player joins
}
```

#### commands

tweaks can register basic commands. they do not currently use mojang's brigadier command library.  
command classes extend `ICommand`:

```kotlin
class MyCommand: ICommand {
    override fun getAliases(): Array<String> = arrayOf("hello")
    override fun getDescription(): String = "Hello, world!"
    
    override fun execute(sender: ICommandSender, array: Array<String>) {
        sender.sendMessage("Hi, world!")
    }
}
```

you register commands in your `runTweak` method:

```kotlin
class MyTweak: ITweak {
    // ...
    
    override fun runTweak(ev: TweakRunEvent) {
        // ...
        
        ev.registerCommand(MyCommand())
    }
}
```