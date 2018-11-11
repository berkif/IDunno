package idunno.spacescavanger.strategy;

import static idunno.spacescavanger.strategy.Comparators.compareByDistance;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import idunno.spacescavanger.dto.Meteorite;

public class CommonMethods {
	
	static List<Meteorite> getHighestPointsMeteorites(List<Meteorite> meteoriteStates) {
		List<Meteorite> result = new ArrayList<>();
		int max = 0;
		for (Meteorite meteorite : meteoriteStates) {
			if(meteorite.getMeteoriteRadius() > max) {
				max = meteorite.getMeteoriteRadius();
				result.clear();
				result.add(meteorite);
			} else if (meteorite.getMeteoriteRadius() == max) {
				result.add(meteorite);
			}
		}
		return result;		
	}
	
	static Optional<Position> getClosestMeteoritePos(List<Meteorite> meteoriteStates, Position position) {
		return meteoriteStates
				.stream()
				.map(Meteorite::getPosition)
				.min(compareByDistance(position));
	}
	

}
