package gallager.game

import gallager.util.Vec2

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.io.File

import scala.collection.mutable.Buffer
import scala.collection.mutable.Queue
import scala.swing.Panel
import scala.swing.Swing
import scala.swing.event.Key
import scala.swing.event.KeyPressed
import scala.swing.event.KeyReleased
import scala.util.Random
import javax.imageio.ImageIO
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// A class which contains the bulk of the game's logic. SpaceGamePanel defines and instantiates the game's assets, animations, and the majority of its mechanics.
class SpaceGamePanel extends Panel {
  //Game Settings
  var score = 0
  var lives = 3
  val width = 800
  val height = 600
  preferredSize = new Dimension(width, height)
  val startPos = new Vec2(300, 500)
  val startPos2 = new Vec2(500, 500)

  //Player 1 Images
  val specImage = ImageIO.read(new File("sprites/playerOne/specGallager.png"))
  val specImage2 = ImageIO.read(new File("sprites/playerOne/specGallager2.png"))
  val playerImage = ImageIO.read(new File("sprites/playerOne/gallager.png"))
  val playerImage2 = ImageIO.read(new File("sprites/playerOne/gallager2.png"))
  val playerImage3 = ImageIO.read(new File("sprites/playerOne/gallager3.png"))
  val playerImage4 = ImageIO.read(new File("sprites/playerOne/gallager4.png"))
  val playerImage5 = ImageIO.read(new File("sprites/playerOne/gallager5.png"))
  val playerImage6 = ImageIO.read(new File("sprites/playerOne/gallager6.png"))
  val playerLeft = ImageIO.read(new File("sprites/playerOne/gallagerLeft.png"))
  val playerLeft2 = ImageIO.read(new File("sprites/playerOne/gallagerLeft2.png"))
  val playerRight = ImageIO.read(new File("sprites/playerOne/gallagerRight.png"))
  val playerRight2 = ImageIO.read(new File("sprites/playerOne/gallagerRight2.png"))
  val lewBod = ImageIO.read(new File("sprites/playerOne/gallagerBod.png"))
  val playerUp = ImageIO.read(new File("sprites/playerOne/gallagerUp.png"))
  val playerUp2 = ImageIO.read(new File("sprites/playerOne/gallagerUp2.png"))

  //Player 2 Images
  val player2Image = ImageIO.read(new File("sprites/playerTwo/p2.png"))
  val player2sub = ImageIO.read(new File("sprites/playerTwo/p2_1_2.png"))
  val player2Image2 = ImageIO.read(new File("sprites/playerTwo/p2_2.png"))
  val player2Image3 = ImageIO.read(new File("sprites/playerTwo/p2_3.png"))
  val specPlayer2Image = ImageIO.read(new File("sprites/playerTwo/specP2.png"))
  val specPlayer2Image2 = ImageIO.read(new File("sprites/playerTwo/specP2_2.png"))
  val fogBod = ImageIO.read(new File("sprites/playerTwo/p2Bod.png"))

  //Laser Images
  val bulletImage = ImageIO.read(new File("sprites/bullets/bullet.png"))
  val bullet2Image = ImageIO.read(new File("sprites/bullets/bullet2.png"))
  val specBullet = ImageIO.read(new File("sprites/bullets/specBullet.png"))
  val specBullet2 = ImageIO.read(new File("sprites/bullets/specBullet2.png"))

  //Robo-Hibbs Images
  val roboHibbsPic = ImageIO.read(new File("sprites/roboHibbs/RoboHibbs.png"))
  val roboHibbsPic2 = ImageIO.read(new File("sprites/roboHibbs/RoboHibbs2.png"))
  val roboHibbsPic3 = ImageIO.read(new File("sprites/roboHibbs/RoboHibbs3.png"))
  val roboHibbsPic4 = ImageIO.read(new File("sprites/roboHibbs/RoboHibbs4.png"))
  val hibbsShot = ImageIO.read(new File("sprites/roboHibbs/hibbsShot.png"))
  val hibbsLaser = ImageIO.read(new File("sprites/roboHibbs/roboShot.png"))
  val hDeathArray = Array[BufferedImage](ImageIO.read(new File("sprites/roboHibbs/hibbsDeath1.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath2.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath3.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath4.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath5.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath6.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath7.png")), ImageIO.read(new File("sprites/roboHibbs/hibbsDeath8.png")))

  //Splash Art and Background Images
  val splashArt = ImageIO.read(new File("sprites/backgrounds/splash.png"))
  val background1 = ImageIO.read(new File("sprites/backgrounds/background1.png"))
  val background2 = ImageIO.read(new File("sprites/backgrounds/background2.png"))
  val background3 = ImageIO.read(new File("sprites/backgrounds/background3.png"))
  var back = new Background(background1, background2, background3, 5, 10, 2)


  //Enemy Images
  val enemyImage = ImageIO.read(new File("sprites/enemy/enemy.png"))
  val explosionAnimation = Array[BufferedImage](ImageIO.read(new File("sprites/enemy/eDeath1.png")), ImageIO.read(new File("sprites/enemy/eDeath2.png")), ImageIO.read(new File("sprites/enemy/eDeath3.png")), ImageIO.read(new File("sprites/enemy/eDeath4.png")), ImageIO.read(new File("sprites/enemy/eDeath5.png")), ImageIO.read(new File("sprites/enemy/eDeath6.png")), ImageIO.read(new File("sprites/enemy/eDeath7.png")), ImageIO.read(new File("sprites/enemy/eDeath8.png")))
  var explosionBuff = Buffer[Animation]()

