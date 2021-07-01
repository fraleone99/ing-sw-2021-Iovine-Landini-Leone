
# Software Engineering final project

Maestri del Rinascimento is the final project of **Software Engineering** course held
at Politecnico di Milano. (2020/2021)  

**Teacher** Pierluigi San Pietro

![Image of the game](src/main/resources/graphics/box_image.png)

## Functionalities

| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/model) |
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/model) |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/server) |
| GUI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/client/view/GUI) |
| CLI |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/client/view/CLI) |
| Multiple games | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/server)|
| Local single player | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/blob/main/src/main/java/it/polimi/ingsw/controller/LocalSPController.java) |
| Resilience | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/fraleone99/ing-sw-2021-Iovine-Landini-Leone/tree/main/src/main/java/it/polimi/ingsw/server)
| Persistence | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
| Editor | [![RED](http://placehold.it/15/f03c15/f03c15)]() |

#### Legend
[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented

## Running
To launch the game use the following command in the directory of the jar.

```
java -jar PSP07.jar 
```
You'll have to choose if you want to run the server, CLI client or GUI client.

If you want to do a local game, you won't need to start a server.

If you want to do a multiplayer game, you'll need to start a server and at least two client.

You can also do a single player game connected to the server.

If you use the default configuration on the server the port is 3456.

The recommended setting for the GUI on Windows are : 
* Resolution 1920x1080 
* Scale and layout: 125%

## Authors
* [Lorenzo Iovine](https://github.com/lorenzoiovine99)
* [Nicola Landini](https://github.com/neekoo0)
* [Francesco Leone](https://github.com/fraleone99)




