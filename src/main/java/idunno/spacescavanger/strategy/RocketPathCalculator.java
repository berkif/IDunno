package idunno.spacescavanger.strategy;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import idunno.spacescavanger.coordgeom.Line;
import idunno.spacescavanger.dto.GameState;
import idunno.spacescavanger.dto.Rocket;

public class RocketPathCalculator {
    
    protected Map<Integer, Line> calculate(Optional<GameState> lastState, GameState currentState, int rocketRange) {
        List<Rocket> lastRockets = getRocketsFromLastState(lastState);
        Map<Integer, Line> rocketPaths = calculateNewPaths(lastState, currentState, rocketRange, lastRockets);
        mergeOldPaths(lastState, rocketPaths);
        removeJustExplodedRockets(lastState, currentState, rocketPaths);
        return rocketPaths;
    }

    private void removeJustExplodedRockets(Optional<GameState> lastState, GameState currentState, Map<Integer, Line> rocketPaths) {
        lastState.map(GameState::getRocketStates)
            .orElse(emptyList())
            .stream()
            .filter(rocketNotInCurrentState(currentState))
            .forEach(rocket -> rocketPaths.remove(rocket.getRocketID()));
    }

    private Predicate<? super Rocket> rocketNotInCurrentState(GameState currentState) {
        return rocket -> !currentState.getRocketStates().stream().anyMatch(rocket::idEquals);
    }

    private void mergeOldPaths(Optional<GameState> lastState, Map<Integer, Line> rocketPaths) {
        rocketPaths.putAll(lastState.map(GameState::getRocketPaths).orElse(Collections.emptyMap()));
    }

    private Map<Integer, Line> calculateNewPaths(Optional<GameState> lastState, GameState currentState, int rocketRange, List<Rocket> lastRockets) {
        return currentState.getRocketStates().stream()
            .filter(rocketExistedInLastState(lastRockets))
            .filter(pathAlreadyCalculated(lastState).negate())
            .map(toOldNewRocketPair(lastRockets))
            .collect(toMap(rocketId(), rocketPath(rocketRange)));
    }

    private Function<Entry<Rocket, Rocket>, Line> rocketPath(int rocketRange) {
        return entry -> new Line(
                entry.getKey().getPosition(),
                entry.getValue().getPosition(),
                rocketRange);
    }

    private Function<Entry<Rocket, Rocket>, Integer> rocketId() {
        return entry -> entry.getValue().getRocketID();
    }

    private Function<Rocket, Entry<Rocket, Rocket>> toOldNewRocketPair(List<Rocket> lastRockets) {
        return rocket -> entry(lastRockets.stream().filter(rocket::idEquals).findFirst().get(), rocket);
    }

    private Predicate<Rocket> pathAlreadyCalculated(Optional<GameState> lastState) {
        return rocket -> lastState.map(GameState::getRocketPaths).orElse(emptyMap()).containsKey(rocket.getRocketID());
    }

    private Predicate<Rocket> rocketExistedInLastState(List<Rocket> lastRockets) {
        return rocket -> lastRockets.stream().anyMatch(rocket::idEquals);
    }

    private List<Rocket> getRocketsFromLastState(Optional<GameState> lastState) {
        return lastState
                .map(GameState::getRocketStates)
                .orElse(emptyList());
    }

}
