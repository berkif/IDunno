package idunno.spacescavanger;

import static idunno.spacescavanger.strategy.Strategy.OUR_NAME;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import javax.xml.bind.DatatypeConverter;

import idunno.spacescavanger.strategy.Strategy;

public class Main {
	public static void main(String[] args) throws IOException, DeploymentException {
		WebSocketContainer webSocket = ContainerProvider.getWebSocketContainer();
		ClientEndpointConfig.Configurator configurator = new ClientEndpointConfig.Configurator() {
			@Override
			public void beforeRequest(Map<String, List<String>> headers) {
				headers.put("Authorization", Arrays.asList(
					"Basic " + DatatypeConverter.printBase64Binary((OUR_NAME + ":ZkxIGkO1eqvchhj9apcY").getBytes())));
			}
		};
		ClientEndpointConfig config = ClientEndpointConfig.Builder.create()
				.configurator(configurator)
				.build();

		ClientEndpoint client = new ClientEndpoint();
		webSocket.connectToServer(client, config,
			URI.create("ws://172.16.0.180:8080/JavaChallenge2018/websocket"));
		System.in.read();
		client.stop();
	}
}
