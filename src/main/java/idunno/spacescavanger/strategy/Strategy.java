package idunno.spacescavanger.strategy;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Optional;

import idunno.spacescavanger.coordgeom.Point;
import idunno.spacescavanger.dto.Game;
import idunno.spacescavanger.dto.GameResponse;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Ship;

public abstract class Strategy {
    protected final Game game;
    private Optional<GameState> lastState;
    private final RocketPathCalculator rocketPathCalculator = new RocketPathCalculator();

    public Strategy(Game game) {
        this.game = game;
        lastState = Optional.empty();
    }

    public final GameResponse move(GameState gameState) {
        GameResponse response;
        gameState.setRocketPaths(rocketPathCalculator.calculate(lastState, gameState, game.getRocketRange()));
        if (lastState.isPresent())
            response = suggestMove(lastState.get(), gameState);
        else
            response = suggestFirstMove(gameState);
        setLastState(gameState);
        return response;
    }

    protected abstract GameResponse suggestFirstMove(GameState currentState);

    // nem tudom, hogy használható lesz e. Nincs semmi infó arról, hogy mi milyen
    // irányba halad,
    // az előző gamestateből viszont ki lehet számolni de nem tudm, hogy jó ötlet e.
    protected abstract GameResponse suggestMove(GameState lastState, GameState currentState);
    
    private void setLastState(GameState gameState) {
        lastState = Optional.of(gameState);
    }
//  public static void main(String[] args) {
//      System.out.println(calculateVelocity(Ship.builder().withShipX(10).withShipY(-1).build(), Ship.builder().withShipX(7).withShipY(2).build()));
//      System.out.println("...");
//      System.out.println(calculateTheta(new Point(1, 1), new Point(5, 1), new Point(0, -3)) * 180 / Math.PI);
//    }
    static Point calculateVelocity(Ship lastShipState, Ship currentShipState) {
        return currentShipState.getPosition().substract(lastShipState.getPosition());
    }
    protected static double calculateCosTheta(Point shooterPosition, Point targetPosition, Point targetVelocity) {
        Point targetShooterVector = shooterPosition.substract(targetPosition);
        System.out.println(targetShooterVector);
        double numerator = targetShooterVector.x() * targetVelocity.x() + targetShooterVector.y() * targetVelocity.y();
        double denominator = (sqrt(pow(targetShooterVector.x(), 2) + pow(targetShooterVector.y(), 2)))
                *
                (sqrt(pow(targetVelocity.x(), 2) + pow(targetVelocity.y(), 2)));
        System.out.println(numerator + "/" + denominator);
        return numerator / denominator;
    }
    //valami el van baszva lehet itt, néha szarul lő
    // amúgy olyankor lenne jó ezt használni amikor nem vagyunk messze és nincs a közelében sok meteor
    protected Optional<Point> calc(Ship target, Point shooterPosition, Point targetVelocity) {
        double cosTheta = calculateCosTheta(shooterPosition, target.getPosition(), targetVelocity);
        double targetSpeed = target.isUpgraded() ? game.getMovementSpeedMultiplier() * game.getShipMovementSpeed() : game.getShipMovementSpeed();
        double rocketSpeed = game.getRocketMovementSpeed();
        double a = pow(rocketSpeed, 2) - pow(targetSpeed, 2);
        double shooterTargetDistance = target.getPosition().distance(shooterPosition);
        double b = 2 * shooterTargetDistance * targetSpeed * cosTheta;
        double c = - pow(shooterTargetDistance, 2);
        double disc= b*b -(4*a*c);
        if (disc < 0) {
            System.out.println("We can't hit it");
            return Optional.empty();
        }
         double time1 = (-b + sqrt(pow(b, 2) * 4*a*c))/2*a;
         double time2 = (-b - sqrt(pow(b, 2) * 4*a*c))/2*a;
         double t = Math.min(time1, time2);
         double aimX=target.getPosition().x() + (targetVelocity.x()*t);
            double aimY=target.getPosition().y()+(targetVelocity.y()*t);
        return Optional.of(new Point(aimY, aimX));
    }
    protected Optional<Point> calculateShootAngle(Ship target, double targetMovingAngle, Point shooter) {
        double targetSpeed = target.isUpgraded() ? game.getMovementSpeedMultiplier() * game.getShipMovementSpeed() : game.getShipMovementSpeed();
        double rocketSpeed = game.getRocketMovementSpeed();
        Point   targetPos = target.getPosition();
        Point targetVelocity = new Point(targetSpeed * Math.sin(targetMovingAngle)  , targetSpeed * Math.cos(targetMovingAngle));
        Point diff = targetPos.substract(shooter);
        double a=(targetVelocity.x()*targetVelocity.x())+(targetVelocity.y()*targetVelocity.y())-(rocketSpeed*rocketSpeed);
        double b=2*(targetVelocity.x()*(diff.x()) + targetVelocity.y()*(diff.y()));
        double c= ((diff.x())*(diff.x())) + ((diff.y())*(diff.y()));
             
        double disc= b*b -(4*a*c);
        if (disc < 0) {
            System.out.println("We can't hit it");
        } else {
        double t1=(-1*b+Math.sqrt(disc))/(2*a);
        double t2=(-1*b-Math.sqrt(disc))/(2*a);
        double t= Math.max(t1,t2);  // ezen kéne imprvolni
        double aimX=targetPos.x() + (targetVelocity.x()*t);
        double aimY=targetPos.y()+(targetVelocity.y()*t);
        return Optional.of(new Point(aimY, aimX));
        }
        return Optional.empty();

        
        
        
        
    }
}