  //PowerUp Images
  val sShot1 = ImageIO.read(new File("sprites/powerUps/powerUp.png"))
  val sShot2 = ImageIO.read(new File("sprites/powerUps/powerUp2.png"))
  val prot = ImageIO.read(new File("sprites/powerUps/protein.png"))
  val prot2 = ImageIO.read(new File("sprites/powerUps/protein2.png"))

  //Sounds
  var bg = new File("sounds/gallager+.wav")
  var bgMusic = AudioSystem.getAudioInputStream(bg)
  var bgClip = AudioSystem.getClip()
  bgClip.open(bgMusic)

  var dm = new File("sounds/death.wav")
  var dmMusic = AudioSystem.getAudioInputStream(dm)
  var dmClip = AudioSystem.getClip()
  dmClip.open(dmMusic)

  var sm = new File("sounds/splashMusic.wav")
  var smMusic = AudioSystem.getAudioInputStream(sm)
  var smClip = AudioSystem.getClip()
  smClip.open(smMusic)

  var rb = new File("sounds/roboHibbs.wav")
  var rbMusic = AudioSystem.getAudioInputStream(rb)
  var rbClip = AudioSystem.getClip()
  rbClip.open(rbMusic)

  var jn = new File("sounds/join.wav")
  var jnMusic = AudioSystem.getAudioInputStream(jn)
  var jnClip = AudioSystem.getClip()
  jnClip.open(jnMusic)

  var ls = new File("sounds/las.wav")
  var lsMusic = AudioSystem.getAudioInputStream(ls)
  var lsClip = AudioSystem.getClip()
  lsClip.open(lsMusic)

  var lb = new File("sounds/lBit.wav")
  var lbMusic = AudioSystem.getAudioInputStream(lb)
  var lbClip = AudioSystem.getClip()
  lbClip.open(lbMusic)

  var ex = new File("sounds/explode.wav")
  var exMusic = AudioSystem.getAudioInputStream(ex)
  var exClip = AudioSystem.getClip()
  exClip.open(exMusic)

  var hl = new File("sounds/hibbsLas.wav")
  var hlMusic = AudioSystem.getAudioInputStream(hl)
  var hlClip = AudioSystem.getClip()
  hlClip.open(hlMusic)

  var pm = new File("sounds/scalaShot.wav")
  var pmMusic = AudioSystem.getAudioInputStream(pm)
  var pmClip = AudioSystem.getClip()
  pmClip.open(pmMusic)

  var bl = new File("sounds/bigLas.wav")
  var blMusic = AudioSystem.getAudioInputStream(bl)
  var blClip = AudioSystem.getClip()
  blClip.open(blMusic)

  var powerUp = new PowerUp(sShot1, new Vec2(width, 500))
  var invince = new PowerUp(prot, new Vec2(width, 500))
  var power = false
  var power2 = false
  var scalaShot = false
  var specNum = 0
  var shots = 0
  var shooting = false
  var shooting2 = false
  var powerBod = false
  var bodTime = 0

  //Variables
  var player = new Player(playerImage, startPos, bulletImage)
  var player2 = new Player(player2Image, startPos2, bullet2Image)
  var specShot = new PowerLaser(specBullet, startPos)
  var specShot2 = new PowerLaser(specBullet2, startPos2)
  var enemies = new EnemySwarm(4, 8)
  var hibbsPos = new Vec2(400, 100)
  var mhibbs = new RoboHibbs(roboHibbsPic, new Vec2(0, 5000), hibbsShot)
  var playerBullets = Buffer[Bullet]()
  var enemyBullets = Buffer[Bullet]()
  var counter = 0
  var counter2 = 0
  var hCounter = 0
  var gameOver = false
  var rest = false
  var theHibbs = false
  var hibbsHealth = 4
  var healthMeter = 396
  var blast = false
  var hibbsAdvanced = false
  var pHealth = 3
  var p2Health = 3
  var roboMoves = Queue[scala.collection.mutable.Set[String]]()
  var title = true
  var currImage = explosionAnimation(0)
  var aCounter = 10
  var flame = true
  var coinCounter = 60
  var coin = true
  var speed = 30
  var partyMode = false
  var player2Bool = false

  var activeKeys = scala.collection.mutable.Set[String](null)

  var tracker = 0

  //Controls the rainbow effect that occurs after getting a powerUp.
  var hue = 0f

  def stepColor(): Color = {
    hue += 0.01f
    if (hue > 1f) hue = 0f
    Color.getHSBColor(hue, 0.8f, 0.8f)
  }

  /*Controls the alternating images used for the playerOne sprites. 
  This was prior to having much experience dealing with changing images in game,
  So the approach is a bit naive*/
  var pTracker = 0

  def pAnimation() {
    if (pHealth == 3) {
      if (pTracker < 5) {
        player = new Player(playerImage4, player.pos, bulletImage)
        pTracker += 1
      }
      if (pTracker >= 5 && pTracker < 10) {
        player = new Player(playerImage, player.pos, bulletImage)
        pTracker += 1
      }
      if (pTracker >= 10) {
        pTracker = 0
      }
    }
    if (pHealth == 2) {
      if (pTracker < 5) {
        player = new Player(playerImage5, player.pos, bulletImage)
        pTracker += 1
      }
      if (pTracker >= 5 && pTracker < 10) {
        player = new Player(playerImage2, player.pos, bulletImage)
        pTracker += 1
      }
      if (pTracker >= 10) {
        pTracker = 0
      }
    }
    if (pHealth == 1) {
      if (pTracker < 5) {
        player = new Player(playerImage6, player.pos, bulletImage)
        pTracker += 1
      }
      if (pTracker >= 5 && pTracker < 10) {
        player = new Player(playerImage3, player.pos, bulletImage)
        pTracker += 1
      }
      if (pTracker >= 10) {
        pTracker = 0
      }
    }
  }

