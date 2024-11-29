# Chisel + Rocket Template

You've done the Chisel Bootcamp, maybe even explored Rocket and/or Chipyard. You want to work on a non-trivial design but also dont want to commit to integrating into Chipyard- at least not quite yet. This repo will help you get off the ground with building Chisel code with the Rocket-chip library. 


## Getting Started

First, follow [Chisel Template](https://github.com/chipsalliance/chisel-template) and make sure that you can build the provided example correctly. Then, run the following commands:


```
git clone https://github.com/HakamAtassi/rocket-template.git
cd rocket-template
git submodule update --init --recursive
sbt run
```

If successful, you should see a gcd.v file in your root directory. If not, please open an [issue](https://github.com/HakamAtassi/rocket-template/issues/new). 


## Adding additional libs

The repo provides rocket chip, diplomacy, and hardfloat out of the box. You may also be interested in adding a UART to your design, for instance. The rocketchip ecosystem keeps UARTs and other useful periphirals in rocket-chip-blocks. This process will guide you through adding rocket-chip-blocks to this repo. 


To add rocket-chip-blocks, we must first get the repo into `deps`. This can be achieved through a simple git clone or through a submodule add. 

```
cd deps
git clone git@github.com:chipsalliance/rocket-chip-blocks.git
cd ..
```

Then add the following to build.sbt. 

```
lazy val rocketchip_blocks = (project in file("deps/rocket-chip-blocks"))
  .dependsOn(rocketchip)
  .settings(libraryDependencies ++= rocketLibDeps.value)
  .settings(commonSettings)
```
Then add rocket-chip-blocks as a dependancy to your root project by inserting it into the `dependsOn()` statement. Ex: 

```
lazy val root = (project in file("."))
  .dependsOn(rocketchip, rocketchip_blocks)
  .settings(libraryDependencies ++= rocketLibDeps.value)
  .settings(
    libraryDependencies ++= Seq(
      "org.reflections" % "reflections" % "0.10.2"
    )
  )
  .settings(commonSettings)
```


## Testing

To test the repos configuration, run `sbt test`. The result should be a series of TL transactions printed to terminal. If this is the case, you are good to go. 
