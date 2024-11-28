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

The repo provides rocket chip, diplomacy, and hardfloat out of the box. You may be interested in extending this repo by adding testchipip or perhaps your own ip. This section is intended to you walk you through that process. 


TODO
