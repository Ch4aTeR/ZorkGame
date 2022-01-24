package ch.bbw.zork;import java.util.ArrayList;import java.util.Collection;/** * Class Game - the main class of the "Zork" game. * <p> * Author:  Michael Kolling, 1.1, March 2000 * refactoring: Rinaldo Lanza, September 2020 */public class Game {    private Parser parser;    private Room currentRoom;    //private Task task;    private Room lobby, bedroom, livingroom, kitchen, office;    private ArrayList<Task> tasks = new ArrayList<Task>();    private ArrayList<Task> answers = new ArrayList<Task>();    boolean finished = false;    public Game() {        parser = new Parser(System.in);        lobby = new Room("You just entered you're House");        bedroom = new Room("The window is open brrrrr");        livingroom = new Room("Don't watch too long netflix");        kitchen = new Room("Where is my Wife?");        office = new Room("And another day in the office.");        tasks.add(new Task("I have to close the Window"));        answers.add(new Task("It's so cold let's close it."));        tasks.add(new Task("Pet the Dog"));        answers.add(new Task("Wuff Wuff, i think the dog likes it."));        tasks.add(new Task("Make a Sandwich"));        answers.add(new Task("I am so hungry."));        tasks.add(new Task("Delete intellij"));        answers.add(new Task("Hopefully i won't ever have to use it"));        bedroom.setTask(tasks.get(0));        livingroom.setTask(tasks.get(1));        kitchen.setTask(tasks.get(2));        office.setTask(tasks.get(3));        lobby.setExits(livingroom, kitchen, null, bedroom);        bedroom.setExits(office, lobby, null, null);        livingroom.setExits(null, kitchen, lobby, office);        kitchen.setExits(livingroom, null, null, lobby);        office.setExits(null, livingroom, bedroom, null);        currentRoom = lobby; // start game in the lobby    }    /**     * Main play routine.  Loops until end of play.     */    public void play() {        printWelcome();        // Enter the main command loop.  Here we repeatedly read commands and        // execute them until the game is over.        do  {            if (tasks.size() <= 0){                finished = true;            }else{                Command command = parser.getCommand();                processCommand(command);            }        }while (!finished);        System.out.println("Thank you for playing.  Good bye.");    }    private void printWelcome() {        System.out.println();        System.out.println("Welcome to Zork!");        System.out.println("Zork is a simple adventure game.");        System.out.println("Type 'help' if you need help.");        System.out.println();        System.out.println(currentRoom.getDescription());    }    private void processCommand(Command command) {        String commandWord = command.getCommandWord();        if (command.isUnknown()) {            System.out.println("I don't know what you mean...");        } else if (commandWord.equals("do") && currentRoom.getTask() != null && !currentRoom.getTask().getTaskDescription().equals("")) {            System.out.println("Give me some time i am doing it rn");            tasks.remove(currentRoom.getTask());            currentRoom.setTask(null);        } else if (commandWord.equals("go")) {            goRoom(command);        } else if (commandWord.equals("quit")) {            if (command.hasSecondWord()) {                System.out.println("Quit what?");            } else if (commandWord.equals("help")) {                printHelp();            } else {                finished = true;// signal that we want to quit            }        }    }    private void printHelp() {        System.out.println("You are lost. You are alone. You wander");        System.out.println("around at Monash Uni, Peninsula Campus.");        System.out.println();        System.out.println("Your command words are:");        System.out.println(parser.showCommands());    }    private void goRoom(Command command) {        if (!command.hasSecondWord()) {            System.out.println("Go where?");        }        String direction = command.getSecondWord();        // Try to leave current room.        Room nextRoom = currentRoom.nextRoom(direction);        if (nextRoom == null)            System.out.println("There is no door!");        else {            currentRoom = nextRoom;            System.out.println(currentRoom.getDescription());            if (currentRoom.getTask() != null && !currentRoom.getTask().getTaskDescription().equals("")) {                System.out.println(currentRoom.getTask().getTaskDescription());            } else {                System.out.println("no task here");            }        }    }}