  /* A function which deals with the movement and shooting of the players. 
  The function reads from a set of direction strings in order to allow for smooth, diagonal movement */
  def playerMove() {
    if (activeKeys.contains("left")) {
      if (!(player.pos.x <= 24)) {
        player.moveLeft()
        specShot.moveLeft
      }
    }
    if (activeKeys.contains("right")) {
      if (!(player.pos.x >= width - 25)) {
        player.moveRight()
        specShot.moveRight
      }
    }
    if (activeKeys.contains("up")) {
      if (!(player.pos.y <= 24)) {
        player.moveUp()
        specShot.moveUp
      }
    }
    if (activeKeys.contains("down")) {
      if (!(player.pos.y >= height - 24)) {
        player.moveDown()
        specShot.moveDown
      }
    }
    if (activeKeys.contains("space") && counter == 0) {
      if (scalaShot && shots > 0) {
        shots -= 10
        shooting = true
      } else {
        shooting = false
        scalaShot = false
        lsClip.setFramePosition(0)
        lsClip.start
        playerBullets += player.shoot
        counter += 10
      }
    }
    if (!(activeKeys.contains("space"))) {
      if (scalaShot) {
        shooting = false
      }
    }
    if (player2Bool) {
      if (activeKeys.contains("left2")) {
        if (!(player2.pos.x <= 24)) {
          player2.moveLeft()
          specShot2.moveLeft()
        }
      }
      if (activeKeys.contains("right2")) {
        if (!(player2.pos.x >= width - 25)) {
          player2.moveRight()
          specShot2.moveRight
        }
      }
      if (activeKeys.contains("up2")) {
        if (!(player2.pos.y <= 27)) {
          player2.moveUp()
          specShot2.moveUp()
        }
      }
      if (activeKeys.contains("down2")) {
        if (!(player2.pos.y >= height - 24)) {
          player2.moveDown()
          specShot2.moveDown()
        }
      }
      if (activeKeys.contains("space2") && counter2 == 0) {
        if (scalaShot && shots > 0) {
          shots -= 10
          shooting2 = true
        } else {
          shooting2 = false
          scalaShot = false
          lsClip.setFramePosition(0)
          lsClip.start
          playerBullets += player2.shoot
          counter2 += 10
        }
      }
      if (!(activeKeys.contains("space2"))) {
        if (scalaShot) {
          shooting2 = false
        }
      }
    }
  }

