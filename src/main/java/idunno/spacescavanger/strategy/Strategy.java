package idunno.spacescavanger.strategy;

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
	
	protected double calculateAngle(Point first, Point second) {
        Point diff = second.substract(first);
        return Math.atan(diff.y() / diff.x());
    }

	//valami el van baszva lehet itt, néha szarul lő
	// amúgy olyankor lenne jó ezt használni amikor nem vagyunk messze és nincs a közelében sok meteor
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
