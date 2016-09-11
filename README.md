# Game-replay-based-strategy-prediction-and-build-order-adaptation
This is research project to develop AI agent for RTS games that can predict the strategy of opponent players and make decision on the change of their strategy by analyzing user replay data. We proposed a feature-expanded decision tree to enhance the ability to predict the strategy of opponent players. It Showed that proposed method can enhance winning probability more than 10% through simulation of the game.

###Abstract ("Replay-based strategy prediction and build order adaptation for StarCraft AI bots,” IEEE Conference on Computational Intelligence in Games")

StarCraft is a real-time strategy (RTS) game and the choice of strategy has big impact on the final results of the game. For human players, the most important thing in the game is to select the strategy in the early stage of the game. Also, it is important to recognize the opponent’s strategy as quickly as possible. Because of the “fog-of-war” in the game, the player should send a scouting unit to opponent’s hidden territory and the player predicts the types of strategy from the partially observed information. Usually, expert players are familiar with the relationships between two build orders and they can change the current build order if his choice is not strong to the opponent’s strategy.  However, players in AI competitions show quite different behaviors compared to the human leagues. For example, they usually have a pre-selected build order and rarely change their order during the game. In fact, the computer players have little interest in recognizing opponent’s strategy and scouting units are used in a limited manner. The reason is that the implementation of scouting behavior and the change of build order from the scouting vision is not a trivial problem. In this paper, we propose to use replays to predict the strategy of players and make decision on the change of build orders. Experimental results on the public replay files show that the proposed method predicts opponent’s strategy accurately and increases the chance of winning in the game. 

# Overview of Method
<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/pic1.png" width="400" align ="left">
<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/pic2.png" width="300">

#Media


#Reference
* [**H.-C. Cho**, K.-J. Kim and S.-B. Cho, “Replay-based strategy prediction and build order adaptation for StarCraft AI bots,” IEEE Conference on Computational Intelligence in Games, 2013.](http://cilab.sejong.ac.kr/home/lib/exe/fetch.php?media=public:paper:cig_2013.pdf)

* [**H.-C. Cho**, and K.-J. Kim, “Comparison of human and AI bots in StarCraft with replay data mining,” IEEE Conference on Computational Intelligence in Games, 2013.](http://cilab.sejong.ac.kr/home/lib/exe/fetch.php?media=public:paper:cig_2013_cho.pdf)
