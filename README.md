# **Dead Giveaway**

*Formerly, H.awt.Lead*

## Description


## Downloads

[DeadGiveawayServer_2.1.jar](files/DeadGiveawayServer_2.1.jar)

[DeadGiveawayClient_2.1.jar](files/DeadGiveawayClient_2.1.jar)

## Source Instructions

The folder `DeadGiveaway` is a netbeans project with all the source files, so if you wish to look around the source I'd recommend using netbeans.

## User instructions

### Installation Instructions

#### Starting a server

1. Download the [Dead Giveaway Server](files/DeadGiveawayServer_2.1.jar)
2. Run this command in the folder containing the jar file
```
java -jar DeadGiveawayServer_2.1.jar
```
3. Forward the **TCP Port 5557** if you wish to play with friends on another network.

#### Connecting a client

1. Download the [Dead Giveaway Client](files/DeadGiveawayClient_2.1.jar)
2. Run this command in the folder containing the jar file
```
java -jar DeadGiveawayClient_2.1.jar -h <IP_ADDRESS>
```
Where `<IP_ADDRESS>` is the ip adress of the computer running the server, or `localhost` if it's on the same computer.

### Gameplay Instructions

Upon initializing the game, the lobby screen will displayed. Enter your desired username and click play to join a game. Once the lobby is filled or the timer has run out, the game's GUI is initialized and the game begins.

#### Gameplay

Plays pass to the left. On your turn, you:

1. Click on the Action card Draw Pile and add a card to your hand.
2. Click on one of your two Action cards to play it (see *Playing Action Cards* below).
3. Note any cards shown to you and feel free to keep notes on your notesheet, such as what you see happening during other players' turns.
4. Unless you are ready to make an Accusation (see *Making an Accusation*), your turn is over and you must press the "End Turn" button. 

#### Playing Action Cards
There are six different types of Action cards. To play an Action card (except a Private Tip), simply select the card from your hand when you are able to do so.

1 Make a Suggestion (from any destination)
  * Decide which Destination will be part of your Suggestion (see *Making a Suggestion*). If the destination is different than the one you are in, your current Destination Marker will be exchanged for the desired destination. If another player has this Destination Marker, that player takes yours in return.

2 Make a Suggestion (from current destination or move destination)
  * You may only make a Suggestion from the destination whose Destination Marker is currently before you OR you can exchange the Destination Marker for another - but if you do so, you end your turn without making a Suggestion (see *Making a Suggestion*).

3 Snoop
  * Select an opponent to whom you wish to snoop on. Pick any one card and look at it.

4 All Snoop
  * Each player will snoop on the player indicated on the card. Each player then snoops on the hand offered to him.

5 Super Sleuth
  * Each player must show you one Clue card with the specified feature. (For example, *"Show me a destination in the south."*) If a player doesn't have a card of the kind specified, he shows nothing.

6 Private Tip
  * A selected player shows one Clue card of the type requested (or all cards held of this type, if so instructed by the Private Tip card). If the opponent has no cards of the type requested, he shows nothing.

#### Ending Your Turn
After you have played an Action card, you may either make an accusation (see \Making an Accusation\) or end your turn.

#### Making a Suggestion
To make a Suggestion, name a Suspect, Vehicle and the Destination whose marker is before you. Example: "I suggest that Aphrodite is traveling in Apollo's Chariot, to Hades Underworld."

*Making Suggestions throughout the game will help you to determine - by process of elimination - which three cards comprise the SOLUTION*

#### Proving a Suggestion True or False
As soon as you make a Suggestion, your opponents, in turn, try to prove it false. The first to try is the player on your left. That player looks at his cards to see if one of the three cards you just named is there. If so, he must show it to you and no one else. If the player has more than one of the cards named, he selects just one to show you. If that opponent has none of the cards that you named, then the chance to prove your Suggestion false passes, in turn, to the next player on the left.

As soon as any opponent shows you one of the cards you named, it is proof that this card cannot be part of the solution. If no one is able to prove your Suggestion false, you may either end your turn or make an Accusation.

Note: When you make a Suggestion, you may, if you wish, name one or more of the cards that you hold in your hand. This can help to pinpoint information or to mislead your opponents.

#### Making an Accusation
When you think you have figured out which three cards comprise the Solution, you should make an Accusation on your turn. You may do so after you play an Action card, or if you are convinced you know the Solution, you may make an Accusation without playing an Action card. Do so by naming the three elements you believe will be in found in the Solution. Then, look at the three Clue cards of the Solution.

Note: In a Suggestion, the Destination you name must be the same as the Destination Marker before you. In an Accusation, you may name any destination. Also remember that you may make only one Accusation per game.

#### Winning
If your Accusation is correct (that is, the three elements you named are in the Solution), claim your victory. Great detective work! You win!

If your Accusation is incorrect...

* You remain in the game but make no further plays, so you cannot win.
* You continue to try to prove your opponents' Suggestions false.
* Opponents may still exchange Destination markers with you as usual. 

*User Instructions written by Bradley Johnson, edited by Alex Saalberg*