  // A function which, again, relies on a tracker variable which moves the enemy swarm as it increases
  def moveEnemies() {
    if (tracker < 5) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(-5, 0))
      }
      tracker += 1
    }
    if (tracker >= 5 && tracker < 10) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(0, 5))
      }
      tracker += 1
    }
    if (tracker >= 10 && tracker < 15) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(-5, 0))
      }
      tracker += 1
    }
    if (tracker >= 15 && tracker < 20) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(0, -5))
      }
      tracker += 1
    }
    if (tracker >= 20 && tracker < 25) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(-5, 0))
      }
      tracker += 1
    }
    if (tracker >= 25 && tracker < 30) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(0, 5))
      }
      tracker += 1
    }
    if (tracker >= 30 && tracker < 35) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(5, 0))
      }
      tracker += 1
    }
    if (tracker >= 35 && tracker < 40) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(0, -5))
      }
      tracker += 1
    }
    if (tracker >= 40 && tracker < 45) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(5, 0))
      }
      tracker += 1
    }
    if (tracker >= 45 && tracker < 50) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(0, 5))
      }
      tracker += 1
    }
    if (tracker >= 50 && tracker < 55) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(5, 0))
      }
      tracker += 1
    }
    if (tracker >= 55 && tracker < 60) {
      for (i <- enemies.enemies) {
        i.move(new Vec2(0, -5))
      }
      tracker += 1
    }
    if (tracker >= 60) {
      tracker = 0
    }
  }

  //A function which causes the boss character to have delayed, mirrored movement
  def mirrorMove() {
    if (roboMoves.length > 30) {
      val dq: scala.collection.mutable.Set[String] = roboMoves.dequeue()
      if (theHibbs) {
        if (dq.contains("left")) {
          mhibbs.mirrorLeft
        }
        if (dq.contains("right")) {
          mhibbs.mirrorRight
        }
        if (dq.contains("up")) {
          mhibbs.mirrorUp
        }
        if (dq.contains("down")) {
          mhibbs.mirrorDown
        }
        if (dq.contains("space") && hCounter == 0) {
          enemyBullets += mhibbs.shoot
          hCounter += 10
        }
      }
    }
  }

  //A function used to respawn playerOne
  def respawn() {
    specShot = new PowerLaser(specBullet, startPos)
    player = new Player(playerImage, startPos, bulletImage)
    pHealth = 3
  }

  //A function used to respawn playerTwo
  def respawn2() {
    specShot2 = new PowerLaser(specBullet2, startPos2)
    player2 = new Player(player2Image, startPos2, bullet2Image)
    p2Health = 3
  }

  //A function which randomly spawns the scala-shot powerUp
  def powerSpawn() {
    if (power == false) {
      if (Random.nextInt(600) == 15) {
        power = true
        powerUp = new PowerUp(sShot1, new Vec2(850, 400 + Random.nextInt(150)))
      }
    }
  }

  //A function which randomly spawns the protein powder, invicibility powerUp
  def invinceSpawn() {
    if (power2 == false) {
      if (Random.nextInt(600) == 8) {
        power2 = true
        invince = new PowerUp(prot, new Vec2(850, 500))
      }
    }
  }

  //A function which spawns the boss character
  def roboSpawn() {
    if (!theHibbs) {
      if (Random.nextInt(500) == 25) {
        rbClip.setFramePosition(0)
        rbClip.start
        mhibbs = new RoboHibbs(roboHibbsPic, new Vec2(player.pos.x, 100), hibbsShot)
        theHibbs = true
        blast = false
        hibbsAdvanced = false
        mhibbs.hibbsAdvanced = false
      }
    }
  }

  //A function which changes the sprite of the boss character depending on its health
  def hibbsPics() {
    if (hibbsHealth == 3) {
      mhibbs = new RoboHibbs(roboHibbsPic2, mhibbs.pos, hibbsShot)
    }
    if (hibbsHealth == 2) {
      mhibbs = new RoboHibbs(roboHibbsPic3, mhibbs.pos, hibbsShot)
    }
    if (hibbsHealth == 1)
      mhibbs = new RoboHibbs(roboHibbsPic4, mhibbs.pos, hibbsShot)
  }

  //A function which changes the sprite of playerOne depending on his health
  def lifePics() {
    if (pHealth == 2) {
      player = new Player(playerImage2, player.pos, bulletImage)
    }
    if (pHealth == 1) {
      player = new Player(playerImage3, player.pos, bulletImage)
    }
  }

  //A function which causes enemies to randomly shoot
  def randomShoots() {
    if (Random.nextDouble < 0.075) {
      enemyBullets += enemies.shoot
    }
  }

  //A function which moves player and enemy bullets
  def moveShots() {
    for (i <- 0 to playerBullets.length - 1) {
      playerBullets(i).timeStep()
    }
    if (enemyBullets.length != 0) {
      for (i <- 0 to enemyBullets.length - 1) {
        enemyBullets(i).timeStep()
      }
    }
  }

  //A function containing much of the game logic and intersection mechanics
  def mainFunc() {
    //First checks if the title variable is true. If it is, the splash screen will displayed, if not, the game
    if (!title) {
      //Spawns new swarm if the current one is empty
      if (enemies.enemies.isEmpty) {
        enemies = new EnemySwarm(4, 8)
        tracker = 0
      }

      //Plays laser shooting sound if a player is shooting
      if (shooting) {
        blClip.loop(Clip.LOOP_CONTINUOUSLY)
      } else blClip.stop

      randomShoots()

      playerMove()

      //Random chance to play one of two sound clips which are inside jokes to the class I took
      if (theHibbs) {
        if (Random.nextInt(400) == 20) {
          lbClip.setFramePosition(0)
          lbClip.start
        } else if (Random.nextInt(400) == 10) {
          jnClip.setFramePosition(0)
          jnClip.start
        }
      }

      roboMoves.enqueue(activeKeys.clone())

      mirrorMove()

      powerSpawn()

      invinceSpawn()

      moveShots()

      //Gives the players body-builder bodies if invincibility powerUp is activated
      if (powerBod) {
        player = new Player(lewBod, player.pos, bulletImage)
        player2 = new Player(fogBod, player2.pos, bullet2Image)
      }

      //If the invincibility power is activated, its duration will increase by two until it hits 0
      if (powerBod) {
        bodTime -= 2
      }

      //Upon hitting 0, the player's default state is reset
      if (bodTime <= 0) {
        powerBod = false
        partyMode = false
      }

      //These counters limit the rate of player fire and count down to 0 to enable a player to fire again
      if (counter > 0) counter -= 1
      if (counter2 > 0) counter2 -= 1
      if (hCounter > 0) hCounter -= 1

      //Sets to be used to temporarily store the bullets, enemies, and explosion animations to be removed upon timer completion
      var playerBulletRemove = Set[Bullet]()
      var enemyBulletRemove = Set[Bullet]()
      var explosionRemove = Set[Animation]()
      var enemyRemove = Set[Enemy]()

      //Player bullets destroy enemies
      for (i <- enemies.enemies) {
        for (j <- 0 until playerBullets.length) {
          if (playerBullets(j).intersects(i)) {
            exClip.setFramePosition(0)
            exClip.start
            enemyRemove += i
            explosionBuff += new Animation(explosionAnimation, 0, i.pos)
            playerBulletRemove += playerBullets(j)
            score += 100
          }
        }
      }

      if (!powerBod) {
      //PlayerOne's scala-shot powerUp destroys enemies 
        for (i <- enemies.enemies) {
          if (shooting) {
            if (specShot.intersects(i)) {
              exClip.setFramePosition(0)
              exClip.start
              enemyRemove += i
              explosionBuff += new Animation(explosionAnimation, 0, i.pos)
              score += 100
            }
          }
        }

      //PlayerOne's scala-shot powerUp damages boss character
        if (shooting) {
          if (specShot.intersects(mhibbs)) {
            exClip.setFramePosition(0)
            exClip.start
            hibbsHealth -= 1
            healthMeter -= 99
          }
        }

      //PlayerOne's scala-shot destroys enemy bullets
        for (i <- 0 until enemyBullets.length) {
          if (shooting) {
            if (specShot.intersects(enemyBullets(i))) {
              enemyBulletRemove += enemyBullets(i)
            }
          }
        }
      }

      //PlayerTwo's scala-shot powerUp destroys enemies 
      if (!powerBod) {
        for (i <- enemies.enemies) {
          if (shooting2) {
            if (specShot2.intersects(i)) {
              exClip.setFramePosition(0)
              exClip.start
              enemyRemove += i
              explosionBuff += new Animation(explosionAnimation, 0, i.pos)
              score += 100
            }
          }
        }

      //PlayerTwo's scala-shot powerUp damages boss character
      if (shooting2) {
        if (specShot2.intersects(mhibbs)) {
          exClip.setFramePosition(0)
          exClip.start
           hibbsHealth -= 1
           healthMeter -= 99
         }
       }

      //PlayerTwo's scala-shot powerUp destroys bullets
        for (i <- 0 until enemyBullets.length) {
          if (shooting2) {
            if (specShot2.intersects(enemyBullets(i))) {
              enemyBulletRemove += enemyBullets(i)
            }
          }
        }
      }

      //Player bullets damage boss character
      for (i <- 0 until playerBullets.length) {
        if (playerBullets(i).intersects(mhibbs)) {
          exClip.setFramePosition(0)
          exClip.start
          hibbsHealth -= 1
          healthMeter -= 99
          playerBulletRemove += playerBullets(i)
        }
      }

      //Puts a laser powerUp in the players' inventory if either player intersect its sprite
      if (player.intersects(powerUp) || player2.intersects(powerUp)) {
        specNum += 1
        power = false
        powerUp = new PowerUp(sShot1, new Vec2(2000, 500))
        pmClip.setFramePosition(0)
        pmClip.start
      }

      //Activates invincibility powerUp if either player intersects its sprite
      if (player.intersects(invince) || player2.intersects(invince)) {
        power2 = false
        invince = new PowerUp(prot, new Vec2(2000, 500))
        lsClip.setFramePosition(0)
        lsClip.start
        powerBod = true
        bodTime = 400
        partyMode = true
      }

      /* Flame is a variable which constantly toggles to true and false which I used to add the flames to the players' ears as they move.
      I reference the variable several times for use in quick, 2-frame animations */
      if (aCounter > 5) {
        flame = true
        aCounter -= 1
      }
      if (aCounter <= 5 && aCounter > 0) {
        flame = false
        aCounter -= 1
      }
      if (aCounter == 0) {
        aCounter = 10
      }

      // Causes the players to blink if the scala-shot powerUp is activated.
      if (!powerBod) {
        if (scalaShot) {
          if (flame) {
            player = new Player(specImage, player.pos, bulletImage)
            player2 = new Player(specPlayer2Image, player2.pos, bullet2Image)
          }
          if (!flame) {
            player = new Player(specImage2, player.pos, bulletImage)
            player2 = new Player(specPlayer2Image2, player2.pos, bullet2Image)
          }
        }

      //Toggles PowerUp Animations
        if (flame) {
          powerUp = new PowerUp(sShot1, powerUp.pos)
        }

        if (!flame) {
          powerUp = new PowerUp(sShot2, powerUp.pos)
        }

        if (flame) {
          invince = new PowerUp(prot, invince.pos)
        }

        if (!flame) {
          invince = new PowerUp(prot2, invince.pos)
        }

      //Changes the picture displayed as playerTwo takes damage
        if (!scalaShot) {
          if (p2Health == 3) {
            player2 = new Player(player2Image, player2.pos, bullet2Image)
          }
          if (p2Health == 2) {
            player2 = new Player(player2Image2, player2.pos, bullet2Image)
          }
          if (p2Health == 1) {
            player2 = new Player(player2Image3, player2.pos, bullet2Image)
          }
        }
      }

      //Kills boss character if its heatlh reaches 0
      if (hibbsHealth <= 0) {
        theHibbs = false
        exClip.setFramePosition(0)
        exClip.start
        explosionBuff += new Animation(hDeathArray, 0, mhibbs.pos)
        mhibbs = new RoboHibbs(roboHibbsPic, new Vec2(-100, 5000), hibbsShot)
        hibbsHealth = 4
        healthMeter = 396
        score += 1000
        hibbsAdvanced = false
        mhibbs.hibbsAdvanced = false
        blast = false
      }

      //Changes fire pattern if boss character's health is below 2'
      if (hibbsHealth <= 2) {
        hibbsAdvanced = true
        mhibbs.hibbsAdvanced = true
        if (hibbsAdvanced == true) {
          if (hibbsHealth == 2) {
            mhibbs = new RoboHibbs(roboHibbsPic3, mhibbs.pos, hibbsLaser)
          }
          if (hibbsHealth == 1) {
            mhibbs = new RoboHibbs(roboHibbsPic4, mhibbs.pos, hibbsLaser)
          }
        }
      }

      //Gives boss character a large laser when toggled
      if (theHibbs == true) {
        if (hibbsAdvanced) {
          if (blast == true) {
            enemyBullets += mhibbs.shoot
          }
        }
      }

      //Enemy bullets damage playerOne
      if (!powerBod) {
        for (i <- 0 until enemyBullets.length) {
          if (enemyBullets(i).intersects(player)) {
            enemyBulletRemove += enemyBullets(i)
            pHealth -= 1
            lifePics()
            exClip.setFramePosition(0)
            exClip.start
          }
        }
      }

      //Enemy bullets damage playerTwo
      if (!powerBod) {
        if (player2Bool) {
          for (i <- 0 until enemyBullets.length) {
            if (enemyBullets(i).intersects(player2)) {
              enemyBulletRemove += enemyBullets(i)
              p2Health -= 1
              lifePics()
              exClip.setFramePosition(0)
              exClip.start
            }
          }
        }
      }

      //Removes player bullets off screen
      for (i <- 0 until playerBullets.length) {
        if (playerBullets(i).pos.y < 0) {
          playerBulletRemove += playerBullets(i)
        }
      }

      //Removes enemy bullets off screen
      for (i <- 0 until enemyBullets.length) {
        if (enemyBullets(i).pos.y > height) {
          enemyBulletRemove += enemyBullets(i)
        }
      }

      //Bullets intersecting eachother get erased
      for (i <- 0 until enemyBullets.length) {
        for (j <- 0 until playerBullets.length) {
          if (enemyBullets(i).intersects(playerBullets(j))) {
            enemyBulletRemove += enemyBullets(i)
            playerBulletRemove += playerBullets(j)
          }
        }
      }

      //Instantly kills playerOne if it intersect with enemy
      if (!powerBod) {
        for (i <- enemies.enemies) {
          if (player.intersects(i) || player.intersects(mhibbs)) {
            enemyRemove += i
            lives -= 1
            explosionBuff += new Animation(explosionAnimation, 0, player.pos)
            respawn()
            explosionBuff += new Animation(explosionAnimation, 0, i.pos)
            exClip.setFramePosition(0)
            exClip.start
          }
        }
      }

      //Instantly kills playerTwo if it intersect with enemy
      if (!powerBod) {
        for (i <- enemies.enemies) {
          if (player2.intersects(i) || player2.intersects(mhibbs)) {
            enemyRemove += i
            lives -= 1
            explosionBuff += new Animation(explosionAnimation, 0, player.pos)
            respawn()
            explosionBuff += new Animation(explosionAnimation, 0, i.pos)
            exClip.setFramePosition(0)
            exClip.start
          }
        }
      }

      //Instantly kills enemy or boss character if playersOne intersects with them with invincibility active
      if (powerBod) {
        for (i <- enemies.enemies) {
          if (player.intersects(i)) {
            enemyRemove += i
            explosionBuff += new Animation(explosionAnimation, 0, i.pos)
            score += 100
            exClip.setFramePosition(0)
            exClip.start
          }
        }
      }

      if (powerBod) {
        if (player.intersects(mhibbs)) {
          theHibbs = false
          exClip.setFramePosition(0)
          exClip.start
          explosionBuff += new Animation(hDeathArray, 0, mhibbs.pos)
          mhibbs = new RoboHibbs(roboHibbsPic, new Vec2(-100, 5000), hibbsShot)
          hibbsHealth = 4
          healthMeter = 396
          score += 1000
          hibbsAdvanced = false
          mhibbs.hibbsAdvanced = false
          blast = false
        }
      }

      //Instantly kills enemy or boss character if playersTwo intersects with them with invincibility active
      if (powerBod) {
        for (i <- enemies.enemies) {
          if (player2.intersects(i)) {
            enemyRemove += i
            explosionBuff += new Animation(explosionAnimation, 0, i.pos)
            score += 100
            exClip.setFramePosition(0)
            exClip.start
          }
        }

        if (player2.intersects(mhibbs)) {
          theHibbs = false
          exClip.setFramePosition(0)
          exClip.start
          explosionBuff += new Animation(hDeathArray, 0, mhibbs.pos)
          mhibbs = new RoboHibbs(roboHibbsPic, new Vec2(-100, 5000), hibbsShot)
          hibbsHealth = 4
          healthMeter = 396
          score += 1000
          hibbsAdvanced = false
          mhibbs.hibbsAdvanced = false
          blast = false
        }
      }

      //Removes Explosions
      for (i <- explosionBuff) {
        if (i.index == 7) {
          explosionRemove += i
        }
      }

      //Removes all bullets, enemies, and explosion animations which have been added to the remove sets through intersection or completion
      playerBullets --= playerBulletRemove
      enemyBullets --= enemyBulletRemove
      enemies.enemies --= enemyRemove
      explosionBuff --= explosionRemove

      //Kills playerOne if its health drops below 0
      if (pHealth <= 0) {
        lives -= 1
        pHealth = 3
        explosionBuff += new Animation(explosionAnimation, 0, player.pos)
        respawn()
      }

      //Kills playerTwo if its health drops below 0
      if (p2Health <= 0) {
        lives -= 1
        p2Health = 3
        explosionBuff += new Animation(explosionAnimation, 0, player2.pos)
        respawn2()
      }

      //Redraws all sprites each frame
      this.repaint
      this.requestFocus
    }
  }

  //Timer which handles enemy movement
  val enemyMoveTimer = new javax.swing.Timer(125, Swing.ActionListener(e => {
    moveEnemies()
  }))

  //Timer which handles player Animation
  val pAnimator = new javax.swing.Timer(125, Swing.ActionListener(e => {
    if (!powerBod) {
      pAnimation()
    }
    hibbsPics()
  }))

  //Timer which randomly spawns boss character
  val hibbsTime = new javax.swing.Timer(30, Swing.ActionListener(e => {
    if (!title) {
      roboSpawn()
    }
  }))

  //Timer used for the 'Insert Coins' display on the main menu
  val coinTimer = new javax.swing.Timer(30, Swing.ActionListener(e => {
    if (coinCounter > 30) {
      coin = true
      coinCounter -= 1
    }
    if (coinCounter <= 30 && coinCounter > 0) {
      coin = false
      coinCounter -= 1
    }
    if (coinCounter == 0) {
      coinCounter = 60
    }

    repaint
  }))

  //Timer which calls everything listed in the main Function
  var mainTimer = new javax.swing.Timer(speed, Swing.ActionListener(e => {
    mainFunc()
  }))

  //Timer which checks if player has reached GameOver state
  val endCheck = new javax.swing.Timer(50, Swing.ActionListener(e => {
    if (lives <= 0) {
      gameOver = true
      bgClip.stop
      dmClip.loop(Clip.LOOP_CONTINUOUSLY)
      mainTimer.stop
      enemyMoveTimer.stop
      hibbsTime.stop
      this.repaint
      this.requestFocus
    }
  }))

  //Timer which causes boss character to fire laser every 3 seconds
  val blastTimer = new javax.swing.Timer(5000, Swing.ActionListener(e => {
    var i = 0
    if (!blast) {
      i = 1
    }
    if (blast) {
      i = 2
    }

    if (i == 1) {
      blast = true
    }

    if (i == 2) {
      blast = false
    }
  }))

  //Timer which checks to see if player has pressed restart, and then resets game
  val restart = new javax.swing.Timer(50, Swing.ActionListener(e => {
    if (rest == true) {
      lives = 3
      score = 0
      respawn()
      respawn2()
      enemies = new EnemySwarm(4, 8)
      playerBullets = Buffer[Bullet]()
      enemyBullets = Buffer[Bullet]()
      counter = 0
      counter2 = 0
      pHealth = 3
      gameOver = false
      dmClip.stop
      mhibbs = new RoboHibbs(roboHibbsPic, new Vec2(0, 5000), hibbsShot)
      tracker = 0
      rest = false
      theHibbs = false
      hibbsHealth = 4
      healthMeter = 396
      hibbsAdvanced = false
      blast = false
      title = true
      flame = true
      power = false
      power2 = false
      scalaShot = false
      specNum = 0
      shots = 0
      shooting = false
      partyMode = false
      player2Bool = false
      power = false
      power2 = false
      scalaShot = false
      specNum = 0
      shots = 0
      shooting = false
      shooting2 = false
      powerBod = false
      bodTime = 0
      mainTimer.start
      enemyMoveTimer.start
      endCheck.start
      hibbsTime.start
    }
  }))

  //Displays images
  override def paint(g: Graphics2D) {
    if (gameOver == true) {
      g.setPaint(Color.black)
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
      g.setPaint(Color.white)
      g.drawString("GAME OVER", 360, 200)
      g.drawString("FINAL SCORE: " + score, 340, 225)
      g.drawString("F: RESTART", 340, 250)
      g.drawString("Q: QUIT", 340, 275)
    } else {
      if (partyMode) {
        g.setPaint(stepColor)
      }
      if (!partyMode) {
        g.setPaint(Color.black)
      }
      g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
      back.display(g)
      if (theHibbs == true) {
        g.setPaint(Color.white)
        g.drawString("ROBO-HIBBS:", 200, 535)
        g.fill(new Rectangle2D.Double(200, 540, 400, 15))
        g.setPaint(Color.red)
        g.fill(new Rectangle2D.Double(202, 542.5, healthMeter, 10))
      }
      g.setPaint(Color.green)
      g.fill(new Rectangle2D.Double(375, 582.5, shots, 5))
      g.setPaint(Color.white)
      g.fill(new Rectangle2D.Double(375, 582.5, bodTime, 5))
      player.display(g)
      if (!powerBod) {
        g.setPaint(Color.green)
        g.fill(new Rectangle2D.Double(player.pos.x - 16, player.pos.y + 40, pHealth * 10, 2))
      }
      if (player2Bool) {
        player2.display(g)
        if (!powerBod) {
          g.setPaint(Color.cyan)
          g.fill(new Rectangle2D.Double(player2.pos.x - 16, player2.pos.y + 40, p2Health * 10, 2))
        }
      }

      if (!powerBod) {
        if (activeKeys.contains("left")) {
          if (flame) {
            g.drawImage(playerLeft, (player.pos.x - playerLeft.getWidth() / 2).toInt, (player.pos.y - playerLeft.getHeight() / 2).toInt, null)
          }
          if (!flame) {
            g.drawImage(playerLeft2, (player.pos.x - playerLeft2.getWidth() / 2).toInt, (player.pos.y - playerLeft2.getHeight() / 2).toInt, null)
          }
        }
        if (activeKeys.contains("right")) {
          if (flame) {
            g.drawImage(playerRight, (player.pos.x - playerRight.getWidth() / 2).toInt, (player.pos.y - playerRight.getHeight() / 2).toInt, null)
          }
          if (!flame) {
            g.drawImage(playerRight2, (player.pos.x - playerRight2.getWidth() / 2).toInt, (player.pos.y - playerRight2.getHeight() / 2).toInt, null)
          }
        }
        if (activeKeys.contains("up")) {
          if (flame) {
            g.drawImage(playerUp, (player.pos.x - playerUp.getWidth() / 2).toInt, (player.pos.y - playerUp.getHeight() / 2).toInt, null)
          }
          if (!flame) {
            g.drawImage(playerUp2, (player.pos.x - playerUp2.getWidth() / 2).toInt, (player.pos.y - playerUp2.getHeight() / 2).toInt, null)
          }
        }

        if (activeKeys.contains("left2")) {
          if (flame) {
            g.drawImage(playerLeft, (player2.pos.x - playerLeft.getWidth() / 2).toInt, (player2.pos.y - playerLeft.getHeight() / 2).toInt, null)
          }
          if (!flame) {
            g.drawImage(playerLeft2, (player2.pos.x - playerLeft2.getWidth() / 2).toInt, (player2.pos.y - playerLeft2.getHeight() / 2).toInt, null)
          }
        }
        if (activeKeys.contains("right2")) {
          if (flame) {
            g.drawImage(playerRight, (player2.pos.x - playerRight.getWidth() / 2).toInt, (player2.pos.y - playerRight.getHeight() / 2).toInt, null)
          }
          if (!flame) {
            g.drawImage(playerRight2, (player2.pos.x - playerRight2.getWidth() / 2).toInt, (player2.pos.y - playerRight2.getHeight() / 2).toInt, null)
          }
        }
        if (activeKeys.contains("up2")) {
          if (flame) {
            g.drawImage(playerUp, (player2.pos.x - playerUp.getWidth() / 2).toInt, (player2.pos.y - playerUp.getHeight() / 2).toInt, null)
          }
          if (!flame) {
            g.drawImage(playerUp2, (player2.pos.x - playerUp2.getWidth() / 2).toInt, (player2.pos.y - playerUp2.getHeight() / 2).toInt, null)
          }
        }
      }

      if (power) {
        powerUp.display(g)
      }

      if (power2) {
        invince.display(g)
      }

      enemies.display(g)
      if (theHibbs == true) {
        mhibbs.display(g)
      }

      for (i <- explosionBuff) {
        i.display(g)
      }

      for (i <- 0 to playerBullets.length - 1) {
        playerBullets(i).display(g)
      }

      if (enemyBullets.length != 0) {
        for (i <- 0 to enemyBullets.length - 1) {
          enemyBullets(i).display(g)
        }
      }

      if (shooting) {
        specShot.display(g)
      }

      if (shooting2) {
        specShot2.display(g)
      }

      g.setPaint(Color.white)
      if (coin) {
        g.drawString("PRESS 'F' TO RESTART", 660, 20)
      }
      g.drawString("LIVES: " + lives + " | SCORE: " + score + " | SCALA SHOT: " + specNum, 10, 590)

      if (title) {
        if (partyMode) {
          g.setPaint(stepColor)
        }
        if (!partyMode) {
          g.setPaint(Color.black)
        }
        g.fill(new Rectangle2D.Double(0, 0, size.width, size.height))
        back.display(g)
        g.drawImage(splashArt, 0, 0, null)
        g.setPaint(Color.white)
        if (coin) {
          g.drawString("INSERT COIN(S)", 350, 20)
        }
      }
    }
  }

  //Reads keyboard presses for movement and shooting
  listenTo(keys)
  reactions += {
    case kp: KeyPressed =>
      kp.key match {
        case Key.E => {
          smClip.stop
          bgClip.setFramePosition(0)
          bgClip.loop(Clip.LOOP_CONTINUOUSLY)
          title = false
        }
        case Key.T => {
          smClip.stop
          bgClip.setFramePosition(0)
          bgClip.loop(Clip.LOOP_CONTINUOUSLY)
          title = false
          if (player2Bool == false) {
            player2Bool = true
          } else player2Bool = false
        }
        case Key.P => {
          if (partyMode == false) {
            partyMode = true
          } else partyMode = false
        }
        case Key.Left => {
          activeKeys = activeKeys + ("left")
        }
        case Key.A => {
          activeKeys = activeKeys + ("left")
        }
        case Key.Right => {
          activeKeys = activeKeys + ("right")
        }
        case Key.D => {
          activeKeys = activeKeys + ("right")
        }
        case Key.Up => {
          activeKeys = activeKeys + ("up")
        }
        case Key.W => {
          activeKeys = activeKeys + ("up")
        }
        case Key.Down => {
          activeKeys = activeKeys + ("down")
        }
        case Key.Enter => {
          if (!powerBod) {
            if (!scalaShot && specNum > 0) {
              shots = 400
              specNum -= 1
              scalaShot = true
            }
          }
        }
        case Key.S => { activeKeys = activeKeys + ("down") }
        case Key.Space => {
          if (!powerBod) {
            activeKeys = activeKeys + ("space")
          }
        }
        case Key.Numpad4 => {
          activeKeys = activeKeys + ("left2")
        }
        case Key.Numpad6 => {
          activeKeys = activeKeys + ("right2")
        }
        case Key.Numpad5 => {
          activeKeys = activeKeys + ("down2")
        }
        case Key.Numpad8 => {
          activeKeys = activeKeys + ("up2")
        }
        case Key.Numpad7 => {
          if (!powerBod) {
            activeKeys = activeKeys + ("space2")
          }
        }
        case Key.Numpad9 => {
          if (!scalaShot && specNum > 0) {
            shots = 400
            specNum -= 1
            scalaShot = true
          }
        }
        case Key.F => {
          bgClip.stop
          smClip.setFramePosition(0)
          smClip.loop(Clip.LOOP_CONTINUOUSLY)
          rest = true
        }
        case Key.Q => System.exit(1)
        case _ => {}
      }

    case kr: KeyReleased =>
      kr.key match {
        case Key.Left => {
          activeKeys = activeKeys - ("left")
        }
        case Key.Right => {
          activeKeys = activeKeys - ("right")
        }
        case Key.Up => { activeKeys = activeKeys - ("up") }
        case Key.Down => { activeKeys = activeKeys - ("down") }
        case Key.Space => { (activeKeys = activeKeys - ("space")) }
        case Key.A => { activeKeys = activeKeys - ("left") }
        case Key.D => { activeKeys = activeKeys - ("right") }
        case Key.W => { activeKeys = activeKeys - ("up") }
        case Key.S => { activeKeys = activeKeys - ("down") }
        case Key.Numpad8 => { activeKeys = activeKeys - ("up2") }
        case Key.Numpad5 => { activeKeys = activeKeys - ("down2") }
        case Key.Numpad4 => { (activeKeys = activeKeys - ("left2")) }
        case Key.Numpad6 => { activeKeys = activeKeys - ("right2") }
        case Key.Numpad7 => { activeKeys = activeKeys - ("space2") }
        case _ => {}
      }
  }

  //Method which starts all game timers
  def startGame() {
    smClip.loop(Clip.LOOP_CONTINUOUSLY)
    mainTimer.start
    enemyMoveTimer.start
    pAnimator.start
    endCheck.start
    hibbsTime.start
    blastTimer.start
    restart.start
    coinTimer.start
  }
}