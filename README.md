# Game-replay-based-strategy-prediction-and-build-order-adaptation
This is a research project to develop AI agent for RTS games that can predict the strategy of opponent players and make decision on the change of their strategy by analyzing user replay data. We proposed a feature-expanded decision tree to enhance the ability to predict the strategy of opponent players. It Showed that proposed method can enhance winning probability more than 10% through simulation of the game.

# Overview of Method
<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/pic1.png" width="400" align ="left">
<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/pic2.png" width="300">

#Result
* Expected win (0~1) from the prediction accuracy (Rotation Forest + FBDT) and the winning ratio of each strategy (P vs. P, YGOSU.com data) [H.-C. Cho et al., 2013]

<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/result.png" width="350">

#Conclusion
* The new framework for build order prediction and adaptation 
* Strategy prediction  with realistic data and introduction of feature expansion.
* Strategy adaptation by combining the statistical winning ratio and prediction accuracy 
* We need to improve accuracy of prediction with fog-of-war and apply this research to AI bots.

 

#Media
* Presentation [H.-C. Cho et al., 2013]
 

[<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/CIG2013_Presentstion.jpg" width="450">](https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/CIG2013_Presentstion.jpg)

* Poster [H.-C. Cho and K.-J. Kim, 2013]

<img src="https://github.com/chc2212/Game-replay-based-strategy-prediction-and-build-order-adaptation/blob/master/poster.jpg" width="550">

#References
* [**H.-C. Cho**, K.-J. Kim and S.-B. Cho, “Replay-based strategy prediction and build order adaptation for StarCraft AI bots,” IEEE Conference on Computational Intelligence in Games, 2013.](http://cilab.sejong.ac.kr/home/lib/exe/fetch.php?media=public:paper:cig_2013.pdf)

* [**H.-C. Cho**, and K.-J. Kim, “Comparison of human and AI bots in StarCraft with replay data mining,” IEEE Conference on Computational Intelligence in Games, 2013.](http://cilab.sejong.ac.kr/home/lib/exe/fetch.php?media=public:paper:cig_2013_cho.pdf)
